package ca.uqac.lif.cep.interpreters;

public class CliInterpreterException extends Exception
{
	/**
	 * Dummy UID
	 */
	private static final long serialVersionUID = 1L;
	
	public CliInterpreterException(Throwable t)
	{
		super(t);
	}
	
	public CliInterpreterException(String message)
	{
		super(message);
	}

}
