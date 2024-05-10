package network;

import java.util.Vector;

public class Link {
    int id;
    double distance;
    Vector<Server> servers = new Vector<Server>();

    public Link() {
    }

    public Link(int id, double distance) {
        this.id = id;
        this.distance = distance;
    }

    public Link(int id, double distance, Vector<Server> servers) {
        this.id = id;
        this.distance = distance;
        this.servers = servers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Vector<Server> getServers() {
        return servers;
    }

    public void setServers(Vector<Server> servers) {
        this.servers = servers;
    }

    public void addServers(Server item) {
        this.servers.add(item);
    }
    public void linked (){
        Server [] list = this.getServers().toArray(new Server[]{});
        for (int i=0; i < list.length; i++){
            for (int j=0; j < list.length ;j++){
                if (j != i){
                    list[i].addNeighbor(list[j]);
                }
            }
        }
    }

    public boolean checkServer(Server server){
        boolean result = false;
        Server [] list = this.getServers().toArray(new Server[]{});

        for (int i=0;i<list.length;i++){
            if (server.getId() == list[i].getId()){
                result = true;
                break;
            }
        }
        return result;
    }
}
