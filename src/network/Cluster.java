package network;
import java.rmi.server.ServerRef;
import java.util.*;

public class Cluster {
    private List<Server> servers;
    private List<Link> links;

    public Cluster() {
        servers = new ArrayList<>();
        links = new ArrayList<>();
    }

    public Cluster(List<Server> servers, List<Link> links) {
        this.servers = servers;
        this.links = links;
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public void addServers(Server item) {
        servers.add(item);
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLinks(Link item) {
        links.add(item);
    }

    public Server searchServer(String ip) {
        for (Server server : servers) {
            if (server.getIp().equals(ip)) {
                return server;
            }
        }
        return null;
    }

    public Server searchServer(int id) {
        for (Server server : servers) {
            if (server.getId() == id) {
                return server;
            }
        }
        return null;
    }

    public List<Server> getNoneVisited(List<Server> servers) {
        List<Server> result = new ArrayList<>();
        for (Server server : servers) {
            if (!server.isVisited()) {
                result.add(server);
            }
        }
        return result;
    }

    public Server extractMin(List<Server> servers) {
        if (servers.isEmpty()) return null;
        Server result = null;
        Boolean bool = false;
        double poids = 0;
        for (Server server : servers) {
        
            if (!bool && server.getPoids() != -1) {
                poids = server.getPoids();
                result = server;
                bool = true;
            }
            if (bool && server.getPoids() != -1 && server.getPoids() < poids) {
                result = server;
                poids = server.getPoids();
            }
        }
        return result;
    }

    public Link extractLink(Server s1, Server s2) {
        for (Link link : links) {
            if (link.checkServer(s1) && link.checkServer(s2)) {
                return link;
            }
        }
        return null;
    }

    public double getDistance(Server server1, Server server2) {
        Link link = extractLink(server1, server2);
        return (link != null) ? link.getDistance() : 0;
    }

    public double sommePoids(Cluster graphe) {
        double result = 0;
        for (Server s : graphe.getServers()) {
            result += s.getPoids();
        }
        return result;
    }

    public void notVisited() {
        for (Server s : servers) {
            s.setVisited(false);
        }
    }

    public void isVisited() {
        for (Server s : servers) {
            s.setVisited(true);
        }
    }

    public Cluster Dijkstra(Cluster graphe, Server sommet, Server cible) {
        Cluster result = new Cluster();
        
        for (Server server : graphe.getServers()) {
            server.setVisited(false);
            server.setPoids(-1);
            server.setPredecessor(null);
        }
        sommet.setPoids(0);
        Cluster domaine = new Cluster();
        List<Server> listServer = new ArrayList<>(graphe.getServers());
        while (!listServer.isEmpty()) {
            listServer = getNoneVisited(listServer);
            Server min = extractMin(listServer);
            if (min != null) {
                min.setVisited(true);
                domaine.addServers(min);
                for (Server neighbor : min.getNeighbor()) {
                    double newDistance = min.getPoids() + graphe.getDistance(min, neighbor);
                    if (neighbor.getPoids() == -1 || neighbor.getPoids() > newDistance) {
                        neighbor.setPoids(newDistance);
                        neighbor.setPredecessor(min);
                    }
                }
            }
        }
        
        for (Server server : domaine.getServers()) {
            server.setVisited(false);
        }
        Server parent = cible;
        while (parent != null) {
            parent.setVisited(true);
            result.addServers(parent);
            parent = parent.getPredecessor();
        }
        return result;
    }

    public Cluster BFS (Server start, Server stop) {
        Cluster result = new Cluster();
        Vector file = new Vector();
        start.setVisited(true);
        // Enfiler
        file.add(start);
        while (file.size() > 0) {
            int suivant = 0;
            Server p = (Server) file.get(suivant);
            // Defiler
            file.remove(suivant);
            Server[] voisins = p.getNeighbor().toArray(new Server[]{});
            for (int i = 0; i < voisins.length; i++) {
                Server lieu = voisins[i];
                if (!lieu.isVisited()) {
                    if (lieu.getId() == stop.getId()) {
                        lieu.setVisited(true);
                        lieu.setPredecessor(p);
                        file = new Vector();
                        
                        for (Server server : this.getServers()) {
                            server.setVisited(false);
                        }
                        Server parent = lieu;
                        while (parent != null) {
                            parent.setVisited(true);
                            result.addServers(parent);
                            parent = parent.getPredecessor();
                        }

                        break;
                    }
                    if (lieu.getId() != stop.getId()) {
                        lieu.setVisited(true);
                        lieu.setPredecessor(p);
                        file.add(lieu);
                    }
                }
            }
        }
        return result;
    }

    public Cluster search(String siteName, Server start) {
        Cluster result = new Cluster();
        for (Server s : servers) {
            s.setVisited(false);
        }

        double totalPoids = -1;
        for (Server server : servers) {
            Site page = server.searchSite(siteName);
            if (page != null) {
                if (server.getId() == start.getId()) {
                    result.addServers(server);
                    this.notVisited();
                    result.isVisited();
                    return result;
                }
                if (server.getId() != start.getId()) {
                    Cluster path = Dijkstra(this, start, server);
                    double distanceTraveled = sommePoids(path);

                    if (totalPoids == -1 || totalPoids > distanceTraveled) {
                        result = path;
                        totalPoids = distanceTraveled;
                    }
                }
            }
        }
        this.notVisited();
        result.isVisited();
        return result;
    }

    public Cluster searchBFS(String siteName, Server start) {
        Cluster result = new Cluster();
        for (Server s : servers) {
            s.setVisited(false);
        }

        double totalPoids = -1;
        for (Server server : servers) {
            Site page = server.searchSite(siteName);
            if (page != null) {
                if (server.getId() == start.getId()) {
                    result.addServers(server);
                    this.notVisited();
                    result.isVisited();
                    return result;
                }
                if (server.getId() != start.getId()) {
                    Cluster path = BFS(start, server);
                    double distanceTraveled = path.getServers().size();

                    if (totalPoids == -1 || totalPoids > distanceTraveled) {
                        result = path;
                        totalPoids = distanceTraveled;
                    }
                }
            }
        }
        this.notVisited();
        result.isVisited();
        return result;
    }
}
