/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
// Find & Match Items to Database
import java.rmi.RemoteException;
import java.rmi.*;
import javax.ejb.*;
import java.util.*;
import java.text.NumberFormat;
import java.io.Serializable;
import javax.naming.*;
import auction.*;
import registration.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

Enumeration enum=(Enumeration)
search.getMatchingItemsList(searchString);

//Iterate through search results
while ((enum != null) &&
	enum.hasMoreElements())) {
	while(enum.hasMoreElements(in)) {
		AuctionItem ai=ahome.findByPrimaryKey((
			AuctionItemPK)enum.nextElement());
		displayLineItem(ai, out);
	}
}

//Establish database connection
static {
	new weblogic.jdbc.jts.Driver();
}

public Connection getConnection()
		throws SQLException {
	return DriverManager.getConnection(
		"jdbc:weblogic:jts:ejbPool");
}

public Enumeration getMatchingItemsList(
		String searchString)
		throws RemoteException {

	ResultSet rs = null;
	PreparedStatement ps = null;
	Vector v = new Vector();
	Connection con = null;

	try{
		//Get database connection
		con=getConnection();
		//Create a prepared statement for database query
		ps=con.prepareStatement("select id from
		auctionitems where summary like ?");
		ps.setString(1, "%"+searchString+"%");
		//Execute database query
		ps.executeQuery();
		//Get results set
		rs = ps.getResultSet();
		//Get information from results set
		AuctionItemPK pk;
		while (rs.next()) {
			pk = new AuctionItemPK();
			pk.id = (int)rs.getInt(1);
		//Store retrieved data in vector
		v.addElement(pk);
		}
		rs.close();
		return v.elements();
	} catch (Exception e) {
		System.out.println("getMatchingItemsList:
					"+e);
		return null;
	} finally {
		try {
			if(rs != null) {
				rs.close();
			}
			if(ps != null) {
				ps.close();
			}
			if(con != null) {
				con.close();
			}
		} catch (Exception ignore) {}
	}
}

// Creat Method
public void ejbCreate() throws CreateException,
	RemoteException {
	Properties p = new Properties();
	p.put(Context.INITIAL_CONTEXT_FACTORY,
		"weblogic.jndi.TengahInitialContextFactory");
	try{
		ctx = new InitialContext(p);
	} catch(Exception e) {
		System.out.println("create exception: "+e);
	}
}