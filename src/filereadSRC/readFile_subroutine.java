/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
public void readFile() {
	if (fileDialog == null) // (fileDialog is an instance variable)
		fileDialog = new JFileChooser();
	fileDialog.setDialogTitle("Select File for Reading");
	fileDialog.setSelectedFile(null); // No file is initially selected.
	int option = fileDialog.showOpenDialog(this);

		// (Using "this" as a parameter to showOpenDialog() assumes that the
		// readFile() method is an instance method in a GUI component class.)
	if (option != JFileChooser.APPROVE OPTION)
		return; // User canceled or clicked the dialog’s close box.
	File selectedFile = fileDialog.getSelectedFile();
	TextReader in; // (or use some other wrapper class)
	try {
		FileReader stream = new FileReader(selectedFile); // (or a FileInputStream)
		in = new TextReader( stream );
	}
	catch (Exception e) {
	JOptionPane.showMessageDialog(this,
		"Sorry, but an error occurred while trying to open the file:\n" + e);
	return;
	}
	try {
		.
		. // Read and process the data from the input stream, in.
		.
		in.close();
	}
	catch (Exception e) {
		JOptionPane.showMessageDialog(this,
			"Sorry, but an error occurred while trying to read the data:\n" + e);
	}
}