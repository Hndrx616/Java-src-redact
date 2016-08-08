/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
// TCP/IP connections secure client server connetion
import java.net.*;
import java.io.*;
import java.util.Date;

public ServerSocket(int port) throws IOException {
	public Socket accept() throws IOException {
		try {
			ServerSocket server = new ServerSocket(1728);
			while (true) {
				Socket connection = server.accept();
				provideService(connection);
			}
		}
		catch (IOException e) {
			System.out.println("Server shut down with error: " + e);
		}
	}

	void doClientConnection(String computerName, int serverPort) {
		Socket connection;
		InputStream in;
		OutputStream out;
		try {
			connection = new Socket(computerName,serverPort);
			in = connection.getInputStream();
			out = connection.getOutputStream();
		}
		catch (IOException e) {
			System.out.println(
				"Attempt to create connection failed with error: " + e);
			return;
		}
		.
		. // Use the streams, in and out, to communicate with server.
		.
		try {
			connection.close();
				// (Alternatively, you might depend on the server
				// to close the connection.)
		}
		catch (IOException e) {
		}
	} // end doClientConnection()
}

public class DateClient {

   public static final int LISTENING_PORT = 32007;

   public static void main(String[] args) {

      String hostName;         // Name of the server computer to connect to.
      Socket connection;       // A socket for communicating with server.
      BufferedReader incoming; // For reading data from the connection.

      /* Get computer name from command line. */

      if (args.length > 0)
         hostName = args[0];
      else {
            // No computer name was given.  Print a message and exit.
         System.out.println("Usage:  java DateClient <server_host_name>");
         return;
      }

      /* Make the connection, then read and display a line of text. */

      try {
         connection = new Socket( hostName, LISTENING_PORT );
         incoming = new BufferedReader( 
                    new InputStreamReader(connection.getInputStream()) );
         String lineFromServer = incoming.readLine();
         if (lineFromServer == null) {
               // A null from incoming.readLine() indicates that
               // end-of-stream was encountered.
            throw new IOException("Connection was opened, " + 
                  "but server did not send any data.");
         }
         System.out.println();
         System.out.println(lineFromServer);
         System.out.println();
         incoming.close();
      }
      catch (Exception e) {
         System.out.println("Error:  " + e);
      }
   }  // end main()
} //end class DateClient

public class DateServer {

   public static final int LISTENING_PORT = 32007;

   public static void main(String[] args) {

      ServerSocket listener;  // Listens for incoming connections.
      Socket connection;      // For communication with the connecting program.

      /* Accept and process connections forever, or until some error occurs.
         (Note that errors that occur while communicating with a connected 
         program are caught and handled in the sendDate() routine, so
           they will not crash the server.) */

      try {
         listener = new ServerSocket(LISTENING_PORT);
         System.out.println("Listening on port " + LISTENING_PORT);
         while (true) {
                // Accept next connection request and handle it.
            connection = listener.accept(); 
            sendDate(connection);
         }
      }
      catch (Exception e) {
         System.out.println("Sorry, the server has shut down.");
         System.out.println("Error:  " + e);
         return;
      }
   }  // end main()

   /**
    * The parameter, client, is a socket that is already connected to another 
    * program.  Get an output stream for the connection, send the current time, 
    * and close the connection.
    */
   private static void sendDate(Socket client) {
      try {
         System.out.println("Connection from " +  
                                      client.getInetAddress().toString() );
         Date now = new Date();  // The current data and time.
         PrintWriter outgoing;   // Stream for sending data.
         outgoing = new PrintWriter( client.getOutputStream() );
         outgoing.println( now.toString() );
         outgoing.flush();  // Make sure the data is actually sent!
         client.close();
      }
      catch (Exception e){
         System.out.println("Error: " + e);
      }
   } // end sendDate()
} //end class DateServer