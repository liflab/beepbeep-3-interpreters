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
		Reader s_reader = new StringReader(builder.toString());
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
