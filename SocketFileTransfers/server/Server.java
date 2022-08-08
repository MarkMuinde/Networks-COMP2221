//importing necessary methods
import java.util.logging.*;
import java.util.concurrent.*;
import java.net.*;

public class Server {

	/*initialise a Logger object for the log file 
	and an Executor object for the 15 clients*/
	private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static Executor executor = Executors.newFixedThreadPool(15);

	//main server method that runs the 15 clients 
	public static void main(String[] args) throws Exception {

		try (ServerSocket server = new ServerSocket(8888);) {

			//counter for the 15 clients
			int counter = 0;

			//print that server started successfully
			logger.log(Level.INFO, "Server Successfully Started ....");
			
			while (counter <= 15) {

				//increment the counter until 15 clients
				counter++;

				// server accepts the client connection request and log the client number
				Socket serverClient = server.accept(); 
				logger.log(Level.INFO, ">> Client No: {0} started!", counter);

				//executor runs
				executor.execute(new ClientRequestHandler(serverClient, counter));

			}
		} 
		
		//catch the exceptions
		catch (Exception e) {
			
			logger.log(Level.SEVERE, e.getMessage());
		}
	}
}