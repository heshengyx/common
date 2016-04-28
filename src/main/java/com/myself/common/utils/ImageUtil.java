package com.myself.common.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.myself.common.exception.ServiceException;

public class ImageUtil {

	public static String creMinImage(String src, int width, int height, String path) throws Exception {
		String image = null;
		File file = new File(path + src); // 查找源图片文件
		if (file.exists()) {
			String suffix = FileUtil.getFileSuffix(src); // 取得源图片的后缀名
			image = src.substring(0, src.indexOf(suffix)) + "_" + width + "X" + height + suffix; // 创建新文件名
			File nfile = new File(path + image); // 查找小图片文件
			if (!nfile.exists()) { // 小图不存在则创建
				image = drawImage(file, nfile, width, height, suffix); // 创建缩小图片,返回图片路径
				if (image != null)
					image = image.substring(path.length(), image.length()).replaceAll("\\\\", "/");
			}
		}
		return image;
	}

	private static String drawImage(File fileImageSrc, File fileImageOut, int width, int height, String suffix) {
		String filePath = null;
		Image image = null;
		OutputStream output = null;
		try {
			image = javax.imageio.ImageIO.read(fileImageSrc); // 创建源图片对象
			ImageVo imageVo = getImageVo(width, height, image);

			if (imageVo.isFlag()) {
				BufferedImage bufferedImage = new BufferedImage(imageVo.getWidth(), imageVo.getHeight(),
						BufferedImage.TYPE_INT_RGB);
				Graphics g = bufferedImage.createGraphics();
				// g.drawImage(image, 0, 0, imageVo.getWidth(), imageVo.getHeight(),
				// null); // 绘制缩小后的图
				g.drawImage(image.getScaledInstance(imageVo.getWidth(), imageVo.getHeight(), Image.SCALE_SMOOTH), 0, 0,
						null);

				byte[] bytes = createImage(bufferedImage);
				output = new FileOutputStream(fileImageOut);
				output.write(bytes);
				filePath = fileImageOut.getPath();
			} else {
				image.flush();
				filePath = fileImageSrc.getPath();
			}
		} catch (Exception e) {
			filePath = null;
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				filePath = null;
			}
		}
		return filePath;
	}

	private static byte[] createImage(BufferedImage bufferedImage) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "jpeg", out);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("图片写入出错");
		}
		return out.toByteArray();
	}

	/**
	 * 根据宽度或高度,定义缩放后新的宽度或高度
	 */
	private static ImageVo getImageVo(int width, int height, Image image) {
		ImageVo imageVo = new ImageVo();
		boolean flag = false;
		int srcw = image.getWidth(null); // 得到源图像宽
		int srch = image.getHeight(null); // 得到源图像高
		
		double scale = (double) width / height; //图像的高宽比例
		double ratio = (double) srcw / srch; // 源图像的高宽比例
		
		if (srcw > width || srch > height) {
			if ((int) ratio >= (int) scale) {
				height = (int) ((double) width / ratio); // 重新设置图片的高
			} else {
				width = (int) ((double) height * ratio); // 重新设置图片的高
			}
			flag = true;
		}
		imageVo.setWidth(width);
		imageVo.setHeight(height);
		imageVo.setFlag(flag);
		return imageVo;
	}
	
	public static void main(String[] args) {
		System.out.println((double) 10/3);
		
		String url = "temps/image-1.jpg";
		try {
			url = ImageUtil.creMinImage(url, 205, 150, "E:/images/");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("url=========" + url);
	}
}

class ImageVo {
	private int width;
	private int height;
	private boolean flag = false; // 判断是否要生成小图

	public ImageVo() {
		super();
	}

	public ImageVo(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
