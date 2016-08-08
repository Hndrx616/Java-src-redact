/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
public void writeFile() {
	if (fileDialog == null)
		fileDialog = new JFileChooser(); // (fileDialog is an instance variable)
	File selectedFile = new File("(default file name)");
	fileDialog.setSelectedFile(selectedFile); // Specify a default file name.
	fileDialog.setDialogTitle("Select File for Writing");
	int option = fileDialog.showSaveDialog(this);
	if (option != JFileChooser.APPROVE OPTION)
		return; // User canceled or clicked the dialog’s close box.
	selectedFile = fileDialog.getSelectedFile();
	if (selectedFile.exists()) { // Ask the user whether to replace the file.
		int response = JOptionPane.showConfirmDialog( this,
			"The file \"" + selectedFile.getName()
				+ "\" already exists.\nDo you want to replace it?",
			"Confirm Save",
			JOptionPane.YES NO OPTION,
			JOptionPane.WARNING MESSAGE );
		if (response != JOptionPane.YES OPTION)
			return; // User does not want to replace the file.
	}
	PrintWriter out; // (or use some other wrapper class)
	try {
		FileWriter stream = new FileWriter(selectedFile); // (or FileOutputStream)
		out = new PrintWriter( stream );
	}
	catch (Exception e) {
		JOptionPane.showMessageDialog(this,
			"Sorry, but an error occurred while trying to open the file:\n" + e);
		return;
	}
	try {
		.
		. // Write data to the output stream, out.
		.
		out.close();
		if (out.checkError()) // (need to check for errors in PrintWriter)
			throw new IOException("Error check failed.");
	}
	catch (Exception e) {
		JOptionPane.showMessageDialog(this,
			"Sorry, but an error occurred while trying to write the data:\n" + e);
	}
}