/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
// Networking Methods
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Date;
import java.util.Enumeration;

// Get InetAddress for IPv4 & IPv6 address
class InetAddressTest {
	public static void main(String args[]) throws UnknownHostException {
		InetAddress Address = InetAddress.getLocalHost();
		System.out.println(Address);
		Address = InetAddress.getByName("sitealpha.com");
		System.out.println(Address);
		InetAddress SW[] = InetAddress.getAllByName("www.sitebeta.com");
		for (int i=0; i<SW.length; i++)
			System.out.println(SW[i]);
	}
}

// Sockets
class WhoisProp {
	public static void main(String args[]) throws Exception {
		int c;

		// Create a socket connected to internic.net, port 43
		Socket s = new Socket("internic.net", 43);

		// Obtain input and output streams
		InputStream in = s.getInputStream();
		OutputStream out = s.getOutputStream();

		// Construct a request string
		604 Part II: The Java Library
		Chapter 20: Networking 605
		String str = (args.length == 0 ? "sitealpha.com" : args[0]) + "\n";

		// Convert to bytes
		byte buf[] = str.getBytes();

		// Send request
		out.write(buf);

		// Read and display response
		while ((c = in.read()) != -1) {
			System.out.print((char) c);
		}
		s.close();
	}
}

// Multi Thread Test
public class Test Threads {
	public static void main(String [] args) {
		ThreadOne t1 = new ThreadOne();
		ThreadTwo t2 = new ThreadTwo();
		Thread One = new Thread(t1);
		Thread Two = new Thread(t2);
		one.start();
		two.start();
	}
}

class Accum {}
	private static Accum a = new Accum();
	private in counter = 0;

	private Accum()	{}	// private constructor

	public static Accum getAccum() {
		return a;
	}

	public void updateCounter(int add) {
		counter += add;
	}

	public int getCount() {
		return counter;
	}
}

class ThreadOne implements Runnable {
	Accum a = Accum.getAccum();
	public void run() {
		for(int x=0; x < 98; x++) {
			a.updateCounter(1000);
			try {
				Thread.sleep(50);
			} catch(InterruptedException ex) {}
		}
		System.out.println("one "+a.getCount());
	}
}

class ThreadTwo implements Runnable {
	Accum a = Accum.getAccum();
	public void run() {
		for(int x=0; x < 99; x++) {
			a.updateCounter(1);
			try {
				Thread.sleep(50);
			} catch(InterruptedException ex) {}
		}
		System.out.println("two "+a.getCount());
	}
}
// Multi Thread Establishment
class BankAccount{
	private int balance = 100;

	public int getBalance() {
		return balance;
	}
	public void withdraw(int amount) {
		balance = balance - amount;
	}
}

public class AlphaAndBetaJob implements Runnable {

	private BankAccount account = new BankAccount();

	public static void main(String[] args) {
		AlphaAndBetaJob theJob = new AlphaAndBetaJob();
		Thread one = new Thread(theJob);
		Thread two = new Thread(theJob);
		one.setName("Alpha");
		two.setName("Beta");
		one.start();
		two.start();
	}

	public void run() {
		for (int x = 0; x < 10; x++) {
			makeWithdrawal(10);
			if (account.getBalance() < 0) {
				System.out.println("Overdrawn!");
			}
		}
	}

	private synchronized void makeWithdrawal(int amount) {
		if (account.getBalance() >= amount) {
		System.out.println(Thread.currentThread().getName() + " is about to withdraw.");
		try{
			System.out.println(Thread.currentThread().getName() + " is going to sleep.");
			Thread.sleep(500);
		} catch(InterruptedException ex) {ex.printStackTrace(); }
		System.out.println(Thread.currentThread().getName() + " woke up.");
		account.withdraw(amount);
		System.out.println(Thread.currentThread().getName() + " completes the withdrawal.");
		}
		else {
			System.out.println("Sorry, not enough for " + Thread.currentThread().getName());
		}
	}
}

// CLient to Server Handshake
public class DailyAdviceClient {
	
	public void go() {
		try {
			Socket s = new Socket("127.0.0.1", 4242);

			InputStreamReader streamreader = new InputStreamReader(s.getInputStream());
			BufferReader reader = new BufferReader(streamReader);

			String advice = reader.readLine();
			System.out.println("Today you should: " + advice);

			reader.close();
			
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}

	public static viod main(Strong[] args) {
		DailyAdviceClient client = new DailyAdviceClient();
		client.go();
	}
}

// Datagrams & Packet Sniffer
class WriteServer {
	public static int serverPort = 998;
	public static int clientPort = 999;
	public static int buffer_size = 1024;
	public static DatagramSocket ds;
	public static byte buffer[] = new byte[buffer_size];
	
	public static void TheServer() throws Exception {
		int pos=0;
		while (true) {
			int c = System.in.read();
			switch (c) {
				case -1:
					System.out.println("Server Quits.");
					return;
				case '\r':
					break;
				case '\n':
					ds.send(new DatagramPacket(buffer,pos,
						InetAddress.getLocalHost(),clientPort));
					pos=0;
					break;
				default:
					buffer[pos++] = (byte) c;
			}
		}
	}

	public static void TheClient() throws Exception {
		while(true) {
			DatagramPacket p = new DatagramPacket(buffer, buffer.length);
			ds.receive(p);
			System.out.println(new String(p.getData(), 0, p.getLength()));
		}
	}

	public static void main(String args[]) throws Exception {
		if(args.length == 1) {
			ds = new DatagramSocket(serverPort);
			TheServer();
		} else {
			ds = new DatagramSocket(clientPort);
			TheClient();
		}
	}
}

// Server to CLient Handshake
public class DailyAdviceServer {

	String[] adviceList = {"Take smaller bites", "Go for the tight jeans. No they do NOT make you look fat", "One word: inappropriate", "Just for today, be honest. Tell your boss what you *really* think", "You Might want to rethink that haicut."};
	
	public void go() {
		
		try {
			ServerSocket serverSock = new ServerSocket (4242);

			while(true) {
				Socket sock = serverSock.accept();

				PrintWriter writer = new PrintWriter(sock.getOutputStream());
				String advice = getAdvice();
				writer.println(advice);
				writer.close();
				System.out.println(advice);
			} catch(IOException ex) {
				ex.printStackTrace();
			}
	}    // close go

	private String getAdvice() {
		int random = (int) (Math.random() * adviceList.length);
		return adviceList[random];
	}

	public static viod main(Strong[] args) {
		DailyAdviceServer server = new DailyAdviceServer();
		server.go();
	}
}

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

// URLConnection
class UCProp {
	public static void main(String args[]) throws Exception {
		int c;
		URL hp = new URL("http://www.internic.net");
		URLConnection hpCon = hp.openConnection();

		// get date
		long d = hpCon.getDate();
		if(d==0)
			System.out.println("No date information.");
		else
			System.out.println("Date: " + new Date(d));

		// get content type
		System.out.println("Content-Type: " + hpCon.getContentType());

		// get expiration date
		d = hpCon.getExpiration();
		if(d==0)
			System.out.println("No expiration information.");
		else
			System.out.println("Expires: " + new Date(d));

		// get last-modified date
		d = hpCon.getLastModified();
		if(d==0)
			System.out.println("No last-modified information.");
		else
			System.out.println("Last-Modified: " + new Date(d));

		// get content length
		int len = hpCon.getContentLength();
		if(len == -1)
			System.out.println("Content length unavailable.");
		else
			System.out.println("Content-Length: " + len);

		if(len != 0) {
			System.out.println("=== Content ===");
			InputStream input = hpCon.getInputStream();
			int i = len;
			while (((c = input.read()) != -1)) { // && (--i > 0)) {
				System.out.print((char) c);
			}
			input.close();
		} else {
			System.out.println("No content available.");
		}
	}
}
// URL & URL Connection test
import java.io.*;

public URL(String urlName) throws MalformedURLException;
public URL(URL context, String relativeName) throws MalformedURLException;
URL url = new URL(getDocumentBase(), "data.txt");
URL url = new URL(urlAddressString);
URLConnection connection = url.openConnection();
InputStream in = connection.getInputStream();

public class ReadURL {

   public static void main(String[] args) {
      if (args.length == 0) {
         System.out.println("Please specify a URL on the command line.");
         return;
      }
      try {
         readTextFromURL(args[0]);
      }
      catch (IOException e) {
         System.out.println("\n*** Sorry, an error has occurred ***\n");
         System.out.println(e);
         System.out.println();
      }  
   }

   /**
    * This subroutine attempts to copy text from the specified URL onto the screen.  
    * Any error must be handled by the caller of this subroutine.
    * @param urlString contains the URL in text form
    */
   static void readTextFromURL( String urlString ) throws IOException {

      /* Open a connection to the URL, and get an input stream
           for reading data from the URL. */

      URL url = new URL(urlString);
      URLConnection connection = url.openConnection();
      InputStream urlData = connection.getInputStream();

      /* Check that the content is some type of text.  Note: If 
         getContentType() method were called before getting  the input 
         stream, it is possible for contentType to be null only because 
         no connection can be made.  The getInputStream() method will 
         throw an error if no connection can be made. */

      String contentType = connection.getContentType();
      if (contentType == null || contentType.startsWith("text") == false)
         throw new IOException("URL does not seem to refer to a text file.");

      /* Copy lines of text from the input stream to the screen, until
           end-of-file is encountered  (or an error occurs). */
      
      BufferedReader in;  // For reading from the connection's input stream.
      in = new BufferedReader( new InputStreamReader(urlData) );

      while (true) {
         String line = in.readLine();
         if (line == null)
            break;
         System.out.println(line);
      }

   } // end readTextFromURL()

} // end class ReadURL
// URL properties examination
class URLProp {
	public static void main(String args[]) throws MalformedURLException {
		URL hp = new URL("http://www.sitealpha.com/downloads");

		System.out.println("Protocol: " + hp.getProtocol());
		System.out.println("Port: " + hp.getPort());
		System.out.println("Host: " + hp.getHost());
		System.out.println("File: " + hp.getFile());
		System.out.println("Ext:" + hp.toExternalForm());
	}
}

// HttpURLConnection
class HttpURLProp {
	public static void main(String args[]) throws Exception {
		URL hp = new URL("http://www.google.com");

		HttpURLConnection hpCon = (HttpURLConnection) hp.openConnection();

		// Display request method.
		System.out.println("Request method is " +
							hpCon.getRequestMethod());

		// Display response code.
		System.out.println("Response code is " +
							hpCon.getResponseCode());

		// Display response message.
		System.out.println("Response Message is " +
							hpCon.getResponseMessage());

		// Get a list of the header fields and a set
		// of the header keys.
		Map<String, List<String>> hdrMap = hpCon.getHeaderFields();
		Set<String> hdrField = hdrMap.keySet();

		System.out.println("\nHere is the header:");

		// Display all header keys and values.
		for(String k : hdrField) {
			System.out.println("Key: " + k +
							" Value: " + hdrMap.get(k));
		}
	}
}
public class ShowMyNetwork {

   public static void main(String[] args) {

      Enumeration netInterfaces;

      System.out.println();

      try {
         netInterfaces = NetworkInterface.getNetworkInterfaces();
      }
      catch (Exception e){
         System.out.println();
         System.out.println("Sorry, an error occurred while looking for network");
         System.out.println("interfaces.  The error was:");
         System.out.println(e);
         return;
      }

      if (! netInterfaces.hasMoreElements() ) {
         System.out.println("No network interfaces found.");
         return;
      }
      
      System.out.println("Network interfaces found on this computer:");
      
      while (netInterfaces.hasMoreElements()) {
         NetworkInterface net = (NetworkInterface)netInterfaces.nextElement();
         String name = net.getName();
         System.out.print("   " + name + " :  ");
         Enumeration inetAddresses = net.getInetAddresses();
         while (inetAddresses.hasMoreElements()) {
            InetAddress address = (InetAddress)inetAddresses.nextElement();
            System.out.print(address + "  ");
         }
         System.out.println();
      }
      System.out.println();
   } // end main()
}
// CLient to Server Communicaiton Establishment
public class SimpleChatClient {

	JTextArea incoming;
	JTextField outgoing;
	PrintWriter writer;
	Socket sock;

	public static void main(String[] args) {
		SimpleChatClient client = new SimpleChatClient();
		client.go();
	}

	public void go() {

		JFrame frame = new JFrame("Ludicrously Simple Chat Client");
		JPanel mainPanel= new JPanel();
		incoming = new JTextArea(15,50);
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		JScrollPane qScroller = new JScrollPane(incoming);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		outgoing = new JTextField(20);
		JButton sendButton = new JeButton("Send");
		sendButton.addActionListener(new SendButtonListner());
		mainPanel.add(qScroller);
		mainPanel.add(outgoing);
		mainPanel.add(sendButton);
		setupNetworking();

		Thread readerThread = new thread(new IncomingReader());
		readerThread.start();

		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(800,500);
		frame.setVisible(true);	
	} // close go

	private void setUpNetworking() {

		try {
			sock = new Socket("127.0.0.1", 5000);
			InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(streamReader);
			writer = new PrintWriter(sock.getOutputStream());
			System.out.println("networking established");
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	} // close setUpNetworking

	public class SendButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			try {
				writer.println(outgoing.getText());
				writer.flush();

			} catch(Exception ex) {
				ex.printStackTrace();
			}
			outgoing.setText("");
			outgoing.requestFocus();
		}
	} // close inner class

	public class IncomingReader inplements Runnable {
		public void run() {
			String message;
			try {

				while ((message = reader.readLine()) != null) {
					System.out.println("read " + message);
					incoming.append(message + "\n");

				} // close while
			} catch(Exception ex) {ex.printStackTrace();}
		} // close run
	} // close inner class
} // close outer class

// Server to Client Communicaiton Establishment
public class VerySimpleChatServer {

	ArrayList clientOutputStreams;

	public class ClientHandler implements Runnable {
		BufferedReader reader;
		Socket sock;

		public ClientHandler(Socket clientSocket) {
			try {
				sock = clientSocket
				InputStreamReader isReader = new InputStreamReader(soc.getInputStream());
				reader = new BufferedReader(isReader);

			} catch(Exception ex) {ex.printStackTrace();}
		} // close constructor

		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("read " + message);
					tellEveryone(message);

				} //close while
			} catch(Exception ex) {ex.printStackTrace();}
		} // close run
	} //close inner class

	public static void main (String[] args) {
		new VerySimpleChatServer().go();
	}

	public void go() {
		clientOutputStreams = new ArrayList();
		try {
			ServerSocket serverSock = new ServerSocket(5000);

			while(true) {
				Socket clientSocket = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				clientOutputStreams.add(writer);

				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();
				System.out.println("got a connection");
			}

		} catch(Exception ex) {ex.printStackTrace();}
	} //close go

	public void tellEveryone(String message) {

		Iterator it = clientOutputStreams.iterator();
		while(it.hasNext()) {
			try {
				PrintWriter writer = (PrintWriter) it.next();
				writer.println(message);
				wirter.flush();
			} catch(Exception ex) {ex.printStackTrace();}
		} // end while
	} // close tellEveryone
} // close class