package io.github.spenhanley;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class TurtleFrame extends JFrame
{
	
	private JButton btnUp;
	private JButton btnDown;
	private JButton btnLeft;
	private JButton btnRight;
	
	private JMenuItem loadScript;
	private JMenuItem saveScript;
	
	private JCheckBox chkDoDraw;
	
	private Turtle turtle;
	
	public boolean doDraw()
	{
		return chkDoDraw.isSelected();
	}
	
	private JPanel createButtonPanel()
	{
		JPanel panel = new JPanel();
		
		btnUp = new JButton("UP");
		btnDown = new JButton("DOWN");
		btnLeft = new JButton("LEFT");
		btnRight = new JButton("RIGHT");
		
		return panel;
	}
	
	private void addActionListeners()
	{
		btnUp.addActionListener((e) -> {
		});
		
		btnDown.addActionListener((e) -> {
		});
		
		btnLeft.addActionListener((e) -> {
		});
		
		btnRight.addActionListener((e) -> {
		});
		
		loadScript.addActionListener((e) -> {
		});
		
		saveScript.addActionListener((e) -> {
		});

	}
	
	private JMenuBar constructMenu()
	{
		JMenuBar mBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		
		loadScript = new JMenuItem("Load Script");
		saveScript = new JMenuItem("Save Script");
		
		fileMenu.add(loadScript);
		fileMenu.add(saveScript);
		
		mBar.add(fileMenu);
		
		addActionListeners();
		
		return mBar;
	}
	
	public TurtleFrame(Turtle instance)
	{
		turtle = instance;
		
		EventQueue.invokeLater(() -> {

			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
			
			add(createButtonPanel());
			setJMenuBar(constructMenu());
			setLocationRelativeTo(null);
			setResizable(false);
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			pack();
			setVisible(true);
		});
	}
	
}