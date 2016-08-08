/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
// Connect to Database & Methods
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

// Connect to Database
//Create static instance of database driver
static {
	new weblogic.jdbc.jts.Driver();
}

//Get registered driver from static instance
public Connection getConnection() throws SQLException{
	return DriverManager.getConnection(
						"jdbc:weblogic:jts:ejbPool");
 }

 // Create Method
public RegistrationPK ejbCreate(String theuser,
 						String password,
 						String emailaddress,
 						String creditcard)
 		throws CreateException, RemoteException {

 	this.theuser=theuser;
 	this.password=password;
 	this.emailaddress=emailaddress;
 	this.creditcard=creditcard;
 	this.balance=0;

 	try {
 		con=getConnection();
 		ps=con.prepareStatement("insert into registration (
 						theuser, password,
						emailaddress, creditcard,
						balance) values (
						?, ?, ?, ?, ?)");
		ps.setString(1, theuser);
		ps.setString(2, password);
		ps.setString(3, emailaddress);
		ps.setString(4, creditcard);
		ps.setDouble(5, balance);
		if (ps.executeUpdate() != 1) {
			throw new CreateException (
						"JDBC did not create a row");
		}
		RegistrationPK primaryKey = new RegistrationPK();
		primaryKey.theuser = theuser;
		return primaryKey;
 		} catch (CreateException ce) {
			throw ce;
		} catch (SQLException sqe) {
			throw new CreateException (sqe.getMessage());
		} finally {
			try {
				ps.close();
			} catch (Exception ignore) {}
			try {
				con.close();
		} catch (Exception ignore) {}
	}
}

// Load Method
public void ejbLoad() throws RemoteException {
	try {
		refresh((RegistrationPK) ctx.getPrimaryKey());
	}
	catch (FinderException fe) {
		throw new RemoteException (fe.getMessage());
	}
}

// Refresh Method
private void refresh(RegistrationPK pk)
			throws FinderException, RemoteException {
	if (pk == null) {
		throw new RemoteException ("primary key
							cannot be null");
	}
	Connection con = null;
	PreparedStatement ps = null;
	try {
		con=getConnection();
		ps=con.prepareStatement("select password,
			emailaddress, creditcard,
			balance from registration
			where theuser = ?");
		ps.setString(1, pk.theuser);
		ps.executeQuery();
		ResultSet rs = ps.getResultSet();
		if (rs.next()) {
			theuser = pk.theuser;
			password = rs.getString(1);
			emailaddress = rs.getString(2);
			creditcard = rs.getString(3);
			balance = rs.getDouble(4);
		}
		else {
			throw new FinderException (
						"Refresh: Registration ("
						+ pk.theuser + ") not found");
		}
	}
	catch (SQLException sqe) {
		throw new RemoteException (sqe.getMessage());
	}
	finally {
		try {
			ps.close();
		}
		catch (Exception ignore) {}
		try {
			con.close();
		}
		catch (Exception ignore) {}
	}
}

// Store Method
public void ejbStore() throws RemoteException {
	Connection con = null;
	PreparedStatement ps = null;
	try {
		con = getConnection();
		ps = con.prepareStatement("update registration
							set password = ?,
							emailaddress = ?,
							creditcard = ?,
							balance = ?
							where theuser = ?");
		ps.setString(1, password);
		ps.setString(2, emailaddress);
		ps.setString(3, creditcard);
		ps.setDouble(4, balance);
		ps.setString(5, theuser);
		int i = ps.executeUpdate();
		if (i == 0) {
			throw new RemoteException (
						"ejbStore: Registration (
				" + theuser + ") not updated");
		}
	} catch (RemoteException re) {
		throw re;
	} catch (SQLException sqe) {
		throw new RemoteException (sqe.getMessage());
	} finally {
		try {
			ps.close();
		} catch (Exception ignore) {}
		try {
			con.close();
		}
		catch (Exception ignore) {}
	}
}

// Find Method
public RegistrationPK ejbFindByPrimaryKey(
		RegistrationPK pk)
				throws FinderException,
				RemoteException {
	if ((pk == null) || (pk.theuser == null)) {
		throw new FinderException ("primary key
							cannot be null");
	}
	refresh(pk);
	return pk;
}