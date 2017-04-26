package io.github.spenhanley;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils
{

	public static Image loadImage(String path)
	{
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static BufferedImage rotateImage(Image img, int degrees)
	{
		BufferedImage image = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		
		if (degrees != 0 || degrees != 180)
			image = new BufferedImage(img.getHeight(null), img.getWidth(null), BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = image.createGraphics();
		
		if (degrees == 0)
			return convertImage(img);

		g2.translate(img.getWidth(null), img.getHeight(null));
		g2.rotate(Math.toRadians(degrees));
		g2.translate(0, 0);
		g2.drawImage(convertImage(img), 0, 0, null);
		
		g2.dispose();
		
		return image;
	}
	
	public static BufferedImage convertImage(Image img)
	{
		if (img instanceof BufferedImage)
			return (BufferedImage) img;
		
		BufferedImage image = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2 = image.createGraphics();
		
		g2.drawImage(img, 0, 0, null);
		g2.dispose();
		
		return image;
	}
	
}