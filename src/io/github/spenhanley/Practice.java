package io.github.spenhanley;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class Practice extends Canvas implements Runnable
{
	
	private static final long serialVersionUID = 1L;
	
	// Draw started here
	private int startX;
	private int startY;
	private int lastX;
	private int lastY;

	private int r = 255;
	private int b = 255;
	
	private int width = 400;
	private int height = 400;
	
	private int rinc = 1;
	private int binc = 3;
	
	private boolean running = false;
	
	private final String TITLE = "Turtle Program";
	
	private JFrame frame;
	private Turtle turtle;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private Thread thread;
	private JPanel btnPanel, fieldPanel, container, canv;
	private JButton up, down, left, right;
	private JTextField cyclesField;
	private JLabel lblCycles;
	private JCheckBox chkDraw;
	
	public Practice()
	{
		frame = new JFrame();
		
		btnPanel = new JPanel();
		fieldPanel = new JPanel();
		container = new JPanel();
		canv = new JPanel();
		
		canv.add(this);
		canv.setSize(new Dimension(width, height));
		
		up = new JButton("Up");
		down = new JButton("Down");
		left = new JButton("Left");
		right = new JButton("Right");
		lblCycles = new JLabel("Cycles:");
		cyclesField = new JTextField(20);
		cyclesField.setText("0");
		
		chkDraw = new JCheckBox("Draw");
		
		btnPanel.add(up);
		btnPanel.add(down);
		btnPanel.add(left);
		btnPanel.add(right);
		
		fieldPanel.add(lblCycles);
		fieldPanel.add(cyclesField);
		fieldPanel.add(chkDraw);
		
		container.setLayout(new BorderLayout());
		container.add(btnPanel, BorderLayout.NORTH);
		container.add(canv, BorderLayout.CENTER);
		container.add(fieldPanel, BorderLayout.SOUTH);
		
		chkDraw.addActionListener((e) -> {
			if (chkDraw.isSelected()) {
				startX = turtle.getOffsetX();
				startY = turtle.getOffsetY();
				lastX = startX;
				lastY = startY;
			}
		});
		
		up.addActionListener((e) -> {
			String cyclesString = cyclesField.getText();
			cyclesField.setText(String.valueOf(0));
			int cycles;
			if (cyclesString.trim().length() > 0)
				cycles = Integer.parseInt(cyclesString);
			else
				cycles = 1;
			turtle.move(Turtle.MOVE_UP, cycles, 0);
		});
		
		down.addActionListener((e) -> {
			String cyclesString = cyclesField.getText();
			cyclesField.setText(String.valueOf(0));
			int cycles;
			if (cyclesString.trim().length() > 0)
				cycles = Integer.parseInt(cyclesString);
			else
				cycles = 1;
			turtle.move(Turtle.MOVE_DOWN, cycles, 180);
		});
		
		left.addActionListener((e) -> {
			String cyclesString = cyclesField.getText();
			cyclesField.setText(String.valueOf(0));
			int cycles;
			if (cyclesString.trim().length() > 0)
				cycles = Integer.parseInt(cyclesString);
			else
				cycles = 1;
			turtle.move(Turtle.MOVE_LEFT, cycles, 270);
		});
		
		right.addActionListener((e) -> {
			String cyclesString = cyclesField.getText();
			cyclesField.setText(String.valueOf(0));
			int cycles;
			if (cyclesString.trim().length() > 0)
				cycles = Integer.parseInt(cyclesString);
			else
				cycles = 1;
			turtle.move(Turtle.MOVE_RIGHT, cycles, 90);
		});
		
		turtle = new Turtle(width, height);
		
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
		
		if (chkDraw.isSelected())
		{
			g2.setStroke(new BasicStroke(10));
			Random rand = new Random();
			
			if (r >= 255 || r <= 0)
				rinc = -rinc;
			
			if (b >= 255 || b <= 0)
				binc = -binc;
			
			
			g2.setColor(new Color(r, 127, b));
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
		EventQueue.invokeLater(new Runnable(){
			public void run()
			{
				
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				
				Practice p = new Practice();
				p.frame.add(p.container);
				p.frame.setTitle(p.TITLE);
				p.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				p.frame.setLocationRelativeTo(null);
				p.frame.pack();
				p.frame.setVisible(true);
				
				p.start();	
			}
		});
	}
	
}