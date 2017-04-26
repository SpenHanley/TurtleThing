package io.github.spenhanley;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Turtle {

	public static final int STOP = 0;
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;

	private Image turtleImage;

	private final int SPEED = 6;

	private int xSpeed = SPEED;
	private int ySpeed = SPEED;

	private int x;
	private int y;
	private int width;
	private int height;
	private int cycles;
	private int curCycles;
	private int xOrigin;
	private int yOrigin;
	private int imgWidth;
	private int imgHeight;
	private int previousRot; // Rotation
	private int dir = STOP; // Current direction

	/**
	 * Create a new turtle instance
	 * @param width The width of the canvas
	 * @param height The height of the canvas
	 * */
	public Turtle(int width, int height) {
		this(width, height, width / 2, height / 2);
	}

	/**
	 * Create a new turtle instance
	 * @param width The width of the canvas
	 * @param height The height of the canvas
	 * @param startX The x-coordinate to place the turtle at
	 * @param startY The y-coordinate to place the turtle at
	 * */
	public Turtle(int width, int height, int startX, int startY) {
		this.curCycles = 0;
		this.width = width;
		this.height = height;
		this.turtleImage = ImageUtils.loadImage("res/Turtle.png");
		this.imgWidth = turtleImage.getWidth(null);
		this.imgHeight = turtleImage.getHeight(null);
		this.xOrigin = imgWidth / 2;
		this.yOrigin = imgHeight / 2;
		this.x = (startX - xOrigin);
		this.y = (startY - yOrigin);
		this.previousRot = 0;
	}

	public void draw(Graphics g) {
		BufferedImage bTurtleImage = ImageUtils.convertImage(turtleImage);

		g.drawImage(bTurtleImage, this.x, this.y, null);
	}

	/**
	 * Moves the turtle.
	 * 
	 * @param dir
	 *            The direction to move
	 * @param cycles
	 *            The number of cycles to move for
	 */
	public void move(int dir, int cycles) {
		if (canMove(dir)) {
			changeDir(dir);

			int validCycles = getValidCycles();

			if (cycles < validCycles)
				this.cycles = cycles;
			else
				this.cycles = validCycles;
		} else {
			ySpeed = 0;
			xSpeed = 0;
			cycles = 0;
			dir = STOP;
		}
	}

	/**
	 * Returns the number of valid cycles in the current direction
	 */
	private int getValidCycles() {
		if (dir == UP)
			return y / SPEED;
		else if (dir == DOWN)
			return (height - y) / SPEED;

		if (dir == LEFT)
			return x / SPEED;
		else if (dir == RIGHT)
			return (width - x) / SPEED;

		return 0;
	}

	// TODO: Simplify update method
	public void update() {
		this.x += this.xSpeed;
		this.y += this.ySpeed;

		if (canMove(dir)) {
			this.x += this.xSpeed;
			this.y += this.ySpeed;
		} else {
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

	public void changeDir(int dir)
	{
		this.dir = dir;
		if (dir == UP)
		{
			this.ySpeed = -SPEED;
			this.xSpeed = 0;
			if (previousRot != 0) {
				turtleImage = ImageUtils.rotateImage(turtleImage, -previousRot);
			}
			previousRot = 0;
		} else if (dir == RIGHT)
		{
			this.xSpeed = SPEED;
			this.ySpeed = 0;
			if (previousRot != 90) {
				turtleImage = ImageUtils.rotateImage(turtleImage, -previousRot);
				turtleImage = ImageUtils.rotateImage(turtleImage, 90);
			}
			previousRot = 90;
		} else if (dir == DOWN)
		{
			this.ySpeed = SPEED;
			this.xSpeed = 0;
			if (previousRot != 180) {
				turtleImage = ImageUtils.rotateImage(turtleImage, -previousRot);
				turtleImage = ImageUtils.rotateImage(turtleImage, 180);
			}
			previousRot = 180;
		} else if (dir == LEFT)
		{
			this.ySpeed = 0;
			this.xSpeed = -SPEED;
			if (previousRot != 270) {
				turtleImage = ImageUtils.rotateImage(turtleImage, -previousRot);
				turtleImage = ImageUtils.rotateImage(turtleImage, 270);
			}
			previousRot = 270;
		}
	}

	public boolean canMove(int dir) {
		return !(dir == UP && this.y <= 0 || dir == DOWN && (this.y + this.imgWidth) >= width
				|| dir == LEFT && this.x <= 0 || dir == RIGHT && (this.x + this.imgHeight) >= height);
	}

	public int getOffsetX() {
		return this.x + this.xOrigin;
	}

	public int getOffsetY() {
		return this.y + this.yOrigin;
	}

	public int getDirection() {
		return dir;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

}