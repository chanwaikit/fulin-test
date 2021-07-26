package com.mazentop.excel.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.alibaba.fastjson.JSONArray;
import com.mazentop.entity.CliClienteleInfo;
import com.mazentop.entity.SysCountry;
import com.mazentop.excel.entity.CliClienteleeriveEntity;
import com.mazentop.excel.entity.UserEntity;
import com.mazentop.excel.service.ExcelService;
import com.mazentop.excel.service.SendEmail;
import com.mazentop.excel.util.RequestUtil;
import com.mazentop.modules.emp.dto.query.ExportCliClienteleQueryDto;
import com.mazentop.modules.emp.service.CliClienteleInfoService;
import com.mazentop.plugins.cache.LFUCache;
import com.mazentop.util.Helper;
import com.mztframework.data.Result;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Service("clientele-excel")
public class ClienteleExcelServiceImpl implements ExcelService {

    @Autowired
    SendEmail sendEmail;
    @Autowired
    CliClienteleInfoService cliClienteleInfoService;


    @Override
    public Result importExcel(HttpServletRequest request, MultipartFile file) throws Exception {
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);
        Map<String, Object> param = RequestUtil.getFromValue(request);
        Integer type = Integer.parseInt(param.get("type").toString());
        List<CliClienteleeriveEntity> list = ExcelImportUtil.importExcel(file.getInputStream(), CliClienteleeriveEntity.class, importParams);
        if (list.size() == 0 || Helper.isEmpty(list.get(0).getEmail())) {
            return Result.toast("文件格式有误！");
        } else {
            list.remove(0);
            if (list.size() == 0) {
                return Result.toast("文件格式有误！");
            }
        }
        List<CliClienteleInfo> cliClienteleInfoList = new ArrayList<>();
        List<CliClienteleInfo> cliClienteleUpdateList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        cache(list, cliClienteleInfoList, map, cliClienteleUpdateList);
        cliClienteleInfoService.doImportCustomers(cliClienteleInfoList, type, cliClienteleUpdateList);
        map.put("count", list.size());
        return Result.build(() -> map);
    }

    @Override
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = RequestUtil.getValue(request);
        if (Helper.isNotEmpty(map)) {
            String scopes = map.get("scope").toString();
            ExportCliClienteleQueryDto queryDto = new ExportCliClienteleQueryDto();
            if (scopes.equals("1")) {
                queryDto.setIds(Helper.toList(map.get("selections").toString(), String.class));
            } else if (scopes.equals("2")) {
                ExportCliClienteleQueryDto condition = JSONArray.parseObject(map.get("condition").toString(), ExportCliClienteleQueryDto.class);
                BeanUtils.copyProperties(condition, queryDto);
            }
            List<CliClienteleInfo> list = cliClienteleInfoService.exportCustomers(queryDto);
            List<UserEntity> entityList = new ArrayList<>();
            list.forEach(cliClienteleInfo -> {
                UserEntity clienteleeriveEntity = new UserEntity();
                BeanUtils.copyProperties(cliClienteleInfo, clienteleeriveEntity);
                if(cliClienteleInfo.getBuySum() != null) {
                    clienteleeriveEntity.setBuySum(Helper.transformF2Y(cliClienteleInfo.getBuySum()).toString());
                }
                if(StringUtils.isNotBlank(cliClienteleInfo.getCountry())) {
                    SysCountry country = SysCountry.me().setId(cliClienteleInfo.getCountry()).get();
                    if(!Objects.isNull(country)) {
                        clienteleeriveEntity.setCountry(country.getNameCn());
                    }
                }
                clienteleeriveEntity.setAddTime(Helper.timestampToDate(cliClienteleInfo.getAddTime(),Helper.DATE_PATTERN1));
                entityList.add(clienteleeriveEntity);
            });
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "顾客信息", ExcelType.XSSF), UserEntity.class, entityList);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            workbook.close();
            String title = "顾客导出信息 " + Helper.format(new Date(), Helper.DATE_PATTERN1);
            if(!Objects.isNull(map.get("mail"))) {
                sendEmail.sendEmail(title, map.get("mail").toString(), baos.toByteArray(), "顾客信息" + Helper.format(new Date(), Helper.DATE_PATTERN3) + ".xlsx");
            }
            response.setContentType("application/vnd.ms-excel");
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }

    }

    private void cache(List<CliClienteleeriveEntity> list, List<CliClienteleInfo> cliClienteleInfoList, Map<String, Object> map, List<CliClienteleInfo> cliClienteleUpdateList) {
        int errorNum = 0;
        String error = null;
        List<CliClienteleeriveEntity> errorCliClientele = new ArrayList<>();
        for (CliClienteleeriveEntity cliClienteleeriveEntity : list) {
            boolean isv = false;
            CliClienteleInfo cliClienteleInfo = new CliClienteleInfo();
            if (Objects.isNull(cliClienteleeriveEntity.getEmail())) {
                error = " (邮箱)列不能为空";
                errorNum++;
                setError(errorCliClientele, error, cliClienteleeriveEntity);
                continue;
            } else {
                CliClienteleInfo info = CliClienteleInfo.me().setEmail(cliClienteleeriveEntity.getEmail()).get();
                if (Helper.isEmpty(info)) {
                    if (cacheEmail(cliClienteleeriveEntity.getEmail())) {
                        cliClienteleInfo = CliClienteleInfo.me().setEmail(cliClienteleeriveEntity.getEmail());
                    } else {
                        error = " (邮箱)列不能格式不正确";
                        errorNum++;
                        setError(errorCliClientele, error, cliClienteleeriveEntity);
                        continue;
                    }
                } else {
                    isv = true;
                    cliClienteleInfo.setId(info.getId());
                }
            }
            if (Objects.isNull(cliClienteleeriveEntity.getClientSurname())) {
                error = " (姓氏)列不能为空";
                errorNum++;
                setError(errorCliClientele, error, cliClienteleeriveEntity);
                continue;
            } else {
                cliClienteleInfo.setClientSurname(cliClienteleeriveEntity.getClientSurname());
            }
            if (Objects.isNull(cliClienteleeriveEntity.getClientName())) {
                error = " (名字)列不能为空";
                errorNum++;
                setError(errorCliClientele, error, cliClienteleeriveEntity);
                continue;
            } else {
                cliClienteleInfo.setClientName(cliClienteleeriveEntity.getClientName());
            }
            if (isv) {
                if (Helper.isNotEmpty(cliClienteleeriveEntity.getPassword())) {
                    cliClienteleInfo.setPassword(cliClienteleeriveEntity.getPassword());
                }
            } else {
                if (Helper.isNotEmpty(cliClienteleeriveEntity.getPassword())) {
                    cliClienteleInfo.setPassword(cliClienteleeriveEntity.getPassword());
                } else {
                    cliClienteleInfo.setPassword("123456");
                }
            }
            if (Helper.isNotEmpty(cliClienteleeriveEntity.getPhone())) {
                cliClienteleInfo.setPhone(cliClienteleeriveEntity.getPhone());
            }
            if (Helper.isNotEmpty(cliClienteleeriveEntity.getCountry())) {
                cliClienteleInfo.setCountry(cliClienteleeriveEntity.getCountry());
            }
            if (Helper.isNotEmpty(cliClienteleeriveEntity.getProvince())) {
                cliClienteleInfo.setProvince(cliClienteleeriveEntity.getProvince());
            }
            if (isv) {
                cliClienteleUpdateList.add(cliClienteleInfo);
            } else {
                cliClienteleInfoList.add(cliClienteleInfo);
            }

        }
        if (errorCliClientele.size() > 0) {
            String key = RandomStringUtils.randomAlphanumeric(20);
            LFUCache.put(key, Helper.toJson(errorCliClientele));
            map.put("errorKey", key);
        }
        map.put("error", errorNum);
        map.put("success", cliClienteleInfoList.size());

    }

    private void setError(List<CliClienteleeriveEntity> errorCliClientele, String error, CliClienteleeriveEntity dto) {
        dto.setError(error);
        errorCliClientele.add(dto);
    }

    private boolean cacheEmail(String email) {
        String pattern = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        return Pattern.matches(pattern, email);
    }
}
