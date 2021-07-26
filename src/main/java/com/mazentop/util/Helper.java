package com.mazentop.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mazentop.model.Constant;
import com.mazentop.modules.web.constants.CacheConstant;
import com.mazentop.plugins.cache.LFUCache;
import com.mztframework.commons.Utils;
import com.mztframework.commons.WebUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Helper extends Utils {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Helper.class);

    private static final Pattern MOBILE_NUM = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[0-9]|18[0-9]|14[57])[0-9]{8}$");

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$");

    private static final Random RANDOM = new Random();
    private static final char[] abcChars = new char[52];
    private static int[] baseInts = { 8, 89, 899, 8999, 89999, 899999, 8999999, 89999999, 899999999 };
    private static int[] nextInts = { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000 };


    private static String tempPreBlock = "%%%HTMLCOMPRESS~PRE&&&";
    private static String tempTextAreaBlock = "%%%HTMLCOMPRESS~TEXTAREA&&&";
    private static String tempScriptBlock = "%%%HTMLCOMPRESS~SCRIPT&&&";
    private static String tempStyleBlock = "%%%HTMLCOMPRESS~STYLE&&&";
    private static String tempJspBlock = "%%%HTMLCOMPRESS~JSP&&&";

    private static Pattern commentPattern = Pattern.compile("<!--\\s*[^\\[].*?-->", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private static Pattern itsPattern = Pattern.compile(">\\s+?<", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private static Pattern prePattern = Pattern.compile("<pre[^>]*?>.*?</pre>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private static Pattern taPattern = Pattern.compile("<textarea[^>]*?>.*?</textarea>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private static Pattern jspPattern = Pattern.compile("<%([^-@][\\w\\W]*?)%>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    // <script></script>
    private static Pattern scriptPattern = Pattern.compile("(?:<script\\s*>|<script type=['\"]text/javascript['\"]\\s*>)(.*?)</script>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private static Pattern stylePattern = Pattern.compile("<style[^>()]*?>(.+)</style>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    // 单行注释，
    private static Pattern signleCommentPattern = Pattern.compile("//.*");
    // 字符串匹配
    private static Pattern stringPattern = Pattern.compile("(\"[^\"\\n]*?\"|'[^'\\n]*?')");
    // trim去空格和换行符
    private static Pattern trimPattern = Pattern.compile("\\n\\s*",Pattern.MULTILINE);
    private static Pattern trimPattern2 = Pattern.compile("\\s*\\r",Pattern.MULTILINE);
    // 多行注释
    private static Pattern multiCommentPattern = Pattern.compile("/\\*.*?\\*/", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    private static String tempSingleCommentBlock = "%%%HTMLCOMPRESS~SINGLECOMMENT&&&";  // //占位符
    private static String tempMulitCommentBlock1 = "%%%HTMLCOMPRESS~MULITCOMMENT1&&&";  // /*占位符
    private static String tempMulitCommentBlock2 = "%%%HTMLCOMPRESS~MULITCOMMENT2&&&";  // */占位符

    public static Date date = null;

    public static DateFormat dateFormat = null;

    public static Calendar calendar = null;

    public static final String DATE_PATTERN1 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN2 = "yyyyMMdd";
    public static final String DATE_PATTERN3 = "yyyy-MM-dd";
    public static final String DATE_PATTERN4 = "HH:mm";
    public static final String DATE_PATTERN5 = "yyyy-MM-dd hh:mm";
    public static final String DATE_PATTERN6 = "yyyy-MM-dd HH";

    private static Gson gson = null;


    static{
        gson  = new Gson();//todo yyyy-MM-dd HH:mm:ss
    }

    /**
     * 验证邮箱
     * 1.长度不限，
     * 2.可以使用英文（包括大小写）、数字、点号、下划线、减号，
     * 3.首字母必须是字母或数字
     * 4.不能以字符"-"开头和结尾
     * 5..com和.cn可以互换位置
     *
     * @param email
     * @return
     */
    public static boolean isEmailNO(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidMobileNum(String mobile) {
        if (notEmptyNum(mobile)) {
            mobile = mobile.trim();
        }
        Matcher matcher = MOBILE_NUM.matcher(mobile);
        return matcher.matches();
    }

    public static boolean notEmptyNum(String str) {
        boolean b = false;
        if (null != str && !"".equals(str.trim()) && StringUtils.isNumeric(str)) {
            b = true;
        }
        return b;
    }

    public static String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }

    public static int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }

    /**
     * 手机号 中间几位变 *号
     *
     * @param photo
     * @return
     */
    public static String dhhmreplaceByXX(String photo) {
        if (isBlank(photo)) {
            return null;
        } else {
            char[] strs = photo.toCharArray();

            for (int i = 3; i < 8; ++i) {
                strs[i] = '*';
            }

            return new String(strs);
        }
    }

    public static BigDecimal transformF2Y(Object obj) {
        return transformF2Y(toCast(obj, Long.class));
    }

    public static String replaceSign(String string) {
        string = string.replaceAll("&amp;", "&");
        string = string.replaceAll("&lt;", "<");
        string = string.replaceAll("&gt;", ">");
        string = string.replaceAll("&nbsp;", " ");
        string = string.replaceAll("&quot;", "\"");
        string = string.replaceAll("&#039;", "\\");
        return string;
    }

    /**
     * signV2签名内容
     *
     * @param parameters
     * @param serviceUrl
     * @return
     * @throws URISyntaxException
     */
    private static String calculateStringToSignV2(
            Map<String, String> parameters, String serviceUrl)
            throws URISyntaxException {
        URI endpoint = new URI(serviceUrl.toLowerCase());
        StringBuilder data = new StringBuilder();
        data.append("POST\n");
        data.append(endpoint.getHost());
        data.append("\n/Orders/2013-09-01");
        data.append("\n");
        return sortParams(data, parameters);

    }

    /***
     * signV2签名方式
     * @param data
     * @param secretKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IllegalStateException
     * @throws UnsupportedEncodingException
     */
    private static String signV2(String data, String secretKey)
            throws NoSuchAlgorithmException, IllegalStateException,
            UnsupportedEncodingException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secretKey.getBytes("UTF-8"),
                "HmacSHA256"));
        byte[] signature = mac.doFinal(data.getBytes("UTF-8"));
        String signatureBase64 = new String(Base64.encodeBase64(signature),
                "UTF-8");
        return new String(signatureBase64);
    }

    /**
     * 对传递参数转换
     *
     * @param data
     * @param parameters
     * @return
     */
    private static String sortParams(StringBuilder data, Map<String, String> parameters) {
        Map<String, String> sorted = new TreeMap<>();
        sorted.putAll(parameters);

        Iterator<Map.Entry<String, String>> pairs =
                sorted.entrySet().iterator();
        while (pairs.hasNext()) {
            Map.Entry<String, String> pair = pairs.next();
            if (pair.getValue() != null) {
                data.append(pair.getKey() + "=" + pair.getValue());
            } else {
                data.append(pair.getKey() + "=");
            }
            if (pairs.hasNext()) {
                data.append("&");
            }
        }
        return data.toString();
    }


    public static String originalProductUrl(String url) {
        if (!url.startsWith("https://") || !url.startsWith("http://")) {
            url = "https:" + url;
        }
        return url;
    }

    public static Long transformY2F(BigDecimal bigDecimal) {
        if (Objects.isNull(bigDecimal)) {
            return 0L;
        }
        return transformY2F(bigDecimal.doubleValue());
    }

    public static HttpServletResponse getHttpServletResponse() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isEnglish(String charaString) {
        return charaString.matches("^[a-zA-Z]*");
    }


    public static List<Map<String, Long>> rangeToList(int offset) {
        List<DateTime> list = cn.hutool.core.date.DateUtil.rangeToList(
                cn.hutool.core.date.DateUtil.offset(new Date(), DateField.DAY_OF_MONTH, offset), cn.hutool.core.date.DateUtil.yesterday(), DateField.DAY_OF_MONTH);
        return list.stream().map(dateTime -> {
            Map<String, Long> map = new HashMap<>(2);
            map.put("beginOfTimes", cn.hutool.core.date.DateUtil.beginOfDay(dateTime).getTime() / 1000);
            map.put("endOfTimes", cn.hutool.core.date.DateUtil.endOfDay(dateTime).getTime() / 1000);
            return map;
        }).collect(Collectors.toList());
    }

    public static Map<String, Long> beginTimeAndEndTime(int beginDay, int endDay) {
        DateTime begin = cn.hutool.core.date.DateUtil.offset(new Date(), DateField.DAY_OF_MONTH, beginDay);
        DateTime end = cn.hutool.core.date.DateUtil.offset(new Date(), DateField.DAY_OF_MONTH, endDay);
        Date beginDate = cn.hutool.core.date.DateUtil.beginOfDay(begin);
        Date endDate = DateUtil.endOfDay(end);
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("beginDateTime", beginDate.getTime() / 1000);
        resultMap.put("endDateTime", endDate.getTime() / 1000);
        return resultMap;
    }


    /**
     * 根据12位编码生成EAN13码
     *
     * @param barcode
     * @return
     */
    public static String generateEAN13(String barcode) {
        int s1 = 0;
        int s2 = 0;
        for (int i = 1; i <= 12; i++) {
            if (i % 2 == 1) {
                //奇数
                s1 = s1 + Convert.toInt(barcode.substring(i - 1, i));
            } else {
                //偶数
                s2 = s2 + Convert.toInt(barcode.substring(i - 1, i));
            }
        }
        s2 = s2 * 3;
        int c = 10 - (s1 + s2) % 10;
        int s = c == 10 ? 0 : c;
        return barcode + s;
    }

    /**
     * 根据11位编码生成UPC12码
     *
     * @param barcode
     * @return
     */
    public static String generateUPF12(String barcode) {
        int s1 = 0;
        int s2 = 0;
        for (int i = 1; i <= 11; i++) {
            if (i % 2 == 1) {
                //奇数
                s1 = s1 + Convert.toInt(barcode.substring(i - 1, i));
            } else {
                //偶数
                s2 = s2 + Convert.toInt(barcode.substring(i - 1, i));
            }
        }
        s1 = s1 * 3;
        int c = 10 - (s1 + s2) % 10;
        int s = c == 10 ? 0 : c;
        return barcode + s;
    }

    public static String escapeStr(String string) {
        string = string.replaceAll("&", "&amp;");
        string = string.replaceAll("<", "&lt;");
        string = string.replaceAll(">", "&gt;");
        string = string.replaceAll("\\\\", "&quot;");
        string = string.replaceAll("\\\\\\\\", "&#039;");
        return string;
    }

    public static XMLGregorianCalendar newXMLGregorianCalendar(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        if (Objects.isNull(date)) {
            Date date1 = new Date();
            cal.setTime(date1);
        } else {
            cal.setTime(date);
        }
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gc;
    }

    public static Long convertAmazonToGMT8Date(String dateStr) {
        //输入的被转化的时间格式
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        Date date1 = null;
        try {
            date1 = dff.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1.getTime();
    }


    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址。
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串
     *
     * @param request
     * @return ip
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        return ip;
    }

    public static LocalDateTime timestampToLocalDateTime(Long timeStamp) {
        if (null == timeStamp) {
            return null;
        }
        return LocalDateTime.ofEpochSecond(timeStamp, 0, ZoneOffset.ofHours(8));
    }

    public static Long localDateTimeToTimestamp(LocalDateTime LocalDateTime) {
        return LocalDateTime.toEpochSecond(ZoneOffset.of("+8"));
    }

    public static String timeStampFormat(Long timeStamp) {
        if (null == timeStamp) {
            return null;
        }
        return LocalDateTime.ofEpochSecond(timeStamp, 0, ZoneOffset.ofHours(8))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String getExchangeId(String exchange) {
        return getExchangeId(WebUtil.getHttpServletRequest(), exchange);
    }

    public static String getExchangeId(HttpServletRequest request, String exchange) {
        String values = null;
        try {
            Cookie[] cookies = request.getCookies();
            Cookie exchangeCookie = null;
            //cookie
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (exchange.equals(cookie.getName())) {
                        exchangeCookie = cookie;
                    }
                }
            }
            if (exchangeCookie != null) {
                values = URLDecoder.decode(exchangeCookie.getValue(), "utf-8");
                if (values != null && !"".equals(values)) {
                    if (values.contains("-")) {
                        return values.substring(0, values.indexOf("-"));
                    } else {
                        return values;
                    }
                }

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getExchangeTuc(HttpServletRequest request, String exchange) {
        String values = null;
        try {
            Cookie[] cookies = request.getCookies();
            Cookie exchange_cookie = null;
            //cookie
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (exchange.equals(cookie.getName())) {
                        exchange_cookie = cookie;
                    }
                }
            }
            if (exchange_cookie != null) {
                values = URLDecoder.decode(exchange_cookie.getValue(), "utf-8");
                if (values != null && !"".equals(values)) {
                    if (values.indexOf("-") > -1) {
                        values = values.substring(values.indexOf("-") + 1);
                        return values;
                    } else {
                        return values;
                    }
                }

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Cookie getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie ratio_cookie = null;
        //获取 cookie
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (CacheConstant.RATIO.equals(cookie.getName())) {
                    ratio_cookie = cookie;
                }
            }
        }
        return ratio_cookie;
    }

    public static void setDefaultCurrency(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie_2st;
        try {
            if (Helper.getCookie(request) == null) {
                // 默认币种

                cookie_2st = new Cookie(Constant.CURRENT_COUNTRY, URLEncoder.encode("US", "utf-8"));

                //设置在该项目下都可以访问该cookie
                cookie_2st.setPath("/");
                cookie_2st.setMaxAge(60 * 15);
                //添加cookie
                response.addCookie(cookie_2st);
            } else {
                cookie_2st = Helper.getCookie(request);
                //设置在该项目下都可以访问该cookie
                cookie_2st.setPath("/");
                cookie_2st.setMaxAge(60 * 15);
                cookie_2st.setValue("US");
                //添加cookie
                response.addCookie(cookie_2st);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param totalNum 传入的数值
     * @param count  分配的集合数量
     * @return 返回集合
     */
    public static List<Integer> random(int totalNum, int count) {
        List<Integer> redList = new ArrayList<>();
        int totalMoney = (totalNum);
        Random rand = new Random();
        int leftMoney = totalMoney;
        int leftCount = count;
        for (int i = 0; i < count - 1; i++) {
            int money_ = 0;
            if (leftMoney > 0) {
                if ((leftMoney / leftCount * 2) < 1) {
                    money_ = leftMoney;
                } else {
                    money_ = 1 + rand.nextInt(leftMoney / leftCount * 2);
                }

            } else {
                money_ = 0;
            }
            redList.add(money_);
            if (money_ > 0) {
                leftMoney -= money_;
                leftCount--;
            }

        }
        redList.add(leftMoney);
        return redList;
    }
    public static String getBaseURl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        StringBuffer url =  new StringBuffer();
        url.append(scheme).append("://").append(serverName);
        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }
        url.append(contextPath);
        if(url.toString().endsWith("/")){
            url.append("/");
        }
        return url.toString();
    }

    public static boolean isEmpty(String string) {
        return string == null || string.length() < 1;
    }

    public static boolean isEmpty(Object object) {
        return null == object || object.toString().length() < 1;
    }

    public static boolean isNotEmpty(String string) {
        return string != null && string.length() > 0;
    }

    public static boolean isNotEmpty(Object object) {
        return object != null && object.toString().length() > 0;
    }

    public static String unite(Object... values) {
        if (values == null || values.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (isNotEmpty(values[i])) {
                sb.append(values[i]);
            }
        }
        return sb.toString();
    }

    public static int StringMayNullToInt(String tmp, int defaultVal) {
        if (null == tmp || "".equals(tmp)) {
            return defaultVal;
        }
        return Integer.parseInt(tmp);
    }

    public static String filterNullToEmpty(String content) {
        if (content == null) {
            return "";
        }
        return content;
    }

    public static String mergeStr(String... args) {
        StringBuilder sb = new StringBuilder();

        for (String arg : args) {
            sb.append(arg);
        }

        return sb.toString();
    }

    public static String getStr(Object o) {
        if (o == null) {
            return "";
        }
        return o.toString();
    }

    public static String linkStrs(String str1, String str2, String link) {
        String strA = getStr(str1);
        String strB = getStr(str2);
        String linker = getStr(link);
        if ("".equals(strA) || "".equals(strB)) {
            return mergeStr(str1, str2);
        }
        return mergeStr(str1, linker, str2);
    }

    /**
     * "file:/home/com/ripple" -> "/home/com/ripple"
     * "jar:file:/home/com/ripple.jar!test/test" -> "/home/com/ripple.jar"
     */
    public static String getRootPath(URL url) {
        String fileUrl = url.getFile();
        int pos = fileUrl.indexOf('!');

        if (-1 == pos) {
            return fileUrl;
        }

        return fileUrl.substring(5, pos);
    }

    /**
     * "com.ripple.hookup" -> "com/ripple/hookup"
     *
     * @param name
     * @return
     */
    public static String dotToSplash(String name) {
        return name.replaceAll("\\.", "/");
    }

    /**
     * "Hookup.class" -> "Hookup"
     */
    public static String trimExtension(String name) {
        int pos = name.indexOf('.');
        if (-1 != pos) {
            return name.substring(0, pos);
        }

        return name;
    }

    public static boolean passwordIsOk(String password) {
        if (Helper.isEmpty(password)) {
            return false;
        }
        if (password.length() < 6) {
            return false;
        }
        char[] chars = password.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] < 33 || chars[i] > 126) {
                return false;
            }
        }
        return true;

    }

    /**
     * /application/home -> /home
     *
     * @param uri
     * @return
     */
    public static String trimURI(String uri) {
        String trimmed = uri.substring(1);
        int splashIndex = trimmed.indexOf('/');

        return trimmed.substring(splashIndex);
    }

    public static String getSerialsNo(String type, String code) {
        return unite(type, code, com.mazentop.util.Helper.format(new Date(), "yyddss"), String.format("%6d", Helper.randInt( 6)).replace(" ", "0"));
    }

    public static String getNoZero(String amount) {
        char[] chars = amount.toCharArray();
        StringBuilder sb = new StringBuilder();
        boolean isOk = true;
        for (int i = chars.length - 1; i >= 0; i--) {
            if (isOk && chars[i] == '0') {
                continue;
            }
            isOk = false;
            sb.insert(0, chars[i]);
        }
        if (sb.lastIndexOf(".") == sb.length() - 1) {
            return sb.substring(0, sb.length() - 1);
        } else {
            return sb.toString();
        }
    }

    /**
     * IP转成整型
     * @param ip
     * @return
     */
    public static Long ipToLong(String ip)
    {
        Long num = 0L;
        if (ip == null){
            return num;
        }

        try{
            ip = ip.replaceAll("[^0-9\\.]", ""); //去除字符串前的空字符
            String[] ips = ip.split("\\.");
            if (ips.length == 4){
                num = Long.parseLong(ips[0], 10) * 256L * 256L * 256L + Long.parseLong(ips[1], 10) * 256L * 256L + Long.parseLong(ips[2], 10) * 256L + Long.parseLong(ips[3], 10);
                num = num >>> 0;
            }
        }catch(NullPointerException ex){
            System.out.println(ip);
        }

        return num;
    }

    public static String removeNoNum(String str){
        StringBuilder temp = new StringBuilder();
        for(int i=0;i<str.length();i++){
            int chr=str.charAt(i);
            if(chr>=48 && chr<=57){
                temp.append((char)chr);
            }
        }
        return temp.toString();
    }

    static {
        for (int i = 0; i < abcChars.length; i++) {
            int index = i + 65;
            if (index > 90) {
                index = index + 6;
            }
            abcChars[i] = (char) index;
        }
    }

    public static int getInt(int max) {
        return RANDOM.nextInt(max);
    }

    public static String getRanText(int len) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < len; i++) {
            text.append(RANDOM.nextInt(10));
        }
        return text.toString();
    }

    public static String getText(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(abcChars[RANDOM.nextInt(abcChars.length)]);
        }
        return sb.toString();
    }

    public static <T> T getListItem(List<T> list) {
        T item = list.get(RANDOM.nextInt(list.size()));
        return item;
    }

    public static <T> T getArrayValue(T[] array) {
        T value = array[RANDOM.nextInt(array.length)];
        return value;
    }

    /**
     * 随机生成数字,最多生成9位
     *
     * @param bits 位数
     */
    public static int randInt(int bits) {
        if (bits < 1) {
            bits = 1;
        }
        if (bits > 9) {
            bits = 9;
        }
        int sub = bits - 1;
        return RANDOM.nextInt(baseInts[sub]) + nextInts[sub];
    }

    public static String getRandomText(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(randInt(1));
            sb.append(abcChars[RANDOM.nextInt(abcChars.length)]);
        }
        return sb.toString();
    }

    /**
     * json 转换 map 只转换第一层
     *
     * @Title json2SimpleMap
     * @param json
     * @return
     * @author zhaoqt
     * @date Jul 27, 2015 5:28:06 AM
     * @return Map<String,String>
     */
    public static Map<String, String> json2SimpleMap(String json){
        JSONObject object = JSONObject.parseObject(json);
        Map<String, String> map = new HashMap<>();
        Set<String> keys = object.keySet();
        for(String key : keys) {
            map.put(key, Utils.toCast(object.get(key), String.class));
        }
        return map;
    }

    /**
     *  将JSON格式的字符串转化为List对象，只进行第一层次的转换
     *
     * @Title json2SimpleList
     * @param json
     * @return
     * @author zhaoqt
     * @date Jul 27, 2015 5:32:16 AM
     * @return List<Map>
     */
    public static List<Map<String, String>> json2SimpleList(String json){
        JSONArray jArray = JSONArray.parseArray(json);
        List<Map<String, String>> list = new LinkedList<>();
        for (int i = 0; i < jArray.size(); i++) {
            list.add(json2SimpleMap(jArray.getString(i)));
        }
        return list;
    }
    public static String toJson(Object obj){
        return gson.toJson(obj);
    }


    public static <T> List<T> toList(String json,Class<T> clz){
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        List<T> list  = new ArrayList<>();
        for(final JsonElement elem : array){
            list.add(gson.fromJson(elem, clz));
        }
        return list;
    }

    public static <E> void forEach(
            Iterable<? extends E> elements, BiConsumer<Integer, ? super E> action) {
        Objects.requireNonNull(elements);
        Objects.requireNonNull(action);
        int index = 0;
        for (E element : elements) {
            action.accept(index++, element);
        }
    }

    /**
     * list 对象 去重
     *
     *      重复属性若为自定义类型，需重写 hascode
     *
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor, int initialCapacity) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>(initialCapacity);
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        return distinctByKey(keyExtractor, 20);
    }

    public static List<List<String>> descartes(List<List<String>> dimvalue, List<List<String>> result, int layer, List<String> curList) {
        if (layer < dimvalue.size() - 1) {
            if (dimvalue.get(layer).size() == 0) {
                descartes(dimvalue, result, layer + 1, curList);
            } else {
                for (int i = 0; i < dimvalue.get(layer).size(); i++) {
                    List<String> list = new ArrayList<String>(curList);
                    list.add(dimvalue.get(layer).get(i));
                    descartes(dimvalue, result, layer + 1, list);
                }
            }
        } else if (layer == dimvalue.size() - 1) {
            if (dimvalue.get(layer).size() == 0) {
                result.add(curList);
            } else {
                for (int i = 0; i < dimvalue.get(layer).size(); i++) {
                    List<String> list = new ArrayList<String>(curList);
                    list.add(dimvalue.get(layer).get(i));
                    result.add(list);
                }
            }
        }
        return result;
    }

    public static String compress(String html) {
        if(html == null || html.length() == 0) {
            return html;
        }

        List<String> preBlocks = new ArrayList<String>();
        List<String> taBlocks = new ArrayList<String>();
        List<String> scriptBlocks = new ArrayList<String>();
        List<String> styleBlocks = new ArrayList<String>();
        List<String> jspBlocks = new ArrayList<String>();

        String result = html;

        //preserve inline java code
        Matcher jspMatcher = jspPattern.matcher(result);
        while(jspMatcher.find()) {
            jspBlocks.add(jspMatcher.group(0));
        }
        result = jspMatcher.replaceAll(tempJspBlock);

        //preserve PRE tags
        Matcher preMatcher = prePattern.matcher(result);
        while(preMatcher.find()) {
            preBlocks.add(preMatcher.group(0));
        }
        result = preMatcher.replaceAll(tempPreBlock);

        //preserve TEXTAREA tags
        Matcher taMatcher = taPattern.matcher(result);
        while(taMatcher.find()) {
            taBlocks.add(taMatcher.group(0));
        }
        result = taMatcher.replaceAll(tempTextAreaBlock);

        //preserve SCRIPT tags
        Matcher scriptMatcher = scriptPattern.matcher(result);
        while(scriptMatcher.find()) {
            scriptBlocks.add(scriptMatcher.group(0));
        }
        result = scriptMatcher.replaceAll(tempScriptBlock);

        // don't process inline css
        Matcher styleMatcher = stylePattern.matcher(result);
        while(styleMatcher.find()) {
            styleBlocks.add(styleMatcher.group(0));
        }
        result = styleMatcher.replaceAll(tempStyleBlock);

        //process pure html
        result = processHtml(result);

        //process preserved blocks
        result = processPreBlocks(result, preBlocks);
        result = processTextareaBlocks(result, taBlocks);
        result = processScriptBlocks(result, scriptBlocks);
        result = processStyleBlocks(result, styleBlocks);
        result = processJspBlocks(result, jspBlocks);

        //preBlocks = taBlocks = scriptBlocks = styleBlocks = jspBlocks = null;

        return result.trim();
    }

    private static String processHtml(String html) {
        String result = html;

        //remove comments
//		if(removeComments) {
        result = commentPattern.matcher(result).replaceAll("");
//		}

        //remove inter-tag spaces
//		if(removeIntertagSpaces) {
        result = itsPattern.matcher(result).replaceAll("><");
//		}

        //remove multi whitespace characters
//		if(removeMultiSpaces) {
        result = result.replaceAll("\\s{2,}"," ");
//		}

        return result;
    }

    private static String processJspBlocks(String html, List<String> blocks){
        String result = html;
        for(int i = 0; i < blocks.size(); i++) {
            blocks.set(i, compressJsp(blocks.get(i)));
        }
        //put preserved blocks back
        while(result.contains(tempJspBlock)) {
            result = result.replaceFirst(tempJspBlock, Matcher.quoteReplacement(blocks.remove(0)));
        }

        return result;
    }
    private static String processPreBlocks(String html, List<String> blocks) {
        String result = html;

        //put preserved blocks back
        while(result.contains(tempPreBlock)) {
            result = result.replaceFirst(tempPreBlock, Matcher.quoteReplacement(blocks.remove(0)));
        }

        return result;
    }

    private static String processTextareaBlocks(String html, List<String> blocks) {
        String result = html;

        //put preserved blocks back
        while(result.contains(tempTextAreaBlock)) {
            result = result.replaceFirst(tempTextAreaBlock, Matcher.quoteReplacement(blocks.remove(0)));
        }

        return result;
    }

    private static String processScriptBlocks(String html, List<String> blocks) {
        String result = html;

//		if(compressJavaScript) {
        for(int i = 0; i < blocks.size(); i++) {
            blocks.set(i, compressJavaScript(blocks.get(i)));
        }
//		}

        //put preserved blocks back
        while(result.contains(tempScriptBlock)) {
            result = result.replaceFirst(tempScriptBlock, Matcher.quoteReplacement(blocks.remove(0)));
        }

        return result;
    }

    private static String processStyleBlocks(String html, List<String> blocks) {
        String result = html;

//		if(compressCss) {
        for(int i = 0; i < blocks.size(); i++) {
            blocks.set(i, compressCssStyles(blocks.get(i)));
        }
//		}

        //put preserved blocks back
        while(result.contains(tempStyleBlock)) {
            result = result.replaceFirst(tempStyleBlock, Matcher.quoteReplacement(blocks.remove(0)));
        }

        return result;
    }

    private static String compressJsp(String source)  {
        //check if block is not empty
        Matcher jspMatcher = jspPattern.matcher(source);
        if(jspMatcher.find()) {
            String result = compressJspJs(jspMatcher.group(1));
            return (new StringBuilder(source.substring(0, jspMatcher.start(1))).append(result).append(source.substring(jspMatcher.end(1)))).toString();
        } else {
            return source;
        }
    }
    private static String compressJavaScript(String source)  {
        //check if block is not empty
        Matcher scriptMatcher = scriptPattern.matcher(source);
        if(scriptMatcher.find()) {
            String result = compressJspJs(scriptMatcher.group(1));
            return (new StringBuilder(source.substring(0, scriptMatcher.start(1))).append(result).append(source.substring(scriptMatcher.end(1)))).toString();
        } else {
            return source;
        }
    }

    private static String compressCssStyles(String source)  {
        //check if block is not empty
        Matcher styleMatcher = stylePattern.matcher(source);
        if(styleMatcher.find()) {
            // 去掉注释，换行
            String result= multiCommentPattern.matcher(styleMatcher.group(1)).replaceAll("");
            result = trimPattern.matcher(result).replaceAll("");
            result = trimPattern2.matcher(result).replaceAll("");
            return (new StringBuilder(source.substring(0, styleMatcher.start(1))).append(result).append(source.substring(styleMatcher.end(1)))).toString();
        } else {
            return source;
        }
    }

    private static String compressJspJs(String source){
        String result = source;
        // 因注释符合有可能出现在字符串中，所以要先把字符串中的特殊符好去掉
        Matcher stringMatcher = stringPattern.matcher(result);
        while(stringMatcher.find()){
            String tmpStr = stringMatcher.group(0);

            if(tmpStr.indexOf("//") != -1 || tmpStr.indexOf("/*") != -1 || tmpStr.indexOf("*/") != -1){
                String blockStr = tmpStr.replaceAll("//", tempSingleCommentBlock).replaceAll("/\\*", tempMulitCommentBlock1)
                        .replaceAll("\\*/", tempMulitCommentBlock2);
                result = result.replace(tmpStr, blockStr);
            }
        }
        // 去掉注释
        result = signleCommentPattern.matcher(result).replaceAll("");
        result = multiCommentPattern.matcher(result).replaceAll("");
        result = trimPattern2.matcher(result).replaceAll("");
        result = trimPattern.matcher(result).replaceAll(" ");
        // 恢复替换掉的字符串
        result = result.replaceAll(tempSingleCommentBlock, "//").replaceAll(tempMulitCommentBlock1, "/*")
                .replaceAll(tempMulitCommentBlock2, "*/");

        return result;
    }

    public static String toString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN1);
        return dateFormat.format(date);
    }

    public static String toString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date toDate(String text) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN1);
        try {
            return dateFormat.parse(text);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date toDate(String text, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(text);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String formatByPattern(Date d, String pattern) {
        String str = "";
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            str = df.format(d);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return str;
    }

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    public static Date parseDate(String dateStr, String format) {
        try {
            dateFormat = new SimpleDateFormat(format);
            String dt = dateStr.replaceAll("-", "/");
            if ((!dt.equals("")) && (dt.length() < format.length())) {
                dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]", "0");
            }
            date = (Date) dateFormat.parse(dt);
        } catch (Exception e) {
        }
        return date;
    }

    /**
     * Return current datetime string.
     *
     * @return current datetime, pattern: "yyyy-MM-dd HH:mm:ss".
     */
    public static String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dt = sdf.format(new Date());
        return dt;
    }

    public static Date StrToDate1(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {

            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期：YYYY-MM-DD 格式
     * @return Date
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy/MM/dd");
    }

    /**
     * 功能描述：格式化输出日期
     *
     * @param date   Date 日期
     * @param format String 格式
     * @return 返回字符型日期
     */
    public static String format(Date date, String format) {
        String result = "";
        try {
            if (date != null) {
                dateFormat = new SimpleDateFormat(format);
                result = dateFormat.format(date);
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 功能描述：
     *
     * @param date Date 日期
     * @return
     */
    public static String format(Date date) {
        return format(date, "yyyy/MM/dd");
    }

    /**
     * 功能描述：返回年份
     *
     * @param date Date 日期
     * @return 返回年份
     */
    public static int getYear(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 功能描述：返回月份
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功能描述：返回日份
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能描述：返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分钟
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫秒
     *
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 功能描述：返回字符型日期
     *
     * @param date 日期
     * @return 返回字符型日期 yyyy/MM/dd 格式
     */
    public static String getDate(Date date) {
        return format(date, "yyyy/MM/dd");
    }

    /**
     * 功能描述：返回字符型时间
     *
     * @param date Date 日期
     * @return 返回字符型时间 HH:mm:ss 格式
     */
    public static String getTime(Date date) {
        return format(date, "HH:mm:ss");
    }

    /**
     * 功能描述：返回字符型日期时间
     *
     * @param date Date 日期
     * @return 返回字符型日期时间 yyyy/MM/dd HH:mm:ss 格式
     */
    public static String getDateTime(Date date) {
        return format(date, "yyyy/MM/dd HH:mm:ss");
    }

    /**
     * 功能描述：日期相加
     *
     * @param date Date 日期
     * @param day  int 天数
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, int day) {
        calendar = Calendar.getInstance();
        long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    /**
     * 功能描述：日期相减
     *
     * @param date  Date 日期
     * @param date1 Date 日期
     * @return 返回相减后的日期
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    /**
     * 功能描述：取得指定月份的第一天
     *
     * @param strdate String 字符型日期
     * @return String yyyy-MM-dd 格式
     */
    public static String getMonthBegin(String strdate) {
        date = parseDate(strdate);
        return format(date, "yyyy-MM") + "-01";
    }

    /**
     * 功能描述：取得指定月份的最后一天
     *
     * @param strdate String 字符型日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String getMonthEnd(String strdate) {
        date = parseDate(getMonthBegin(strdate));
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return formatDate(calendar.getTime());
    }

    /**
     * 功能描述：常用的格式化日期
     *
     * @param date Date 日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String formatDate(Date date) {
        return formatDateByFormat(date, "yyyy-MM-dd");
    }

    /**
     * 功能描述：以指定的格式来格式化日期
     *
     * @param date   Date 日期
     * @param format String 格式
     * @return String 日期字符串
     */
    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String formatTimestampByFormat(Timestamp date) {
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String result = "";
        if (date != null) {
            try {
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @param format  如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long Date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getFirstDayOfMonth1(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最小天数
        int firstDay = cal.getMinimum(Calendar.DATE);
        // 设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(cal.getTime());
    }

    /**
     * 获取指定年月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth1(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    /**
     * 根据当前时间获取上周一和周日的日期 date1-周一 date2-周日
     *
     * @return
     */
    public static Map<String, Long> getDate() {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);

        calendar2.add(Calendar.DATE, offset2 - 7);
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);
        Date date1 = calendar1.getTime();
        Date date2 = calendar2.getTime();
        Map<String, Long> map = new HashMap<>();
        map.put("date1", date1.getTime());
        map.put("date2", date2.getTime());
        return map;
    }

    /**
     * 根据当前时间获取上个月开始 和结束
     *
     * @return
     */
    public static Map<String, Long> getMonthDate() {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();


        calendar1.add(Calendar.MONTH, -1);
        calendar1.set(Calendar.DAY_OF_MONTH, calendar1.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);


        calendar2.add(Calendar.MONTH, -1);
        calendar2.set(Calendar.DAY_OF_MONTH, calendar2.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);
        Date date1 = calendar1.getTime();
        Date date2 = calendar2.getTime();
        Map<String, Long> map = new HashMap<>();
        map.put("date1", date1.getTime());
        map.put("date2", date2.getTime());
        return map;
    }

    /**
     * 根据当前时间获取本周一的时间戳
     *
     * @return
     */
    public static Long getThisWeekMonday() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime().getTime();
    }

    public static Date nextDate(Date date, int dateType, int no) {
        Calendar c = Calendar.getInstance();
        String[] tmp = format(date, "yyyy-MM-dd-HH-mm-ss").split("-");
        c.set(Calendar.YEAR, Integer.valueOf(tmp[0]));
        c.set(Calendar.MARCH, Integer.valueOf(tmp[1]) - 1);
        c.set(Calendar.DATE, Integer.valueOf(tmp[2]));
        c.set(Calendar.HOUR_OF_DAY, Integer.valueOf(tmp[3]));
        c.set(Calendar.MINUTE, Integer.valueOf(tmp[4]));
        c.set(Calendar.SECOND, Integer.valueOf(tmp[5]));
        c.add(dateType, no);
        return c.getTime();
    }

    public static Date daysPlusOne(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);
        date = c.getTime();
        return date;
    }

    // 时间加上时分秒00:00:00
    public static String conversionData(Date date) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        cal1.set(Calendar.HOUR_OF_DAY, 00);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        return timestampToDate(cal1.getTime().getTime(), DATE_PATTERN1);
    }

    // 时间加上分秒23:59:59
    public static Long plusMinuteData(Long time) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(time);
        cal1.set(Calendar.HOUR_OF_DAY, 23);
        cal1.set(Calendar.MINUTE, 59);
        cal1.set(Calendar.SECOND, 59);

        long timeStamp = Date2TimeStamp(timestampToDate(cal1.getTime().getTime(), DATE_PATTERN1), DATE_PATTERN1);
        return timeStamp;
    }

    /**
     * @param
     * @return 获取昨天日期
     */
    public static Date getYesterday() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);//-1.昨天时间 0.当前时间 1.明天时间 *以此类推
        c.set(Calendar.HOUR_OF_DAY, 00);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }


    /**
     * 时间戳转日期
     *
     * @param timeStamp
     * @return
     */
    public static String timestampToDate(Long timeStamp, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (timeStamp.toString().length() == 10) {
            String sd = sdf.format(new Date(timeStamp * 1000L));
            return sd;
        } else {
            String sd = sdf.format(timeStamp);
            return sd;
        }

    }


    /**
     * 获取当天开始的时间戳
     *
     * @return
     */
    public static Long dayTimeInMillis() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return Date2TimeStamp(conversionData(cal.getTime()), DATE_PATTERN1);
    }

    //获取明天的开始时间
    public static long getBeginDayOfTomorrow() {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(calendar.DATE, 1);
        return Date2TimeStamp(conversionData(calendar.getTime()), DATE_PATTERN1);
    }

    /**
     * 计算时间差
     *
     * @param time1
     * @param time2
     * @return
     */
    public static Map<String, Long> getDistanceTime(long time1, long time2) {
        if (String.valueOf(time1).length() == 10) {
            time1 = time1 * 1000l;
        }
        if (String.valueOf(time2).length() == 10) {
            time2 = time2 * 1000l;
        }
        Map<String, Long> map = new HashMap<>();
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff;

        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day != 0) {
            map.put("day", day);
        }
        if (hour != 0) {
            map.put("hour", hour);
        }
        if (min != 0 || sec != 0) {
            map.put("min", min);
        }
        if (sec != 0) {
            map.put("sec", sec);
        }
        return map;
    }

    /**
     * 获取英式时间
     *
     * @return
     */
    public static String getDateEngLiSh() {
        SimpleDateFormat sdf = new SimpleDateFormat(" MMM dd yyyy hh:mm a", Locale.ENGLISH);
        String sd = sdf.format(new Date());
        return sd;
    }

    /**
     * 根据当前时间获取过去几个小时的时间
     *
     * @param past
     * @return
     */
    public static Long getPastHourDate(int past) {
        if (past > 1) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR,
                    calendar.get(Calendar.HOUR) - past);// 让日期加1
            return calendar.getTime().getTime();
        }
        return null;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - past);
        Date today = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN1);
        String result = sdf.format(today);
        return result;
    }

    /**
     * 获取当前系统时间最近12月的年月（含当月）
     */
    public static String getLatest12Month(Date date, int month) {
        Calendar from = Calendar.getInstance();
        from.setTime(date);
        from.add(Calendar.MONTH, -month);//11个月前
        String str2 = from.get(Calendar.YEAR) + "-" + fillZero(from.get(Calendar.MONTH) + 1) + "-" + from.get(Calendar.DATE);
        return str2;
    }

    /**
     * 格式化月份
     */
    public static String fillZero(int i) {
        String month = "";
        if (i < 10) {
            month = "0" + i;
        } else {
            month = String.valueOf(i);
        }
        return month;
    }

    /**
     * 根据传入的时间 截出小时
     *
     * @param date
     * @return
     */
    public static int getHour(String date) {
        String substring = date.substring(0, date.indexOf(":"));
        return Convert.toInt(substring);
    }

    /**
     * 获取指定的日期的24小时每个小时的节点
     *
     * @param
     * @return 返回时间戳格式 yyyy-MM-dd HH:mm:ss
     */
    public static List<Long> pastHour(Date date) {
        String time = format(date, DATE_PATTERN3);
        List<Long> list = new ArrayList<>();
        for (int h = 0, m = 0; h < 24; m += 60) {
            if (m >= 60) {
                h++;
                m = 0;
            }
            if (h >= 24) {
                break;
            }
            String hour = String.valueOf(h);
            String minute = String.valueOf(m);
            if (hour.length() < 2) {
                hour = "0" + hour;
            }
            if (minute.length() < 2) {
                minute = "0" + minute;
            }
            list.add(Date2TimeStamp(time + " " + hour + ":" + minute + ":" + "00", DATE_PATTERN1));
        }
        return list;
    }

    public static String toBritishTime(Long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN5 + "a", Locale.ENGLISH);
        if (time.toString().length() == 10) {
            String sd = sdf.format(new Date(time * 1000L));
            return sd;
        } else {
            String sd = sdf.format(time);
            return sd;
        }
    }


    /**
     * 获取过去12个月的日期
     *
     * @return
     */
    public static List<String> getMonthDateList() {
        List<String> monthDaysList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (long i = 0L; i < 12L; i++) {
            LocalDate localDate = today.minusMonths(i);
            String month = localDate.toString().substring(0, 7);
            monthDaysList.add(month);
        }
        return monthDaysList;
    }

    /**
     * 获取过去日期
     *
     * @param intervals
     * @return
     */
    public static List<String> getPastDateList(int intervals) {
        List<String> pastDaysList = new ArrayList<>();
        List<String> fetureDaysList = new ArrayList<>();
        for (int i = 0; i < intervals; i++) {
            pastDaysList.add(getPastDate(i));
            fetureDaysList.add(getFetureDate(i));
        }
        return pastDaysList;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN3);
        String result = format.format(today);
        return result;
    }


    /**
     * 获取未来 第 past 天的日期
     *
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN3);
        String result = format.format(today);
        return result;
    }

    public static long get_D_Plaus_1(Calendar c) {
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
        return c.getTimeInMillis();
    }

    public static List<String> getIntervalDate(Long startTime,Long endTime) {
        List<String> resultDate=new ArrayList<>();
        String beginDate = timestampToDate(startTime, DATE_PATTERN3);
        String endDate = timestampToDate(endTime, DATE_PATTERN3);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN3);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(beginDate));
            for (long d = cal.getTimeInMillis(); d <= sdf.parse(endDate).getTime(); d = get_D_Plaus_1(cal)) {
                resultDate.add(sdf.format(d));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return resultDate;
    }

    public static boolean isConvertType(Class<?> classInfo) {
        boolean isConvertType = false;
        isConvertType = classInfo == byte.class || classInfo == Byte.class ? true : isConvertType;
        isConvertType = classInfo == boolean.class || classInfo == Boolean.class ? true : isConvertType;
        isConvertType = classInfo == char.class || classInfo == Character.class ? true : isConvertType;
        isConvertType = classInfo == char[].class ? true : isConvertType;
        isConvertType = classInfo == short.class || classInfo == Short.class ? true : isConvertType;
        isConvertType = classInfo == double.class || classInfo == Double.class ? true : isConvertType;
        isConvertType = classInfo == float.class || classInfo == Float.class ? true : isConvertType;
        isConvertType = classInfo == int.class || classInfo == Integer.class ? true : isConvertType;
        isConvertType = classInfo == long.class || classInfo == Long.class ? true : isConvertType;
        isConvertType = classInfo == String.class ? true : isConvertType;
        isConvertType = classInfo == Date.class || classInfo == java.sql.Date.class ? true : isConvertType;
        isConvertType = classInfo == BigInteger.class || classInfo == BigDecimal.class ? true : isConvertType;
        return isConvertType;
    }

    @SuppressWarnings("unchecked")
    public static <T> T toIts(Object[] datas, Class<?> classInfo) {
        Object array = Array.newInstance(classInfo, datas.length);
        for (int i = 0; i < datas.length; i++) {
            Array.set(array, i, toIt(datas[i], classInfo));
        }
        return (T) array;
    }

    public static <T> T toIt(Object data, Class<T> classInfo) {
        T object = null;
        String inData = null;
        if (data instanceof String) {
            inData = (String) data;
        } else {
            inData = toString(data);
        }
        if (org.apache.commons.lang.StringUtils.isNotEmpty(inData)) {
            object = stringToIt(inData, classInfo);
        }
        return object;
    }

    public static String toString(Object object) {
        if (object instanceof String) {
            return (String) object;
        } else if (object instanceof Byte) {
            return String.valueOf((byte) object);
        } else if (object instanceof Boolean) {
            return String.valueOf((boolean) object);
        } else if (object instanceof Character) {
            return String.valueOf((char) object);
        } else if (object instanceof char[]) {
            return String.valueOf((char[]) object);
        } else if (object instanceof Short) {
            return String.valueOf((short) object);
        } else if (object instanceof Double) {
            return String.valueOf((double) object);
        } else if (object instanceof Float) {
            return String.valueOf((float) object);
        } else if (object instanceof Integer) {
            return String.valueOf((int) object);
        } else if (object instanceof Long) {
            return String.valueOf((long) object);
        } else if (object instanceof Date) {
            return Helper.format((Date) object, "yyyy-MM-dd HH:mm:ss");
        } else if (object instanceof BigInteger) {
            return ((BigInteger) object).toString();
        } else if (object instanceof BigDecimal) {
            return ((BigDecimal) object).toString();
        } else {
            return String.valueOf(object);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T stringToIt(String string, Class<T> classInfo) {
        Object object = null;
        try {
            if (classInfo == String.class) {
                object = string;
            } else if (classInfo == byte.class || classInfo == Byte.class) {
                object = new BigDecimal(string).byteValue();
            } else if (classInfo == boolean.class || classInfo == Boolean.class) {
                object = Boolean.parseBoolean(string);
            } else if (classInfo == char.class || classInfo == Character.class) {
                object = string != null && string.length() == 1 ? string.charAt(0) : string;
            } else if (classInfo == char[].class) {
                object = string.toCharArray();
            } else if (classInfo == short.class || classInfo == Short.class) {
                object = new BigDecimal(string).shortValue();
            } else if (classInfo == int.class || classInfo == Integer.class) {
                object = new BigDecimal(string).intValue();
            } else if (classInfo == long.class || classInfo == Long.class) {
                object = new BigDecimal(string).longValue();
            } else if (classInfo == float.class || classInfo == Float.class) {
                object = new BigDecimal(string).floatValue();
            } else if (classInfo == double.class || classInfo == Double.class) {
                object = new BigDecimal(string).doubleValue();
            } else if (classInfo == Date.class || classInfo == java.sql.Date.class) {
                object = Helper.toDate(string, "yyyy-MM-dd HH:mm:ss");
            } else if (classInfo == BigInteger.class) {
                object = new BigInteger(string);
            } else if (classInfo == Date.class || classInfo == java.sql.Date.class) {
                object = new BigDecimal(string);
            } else {
                object = string;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return (T) object;
    }

    public static String division(Integer divisor,Integer dividend){
        if (divisor>0 && dividend>0){
            DecimalFormat df = new DecimalFormat("0.00");
            String num = df.format((float)divisor/dividend);
            return num;
        }
        return "0";
    }
    public static boolean isNumber(String str) {
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
