package com.mazentop.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.*;
import java.util.Map.Entry;
import java.util.zip.*;

public class FileUtil {
	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static void unZipFiles(String zipPath, String descDir) throws IOException {
		File pathFile = new File(descDir);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		ZipFile zipFile = new ZipFile(zipPath);
		Enumeration<? extends ZipEntry> entrys = zipFile.entries();
		while (entrys.hasMoreElements()) {
			ZipEntry entry = entrys.nextElement();
			String zipEntryName = entry.getName();
			zipEntryName = zipEntryName.substring(zipEntryName.lastIndexOf("/"));
			String outPath = descDir + zipEntryName;
			OutputStream out = new FileOutputStream(outPath);
			InputStream in = zipFile.getInputStream(entry);
			byte[] bt = new byte[in.available()];
			in.read(bt);
			in.close();
			out.write(bt);
			out.flush();
			out.close();
		}
		zipFile.close();
	}

	public static void createZipFile(File file, OutputStream os) throws IOException {
		CheckedOutputStream cos = new CheckedOutputStream(os, new Adler32());
		ZipOutputStream zos = new ZipOutputStream(cos);
		BufferedOutputStream bos = new BufferedOutputStream(zos);
		zos.setLevel(9);
		zos.setComment("JAVA ZIP compressed files");
		if (file.isFile()) {
			createZipFolder("", bos, zos, file);
		} else {
			File[] files = file.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					createZipFolder(Helper.unite(file.getName(), "/"), bos, zos, files[i]);
				}
			}
		}
		bos.close();
	}

	public static boolean createZipFile(String path, String filename, String... paths) {
		if (createFolder(path)) {
			try {
				String allPath = Helper.unite(path, File.separator, filename, ".zip");
				FileOutputStream f = new FileOutputStream(allPath);
				CheckedOutputStream csum = new CheckedOutputStream(f, new Adler32());
				ZipOutputStream zos = new ZipOutputStream(csum);
				BufferedOutputStream out = new BufferedOutputStream(zos);
				zos.setComment("JAVA ZIP compressed files");
				for (int i = 0; i < paths.length; i++) {
					FileInputStream fis = new FileInputStream(paths[i]);
					byte[] bf = new byte[fis.available()];
					zos.putNextEntry(new ZipEntry(paths[i].substring(paths[i].lastIndexOf(File.separator) + 1)));
					fis.read(bf);
					out.write(bf);
					out.flush();
					fis.close();
				}
				out.close();
				return true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return false;
	}

	public static byte[] createZipFileBytes(byte[] file, String name) {
		try {
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			CheckedOutputStream csum = new CheckedOutputStream(byteArray, new Adler32());
			ZipOutputStream zos = new ZipOutputStream(csum);
			BufferedOutputStream out = new BufferedOutputStream(zos);
			zos.putNextEntry(new ZipEntry(name));
			out.write(file);
			out.close();
			return byteArray.toByteArray();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static byte[] createZipFileBytes(Map<String, byte[]> files) {
		try {
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			CheckedOutputStream csum = new CheckedOutputStream(byteArray, new Adler32());
			ZipOutputStream zos = new ZipOutputStream(csum);
			BufferedOutputStream out = new BufferedOutputStream(zos);
			Iterator<Entry<String, byte[]>> iterator = files.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, byte[]> entry = iterator.next();
				zos.putNextEntry(new ZipEntry(entry.getKey()));
				out.write(entry.getValue());
				out.flush();
			}
			out.close();
			return byteArray.toByteArray();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static void createZipFilePath(Map<String, String> files, String outPath) {
		try {
			FileOutputStream fos = new FileOutputStream(outPath);
			CheckedOutputStream csum = new CheckedOutputStream(fos, new Adler32());
			ZipOutputStream zos = new ZipOutputStream(csum);
			BufferedOutputStream out = new BufferedOutputStream(zos);
			Iterator<Entry<String, String>> iterator = files.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				if (Helper.isNotEmpty(entry.getValue())) {
					zos.putNextEntry(new ZipEntry(entry.getKey()));
					FileInputStream fis = new FileInputStream(entry.getValue());
					byte[] temp = new byte[1024];
					int size = 0;
					while ((size = fis.read(temp)) != -1) {
						out.write(temp, 0, size);
					}
					out.flush();
					fis.close();
					new File(entry.getValue()).delete();
				}
			}
			out.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static List<byte[]> readZipFile(byte[] zipFile) {
		List<byte[]> byteList = new ArrayList<>();
		try {
			ByteArrayInputStream byteArray = new ByteArrayInputStream(zipFile);
			CheckedInputStream csumi = new CheckedInputStream(byteArray, new Adler32());
			ZipInputStream in2 = new ZipInputStream(csumi);
			BufferedInputStream bis = new BufferedInputStream(in2);
			while ((in2.getNextEntry()) != null) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] bytes = new byte[10240];
				int len = 0;
				while ((len = bis.read(bytes)) != -1) {
					baos.write(bytes, 0, len);
				}
				byteList.add(baos.toByteArray());
				baos.close();
			}
			bis.close();
			return byteList;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public static void findPathAllFile(Set<String> fileSet, String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (file.isDirectory()) {
			String[] paths = file.list();
			if (paths != null) {
				for (int i = 0; i < paths.length; i++) {
					String nextPath = file.getPath();
					if (nextPath.endsWith(File.separator)) {
						nextPath = Helper.unite(file.getPath(), paths[i]);
					} else {
						nextPath = Helper.unite(file.getPath(), File.separator, paths[i]);
					}
					findPathAllFile(fileSet, nextPath);
				}
			}
		} else {
			fileSet.add(file.getPath());
		}
	}

	public static void findFile(File file, List<File> fileList, String condition) {
		if (!file.exists()) {
			return;
		}
		if (file.getName().indexOf(condition) > -1) {
			fileList.add(file);
		}
		if (!file.isFile()) {
			File[] files = file.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					findFile(files[i], fileList, condition);
				}
			}
		}
	}

	public static long fileSize(final File file) {
		if (!file.exists()) {
			return 0L;
		}
		if (file.isFile()) {
			return file.length();
		} else {
			long size = 0L;
			final File[] files = file.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					size += fileSize(files[i]);
				}
			}
			return size;
		}
	}

	public static long fileNum(final File file) {
		if (!file.exists()) {
			return 0L;
		}
		if (file.isFile()) {
			return 1L;
		} else {
			long num = 0L;
			final File[] files = file.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					num += fileNum(files[i]);
				}
			}
			return num;
		}
	}

	public static void deleteFiles(String path) {
		File f = new File(path);
		if (f.isFile()) {
			f.delete();
		} else {
			String[] names = f.list();
			if (names != null) {
				for (int i = 0; i < names.length; i++) {
					deleteFiles(path + File.separator + names[i]);
				}
			}
			f.delete();
		}
	}

	public static boolean createFolder(String path) {
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		return f.exists();
	}

	public static void folderCopy(String fromPath, String toPath) throws IOException {
		File f = new File(fromPath);
		if (f.isFile()) {
			fileCopy(fromPath, toPath);
		} else {
			File t = new File(toPath);
			if (!t.exists()) {
				t.mkdirs();
			}
			String[] names = f.list();
			if (names != null) {
				for (int i = 0; i < names.length; i++) {
					folderCopy(fromPath + File.separator + names[i], toPath + File.separator + names[i]);
				}
			}
		}
	}

	public static void fileCopy(String fromPath, String toPath) throws IOException {
		FileInputStream fis = new FileInputStream(fromPath);
		FileOutputStream fos = new FileOutputStream(toPath);
		FileChannel from = fis.getChannel();
		FileChannel to = fos.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(fis.available());
		from.read(buffer);
		buffer.flip();
		to.write(buffer);
		from.close();
		to.close();
		fis.close();
		fos.close();
	}

	public static byte[] readBytes(InputStream in) throws IOException {
		BufferedInputStream bufin = new BufferedInputStream(in);
		int buffSize = 1024;
		ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);
		byte[] temp = new byte[buffSize];
		int size = 0;
		while ((size = bufin.read(temp)) != -1) {
			out.write(temp, 0, size);
		}
		byte[] content = out.toByteArray();
		bufin.close();
		out.close();
		return content;
	}

	public static void writeInputStream(OutputStream os, InputStream is) throws IOException {
		BufferedInputStream bufin = new BufferedInputStream(is);
		byte[] temp = new byte[1024];
		int size = 0;
		while ((size = bufin.read(temp)) != -1) {
			os.write(temp, 0, size);
		}
		bufin.close();
	}

	public static void writeReader(Writer w, Reader r) throws IOException {
		BufferedReader br = new BufferedReader(r);
		char[] temp = new char[512];
		int size = 0;
		while ((size = br.read(temp)) != -1) {
			w.write(temp, 0, size);
		}
		br.close();
	}

	public static byte[] objectToBytes(Serializable object) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(object);
		byte[] bytes = baos.toByteArray();
		oos.close();
		baos.close();
		return bytes;
	}

	public static Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object tmpObject = ois.readObject();
		ois.close();
		bais.close();
		return tmpObject;
	}

	public static byte[] mergeBytes(byte[] thisBytes, byte[] thatBytes) {
		return ByteBuffer.allocate(thisBytes.length + thatBytes.length).put(thisBytes).put(thatBytes).array();
	}

	public static byte[] subBytes(byte[] original, int beginIndex, int endIndex) {
		if (beginIndex < 0 || endIndex > original.length) {
			return original;
		} else {
			return Arrays.copyOfRange(original, beginIndex, endIndex);
		}
	}

	public static byte[] fillZero(byte[] bytes, int length) {
		byte[] newBytes = new byte[length];
		for (int i = 0; i < newBytes.length; i++) {
			if (i < bytes.length) {
				newBytes[i] = bytes[i];
			} else {
				newBytes[i] = 0;
			}
		}
		return newBytes;
	}

	public static byte[] trimZero(byte[] bytes) {
		int length = 0;
		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] == 0) {
				length = i;
				break;
			}
		}
		byte[] newBytes = new byte[length];
		for (int i = 0; i < newBytes.length; i++) {
			newBytes[i] = bytes[i];
		}
		return newBytes;
	}

	private static void createZipFolder(String path, BufferedOutputStream bos, ZipOutputStream zos, File file) throws IOException {
		if (file.isFile()) {
			ZipEntry zipEntry = new ZipEntry(Helper.unite(path, file.getName()));
			zos.putNextEntry(zipEntry);
			if (file.lastModified() != 0L) {
				zipEntry.setTime(file.lastModified());
			}
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			byte[] bf = new byte[40960];
			int size = 0;
			while ((size = bis.read(bf)) != -1) {
				bos.write(bf, 0, size);
			}
			bos.flush();
			bis.close();
		} else {
			String thisPath = Helper.unite(path, file.getName(), "/");
			ZipEntry zipEntry = new ZipEntry(thisPath);
			if (file.lastModified() != 0L) {
				zipEntry.setTime(file.lastModified());
			}
			zos.putNextEntry(zipEntry);
			zos.closeEntry();
			File[] files = file.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					createZipFolder(thisPath, bos, zos, files[i]);
				}
			}
		}
	}

	public static byte[] imageCompress(byte[] inBytes) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(inBytes);
		BufferedImage inImage = ImageIO.read(bais);

		int width = 600;
		int height = 600;

		if (inImage.getHeight() > 1800 && inImage.getHeight() > 1800) {
			width = inImage.getWidth() / 3;
			height = inImage.getHeight() / 3;
		} else {
			BigDecimal inHeight = new BigDecimal(inImage.getHeight());
			BigDecimal inWidth = new BigDecimal(inImage.getWidth());
			if (inHeight.compareTo(inWidth) > 0) {
				height = inHeight.divide(inWidth, 5, BigDecimal.ROUND_UP).multiply(new BigDecimal(height)).intValue();
			} else {
				width = inWidth.divide(inHeight, 5, BigDecimal.ROUND_UP).multiply(new BigDecimal(width)).intValue();
			}
		}

		BufferedImage outImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics2D graphics = outImage.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		graphics.drawImage(inImage, 0, 0, width, height, null);
		graphics.dispose();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(outImage, "JPEG", baos);
		return baos.toByteArray();
	}

}