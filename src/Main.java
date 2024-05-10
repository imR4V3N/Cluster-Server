import java.awt.Point;
import java.util.Vector;
import gui.Frame;
import network.Cluster;
import network.Link;
import network.Server;

public class Main {
    public static void main(String[] args) {
        Server s1 = new Server(1,new Point(0,0));
        s1.setIp("172.20.32.63");
        Server s2 = new Server(2,new Point(0,0));
        s2.setIp("172.20.34.56");
        Server s3 = new Server(3,new Point(0,0));
        s3.setIp("172.20.225.250");
        Server s4 = new Server(4,new Point(0,0));
        s4.setIp("192.0.20.45");
        Server s5 = new Server(5,new Point(0,0));
        s5.setIp("192.0.147.253");
        Server s6 = new Server(6,new Point(0,0));
        s6.setIp("192.168.88.73");

        Link l1 = new Link(1,100);
        l1.addServers(s1);
        l1.addServers(s2);
        l1.linked();

        Link l2 = new Link(2,5);
        l2.addServers(s1);
        l2.addServers(s3);
        l2.linked();

        Link l3 = new Link(3,20);
        l3.addServers(s3);
        l3.addServers(s4);
        l3.linked();

        Link l4 = new Link(4,25);
        l4.addServers(s2);
        l4.addServers(s6);
        l4.linked();

        Link l5 = new Link(5,5);
        l5.addServers(s4);
        l5.addServers(s5);
        l5.linked();

        // Link l6 = new Link(6,8);
        // l6.addServers(s5);
        // l6.addServers(s4);
        // l6.linked();

        // Link l7 = new Link(7,6);
        // l7.addServers(s4);
        // l7.addServers(s6);
        // l7.linked();

        Vector<Server> servers = new Vector<Server>();
        servers.add(s1);
        servers.add(s2);
        servers.add(s3);
        servers.add(s4);
        servers.add(s5);
        servers.add(s6);

        Vector<Link> links = new Vector<Link>();
        links.add(l1);
        links.add(l2);
        links.add(l3);
        links.add(l4);
        links.add(l5);
        // links.add(l6);
        // links.add(l7);

        Cluster graphe = new Cluster(servers,links);
        // Cluster graphe = new Cluster();
        new Frame(graphe);
    }
}
