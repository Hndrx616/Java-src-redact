/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
// URL properties examination
import java.net.*;

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