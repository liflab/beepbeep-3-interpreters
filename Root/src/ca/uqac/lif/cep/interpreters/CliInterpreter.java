/*
    Interpreters for programming languages in BeepBeep
    Copyright (C) 2018 Sylvain Hallé

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
