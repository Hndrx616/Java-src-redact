/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
import java.net.*;

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