package io.github.spenhanley;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Turtle
{
	
	public static final int STOP = 0;
	public static final int MOVE_UP = 1;
	public static final int MOVE_DOWN = 2;
	public static final int MOVE_LEFT = 3;
	public static final int MOVE_RIGHT = 4;
	
	private Image turtleImage;
	
	private final int SPEED = 1;
	
	private int xSpeed = SPEED;
	private int ySpeed = SPEED;
	
	private int x;
	private int y;
	private int width;
	private int height;
	private int cycles;
	private int curCycles;
	private int imgWidth;
	private int imgHeight;
	private int deg; // Rotation
	private int dir = STOP; // Current direction
	
	public Turtle(int width, int height)
	{
		this(width, height, width/2, height/2);
	}
	
	public Turtle(int width, int height, int startX, int startY)
	{
		this.curCycles = 0;
		this.width = width;
		this.height = height;
		this.x = startX;
		this.y = startY;
		this.deg = 0;
		this.turtleImage = loadImage("res/Turtle.png");
		this.imgWidth = turtleImage.getWidth(null);
		this.imgHeight = turtleImage.getHeight(null);
	}
	
	public void draw(Graphics g)
	{
		BufferedImage bTurtleImage = toBufferedImage(turtleImage);
		
		g.drawImage(bTurtleImage, this.x, this.y, null);
	}
	
	private BufferedImage toBufferedImage(Image img)
	{
		if (img instanceof BufferedImage)
			return (BufferedImage) img;
		
		BufferedImage bImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bImage.createGraphics();
		
		g2.drawImage(img, 0, 0, null);
		g2.dispose();
		
		return bImage;
	}
	
	public void move(int dir, int cycles, int deg)
	{
		if (canMove(dir))
		{
			this.dir = dir;
			
			int validCycles = getValidCycles();
			
			if (cycles < validCycles)
				this.cycles = cycles;
			else 
				this.cycles = validCycles;
			
			if (dir == MOVE_UP) {
				this.ySpeed = -SPEED;
				this.xSpeed = 0;
			} else if (dir == MOVE_DOWN)
			{
				this.ySpeed = SPEED;
				this.xSpeed = 0;
			}
			
			if (dir == MOVE_LEFT) {
				this.xSpeed = -SPEED;
				this.ySpeed = 0;
			} else if (dir == MOVE_RIGHT) {
				this.xSpeed = SPEED;
				this.ySpeed = 0;
			}
			
		} else
		{
			ySpeed = 0;
			xSpeed = 0;
			cycles = 0;
			dir = STOP;
		}
	}
	
	private int getValidCycles() {
		if (dir == MOVE_UP)
			return y / SPEED;
		else if (dir == MOVE_DOWN)
			return (height - y) / SPEED;

		if (dir == MOVE_LEFT)
			return x / SPEED;
		else if (dir == MOVE_RIGHT)
			return (width - x) / SPEED;
		
		return 0;
	}

	private Image loadImage(String path)
	{
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void update()
	{
		this.x += this.xSpeed;
		this.y += this.ySpeed;
		
		if (canMove(dir)) {
			this.x += this.xSpeed;
			this.y += this.ySpeed;
		} else
		{
			dir = STOP;
		}

		if (dir == STOP) {
			this.xSpeed = 0;
			this.ySpeed = 0;
			this.curCycles = 0;
		}
		
		if ((cycles - curCycles) > getValidCycles())
			cycles = getValidCycles();
		
		if (cycles == 0)
			dir = STOP;
		
		if (curCycles < cycles)
			curCycles += 1;
		
		if (curCycles == cycles) {
			cycles = 0;
			curCycles = 0;
			dir = STOP;
		}
	}
	
	public boolean canMove(int dir)
	{
		return !(dir == MOVE_UP && this.y <= 0 || dir == MOVE_DOWN && (this.y + this.imgWidth) >= width || dir == MOVE_LEFT && this.x <= 0 || dir == MOVE_RIGHT && (this.x + this.imgHeight) >= height);
	}
	
	public int getOffsetX()
	{
		return this.x + (this.imgWidth / 2);
	}
	
	public int getOffsetY()
	{
		return this.y + (this.imgHeight / 2);
	}
	
	public int getDirection()
	{
		return dir;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
}