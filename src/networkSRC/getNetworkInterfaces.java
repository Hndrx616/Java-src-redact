/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
import java.net.*;
import java.util.Enumeration;

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