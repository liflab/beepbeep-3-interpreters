package ca.uqac.lif.cep.interpreters;

import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;

import ca.uqac.lif.util.CliParser;
import ca.uqac.lif.util.CliParser.ArgumentMap;

public abstract class CliInterpreter
{
	/**
	 * Return code signaling an error in the parsing of command-line
	 * arguments
	 */
	public static final int ERR_ARGS = 1;

	/**
	 * Reads and interprets a source file
	 * @param filename The name of the source file
	 * @throws CliInterpreterException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public abstract void read(String filename)  throws CliInterpreterException, FileNotFoundException, IOException;

	public void runCli(String[] args)
	{
		CliParser parser = new CliParser();
		ArgumentMap arg_map = parser.parse(args);
		if (arg_map == null)
		{
			parser.printHelp("BeepBeep Interpreter", System.err);
			System.exit(ERR_ARGS);
		}
		List<String> filenames = arg_map.getOthers();
		if (filenames.isEmpty())
		{
			System.err.println("ERROR: no input filename specified");
			System.exit(ERR_ARGS);
		}
		for (String filename : filenames)
		{
			try {
				read(filename);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (CliInterpreterException e) 
			{
				System.err.println(e.getMessage());
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
