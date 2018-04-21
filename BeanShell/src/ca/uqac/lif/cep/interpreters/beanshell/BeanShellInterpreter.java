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
package ca.uqac.lif.cep.interpreters.beanshell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import bsh.EvalError;
import bsh.Interpreter;
import ca.uqac.lif.cep.interpreters.CliInterpreter;
import ca.uqac.lif.cep.interpreters.CliInterpreterException;
import ca.uqac.lif.util.FileHelper;

public class BeanShellInterpreter extends CliInterpreter 
{
	protected final Interpreter m_interpreter;
	
	protected boolean m_addImports = true;
	
	public BeanShellInterpreter()
	{
		super();
		m_interpreter = new Interpreter();
	}

	@Override
	public void read(String filename) throws FileNotFoundException, IOException, CliInterpreterException
	{
		StringBuilder builder = new StringBuilder();
		
		if (m_addImports)
		{
			builder.append("import ca.uqac.lif.cep.*;\n");
			builder.append("import ca.uqac.lif.cep.functions.*;\n");
			builder.append("import ca.uqac.lif.cep.io.*;\n");
			builder.append("import ca.uqac.lif.cep.tmf.*;\n");
			builder.append("import ca.uqac.lif.cep.util.*;\n");
		}
		builder.append(FileHelper.readToString(new File(filename)));
		String code = builder.toString();
		//code = code.replaceAll("connect\\((.*?)\\)", "Connector.connect(new Processor[]{$1})");
		System.out.println(code);
		Reader s_reader = new StringReader(code);
		try
		{
			m_interpreter.eval(s_reader);
		}
		catch (EvalError e)
		{
			throw new CliInterpreterException(e.getMessage());
		}
	}
	
	public static void main(String[] args)
	{
		BeanShellInterpreter new_int = new BeanShellInterpreter();
		new_int.runCli(args);
	}
}
