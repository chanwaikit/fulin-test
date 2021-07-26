package com.mazentop.plugins.resservlet.rule.impl;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import com.mazentop.plugins.resservlet.ResourcesServlet;
import com.mazentop.plugins.resservlet.rule.Rule;
import com.mazentop.plugins.resservlet.util.RuleUtils;
import com.mztframework.FileProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.util.ThumbnailatorUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Data
@Slf4j
public class ImagerRule extends Rule {

    public static final String COMPRESSION_SUFFIX = ".jpg";
    public static final String COMPRESSION_FORMAT = "jpg";
    public static final String HEADER_ACCEPT = "Accept";
    public static final String IMAGE_WEBP = "image/webp";
    public static final String FORMAT_WEBP = "webp";


    private int width = 0;

    private int height = 0;

    private File srcFile;

    private File cacheFile;

    private String filePath;

    private String fileName;

    private String format;

    private String ruleName = "none";


    private boolean scale = false;

    public ImagerRule(HttpServletRequest req, HttpServletResponse resp, FileProperties fileProperties) {
        this.req = req;
        this.resp = resp;
        this.fileProperties = fileProperties;
    }

    private void analyze() throws IOException {
        String uri = this.req.getRequestURI();
        String filePath = URLUtil.decode(StringUtils.removeStart(uri, ResourcesServlet.URL_MAPPING));
        this.format = RuleUtils.suffix(this.req).toLowerCase();
        if(!ThumbnailatorUtils.isSupportedOutputFormat(format)) {
            this.format = COMPRESSION_FORMAT;
        }
        if(filePath.contains(COMPRESSION_MARK)) {
            this.filePath = filePath.substring(0, filePath.lastIndexOf(COMPRESSION_MARK));
            this.ruleName = filePath.substring(filePath.lastIndexOf(COMPRESSION_MARK) + 1).toLowerCase();
            if(this.ruleName.contains(COMPRESSION_WIDTH_HEIGHT_GAP)) {
                this.width = toInt(this.ruleName.split(COMPRESSION_WIDTH_HEIGHT_GAP)[0]);
                this.height = toInt(this.ruleName.split(COMPRESSION_WIDTH_HEIGHT_GAP)[1]);
            }
        } else {
            this.filePath = filePath;
        }
        this.srcFile = file(this.filePath);

        if(!this.srcFile.exists()) {
            this.srcFile = new File(fileProperties.getNotFoundUrl());
        }
        this.fileName = RuleUtils.getFilePrefixName(this.srcFile.getName());
        // 图片裁剪
        scale();
    }

    private int toInt(String str) {
        if(INT_PATTERN.matcher(str).matches()) {
            return Integer.parseInt(str);
        }
        return 0;
    }

    private File cacheFile() {
        File cacheDir = new File(fileProperties.getLocalPath() +File.separator + "cache");
        return new File(cacheDir, parentPath() + this.fileName+"_"+this.ruleName + "." + geFormat());
    }

    private String parentPath() {
        return StringUtils.substringBefore(this.filePath, this.fileName);
    }

    private void scale() throws IOException {
        this.cacheFile = cacheFile();
        if(cacheFile.exists()) {
            return;
        }
        FileUtil.mkParentDirs(this.cacheFile);
        // gif 格式图片 不进行大小缩放
        boolean isDrawCanvas = !isGif() && (this.width > 0 || this.height > 0);
        if(isDrawCanvas) {
            Thumbnails.Builder<File> thumbnails =  Thumbnails.of(this.srcFile).outputQuality(0.9F);
            if(this.width > 0 && this.height > 0) {
                thumbnails.size(width, height);
                BufferedImage buffereImage = getCanvas(width, height);
                Graphics imgGraphics = buffereImage.getGraphics();
                BufferedImage originalImage  = thumbnails.asBufferedImage();
                //画布的大小
                try {
                    imgGraphics.drawImage(originalImage,
                            (this.width-originalImage.getWidth())/2,
                            (this.height-originalImage.getHeight())/2,
                            originalImage.getWidth(),
                            originalImage.getHeight(), null);
                    Thumbnails.of(buffereImage).scale(1L).outputQuality(1F).outputFormat(geFormat()).toFile(this.cacheFile);
                } finally {
                    imgGraphics.dispose();
                }
            } else {
                if(this.width > 0) {
                    thumbnails.width(this.width);
                }
                if(this.height > 0) {
                    thumbnails.height(this.height);
                }
                thumbnails.outputFormat(geFormat()).toFile(this.cacheFile);
            }
        } else {
            if(!isGif() && isCompress()) {
                Thumbnails.of(this.srcFile).scale(1L).outputQuality(0.9F).outputFormat(geFormat()).toFile(this.cacheFile);
            } else {
                this.cacheFile = this.srcFile;
            }
        }
    }

    private BufferedImage getCanvas(int width, int height){
        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.fillRect(0,0,width,height);
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0,width,height);
        g2.setColor(Color.RED);
        g2.setColor(Color.BLACK);
        return bi;
    }

    @Override
    public void write() throws IOException {
        analyze();
        this.processRequest(req, resp, this.cacheFile);
    }

    public boolean isCompress() {
        return FileUtil.size(this.srcFile) > 102400;
    }
    public boolean isGif() {
        System.out.println(FileTypeUtil.getType(this.srcFile));

        return !Objects.isNull(FileTypeUtil.getType(this.srcFile))&&FileTypeUtil.getType(this.srcFile).equals(ImgUtil.IMAGE_TYPE_GIF);
    }

    public boolean isSupportWebp() {
        return !Objects.isNull(this.req.getHeader(HEADER_ACCEPT)) && this.req.getHeader(HEADER_ACCEPT).contains(IMAGE_WEBP);
    }

    private String geFormat() {
        if(!isSupportWebp()) {
           return FORMAT_WEBP;
        }
        return format;
    }
}
