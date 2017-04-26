package io.github.spenhanley;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class TurtleMain extends Canvas implements Runnable
{
	
	private static final long serialVersionUID = 1L;
	
	// Draw started here
	private int startX;
	private int startY;
	private int lastX;
	private int lastY;

	private int r = 255;
	private int b = 255;
	
	private int width = 640;
	private int height = 640;
	
	private int rinc = 1;
	private int binc = 3;
	
	private boolean running = false;
	private boolean draw = false;
	
	private Turtle turtle;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private Thread thread;
	
	public TurtleMain()
	{
		turtle = new Turtle(width, height);
		new TurtleFrame(turtle);
		
		setSize(new Dimension(width, height));
		setBackground(Color.BLACK);
		setFocusable(true);
	}
	
	public void render()
	{		
		BufferStrategy bs = getBufferStrategy();
		
		if (bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2 = image.createGraphics();
		
		if (draw)
		{
			g2.setStroke(new BasicStroke(10));

			if (r >= 255 || r <= 0)
				rinc = -rinc;
			
			if (b >= 255 || b <= 0)
				binc = -binc;
			
			
			g2.setColor(new Color(127, 127, b));
			g2.drawLine(lastX, lastY, turtle.getOffsetX(), turtle.getOffsetY());
			
			lastX = (turtle.getOffsetX());
			lastY = (turtle.getOffsetY());
			r += rinc;
			b += binc;
		}
		
		g.setColor(Color.BLACK);
		
		g.clearRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		turtle.draw(g);
		
		g.dispose();
		bs.show();
	}
	
	public synchronized void start()
	{
		if (running)
			return;
		
		running = true;
		thread = new Thread(this, "Turtle");
		thread.start();
	}
	
	public synchronized void stop()
	{
		if (!running)
			return;
		
		running = false;
		try
		{
			thread.join();
		} catch (InterruptedException ie)
		{
			ie.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	public void run()
	{

		double ns = 1000000000.0 / 30.0;
		double delta = 0;
		
		int frames = 0;
		int updates = 0;
		
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
	
	    while (running) {
	    	long now = System.nanoTime();
	    	
	    	delta += (now - lastTime) / ns;
	    	lastTime = now;
	    	
	    	while(delta >= 1) {
	    		turtle.update();
	    		updates++;
	    		delta--;
	    	}
	    	
	      render();
	      frames++;
	      
	      if(System.currentTimeMillis() - timer >= 1000) {
	      	timer += 1000;
	      	frames = 0;
	      	updates = 0;
	      }
	    }
	    
	    stop();
	}
	
	public static void main(String[] args)
	{
		TurtleMain p = new TurtleMain();
		p.start();	
	}	
}