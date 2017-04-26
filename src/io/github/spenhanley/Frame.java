package io.github.spenhanley;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

public class Frame extends JFrame
{
	
		
	
	private JMenuBar constructMenu()
	{
		JMenuBar mBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem loadScript = new JMenuItem("Load Script");
		JMenuItem saveScript = new JMenuItem("Save Script");
		
		fileMenu.add(loadScript);
		fileMenu.add(saveScript);
		
		mBar.add(fileMenu);
		
		return mBar;
	}
	
	public Frame()
	{
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
		setJMenuBar(constructMenu());
		setVisible(true);
	}
	
}