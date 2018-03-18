Interpreters for BeepBeep 3
===========================
 
The goal of this project is to develop a set of command-line **interpreters**
to create and manipulate [BeepBeep](https://liflab.github.io/beepbeep-3)
processor chains in various programming languages.

What is BeepBeep?
-----------------

BeepBeep is an open source event stream processing engine developed in Java.
Learn more about BeepBeep by visiting its
[web site](https://liflab.github.io/beepbeep-3), or read a complete
[user manual on GitBook](https://www.gitbook.com/read/book/liflab/event-stream-processing-with-beepbeep-3).

How does it work?
-----------------

Let us take the example of the BeanShell interpreter (all other interpreters
work the same way). Suppose this interpreter was compiled as
`beepbeep-bsh.jar`. Consider the following bit of BeanShell script:

``` beanshell
print = new Print();
p = print.getPushableInput();
p.push("foo");
```

Save this script in `my-file.bsh`. You can run this script from the command
line as:

    $ java -jar beepbeep-bsh.jar my-file.bsh

It will print `foo`.

Available interpreters
----------------------

See the `README` file in each folder for specific information about each
interpreter.

- [BeanShell](http://beanshell.org)

Projected interpreters
----------------------

Contact us if you want to contribute!

- Python (with [Jython](http://www.jython.org/))
- JavaScript (with [Rhino](https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino))

Structure of this repository
----------------------------

The `Root` project defines all the base classes. All other projects depend
on this one.

Each other project (except `Root`) defines a command-line interpreter for
a specific language. Compiling the project as a runnable JAR generates the
interpreter.