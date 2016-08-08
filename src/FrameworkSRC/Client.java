/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Client {

    private String userID;
    private String userPassword;
    private String userRegion;
    private int ipAddress;
    private int subNet;
    private int domainName;
    private String dnsServerIP;
    private String portPASV;
    
    // <editor-fold defaultstate="collapsed" desc="PropertyChange Stuff">
    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Get Methods">
    public String getuserID() {
        return userID;
    }
    
    public String getuserPassword() {
        return userPassword;
    }

    public String getuserRegion() {
        return userRegion;
    }
    
    public int getipAddress() {
        return ipAddress;
    }

    public String getdnsServerIP() {
        return dnsServerIP;
    }

    public String getportPASV() {
        return portPASV;
    }

    public int getsubNet() {
        return subNet;
    }
    
    public int getdomainName() {
        return domainName;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Set Methods">
    public void setuserID(String userID) {
        String olduserID = this.userID;
        this.userID = userID;
        changeSupport.firePropertyChange("userID", olduserID, userID);
    }
    
    public void setuserPassword(String userPassword) {
        String olduserPassword = this.userPassword;
        this.userPassword = userPassword;
        changeSupport.firePropertyChange("userPassword", olduserPassword, userPassword);
    }

    public void setuserRegion(String userRegion) {
        String olduserRegion = this.userRegion;
        this.userRegion = userRegion;
        changeSupport.firePropertyChange("userRegion", olduserRegion, userRegion);
    }

    public void setipAddress(int ipAddress) {
        int oldipAddress = this.ipAddress;
        this.ipAddress = ipAddress;
        changeSupport.firePropertyChange("ipAddress", oldipAddress, ipAddress);
    }

    public void setdnsServerIP(String dnsServerIP) {
        String olddnsServerIP = this.dnsServerIP;
        this.dnsServerIP = dnsServerIP;
        changeSupport.firePropertyChange("dnsServerIP", olddnsServerIP, dnsServerIP);
    }

    public void setportPASV(String portPASV) {
        String oldportPASV = this.portPASV;
        this.portPASV = portPASV;
        changeSupport.firePropertyChange("portPASV", oldportPASV, portPASV);
    }

    public void setsubNet(int subNet) {
        int oldsubNet = this.subNet;
        this.subNet = subNet;
        changeSupport.firePropertyChange("subNet", oldsubNet, subNet);
    }
    
    public void setdomainName(int domainName) {
        int olddomainName = this.domainName;
        this.domainName = domainName;
        changeSupport.firePropertyChange("domainName", olddomainName, domainName);
    }

    // </editor-fold>
    
    public static Client createTestClient() {
        Client client = new Client();
        client.setuserID("_");
        client.setuserPassword("_");
        client.setuserRegion("_");
        client.setipAddress();
        
        client.setdnsServerIP("_");
        client.setportPASV("_");
        
        client.setsubNet();
        client.setdomainName();

        return client;
    }
}
