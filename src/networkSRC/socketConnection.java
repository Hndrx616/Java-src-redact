/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
// Sockets
import java.net.*;
import java.io.*;

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