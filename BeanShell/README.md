BeanShell interpreter for BeepBeep
==================================

Usage
-----

Run this interpreter from the command like any other interpreter in this
repository. The basic usage is:

    $ java -jar interpreter.jar file1 file2 ...

where `interpreter.jar` is the compiled runnable JAR for this interpreter,
and `fileX` are any number of BeanShell scripts.

Build instructions
------------------

1. First build the project in the `Root` folder by going into that folder
and typing `ant`.
2. Copy the resulting `root.jar` in `BeanShell/dep`.
3. Type `ant download-deps` to download the BeanShell library. (This only
needs to be done once.)
4. Type `ant` to build the interpreter. This will create the runnable JAR
file `beepbeep-bsh.jar`.

Automatic imports
-----------------

By default, the interpreter already loads all the classes from the following
packages:

- `ca.uqac.lif.cep`
- `ca.uqac.lif.cep.functions`
- `ca.uqac.lif.cep.io`
- `ca.uqac.lif.cep.tmf`
- `ca.uqac.lif.cep.util`

There is no need to include them using an `import` statement; you can use
their unqualified name directly, such as:

``` beanshell
pr = new Print();
tr = new Trim(2);
```

Use of `connect`
----------------

Connecting processors requires a special syntax, as the BeanShell interpreter
does not support method signatures with varargs. Therefore, you must encase
the arguments to `connect` into a `Processor` array, as follows:

``` beanshell
Connector.connect(new Processor[]{pr, tr});
```

Multiple input files
--------------------

If you specify multiple files at the command line, each file is read and
evaluated in the order they appear. This makes it possible to define reusable
objects in separate files, and to reuse them in multiple scripts. For example,
supposed that a file named `runavg.bsh` defines an object `RunAvg` that
computes the running average of a stream:

``` beanshell
RunAvg = new GroupProcessor(1,1);
{
	fork = new Fork(2);
	sum = new Cumulate(new CumulativeFunction(Numbers.addition));
	Connector.connect(fork, 0, sum, 0);
	ti = new TurnInto(1);
	Connector.connect(fork, 1, ti, 0);
	sum_1 = new Cumulate(new CumulativeFunction(Numbers.addition));
	Connector.connect(new Processor[]{ti, sum_1});
	div = new ApplyFunction(Numbers.division);
	Connector.connect(sum, 0, div, 0);
	Connector.connect(sum_1, 0, div, 1);
	RunAvg.addProcessors(new Processor[]{fork, div, sum, ti, sum_1});
	RunAvg.associateInput(0, fork, 0);
	RunAvg.associateOutput(0, div, 0);
}
```

It is possible to reuse this object in another script called `useavg.bsh`:

``` beanshell
avg = RunAvg.duplicate();
Connector.connect(new Processor[]{avg, new Print()});
p = avg.getPushableInput();
p.push(1);
p.push(2);
```

To run this script, it suffices to call the interpreter by loading
`runavg.bsh` before `useavg.bsh`:

    $ java -jar interpreter.jar runavg.bsh useavg.bsh

The object defined in `runavg.bsh` can be used in multiple scripts.