/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
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