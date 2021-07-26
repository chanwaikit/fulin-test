package com.mazentop.plugins.filter;

import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.FilterConfig;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class ThreadContent implements Filter {

    public static class ThreadObject {
        public final HttpServletRequest request;
        final HttpServletResponse response;
        /** 其他附加数据 */
        final Map<String, Object> data = new HashMap<>();

        public ThreadObject(HttpServletRequest request, HttpServletResponse response) {
            this.request = request;
            this.response = response;
        }
    }

    private static ThreadLocal<ThreadObject> THREAD_OBJECT = ThreadLocal.withInitial(() -> {
        throw new RuntimeException(" 程序未初始化，请在web.xml中 配置");
    });

    /**
     * 从线程对象中获取数据
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getData(String key) {
        if(!Objects.isNull(THREAD_OBJECT.get())) {
            return (T) THREAD_OBJECT.get().data.get(key);
        }
        return null;
    }

    /**
     * 向线程对象中设置数据， 为保证安全，如果该对象本身有值时会抛出错误
     *
     * @param key
     *            关键字
     * @param obj
     *            要设置的对象
     */
    public static void addData(String key, Object obj) {
        if(!Objects.isNull(THREAD_OBJECT.get())) {
            THREAD_OBJECT.get().data.put(key, obj);
        }
    }

    /**
     * 从线程中移除对象
     *
     * @param key
     *            要移除的对象名
     */
    @SuppressWarnings("unchecked")
    public static <T> T removeData(String key) {
        return (T) THREAD_OBJECT.get().data.remove(key);
    }

    /** 得到servlet请求中的 request */
    public static HttpServletRequest request() {
        return THREAD_OBJECT.get().request;
    }

    /** 得到servlet请求中的 response */
    public static HttpServletResponse response() {
        return THREAD_OBJECT.get().response;
    }


    @Override
    public void init(FilterConfig arg0) throws ServletException {
        log.info("#init threadContent");
    }

    @Override
    public void destroy() {
        log.info("#destroy threadContent");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (isStaticFile(request, response)) {
            filterChain.doFilter(request, response);
        } else {
            THREAD_OBJECT.set(new ThreadObject((HttpServletRequest) request, (HttpServletResponse) response));
            filterChain.doFilter(request, response);
            THREAD_OBJECT.set(null);
        }
    }

    /** 静态文件后缀名 */
    ImmutableSet<String> ignoreSuffix = new ImmutableSet.Builder<String>().add("jpg", "jpeg", "ico", "txt", "doc", "ppt", "xls", "pdf", "gif", "png",
            "bmp", "css", "js", "swf", "flv", "mp3", "htc").build();

    /**
     * 判断是否过滤该请求
     *
     * @param request
     * @param response
     * @return
     */
    private boolean isStaticFile(ServletRequest request, ServletResponse response) {
        if (request instanceof HttpServletRequest) {
            String uri = ((HttpServletRequest) request).getRequestURI();
            String suffix = StringUtils.substringAfterLast(uri, ".").toLowerCase();
            return ignoreSuffix.contains(suffix);
        }
        // request 无法解析时 暂时定为静态文件处理
        return true;
    }

    /**
     * 设置新的共享对象
     *
     * @param threadObject
     */
    public static void setThreadObject(ThreadObject threadObject) {
        THREAD_OBJECT.set(threadObject);
    }
}
