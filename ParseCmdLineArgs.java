//
// Simple Java code intended as a refresher for students who need to 'brush up' on their Java.
// There is no networking in this example.
//
// Compile with: javac ParseCmdLineArgs.java
// Execute with: java ParseCmdLineArgs arg1 arg2 arg3 ...
//

public class ParseCmdLineArgs {

	// The sole constructor, which expects the command line arguments to be provided as a String array.
	public ParseCmdLineArgs( String[] args )
	{
        // Your code should go here.
        if (args.length == 0) {
            System.err.println("enter a command line argument. Program exiting");
        } else {
            String[] cmdLineStore = new String[args.length];
            for(int i = 0; i< args.length; i++) {
                cmdLineStore[i] = args[i];
            }
        }
        
	}

    // main(): This is the function that is called after executing with 'java ReadCmdLineArgs'. Any command line arguments,
    // such as 'java ReadCmdLineArgs arg1 arg2 arg3', are passed to main() as a String array.
    public static void main( String[] args )
	{
        ParseCmdLineArgs parser = new ParseCmdLineArgs(args);
        for (String argument : args) {
            if (argument.contains(".")){
                System.out.println(argument + "-this may be a host name");
            } else {
            System.out.println (argument);
            }
        }
	}
}