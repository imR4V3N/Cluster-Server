package utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import gui.Panel;
import network.Cluster;
import network.Server;
import network.Site;

public class Action implements ActionListener{
    JButton button;
    JTextField [] inputs;
    Panel panel;

    public Action() {}
    public Action(JButton button, JTextField[] inputs, Panel panel) {
        this.button = button;
        this.inputs = inputs;
        this.panel = panel;
    }

    public boolean checkImput(){
        for (int i=0;i<this.inputs.length;i++){
            if (this.inputs[i].getText().compareToIgnoreCase("")==0 || this.inputs[i].getText().compareToIgnoreCase(" ")==0){
                return false;
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        System.out.println(button.getName());

        //Ajouter Server 
        if (button.getName().compareToIgnoreCase("bt_addServer")==0){
            if (this.checkImput()){
                // ip du server
                String ip = this.inputs[0].getText();
                this.panel.createServer(ip);
            }
        }
        // Ajouter lien 
        if (button.getName().compareToIgnoreCase("bt_addLink")==0){
            if (this.checkImput()){
                // server 1 ip
                String ip1 = this.inputs[0].getText();
                // server 2 ip
                String ip2 = this.inputs[1].getText();
                // longueur
                double longueur = Integer.parseInt(this.inputs[2].getText());
                if (!ip1.equals(ip2)){
                    Server server1 = this.panel.getDomaine().searchServer(ip1);
                    Server server2 = this.panel.getDomaine().searchServer(ip2);
                    if (server1!=null && server2!=null){
                        this.panel.createLink(server1,server2,longueur);
                    }
                }
            }
        }
        // Ajouter Site
        if (button.getName().compareToIgnoreCase("bt_addPage")==0){
            if (this.checkImput()){
                // server
                String ip = this.inputs[0].getText();
                // nom du site
                String name = this.inputs[1].getText();
                Server server = this.panel.getDomaine().searchServer(ip);
                if (server != null){
                    int id = server.getSites().size()+1;
                    Site page = new Site(id,name);
                    server.addSites(page);
                }
            }
        }
        // Recherche by Site
        if (button.getName().compareToIgnoreCase("bt_searchPage")==0){
            if (this.checkImput()){
                // algo
                String algo = this.inputs[0]. getText();
                // server
                String ip = this.inputs[1].getText();
                // nom du site
                String name = this.inputs[2].getText();
                Server server = this.panel.getDomaine().searchServer(ip);
                
                if (server != null){
                    Cluster path = new Cluster();
                    if (algo.equals("bfs")) {
                        path = this.panel.getDomaine().searchBFS(name,server);
                    } else {
                        path = this.panel.getDomaine().search(name,server);
                    }
                    this.panel.setPath(path);
                    Server [] S = path.getServers().toArray(new Server[]{});
                    for (int i=0;i<S.length;i++){
                        System.out.println("Server : "+S[i].getId());
                    }
                }
            }
        }
    }
}
