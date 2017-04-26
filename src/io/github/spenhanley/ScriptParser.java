package io.github.spenhanley;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ScriptParser
{
	
	private File scriptFile;
	
	public void loadFile()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Turtle Files", "turt"));
		int action = fileChooser.showOpenDialog(null);
		if (action == JFileChooser.APPROVE_OPTION)
		{
			scriptFile = fileChooser.getSelectedFile();
		}
		parseScript();
	}
	
	private String readFile()
	{
		try(BufferedReader reader = new BufferedReader(new FileReader(scriptFile));) 
		{
			String line;
			StringBuilder builder = new StringBuilder();
			while ((line = reader.readLine()) != null)
			{
				builder.append(line);
			}
			return builder.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Not found";
	}

	/**
	 *  Valid commands:
	 *  - FORWARD(num_of_cycles)
	 *  - ROTATE(degrees)
	 *  - START_DRAW()
	 *  - END_DRAW()
	 *   
	 */
	private void parseScript()
	{
		String[] commands = readFile().split(";");
		for (String command : commands)
		{
			System.out.println(command);
		}
	}
	
}