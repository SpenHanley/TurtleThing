package io.github.spenhanley;

import java.awt.Image;

public class Turtle
{
	
	public static final int STOP = 0;
	public static final int MOVE_UP = 1;
	public static final int MOVE_DOWN = 2;
	public static final int MOVE_LEFT = 3;
	public static final int MOVE_RIGHT = 4;
	
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
	
	private int dir = STOP;
	
	public Turtle(int width, int height, Image img)
	{
		this(width, height, width/2, height/2);
		this.imgWidth = img.getWidth(null);
		this.imgHeight = img.getHeight(null);
	}
	
	public Turtle(int width, int height, int startX, int startY)
	{
		this.curCycles = 0;
		this.width = width;
		this.height = height;
		this.x = startX;
		this.y = startY;
	}
	
	public void move(int dir, int cycles)
	{
		this.cycles = cycles;
		if (canMove(dir))
		{
			this.dir = dir;
			
			if (dir == MOVE_UP) {
				this.ySpeed = -5;
				this.xSpeed = 0;
			} else if (dir == MOVE_DOWN)
			{
				this.ySpeed = 5;
				this.xSpeed = 0;
			}
			
			if (dir == MOVE_LEFT) {
				this.xSpeed = -5;
				this.ySpeed = 0;
			} else if (dir == MOVE_RIGHT) {
				this.xSpeed = 5;
				this.ySpeed = 0;
			}
			this.cycles = cycles;
		}
	}
	
	public void update()
	{
		this.x += this.xSpeed;
		this.y += this.ySpeed;

		if (dir == STOP) {
			this.xSpeed = 0;
			this.ySpeed = 0;
			this.curCycles = 0;
		}
		
		if (cycles == 0)
			dir = STOP;
		
		curCycles += 1;
		if (curCycles == cycles) {
			cycles = 0;
			curCycles = 0;
			dir = STOP;
		}
	}
	
	public boolean canMove(int dir)
	{
		if (dir == MOVE_UP && this.y <= 0 || dir == MOVE_DOWN && (this.y + this.imgWidth) >= width)
		{
			System.err.println("Out of bounds Y");
			dir = STOP;
			cycles = 0;
			return false;
		}
		
		if (dir == MOVE_LEFT && this.x <= 0 || dir == MOVE_RIGHT && (this.x + this.imgHeight) >= height)
		{
			System.err.println("Out of bounds X");
			dir = STOP;
			cycles = 0;
			return false;
		}
		return true;
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