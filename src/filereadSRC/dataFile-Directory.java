/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
// dataFile Directory
import java.io.*;

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