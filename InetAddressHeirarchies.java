//importing the necessary libraries
import java.net.*;
import java.util.*;

//creating the class
public class Coursework1 {
    //initializing global InetAddress instances 
    private InetAddress firstHost = null;
    private InetAddress secondHost = null;

    //method to process single cmd line argument
    public void Coursework1Args(String[] args) 
    {
        // creating necessary local InetAddress instance
        InetAddress ipAddress = null;

        /*setting a variable for the number of cmd line arguments
        and setting them in an array*/
        int count = args.length;
        String[] cmdLineStore = new String[count];

        //if no command line argument, print error message
        if (count == 0) 
        {
            System.err.println("Please recompile and enter a command line argument. Program exiting");
        }

        //store cmd line argument in array
        for (int i = 0; i < count; i++) 
        {
            cmdLineStore[i] = args[i];
        }

        //if a single cmd line argument i.e a single hostname
        if (count ==1)
        {
        //iterate through cmd line array
        for (String hostName : cmdLineStore) 
        {
            try 
            {
                ipAddress = InetAddress.getByName(hostName);

                // Getting IP address from hostname
                String IPAddress = ipAddress.getHostAddress();

                //print IPv6 Host & address
                if (ipAddress instanceof Inet6Address) 
                {
                    System.out.printf("IPv6 address of host %s  is %s %n", hostName, IPAddress);
                }

                // print IPv4 Host & address
                if (ipAddress instanceof Inet4Address) 
                {
                        System.out.printf("IPv4 address of host %s is %s %n", hostName, IPAddress);
                }
            }

            catch (UnknownHostException e) 
            {
                // catching the error
                e.printStackTrace();
                System.out.printf("Invalid host entered. Please recompile and try a different host name");
            }

        }
        }
    }

    public void Coursework2Args(String[] args) 
    {
        //setting a variable for the number of cmd line arguments
        int count = args.length;

        if (count == 0) 
        {
            System.err.println("enter a command line argument. Program exiting");
        }

        if (count ==2)
        {
            try 
            {
            //Turn each cmd line args into an instance of InetAddress
            firstHost = InetAddress.getByName(args[0]);
            secondHost = InetAddress.getByName(args[1]);

            //check if both addresses are IPv4
            if (firstHost instanceof Inet4Address && secondHost instanceof Inet4Address) 
            {
                //check if first host is similar to second host
                if (firstHost.equals(secondHost)) 
                {
                    System.out.printf("IPv4 address of host %s is %s %n", firstHost.getHostName(),
                            firstHost.getHostAddress());
                } 
                else 
                {
                    //get strings of both addresses
                    String firstIP4 = firstHost.getHostAddress();
                    String secondIP4 = secondHost.getHostAddress();

                    //tokenize first IP into integer array
                    String [] levelsOfHeirarchyFirstIP4 = firstIP4.split("\\.");
                    int [] levelsOfHeirarchyFirstIP4Int = new int [levelsOfHeirarchyFirstIP4.length];

                    //put each string array element into integer array element
                    for(int j = 0; j < levelsOfHeirarchyFirstIP4.length; j++)
                    {
                        levelsOfHeirarchyFirstIP4Int[j] = Integer.parseInt(levelsOfHeirarchyFirstIP4[j]);
                    }

                    // tokenize second IP into integer array
                    String [] levelsOfHeirarchySecondIP4 = secondIP4.split("\\.");
                    int [] levelsOfHeirarchySecondIP4Int = new int [levelsOfHeirarchySecondIP4.length];

                    //put each string array element into integer array element
                    for(int y = 0; y < levelsOfHeirarchySecondIP4.length; y++)
                    {
                        levelsOfHeirarchySecondIP4Int[y] = Integer.parseInt(levelsOfHeirarchySecondIP4[y]);
                    }
                    
                    //check if array of tokens match, else print asterisks
                    if (levelsOfHeirarchyFirstIP4Int[0] != levelsOfHeirarchySecondIP4Int[0]) 
                    {
                        System.out.printf("*.*.*.*");
                    } 

                    else if (levelsOfHeirarchyFirstIP4Int[1] != levelsOfHeirarchySecondIP4Int[1]) 
                    {
                        System.out.printf(levelsOfHeirarchyFirstIP4Int[0] + ".*.*.*");
                    } 

                    else if (levelsOfHeirarchyFirstIP4Int[2] != levelsOfHeirarchySecondIP4Int[2]) 
                    {
                        System.out.printf(levelsOfHeirarchyFirstIP4Int[0] + "." + levelsOfHeirarchyFirstIP4Int[1] + ".*.*");
                    } 
                    
                    else if (levelsOfHeirarchyFirstIP4Int[3] != levelsOfHeirarchySecondIP4Int[3]) 
                    {
                        System.out.printf(levelsOfHeirarchyFirstIP4Int[0] + "." + levelsOfHeirarchyFirstIP4Int[1] +
                         "." + levelsOfHeirarchyFirstIP4Int[2] + ".*");
                    }

                }
            }

        } 
        catch (UnknownHostException e) 
        {
            // catching the error
            e.printStackTrace();
            System.out.printf("Invalid host entered. Please recompile and try a different host name");
        }
        }
    }

    // main method
    public static void main(String args[]) {
        Coursework1 coursework = new Coursework1();

        //if there is one cmd line argument
        if (args.length == 1) 
        {
            coursework.Coursework1Args(args);
        } 
        else 
        {
            //if there is more than one command line argument i.e 2
            coursework.Coursework2Args(args);
        }
    }
}
