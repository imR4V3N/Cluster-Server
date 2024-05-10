package gui;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdesktop.swingx.prompt.PromptSupport;
import org.w3c.dom.Text;

import network.Cluster;
import utils.Action;

public class Frame  extends JFrame{
    Panel panel;
    public Frame(Cluster graph){
        panel=new Panel();
        panel.setDomaine(graph);
        panel.setController(0);

        this.getContentPane().add(panel, BorderLayout.CENTER);
        this.setTitle("Cluster");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        panel.startGame();

        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.add(this.getAddLinkForm());
        panel.add(this.getAddSiteForm());
        this.getContentPane().add(panel,BorderLayout.SOUTH);
        
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.GRAY);
        panel2.add(this.getServerForm());
        panel2.add(this.getSearchSiteForm());
        this.getContentPane().add(panel2,BorderLayout.NORTH);
    }

    public JPanel getAddLinkForm (){
        JPanel panel=new JPanel();

        JButton bt=new JButton("Add Link");
        bt.setName("bt_addLink");
        panel.add(bt);

        JTextField [] inputs=new JTextField [3];
        inputs[0]=new JTextField(10);
        PromptSupport.setPrompt("Server 1 IP", inputs[0]); 
        PromptSupport.setForeground(Color.GRAY, inputs[0]); 
        panel.add(inputs[0]);
        inputs[1]=new JTextField(10);
        PromptSupport.setPrompt("Server 2 IP", inputs[1]); 
        PromptSupport.setForeground(Color.GRAY, inputs[1]); 
        panel.add(inputs[1]);
        inputs[2]=new JTextField(10);
        PromptSupport.setPrompt("Latence", inputs[2]); 
        PromptSupport.setForeground(Color.GRAY, inputs[2]); 
        panel.add(inputs[2]);

        bt.addActionListener(new Action(bt,inputs,this.panel));
        return panel;
    }

    public JPanel getAddSiteForm (){
        JPanel panel=new JPanel();

        JButton bt=new JButton("Add Site");
        bt.setName("bt_addPage");
        panel.add(bt);

        JTextField [] inputs=new JTextField [2];
        inputs[0]=new JTextField(10);
        PromptSupport.setPrompt("Server IP", inputs[0]); 
        PromptSupport.setForeground(Color.GRAY, inputs[0]);  
        panel.add(inputs[0]);
        inputs[1]=new JTextField(10);
        PromptSupport.setPrompt("Site Name", inputs[1]); 
        PromptSupport.setForeground(Color.GRAY, inputs[1]); 
        panel.add(inputs[1]);

        bt.addActionListener(new Action(bt,inputs,this.panel));
        return panel;
    }

    public JPanel getSearchSiteForm (){
        JPanel panel=new JPanel();

        JButton bt=new JButton("Search by Site");
        bt.setName("bt_searchPage");
        panel.add(bt);

        JTextField [] inputs=new JTextField [3];
        JLabel text = new JLabel("Algorithme: bfs or dikjstra");        
        panel.add(text);
        inputs[0]=new JTextField(10);
        PromptSupport.setPrompt("Algorithme", inputs[0]); 
        PromptSupport.setForeground(Color.GRAY, inputs[0]); 
        panel.add(inputs[0]);

        inputs[1]=new JTextField(10);
        PromptSupport.setPrompt("Server Start IP", inputs[1]); 
        PromptSupport.setForeground(Color.GRAY, inputs[1]); 
        panel.add(inputs[1]);
        
        inputs[2]=new JTextField(10);
        PromptSupport.setPrompt("Site Name", inputs[2]); 
        PromptSupport.setForeground(Color.GRAY, inputs[2]); 
        panel.add(inputs[2]);

        bt.addActionListener(new Action(bt,inputs,this.panel));
        return panel;
    }

    public JPanel getServerForm (){
        JPanel panel=new JPanel();

        JButton bt=new JButton("Add Server");
        bt.setName("bt_addServer");
        panel.add(bt);

        JTextField [] inputs=new JTextField [1];
        inputs[0]=new JTextField(10);
        PromptSupport.setPrompt("Server IP", inputs[0]); 
        PromptSupport.setForeground(Color.GRAY, inputs[0]); 
        panel.add(inputs[0]);

        bt.addActionListener(new Action(bt,inputs,this.panel));
        return panel;
    }
}
