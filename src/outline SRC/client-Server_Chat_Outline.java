/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
public class SimpleChatClientA{

	JTextField outgoing;
	PrintWriter writer;
	Socket sock;

	public void go() {
		// make gui and register a listner with send button
		// call the setUpNetworking() method
	}

	private void setUpNetworking() {
		// Make a Socket, then make a PrintWriter
		// assign the PrintWriter to writer instance variable
	}

	public class SendButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			// get the text from the text field and
			// send it to the server using the writer (a PrintWriter)
		}
	} // close SendButton Listener inner class
} // close outer class