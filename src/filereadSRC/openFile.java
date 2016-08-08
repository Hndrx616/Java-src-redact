/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
// openFile in directory
import java.io.*;

public void doOpen() {
	if (fileDialog == null)
		fileDialog = new JFileChooser();
	fileDialog.setDialogTitle("Select File to be Opened");
	fileDialog.setSelectedFile(null); // No file is initially selected.
	int option = fileDialog.showOpenDialog(this);
	if (option != JFileChooser.APPROVE OPTION)
		return; // User canceled or clicked the dialog’s close box.
	File selectedFile = fileDialog.getSelectedFile();
	BufferedReader in;
	try {
		FileReader stream = new FileReader(selectedFile);
		in = new BufferedReader( stream );
	}
	catch (Exception e) {
		JOptionPane.showMessageDialog(this,
			"Sorry, but an error occurred while trying to open the file:\n" + e);
		return;
	}
	try {
		String input = "";
		while (true) {
			String lineFromFile = in.readLine();
			if (lineFromFile == null)
				break; // End-of-file has been reached.
			input = input + lineFromFile + ’\n’;
			if (input.length() > 10000)
				throw new IOException("Input file is too large for this program.");
		}
		in.close();
		text.setText(input);
		editFile = selectedFile;
		setTitle("TrivialEdit: " + editFile.getName());
	}
	catch (Exception e) {
	JOptionPane.showMessageDialog(this,
	"Sorry, but an error occurred while trying to read the data:\n" + e);
	}
}