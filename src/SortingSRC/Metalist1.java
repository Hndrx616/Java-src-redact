/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
import java.util.*;
import java.io.*;
/*Collections method in Java for proj-Kettler*/
public class Metalist1 {
	ArrayList<Document> documentList = new ArrayList<Document>();
	public static void main(String[] args) {
		new Metalist1().go();
	}

	class KeywordCompare implements Comparator<Document> {
		public int compare(Document one, Document two) {
			return one.getKeyword().compareTo(two.getKeyword());
		}
	}

	public void go() {
		getDocuments();
		System.out.println(documentList);
		Collections.sort(documentList);
		System.out.println(documentList);
		KeywordCompare keywordCompare = new KeywordCompare();
		Collections.sort(documentList, keywordCompare);
		System.out.println(documentList);
		HashSet<Document> documentSet = new HashSet<document>();
			/*if (foo.equals(bar) && foo.hashCode() == bar.hashCode()) {

			}*/
		documentSet.addAll(documentList);
		System.out.println(documentList);
		/*TreeSet<Document> documentSet = new TreeSet<document>();
		documentSet.addAll(documentList);
		System.out.println(documentList);*/
	}

	void getDocuments() {
		try {
			File file = new File("documentList.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line= reader.readLine()) !=null) {
				addDocument(line);
			}

		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	void addDocument(String lineToParse) {
		String[] tokens = lineToParse.split("/");
		Document nextDocument = new Document(tokens[0], tokens[1], tokens[2], tokens[3]);
		documentList.add(nextDocument);
	}
}
class Document implements Comparable<Document>{
	String title;
	String keyword;
	String metadata;
	String date;

	public boolean equals(Object aDocument) {
		Document s = (Document) aDocument;
		return getTitle().equals(s.getTitle());
	}

	public int hashCode() {
		return title.hashCode();
	}

	public int compareTo(Document s) {
		return title.compareTo(s.getTitle());
	}

	Document(String t, String k, String m, String d,) {
		title = t;
		keyword = k;
		metadata = m;
		date = d;
	}

	public String getTitle() {
		return title;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getMetadata() {
		return metadata;
	}

	public String getDate() {
		return date;
	}

	public String toString() {
		return title;
	}
}