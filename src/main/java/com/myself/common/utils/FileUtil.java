package com.myself.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class FileUtil {
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy/MM/dd");
	public static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat(
			"yyyy-MM");
	public static final int SUCCESS = 1;
	public static final int FAIL = 2;
	public static final int ERROR = 3;
	public static final String[] HEX_DIGITS = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	public static final String TEMP_FOLDER = "temps";
	public static final String UPLOAD_FOLDER = "uploads";
	private static final char zeroArray[] = "00000000000000000".toCharArray();

	public static String getDateFilePath() {
		return DATE_FORMAT.format(new Date());
	}

	public static String getMonthFilePath() {
		return MONTH_FORMAT.format(new Date());
	}

	public static String getFileName() {
		return UIDGeneratorUtil.getUID();
	}
	
	public static String getFileSystemNanoTimeName() {
		return String.valueOf(System.nanoTime());
	}

	private static String getPath(String path) {
		File file = new File(path);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		return path;
	}

	public static String getMonthFileFolder(String root) {
		String path = root + "/" + getMonthFilePath();
		return getPath(path);
	}

	public static String getDateFileFolder(String root) {
		String path = root + "/" + getDateFilePath();
		return getPath(path);
	}

	public static String getRootFileFolder(String root) {
		return getPath(root);
	}

	public static String getFileSuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.'), fileName.length())
				.toLowerCase();
	}

	public static File uploadFile(InputStream in, String fileFolder,
			String fileName, String suffix) throws Exception {
		File file = null;
		OutputStream os = null;
		try {
			file = new File(fileFolder + "/" + fileName + suffix);
			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[4096];
			while ((bytesRead = in.read(buffer, 0, 4096)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			file = null;
			e.printStackTrace();
		} finally {
			try {
				os.close();
				in.close();
			} catch (Exception e) {
			}
		}
		return file;
	}

	public static File uploadFile(String srcFile, String fileFolder,
			String fileName) throws Exception {
		File file = new File(srcFile);
		if (file.exists()) {
			InputStream in = new FileInputStream(file);
			OutputStream os = null;
			try {
				file = new File(fileFolder + "/" + fileName);
				os = new FileOutputStream(file);
				int bytesRead = 0;
				byte[] buffer = new byte[4096];
				while ((bytesRead = in.read(buffer, 0, 4096)) != -1) {
					os.write(buffer, 0, bytesRead);
				}

			} catch (Exception e) {
				file = null;
				e.printStackTrace();
			} finally {
				try {
					os.close();
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return file;
	}
	
	public static boolean downLoadFile(OutputStream os, String fileName,
			String fileFolder) {
		InputStream in = null;
		OutputStream out = null;
		try {
			File file = new File(fileFolder + fileName);
			if (file.exists()) {
				in = new BufferedInputStream(new FileInputStream(fileFolder + fileName));
				out = new BufferedOutputStream(os);
				int bytesRead = 0;
				byte[] buffer = new byte[4096];
				
				while ((bytesRead = in.read(buffer, 0, 4096)) > 0) {
					out.write(buffer, 0, bytesRead);
				}
				out.write(buffer);
				out.flush();
				return true;
			} else {
				System.out.println("");
				return false;
			}
		} catch (Exception e) {
			return false;
		} finally {
			try {
				out.close();
				out = null;
			} catch (Exception e) {
				return false;
			}
			try {
				in.close();
				in = null;
			} catch (Exception e) {
				return false;
			}
		}
	}

	public static boolean deleteFile(String path) {
		boolean flag = false;
		try {
			File file = new File(path);
			if (file.exists()) {
				flag = file.delete();
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return HEX_DIGITS[d1] + HEX_DIGITS[d2];
	}

	public static String MD5Encode(String origin) {
		String resultString = null;

		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {
			resultString = null;
		}
		return resultString;
	}

	public static final String zeroPad(String code, int length) {
		String result = null;
		if (code != null) {
			if (code.length() > length) {
				result = code;
			} else {
				StringBuffer buf = new StringBuffer(length);
				buf.append(zeroArray, 0, length - code.length()).append(code);
				result = buf.toString();
			}
		}
		return result;
	}
	
	public static String getStringTokenizer(String tokens, String delim, String sign) {
		String content = "";
		if (tokens != null && !"".equals(tokens)) {
			StringBuilder sb = new StringBuilder();
			
			StringTokenizer st = new StringTokenizer(tokens, delim);
			for (int i = 0; st.hasMoreTokens(); i++) {
				sb.append(st.nextToken()).append("/");
			}
			content = sb.toString();
		}
		return content;
	}
	
	public static boolean move(File srcFile, String destPath) {
		boolean success = false;
		try {
			File dir = new File(destPath);
			if (!dir.isDirectory()) {
				dir.mkdirs();
			}
			success = srcFile.renameTo(new File(dir, srcFile.getName()));
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}
}
