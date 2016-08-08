/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
import java.io.*;
import java.net.*;

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