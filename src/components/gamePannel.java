package components;

import javax.swing.*;
import java.awt.*;
import java.security.PublicKey;

public class gamePannel extends JPanel{
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    PlayManager pm;


    public gamePannel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);
        pm = new PlayManager();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        pm.draw(g2);
    }

}
