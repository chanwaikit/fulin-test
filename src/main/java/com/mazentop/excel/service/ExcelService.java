package com.mazentop.excel.service;

import com.mztframework.data.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExcelService {

    Result importExcel(HttpServletRequest request, MultipartFile file) throws IOException, Exception;

    void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
