package network;

public class Site {
    int ip;
    String name;

    public Site() {}
    public Site(int ip, String name) {
        this.ip = ip;
        this.name = name;
    }

    public int getIp() {
        return ip;
    }

    public void setIp(int ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }   
}
