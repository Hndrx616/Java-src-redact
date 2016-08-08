/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
// Storing object
import java.io.*;

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