package io.github.spenhanley;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Practice extends Canvas implements Runnable
{
	
	private static final long serialVersionUID = 1L;
	
	private int width = 400;
	private int height = 400;
	private boolean running = false;
	
	private final String TITLE = "Turtle Program";
	
	private JFrame frame;
	private Turtle turtle;
	private Image turtleImage;
	private Thread thread;
	private JPanel btnPanel, fieldPanel, container, canv;
	private JButton up, down, left, right;
	private JTextField cyclesField;
	private JLabel lblCycles;
	
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
		
		btnPanel.add(up);
		btnPanel.add(down);
		btnPanel.add(left);
		btnPanel.add(right);
		
		fieldPanel.add(lblCycles);
		fieldPanel.add(cyclesField);
		
		container.setLayout(new BorderLayout());
		container.add(btnPanel, BorderLayout.NORTH);
		container.add(canv, BorderLayout.CENTER);
		container.add(fieldPanel, BorderLayout.SOUTH);
		
		up.addActionListener((e) -> {
			String cyclesString = cyclesField.getText();
			cyclesField.setText(String.valueOf(0));
			int cycles;
			if (cyclesString.trim().length() > 0)
				cycles = Integer.parseInt(cyclesString);
			else
				cycles = 1;
			turtle.move(Turtle.MOVE_UP, cycles);
		});
		
		down.addActionListener((e) -> {
			String cyclesString = cyclesField.getText();
			cyclesField.setText(String.valueOf(0));
			int cycles;
			if (cyclesString.trim().length() > 0)
				cycles = Integer.parseInt(cyclesString);
			else
				cycles = 1;
			turtle.move(Turtle.MOVE_DOWN, cycles);
		});
		
		left.addActionListener((e) -> {
			String cyclesString = cyclesField.getText();
			cyclesField.setText(String.valueOf(0));
			int cycles;
			if (cyclesString.trim().length() > 0)
				cycles = Integer.parseInt(cyclesString);
			else
				cycles = 1;
			turtle.move(Turtle.MOVE_LEFT, cycles);
		});
		
		right.addActionListener((e) -> {
			String cyclesString = cyclesField.getText();
			cyclesField.setText(String.valueOf(0));
			int cycles;
			if (cyclesString.trim().length() > 0)
				cycles = Integer.parseInt(cyclesString);
			else
				cycles = 1;
			turtle.move(Turtle.MOVE_RIGHT, cycles);
		});
		
		turtleImage = loadImage("res/Turtle.png");
		if (turtleImage == null) {
			System.err.println("Could not load turtle!");
			System.exit(1);
		}
		turtle = new Turtle(width, height, turtleImage);
		
		setSize(new Dimension(width, height));
		setBackground(Color.BLACK);
		setFocusable(true);
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
	
	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
		
		if (bs == null)
		{
			createBufferStrategy(3);
			return;
		}
				
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		
		g.clearRect(0, 0, getWidth(), getHeight());
		g.drawImage(turtleImage, turtle.getX(), turtle.getY(), null);
		
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
	
	@SuppressWarnings("static-access")
	public void run()
	{
		while (running)
		{
			render();
			// Temporary
			turtle.update();
			try {
				thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args)
	{
		Practice p = new Practice();
		
		p.frame.add(p.container);
		p.frame.setTitle(p.TITLE);
		p.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		p.frame.setLocationRelativeTo(null);
		p.frame.pack();
		p.frame.setVisible(true);
		
		p.start();
	}
	
}