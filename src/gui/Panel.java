package gui;
 
import network.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Panel extends java.awt.Panel implements Runnable {
    Cluster domaine;
    Cluster path;
    int controller;
    int speed = 8;
    Thread gameThread;
    static final int SCREEN_WIDTH = 1200;
    static final int SCREEN_HEIGHT = 600;
    static final int DELAY = 75;
    int WIDTH_SIZE = 70;
    int HEIGHT_SIZE = 100;
    Image serverImage;
    Image visitedImage;
    int thread = 200;

    public Panel() {
        this.setBackground(Color.GRAY);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);

        // Ajoutez un écouteur de souris pour détecter les événements de souris
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mousePosition = e.getPoint();
                isDragging = false;

                // Vérifiez si un server est cliqué
                selectedServer = getClickedServer(mousePosition);
                if (selectedServer != null) {
                    isDragging = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                isDragging = false;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (isDragging && selectedServer != null) {
                    Point currentPoint = e.getPoint();
                    int dx = currentPoint.x - mousePosition.x;
                    int dy = currentPoint.y - mousePosition.y;
                    selectedServer.move(dx, dy);
                    mousePosition = currentPoint;
                    repaint();
                }
            }
        });

        // Images des serveurs
        try {
            serverImage = ImageIO.read(new File("F:\\ITU\\S4\\Tsinjo(TP)\\Algo\\Dikjstra\\Dikjstra1.1\\img\\server.png"));
            visitedImage = ImageIO.read(new File("F:\\ITU\\S4\\Tsinjo(TP)\\Algo\\Dikjstra\\Dikjstra1.1\\img\\visited.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Cluster getDomaine() {
        return domaine;
    }

    public void setDomaine(Cluster domaine) {
        this.domaine = domaine;
    }

    public int getController() {
        return controller;
    }

    public void setController(int controller) {
        this.controller = controller;
    }

    public Cluster getPath() {
        return path;
    }

    public void setPath(Cluster path) {
        this.path = path;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            this.repaint();
            try {
                Thread.sleep(thread);
            } catch (Exception x) {
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        this.draw(g);
    }

    public void draw(Graphics g) {
        // Draw Server
        Server[] allServer = this.getDomaine().getServers().toArray(new Server[]{});
        for (int i = 0; i < allServer.length; i++) {
            if (allServer[i].isVisited()) {
                g.drawImage(visitedImage, (int) allServer[i].getPosition().getX(), (int) allServer[i].getPosition().getY(), WIDTH_SIZE, HEIGHT_SIZE, this);
            }
            else {
                g.drawImage(serverImage, (int) allServer[i].getPosition().getX(), (int) allServer[i].getPosition().getY(), WIDTH_SIZE, HEIGHT_SIZE, this);
            }

            // draw idServer
            int x = (int) (allServer[i].getPosition().getX());
            int y = (int) allServer[i].getPosition().getY() - 5;
            g.setColor(Color.BLACK);
            g.drawString("" + allServer[i].getIp(), x, y);

            int xposition = (int) (allServer[i].getPosition().getX() + 5);
            int yposition = (int) allServer[i].getPosition().getY() + 15;
            int j = 0;
            for (Site site : allServer[i].getSites()) {
                g.setColor(Color.WHITE);
                g.drawString("-" + site.getName(), xposition, yposition + j);
                j += 10;
            }
        }

        // Draw Link
        Link[] allLink = this.getDomaine().getLinks().toArray(new Link[]{});
        for (int i = 0; i < allLink.length; i++) {
            Server[] list = allLink[i].getServers().toArray(new Server[]{});
            if (list[0].isVisited() && list[1].isVisited()) {
                g.setColor(Color.GREEN);
            }
            int x1 = (int)( list[0].getPosition().getX() + WIDTH_SIZE/2 );
            int y1 = (int) (list[0].getPosition().getY() + HEIGHT_SIZE/2 );
            int x2 = (int) (list[1].getPosition().getX() + WIDTH_SIZE/2 );
            int y2 = (int) (list[1].getPosition().getY() + HEIGHT_SIZE/2 );

            g.setColor(Color.BLACK);
            g.drawLine(x1, y1, x2, y2);


            // Draw distance
            g.setColor(Color.RED);
            int centreX = (x1 + x2) / 2;
            int centreY = (y1 + y2) / 2;
            g.drawString("" + (int) allLink[i].getDistance() + " ms", centreX, centreY);
        }
    }

    public void createServer(String ip) {
        int id = this.getDomaine().getServers().size() + 1;
        Server s = new Server(id);
        s.setIp(ip);
        s.setPosition(new Point(20, 20));
        this.getDomaine().addServers(s);
    }

    public void createLink(Server s1, Server s2, double longueur) {
        int id = this.getDomaine().getLinks().size() + 1;
        Link l = new Link(id, longueur);
        l.addServers(s1);
        l.addServers(s2);
        l.linked();
        this.getDomaine().addLinks(l);
    }

    // Attributs pour la gestion du déplacement du serveur avec la souris
    private Point mousePosition;
    private boolean isDragging;
    private Server selectedServer;

    // Méthode pour obtenir le serveur cliqué
    private Server getClickedServer(Point clickPoint) {
        for (Server server : domaine.getServers()) {
            if (server.contains(clickPoint, WIDTH_SIZE, HEIGHT_SIZE)) {
                return server;
            }
        }
        return null;
    }
}
