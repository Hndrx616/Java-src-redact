/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

File userHomeDirectory = new File( System.getProperty("user.home") );
File dataFile = new File( userHomeDirectory, ".phone book data" );

if ( ! dataFile.exists() ) {
	System.out.println("No phone book data file found.");
	System.out.println("A new one will be created.");
	System.out.println("File name: " + dataFile.getAbsolutePath());
}
else {
	System.out.println("Reading phone book data...");
	try {
		Scanner scanner = new Scanner( dataFile );
		while (scanner.hasNextLine()) {
		// Read one line from the file, containing one name/number pair.
			String phoneEntry = scanner.nextLine();
			int separatorPosition = phoneEntry.indexOf(’%’);
			if (separatorPosition == -1)
				throw new IOException("File is not a phonebook data file.");
			name = phoneEntry.substring(0, separatorPosition);
			number = phoneEntry.substring(separatorPosition+1);
			phoneBook.put(name,number);
		}
	}
	catch (IOException e) {
		System.out.println("Error in phone book data file.");
		System.out.println("File name: " + dataFile.getAbsolutePath());
		System.out.println("This program cannot continue.");
		System.exit(1);
	}
}
if (changed) {
	System.out.println("Saving phone directory changes to file " +
		dataFile.getAbsolutePath() + " ...");
	PrintWriter out;
	try {
		out = new PrintWriter( new FileWriter(dataFile) );
	}
	catch (IOException e) {
		System.out.println("ERROR: Can’t open data file for output.");
		return;
	}
	for ( Map.Entry<String,String> entry : phoneBook.entrySet() )
		out.println(entry.getKey() + "%" + entry.getValue() );
	out.close();
	if (out.checkError())
		System.out.println("ERROR: Some error occurred while writing data file.");
	else
		System.out.println("Done.");
}

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

public class FileReaderdata {
	
	JFileChooser File startDirectory;
	JFileChooser String pathToStartDirectory;

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
	try {
	data = new FileReader("data.txt"); // create the stream
	}
	catch (FileNotFoundException e) {
		... // do something to handle the error---maybe, end the program
	}
}

public class TextReaderdata{
	try {
	data = new TextReader( new FileReader("data.dat") );
	}
	catch (FileNotFoundException e) {
		... // handle the exception
	}
	if (option != JFileChooser.APPROVE OPTION) {
		try {
		data = new TextReader( new FileReader(file) );
		}
		catch (FileNotFoundException e) {
			... // handle the exception
		}
	}
}

public class PrintWriterresult {
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
	try {
		result = new PrintWriter(new FileWriter("result.dat"));
	}
	catch (IOException e) {
		... // handle the exception
	}
}

public class ReverseFile {

	public static void main(String[] args) {
		TextReader data; // Character input stream for reading data
		PrintWriter result; // Character output stream for writing data

		// An ArrayList for holding the data
		ArrayList<Double> numbers;

		numbers = new ArrayList<Double>();
		try { // Create the input stream
			data = new TextReader(new FileReader("data.dat"));
		}
		catch (FileNotFoundException e) {
			System.out.println("Can’t find file data.dat!");
			return; // End the program by returning from main()
		}

		try { // Create the output stream
			result = new PrintWriter(new FileWriter("result.dat"));
		}
		catch (IOException e) {
			System.out.println("Can’t open file result.dat!");
			System.out.println("Error: " + e);
			data.close(); // Close the input file
			return; // End the program
		}

		// Read numbers from the input file, adding them to the ArrayList.
		try {
			while ( data.eof() == false ) { // Read until end-of-file.
				double inputNumber = data.getlnDouble();
				numbers.add( inputNumber );
			}

			// Output the numbers in reverse order.
			for (int i = numbers.size()-1; i >= 0; i--)
				result.println(numbers.get(i));
			System.out.println("Done!");
		}
		catch (IOException e) {
			// Some problem reading the data from the input file.
			System.out.println("Input Error: " + e.getMessage());
		}
		finally {
			// Finish by closing the files, whatever else may have happened.
			data.close();
			result.close();
		}
	} // end of main()
} // end of class

public class DirectoryList {

	public static void main(String[] args) {
		String directoryName; // Directory name entered by the user.
		File directory; // File object referring to the directory.
		String[] files; // Array of file names in the directory.
		Scanner scanner; // For reading a line of input from the user.

		// scanner reads from standard input.
		scanner = new Scanner(System.in); 

		System.out.print("Enter a directory name: ");
		directoryName = scanner.nextLine().trim();
		directory = new File(directoryName);

		if (directory.isDirectory() == false) {
			if (directory.exists() == false)
				System.out.println("There is no such directory!");
			else
				System.out.println("That file is not a directory.");
		}
		else {
			files = directory.list();
			System.out.println("Files in directory \"" + directory + "\":");
			for (int i = 0; i < files.length; i++)
				System.out.println(" " + files[i]);
		}
	} // end main()
} // end class DirectoryList

public class CopyFile {
	public static void main(String[] args) {
		String sourceName; // Name of the source file, as specified on the command line.
		String copyName; // Name of the copy, as specified on the command line.
		InputStream source; // Stream for reading from the source file.
		OutputStream copy; // Stream for writing the copy.
		boolean force; // This is set to true if the "-f" option is specified on the command line.
		int byteCount; // Number of bytes copied from the source file.

		/* Get file names from the command line and check for the
			presence of the -f option. If the command line is not one
			of the two possible legal forms, print an error message and
			end this program. */
		if (args.length == 3 && args[0].equalsIgnoreCase("-f")) {
			sourceName = args[1];
			copyName = args[2];
			force = true;
		}
		else if (args.length == 2) {
			sourceName = args[0];
			copyName = args[1];
			force = false;
		}
		else {
			System.out.println(
				"Usage: java CopyFile <source-file> <copy-name>");
			System.out.println(
				" or java CopyFile -f <source-file> <copy-name>");
			return;
		}

		/* Create the input stream. If an error occurs, end the program. */
		try {
			source = new FileInputStream(sourceName);
		}
		catch (FileNotFoundException e) {
			System.out.println("Can’t find file \"" + sourceName + "\".");
			return;
		}

		/* If the output file already exists and the -f option was not
			specified, print an error message and end the program. */
		File file = new File(copyName);
		if (file.exists() && force == false) {
			System.out.println(
				"Output file exists. Use the -f option to replace it.");
			return;
		}

		/* Create the output stream. If an error occurs, end the program. */
		try {
			copy = new FileOutputStream(copyName);
		}
		catch (IOException e) {
			System.out.println("Can’t open output file \"" + copyName + "\".");
			return;
		}

		/* Copy one byte at a time from the input stream to the output
			stream, ending when the read() method returns -1 (which is
			the signal that the end of the stream has been reached). If any
			error occurs, print an error message. Also print a message if
			the file has been copied successfully. */

		byteCount = 0;
		try {
			while (true) {
			int data = source.read();
			if (data < 0)
				break;
			copy.write(data);
			byteCount++;
		}
		source.close();
		copy.close();
		System.out.println("Successfully copied " + byteCount + " bytes.");
		}
		catch (Exception e) {
			System.out.println("Error occurred while copying. "
									+ byteCount + " bytes copied.");
			System.out.println("Error: " + e);
		}
	} // end main()
} // end class CopyFile

private void doSaveAsBinary() {
	if (fileDialog == null)
		fileDialog = new JFileChooser();
	File selectedFile; //Initially selected file name in the dialog.
	if (editFile == null)
		selectedFile = new File("sketchData.binary");
	else
		selectedFile = new File(editFile.getName());
	fileDialog.setSelectedFile(selectedFile);
	fileDialog.setDialogTitle("Select File to be Saved");
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
	ObjectOutputStream out;
	try {
		FileOutputStream stream = new FileOutputStream(selectedFile);
		out = new ObjectOutputStream( stream );
	}
	catch (Exception e) {
		JOptionPane.showMessageDialog(this,
			"Sorry, but an error occurred while trying to open the file:\n" + e);
		return;
	}
	try {
		out.writeObject(getBackground());
		out.writeInt(curves.size());
		for ( CurveData curve : curves )
			out.writeObject(curve);
		out.close();
		editFile = selectedFile;
		setTitle("SimplePaint: " + editFile.getName());
	}
	catch (Exception e) {
		JOptionPane.showMessageDialog(this,
			"Sorry, but an error occurred while trying to write the text:\n" + e);
	}
}

out.writeObject(getBackground()); // Writes the panel’s background color.
out.writeInt(curves.size()); // Writes the number of curves.
for ( CurveData curve : curves ) // For each curve...
out.writeObject(curve); // write the corresponding CurveData object.

/* Read data from the file into local variables */

Color newBackgroundColor = (Color)in.readObject();
int curveCount = in.readInt();
ArrayList<CurveData> newCurves = new ArrayList<CurveData>();
for (int i = 0; i < curveCount; i++)
	newCurves.add( (CurveData)in.readObject() );
in.close();

/* Copy the data that was read into the instance variables that
describe the sketch that is displayed by the program.*/

curves = newCurves;
setBackground(newBackgroundColor);
repaint();

Color bgColor = getBackground(); // Write the background color to the file.
out.println( bgColor.getRed() );
out.println( bgColor.getGreen() );
out.println( bgColor.getBlue() );

out.println( curves.size() ); // Write the number of curves.

for ( CurveData curve : curves ) { // For each curve, write...
	out.println( curve.color.getRed() ); // the color of the curve
	out.println( curve.color.getGreen() );
	out.println( curve.color.getBlue() );
	out.println( curve.symmetric ? 0 : 1 ); // the curve’s symmetry property
	out.println( curve.points.size() ); // the number of points on curve
	for ( Point pt : curve.points ) { // the coordinates of each point
		out.println( pt.x );
		out.println( pt.y );
	}
}

Color newBackgroundColor; // Read the background Color.
int red = scanner.nextInt();
int green = scanner.nextInt();
int blue = scanner.nextInt();
newBackgroundColor = new Color(red,green,blue);

ArrayList<CurveData> newCurves = new ArrayList<CurveData>();

int curveCount = scanner.nextInt(); // The number of curves to be read.
for (int i = 0; i < curveCount; i++) {
	CurveData curve = new CurveData();
	int r = scanner.nextInt(); // Read the curve’s color.
	int g = scanner.nextInt();
	int b = scanner.nextInt();
	curve.color = new Color(r,g,b);
	int symmetryCode = scanner.nextInt(); // Read the curve’s symmetry property.
	curve.symmetric = (symmetryCode == 1);
	curveData.points = new ArrayList<Point>();
	int pointCount = scanner.nextInt(); // The number of points on this curve.
	for (int j = 0; j < pointCount; j++) {
		int x = scanner.nextInt(); // Read the coordinates of the point.
		int y = scanner.nextInt();
		curveData.points.add(new Point(x,y));
	}
	newCurves.add(curve);
}

curves = newCurves; // Install the new data structures.
setBackground(newBackgroundColor);

out.println("SimplePaintWithFiles 1.0"); // Version number.
Color bgColor = getBackground();
out.println( "background " + bgColor.getRed() + " " +
	bgColor.getGreen() + " " + bgColor.getBlue() );
for ( CurveData curve : curves ) {
	out.println();
	out.println("startcurve");
	out.println(" color " + curve.color.getRed() + " " +
		curve.color.getGreen() + " " + curve.color.getBlue() );
	out.println( " symmetry " + curve.symmetric );
	for ( Point pt : curve.points )
		out.println( " coords " + pt.x + " " + pt.y );
	out.println("endcurve");
}

private void doOpenAsText() {

	if (fileDialog == null)
		fileDialog = new JFileChooser();
	fileDialog.setDialogTitle("Select File to be Opened");
	fileDialog.setSelectedFile(null); // No file is initially selected.
	int option = fileDialog.showOpenDialog(this);
	if (option != JFileChooser.APPROVE OPTION)
		return; // User canceled or clicked the dialog’s close box.
	File selectedFile = fileDialog.getSelectedFile();

	Scanner scanner; // For reading from the data file.
	try {
		Reader stream = new BufferedReader(new FileReader(selectedFile));
		scanner = new Scanner( stream );
	}
	catch (Exception e) {
		JOptionPane.showMessageDialog(this,
			"Sorry, but an error occurred while trying to open the file:\n" + e);
		return;
	}

	try { // Read the contents of the file.
		String programName = scanner.next();
		if ( ! programName.equals("SimplePaintWithFiles") )
			throw new IOException("File is not a SimplePaintWithFiles data file.");
		double version = scanner.nextDouble();
		if (version > 1.0)
			throw new IOException("File requires newer version of this program.");
		Color newBackgroundColor = Color.WHITE;
		ArrayList<CurveData> newCurves = new ArrayList<CurveData>();
		while (scanner.hasNext()) {
			String itemName = scanner.next();
			if (itemName.equalsIgnoreCase("background")) {
				int red = scanner.nextInt();
				int green = scanner.nextInt();
				int blue = scanner.nextInt();
				newBackgroundColor = new Color(red,green,blue);
			}
			else if (itemName.equalsIgnoreCase("startcurve")) {
				CurveData curve = new CurveData();
				curve.color = Color.BLACK;
				curve.symmetric = false;
				curve.points = new ArrayList<Point>();
				itemName = scanner.next();
				while ( ! itemName.equalsIgnoreCase("endcurve") ) {
					if (itemName.equalsIgnoreCase("color")) {
						int r = scanner.nextInt();
						int g = scanner.nextInt();
						int b = scanner.nextInt();
						curve.color = new Color(r,g,b);
					}
					else if (itemName.equalsIgnoreCase("symmetry")) {
						curve.symmetric = scanner.nextBoolean();
					}
					else if (itemName.equalsIgnoreCase("coords")) {
						int x = scanner.nextInt();
						int y = scanner.nextInt();
						curve.points.add( new Point(x,y) );
					}
					else {
						throw new Exception("Unknown term in input.");
					}
					itemName = scanner.next();
				}
				newCurves.add(curve);
			}
			else {
				throw new Exception("Unknown term in input.");
			}
		}

		scanner.close();
		setBackground(newBackgroundColor); // Install the new picture data.
		curves = newCurves;
		repaint();
		editFile = selectedFile;
		setTitle("SimplePaint: " + editFile.getName());
	}
	catch (Exception e) {
		JOptionPane.showMessageDialog(this,
			"Sorry, but an error occurred while trying to read the data:\n" + e);
	}
}