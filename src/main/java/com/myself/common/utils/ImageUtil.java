package com.myself.common.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class ImageUtil {

	public static String creMinImage(String imageSrc, int width, int height,
			String path) throws Exception {
		File file = new File(path + imageSrc); // 查找源图片文件
		if (file.exists()) {
			String suffix = FileUtil.getFileSuffix(imageSrc); // 取得源图片的后缀名
			int wh = 0;
			if (width > 0) {
				wh = width;
			}
			if (height > 0) {
				wh = height;
			}
			String img = imageSrc.substring(0, imageSrc.indexOf(suffix)) + "_"
					+ wh + suffix; // 创建新文件名
			File nfile = new File(path + img); // 查找小图片文件
			if (!nfile.exists()) { // 小图不存在则创建
				img = drawImage(file, nfile, width, height, suffix); // 创建缩小图片,返回图片路径
				if (img != null)
					img = img.substring(path.length(), img.length())
							.replaceAll("\\\\", "/");
			}
			return img;
		}
		return "";
	}

	private static String drawImage(File fileImageSrc, File fileImageOut,
			int width, int height, String suffix) {
		String filePath = null;
		Image image = null;
		OutputStream output = null;
		try {
			image = javax.imageio.ImageIO.read(fileImageSrc); // 创建源图片对象
			ImageVo imageVo = getOutImageVo(new ImageVo(width, height), image);

			if (imageVo.isFlag()) {
				image.flush();
				return fileImageSrc.getPath();
			}

			BufferedImage bufferedImage = new BufferedImage(imageVo.getWidth(),
					imageVo.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics g = bufferedImage.createGraphics();
			//g.drawImage(image, 0, 0, imageVo.getWidth(), imageVo.getHeight(), null); // 绘制缩小后的图
			g.drawImage(image.getScaledInstance(imageVo.getWidth(), imageVo.getHeight(), Image.SCALE_SMOOTH), 0, 0,  null); 
			
			byte[] bytes = createImage(bufferedImage);
			output = new FileOutputStream(fileImageOut);
			output.write(bytes);

			filePath = fileImageOut.getPath();
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

	private static byte[] createImage(BufferedImage bufferedImage)
			throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "jpeg", out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	/**
	 * 根据宽度或高度,定义缩放后新的宽度或高度
	 */
	private static ImageVo getOutImageVo(ImageVo imageVo, Image image) {
		boolean flag = false;
		int width = imageVo.getWidth(); // 得到设置图像宽
		int height = imageVo.getHeight(); // 得到设置图像高
		int srcw = image.getWidth(null); // 得到源图像宽
		int srch = image.getHeight(null); // 得到源图像高
		double scale = (double) srcw / srch; // 源图像的高宽比例

		if (width > 0 && height > 0) {
			if (srcw > srch) {
				if (srcw > width) { // 如果源图像宽比新设置的大,那就要缩放源图片的宽和高
					height = (int) ((double) width / scale); // 重新设置图片的高
					if (height == 0)
						height = srch;
				} else {
					width = srcw;
					height = srch;
					flag = true;
				}
			} else {
				if (srch > height) { // 如果源图像高比新设置的大,那就要缩放源图片的宽和高
					width = (int) ((double) height * scale); // 重新设置图片的宽
					if (width == 0)
						width = srcw;
				} else {
					width = srcw;
					height = srch;
					flag = true;
				}
			}
		} else if (width > 0) { //如果只设置了宽度,且大于0
			if (srcw > width) { // 如果源图像宽比新设置的大,那就要缩放源图片的宽和高
				height = (int) ((double) width / scale); // 重新设置图片的高
				if (height == 0)
					height = srch;
			} else {
				flag = true;
			}
		} else if (height > 0) {
			if (srch > height) { // 如果源图像高比新设置的大,那就要缩放源图片的宽和高
				width = (int) ((double) height * scale); // 重新设置图片的宽
				if (width == 0)
					width = srcw;
			} else {
				flag = true;
			}
		} else {
			width = srcw;
			height = srch;
			flag = true;
		}
		imageVo.setWidth(width);
		imageVo.setHeight(height);
		imageVo.setFlag(flag);
		return imageVo;
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
