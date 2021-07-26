package com.mazentop.plugins.resservlet.rule;

import com.mazentop.plugins.resservlet.rule.impl.ImagerRule;
import com.mazentop.plugins.resservlet.rule.impl.VideoRule;
import com.mazentop.plugins.resservlet.util.RuleUtils;
import com.mztframework.FileProperties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.mazentop.plugins.resservlet.util.RuleUtils.*;
import static java.lang.String.format;

public abstract class Rule {

    private static final Long DEFAULT_EXPIRE_TIME_IN_SECONDS = TimeUnit.DAYS.toSeconds(30);
    private static final long ONE_SECOND_IN_MILLIS = TimeUnit.SECONDS.toMillis(1);
    private static final String ETAG = "W/\"%s-%s\"";
    private static final Pattern RANGE_PATTERN = Pattern.compile("^bytes=[0-9]*-[0-9]*(,[0-9]*-[0-9]*)*$");
    public static final Pattern INT_PATTERN = Pattern.compile("[-+]?\\d+");
    private static final String MULTIPART_BOUNDARY = UUID.randomUUID().toString();
    public static final String COMPRESSION_MARK = "!";
    public static final String COMPRESSION_WIDTH_HEIGHT_GAP= "x";

    protected HttpServletRequest req;

    protected HttpServletResponse resp;

    protected FileProperties fileProperties;


    protected abstract void write() throws IOException;

    protected File file(String uri) {
        return new File(fileProperties.getLocalPath() + uri);
    }


    public static void write(HttpServletRequest req, HttpServletResponse resp, FileProperties fileProperties) throws IOException {
        // 简单判断图片视频资源
        if(RuleUtils.isImage(req)) {
            new ImagerRule(req, resp, fileProperties).write();
        } else {
            new VideoRule(req, resp, fileProperties).write();
        }
    }

    /**
     * Process the actual request.
     * @param request The request to be processed.
     * @param response The response to be created.
     * @throws IOException If something fails at I/O level.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, File file)
            throws IOException {
        response.reset();

        Resource resource = new Resource(file);

        if (resource.file == null) {
            handleFileNotFound(request, response);
            return;
        }
        if (preconditionFailed(request, resource)) {
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
            return;
        }

        setCacheHeaders(response, resource, getExpireTime());

        List<Range> ranges = getRanges(request, resource);

        if (ranges == null) {
            response.setHeader("Content-Range", "bytes */" + resource.length);
            response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
            return;
        }

        if (!ranges.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
        } else {
            // Full content.
            ranges.add(new Range(0, resource.length - 1));
        }

        String contentType = setContentHeaders(request, response, resource, ranges);

        writeContent(response, resource, ranges, contentType);
    }


    /**
     * Returns the content type associated with the given HTTP servlet request and file.
     * <p>
     * fallback default value of <code>application/octet-stream</code>.
     * @param request The involved HTTP servlet request.
     * @param resource The involved file.
     * @return The content type associated with the given HTTP servlet request and file.
     */
    protected String getContentType(HttpServletRequest request, Resource resource) {
        if(resource.isWebp()) {
            return "image/webp";
        }
        return coalesce(request.getServletContext().getMimeType(resource.name), "application/octet-stream");
    }

    /**
     * Returns <code>true</code> if we must force a "Save As" dialog based on the given HTTP servlet request and content
     * <p>
     * The default implementation will return <code>true</code> if the content type does <strong>not</strong> start with
     * <code>text</code> or <code>image</code>, and the <code>Accept</code> request header is either <code>null</code>
     * or does not match the given content type.
     * @param request The involved HTTP servlet request.
     * @param contentType The content type of the involved file.
     * @return <code>true</code> if we must force a "Save As" dialog based on the given HTTP servlet request and content
     * type.
     */
    protected boolean isAttachment(HttpServletRequest request, String contentType) {
        String accept = request.getHeader("Accept");
        return !startsWithOneOf(contentType, "text", "image", "webp") && (accept == null || !accepts(accept, contentType));
    }

    /**
     * Returns the file name to be used in <code>Content-Disposition</code> header.
     * This does not need to be URL-encoded as this will be taken care of.
     * <p>
     * The default implementation returns {@link File#getName()}.
     * @param request The involved HTTP servlet request.
     * @param resource The involved file.
     * @return The file name to be used in <code>Content-Disposition</code> header.
     * @since 2.3
     */
    protected String getAttachmentName(HttpServletRequest request, Resource resource) {
        return resource.name;
    }


    /**
     * Returns true if it's a conditional request which must return 412.
     */
    private boolean preconditionFailed(HttpServletRequest request, Resource resource) {
        String match = request.getHeader("If-Match");
        long unmodified = request.getDateHeader("If-Unmodified-Since");
        return (match != null) ? !matches(match, resource.eTag) : (unmodified != -1 && modified(unmodified, resource.lastModified));
    }

    /**
     * Returns true if the given modified header is older than the given last modified value.
     */
    private static boolean modified(long modifiedHeader, long lastModified) {
        // That second is because the header is in seconds, not millis.
        return (modifiedHeader + ONE_SECOND_IN_MILLIS <= lastModified);
    }

    /**
     * Set cache headers.
     */
    private void setCacheHeaders(HttpServletResponse response, Resource resource, long expires) {
        RuleUtils.setCacheHeaders(response, expires);
        response.setHeader("ETag", resource.eTag);
        response.setDateHeader("Last-Modified", resource.lastModified);
    }

    /**
     * Set content headers.
     */
    private String setContentHeaders(HttpServletRequest request, HttpServletResponse response, Resource resource, List<Range> ranges) {
        String contentType = getContentType(request, resource);
        String filename = getAttachmentName(request, resource);
        boolean attachment = isAttachment(request, contentType);
        response.setHeader("Content-Disposition", formatContentDispositionHeader(filename, attachment));
        response.setHeader("Accept-Ranges", "bytes");

        if (ranges.size() == 1) {
            Range range = ranges.get(0);
            response.setContentType(contentType);
            response.setHeader("Content-Length", String.valueOf(range.length));

            if (response.getStatus() == HttpServletResponse.SC_PARTIAL_CONTENT) {
                response.setHeader("Content-Range", "bytes " + range.start + "-" + range.end + "/" + resource.length);
            }
        }
        else {
            response.setContentType("multipart/byteranges; boundary=" + MULTIPART_BOUNDARY);
        }

        return contentType;
    }

    /**
     * Handles the case when the file is not found.
     * <p>
     * The default implementation sends a HTTP 404 error.
     * @param request The involved HTTP servlet request.
     * @param response The involved HTTP servlet response.
     * @throws IOException When something fails at I/O level.
     * @since 2.3
     */
    private void handleFileNotFound(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private long getExpireTime() {
        return DEFAULT_EXPIRE_TIME_IN_SECONDS;
    }

    private List<Range> getRanges(HttpServletRequest request, Resource resource) {
        List<Range> ranges = new ArrayList<>(1);
        String rangeHeader = request.getHeader("Range");

        if (rangeHeader == null) {
            return ranges;
        }
        else if (!RANGE_PATTERN.matcher(rangeHeader).matches()) {
            return null; // Syntax error.
        }

        String ifRange = request.getHeader("If-Range");

        if (ifRange != null && !ifRange.equals(resource.eTag)) {
            try {
                long ifRangeTime = request.getDateHeader("If-Range");

                if (ifRangeTime != -1 && modified(ifRangeTime, resource.lastModified)) {
                    return ranges;
                }
            }
            catch (IllegalArgumentException ifRangeHeaderIsInvalid) {
                return ranges;
            }
        }

        for (String rangeHeaderPart : rangeHeader.split("=")[1].split(",")) {
            Range range = parseRange(rangeHeaderPart, resource.length);

            if (range == null) {
                return null; // Logic error.
            }

            ranges.add(range);
        }

        return ranges;
    }

    /**
     * Parse range header part. Returns null if there's a logic error (i.e. start after end).
     */
    private Range parseRange(String range, long length) {
        long start = sublong(range, 0, range.indexOf('-'));
        long end = sublong(range, range.indexOf('-') + 1, range.length());

        if (start == -1) {
            start = length - end;
            end = length - 1;
        }
        else if (end == -1 || end > length - 1) {
            end = length - 1;
        }

        if (start > end) {
            return null; // Logic error.
        }

        return new Range(start, end);
    }


    // Helpers (can be refactored to public utility class) ----------------------------------------

    /**
     * Returns true if the given accept header accepts the given value.
     * @param acceptHeader The accept header.
     * @param toAccept The value to be accepted.
     * @return True if the given accept header accepts the given value.
     */
    private static boolean accepts(String acceptHeader, String toAccept) {
        String[] acceptValues = acceptHeader.split("\\s*(,|;)\\s*");
        Arrays.sort(acceptValues);
        return Arrays.binarySearch(acceptValues, toAccept) > -1
                || Arrays.binarySearch(acceptValues, toAccept.replaceAll("/.*$", "/*")) > -1
                || Arrays.binarySearch(acceptValues, "*/*") > -1;
    }

    /**
     * Returns true if the given match header matches the given value.
     * @param matchHeader The match header.
     * @param toMatch The value to be matched.
     * @return True if the given match header matches the given value.
     */
    private static boolean matches(String matchHeader, String toMatch) {
        String[] matchValues = matchHeader.split("\\s*,\\s*");
        Arrays.sort(matchValues);
        return Arrays.binarySearch(matchValues, toMatch) > -1
                || Arrays.binarySearch(matchValues, "*") > -1;
    }

    /**
     * Returns a substring of the given string value from the given begin index to the given end
     * index as a long. If the substring is empty, then -1 will be returned
     * @param value The string value to return a substring as long for.
     * @param beginIndex The begin index of the substring to be returned as long.
     * @param endIndex The end index of the substring to be returned as long.
     * @return A substring of the given string value as long or -1 if substring is empty.
     */
    private static long sublong(String value, int beginIndex, int endIndex) {
        String substring = value.substring(beginIndex, endIndex);
        return (substring.length() > 0) ? Long.parseLong(substring) : -1;
    }

    /**
     * Write given file to response with given content type and ranges.
     */
    private void writeContent(HttpServletResponse response, Resource resource, List<Range> ranges, String contentType) throws IOException {
        ServletOutputStream output = response.getOutputStream();

        if (ranges.size() == 1) {
            Range range = ranges.get(0);
            stream(resource.file, output, range.start, range.length);
        }
        else {
            for (Range range : ranges) {
                output.println();
                output.println("--" + MULTIPART_BOUNDARY);
                output.println("Content-Type: " + contentType);
                output.println("Content-Range: bytes " + range.start + "-" + range.end + "/" + resource.length);
                stream(resource.file, output, range.start, range.length);
            }

            output.println();
            output.println("--" + MULTIPART_BOUNDARY + "--");
        }
    }


    // Inner classes ------------------------------------------------------------------------------

    /**
     * Convenience class for a file resource.
     */
    private static class Resource {
        private final File file;
        private final long length;
        private final long lastModified;
        private final String eTag;
        private final String name;

        public Resource(File file) {
            if (file != null && file.isFile()) {
                this.file = file;
                this.name = file.getName();
                length = file.length();
                lastModified = file.lastModified();
                eTag = format(ETAG, encodeURL(this.name), lastModified);
            }
            else {
                this.file = null;
                length = 0;
                lastModified = 0;
                eTag = null;
                name = null;
            }
        }
        public Resource(String name, long lastModified, long length) {
            this.file = null;
            this.name = name;
            this.length = length;
            this.lastModified = lastModified;
            this.eTag = format(ETAG, encodeURL(this.name), lastModified);
        }

        public boolean isWebp() {
            if(!Objects.isNull(name)) {
                int period = name.lastIndexOf(46);
                return period > 0 && ImagerRule.FORMAT_WEBP.equals(name.substring(period + 1));
            }
            return false;
        }
    }


    /**
     * Convenience class for a byte range.
     */
    private static class Range {
        private final long start;
        private final long end;
        private final long length;

        public Range(long start, long end) {
            this.start = start;
            this.end = end;
            length = end - start + 1;
        }
    }
}
