package network;

import java.awt.Point;
import java.util.Vector;

public class Server {
    int id;
    String ip;
    double poids;
    Server predecessor;
    Vector<Server> neighbor = new Vector<Server>();
    boolean visited = false;
    Point position;
    Vector<Site> sites = new Vector<Site>();

    public Server() {}
    public Server(int id) {
        this.id = id;
    }

    public Server(int id, Point position) {
        this.id = id;
        this.position = position;
    }

    public Server(int id, double poids, Server predecessor, Vector<Server> neighbor) {
        this.id = id;
        this.poids = poids;
        this.predecessor = predecessor;
        this.neighbor = neighbor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public Server getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Server predecessor) {
        this.predecessor = predecessor;
    }

    public Vector<Server> getNeighbor() {
        return neighbor;
    }

    public void setNeighbor(Vector<Server> neighbor) {
        this.neighbor = neighbor;
    }

    public void addNeighbor(Server item) {
        this.neighbor.add(item);
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Vector<Site> getSites() {
        return sites;
    }

    public void setSites(Vector<Site> sites) {
        this.sites = sites;
    }

    public void addSites(Site sites) {
        this.sites.add(sites);
    }

    public void move(int dx, int dy){
        int x = (int)this.getPosition().getX() + dx;
        int y = (int)this.getPosition().getY() + dy;
        this.setPosition(new Point(x,y));
    }

    public void goUp(int vitesse){
        this.move(0, (-1)*vitesse);
    }

    public void goDown(int vitesse){
        this.move(0, vitesse);
    }

    public void goLeft(int vitesse){
        this.move((-1)*vitesse, 0);
    }

    public void goRight(int vitesse){
        this.move(vitesse, 0);
    }

    public void keyPressed (int code,int vitesse){
        if (code == 38){
            this.goUp(vitesse);
        }
        if (code == 40){
            this.goDown(vitesse);
        }
        if (code == 37){
            this.goLeft(vitesse);
        }
        if (code == 39){
            this.goRight(vitesse);
        }
    }

    public Site searchSite(String name){
        Site [] list = this.getSites().toArray(new Site[]{});

        for (int i=0;i< list.length;i++){
            if (list[i].getName().compareToIgnoreCase(name) == 0){
                return list[i];
            }
        }
        return null;
    }

    public boolean contains(Point p, int WIDTH_SIZE, int HEIGHT_SIZE) {
        int x = (int) position.getX();
        int y = (int) position.getY();
        int width = WIDTH_SIZE;
        int height = HEIGHT_SIZE;
        return (p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height);
    }
}
