package com.mazentop.plugins.resservlet.util;

import com.mazentop.plugins.resservlet.rule.Rule;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author zhaoqt
 */
public final class RuleUtils {


    private static final String CONTENT_DISPOSITION_HEADER = "%s;filename=\"%2$s\"; filename*=UTF-8''%2$s";
    private static final int DEFAULT_STREAM_BUFFER_SIZE = 524288;
    private static final String ERROR_UNSUPPORTED_ENCODING = "UTF-8 is apparently not supported on this platform.";

    /**
     * URL-encode the given string using UTF-8.
     * @param string The string to be URL-encoded using UTF-8.
     * @return The given string, URL-encoded using UTF-8, or <code>null</code> if <code>null</code> was given.
     * @throws UnsupportedOperationException When this platform does not support UTF-8.
     * @since 1.4
     */
    public static String encodeURL(String string) {
        if (string == null) {
            return null;
        }

        try {
            return URLEncoder.encode(string, UTF_8.name());
        }
        catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(ERROR_UNSUPPORTED_ENCODING, e);
        }
    }

    /**
     * URI-encode the given string using UTF-8. URIs (paths and filenames) have different encoding rules as compared to
     * URL query string parameters. {@link URLEncoder} is actually only for www (HTML) form based query string parameter
     * values (as used when a webbrowser submits a HTML form). URI encoding has a lot in common with URL encoding, but
     * the space has to be %20 and some chars doesn't necessarily need to be encoded.
     * @param string The string to be URI-encoded using UTF-8.
     * @return The given string, URI-encoded using UTF-8, or <code>null</code> if <code>null</code> was given.
     * @throws UnsupportedOperationException When this platform does not support UTF-8.
     * @since 2.4
     */
    public static String encodeURI(String string) {
        if (string == null) {
            return null;
        }

        return encodeURL(string)
                .replace("+", "%20")
                .replace("%21", "!")
                .replace("%27", "'")
                .replace("%28", "(")
                .replace("%29", ")")
                .replace("%7E", "~");
    }

    /**
     * <p>Set the cache headers. If the <code>expires</code> argument is larger than 0 seconds, then the following headers
     * will be set:
     * <ul>
     * <li><code>Cache-Control: public,max-age=[expiration time in seconds],must-revalidate</code></li>
     * <li><code>Expires: [expiration date of now plus expiration time in seconds]</code></li>
     * </ul>
     * <p>Else the method will delegate to {@link #setNoCacheHeaders(HttpServletResponse)}.
     * @param response The HTTP servlet response to set the headers on.
     * @param expires The expire time in seconds (not milliseconds!).
     * @since 2.2
     */
    public static void setCacheHeaders(HttpServletResponse response, long expires) {
        if (expires > 0) {
            response.setHeader("Cache-Control", "public,max-age=" + expires + ",must-revalidate");
            response.setDateHeader("Expires", System.currentTimeMillis() + SECONDS.toMillis(expires));
            response.setHeader("Pragma", ""); // Explicitly set pragma to prevent container from overriding it.
        }
        else {
            setNoCacheHeaders(response);
        }
    }

    /**
     * <p>Set the no-cache headers. The following headers will be set:
     * <ul>
     * <li><code>Cache-Control: no-cache,no-store,must-revalidate</code></li>
     * <li><code>Expires: [expiration date of 0]</code></li>
     * <li><code>Pragma: no-cache</code></li>
     * </ul>
     * Set the no-cache headers.
     * @param response The HTTP servlet response to set the headers on.
     * @since 2.2
     */
    public static void setNoCacheHeaders(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma", "no-cache"); // Backwards compatibility for HTTP 1.0.
    }

    @SafeVarargs
    public static <T> T coalesce(T... objects) {
        for (T object : objects) {
            if (object != null) {
                return object;
            }
        }

        return null;
    }

    /**
     * Returns <code>true</code> if the given string starts with one of the given prefixes.
     * @param string The object to be checked if it starts with one of the given prefixes.
     * @param prefixes The argument list of prefixes to be checked
     * @return <code>true</code> if the given string starts with one of the given prefixes.
     * @since 1.4
     */
    public static boolean startsWithOneOf(String string, String... prefixes) {
        for (String prefix : prefixes) {
            if (string.startsWith(prefix)) {
                return true;
            }
        }

        return false;
    }

    // I/O ------------------------------------------------------------------------------------------------------------
    /**
     * Stream the given input to the given output via NIO {@link Channels} and a directly allocated NIO
     * {@link ByteBuffer}. Both the input and output streams will implicitly be closed after streaming,
     * regardless of whether an exception is been thrown or not.
     * @param input The input stream.
     * @param output The output stream.
     * @return The length of the written bytes.
     * @throws IOException When an I/O error occurs.
     */
    public static long stream(InputStream input, OutputStream output) throws IOException {
        try (ReadableByteChannel inputChannel = Channels.newChannel(input);
             WritableByteChannel outputChannel = Channels.newChannel(output))
        {
            ByteBuffer buffer = ByteBuffer.allocateDirect(DEFAULT_STREAM_BUFFER_SIZE);
            long size = 0;

            while (inputChannel.read(buffer) != -1) {
                buffer.flip();
                size += outputChannel.write(buffer);
                buffer.clear();
            }

            return size;
        }
    }

    /**
     * Stream a specified range of the given file to the given output via NIO {@link Channels} and a directly allocated
     * NIO {@link ByteBuffer}. The output stream will only implicitly be closed after streaming when the specified range
     * represents the whole file, regardless of whether an exception is been thrown or not.
     * @param file The file.
     * @param output The output stream.
     * @param start The start position (offset).
     * @param length The (intented) length of written bytes.
     * @return The (actual) length of the written bytes. This may be smaller when the given length is too large.
     * @throws IOException When an I/O error occurs.
     * @since 2.2
     */
    public static long stream(File file, OutputStream output, long start, long length) throws IOException {
        if (start == 0 && length >= file.length()) {
            return stream(new FileInputStream(file), output);
        }

        try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(file.toPath(), StandardOpenOption.READ)) {
            WritableByteChannel outputChannel = Channels.newChannel(output);
            ByteBuffer buffer = ByteBuffer.allocateDirect(DEFAULT_STREAM_BUFFER_SIZE);
            long size = 0;

            while (fileChannel.read(buffer, start + size) != -1) {
                buffer.flip();

                if (size + buffer.limit() > length) {
                    buffer.limit((int) (length - size));
                }

                size += outputChannel.write(buffer);

                if (size >= length) {
                    break;
                }

                buffer.clear();
            }

            return size;
        }
    }

    /**
     * <p>Format an UTF-8 compatible content disposition header for the given filename and whether it's an attachment.
     * @param filename The filename to appear in "Save As" dialogue.
     * @param attachment Whether the content should be provided as an attachment or inline.
     * @return An UTF-8 compatible content disposition header.
     * @since 2.6
     */
    public static String formatContentDispositionHeader(String filename, boolean attachment) {
        return format(CONTENT_DISPOSITION_HEADER, (attachment ? "attachment" : "inline"), encodeURI(filename));
    }


    public static String getFilePrefixName(String path) {
        return StringUtils.left(path, path.lastIndexOf("."));
    }


    public static String suffix(HttpServletRequest req) {
        String suffix = StringUtils.substringAfter(req.getRequestURI(), ".");
        if(suffix.contains(Rule.COMPRESSION_MARK)) {
            return StringUtils.substringBefore(suffix, Rule.COMPRESSION_MARK);
        }
        return suffix;
    }

    public static boolean isImage(HttpServletRequest req) {
        return BlobFormat.isImage(suffix(req));
    }

}
