package test;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MoveTest2 extends JFrame {

    private JPanel pn[] = new JPanel[64];
    private JButton pone1[]=new JButton[8];
    private JButton pone2[]=new JButton[8];
    
    
    public MoveTest2() {
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 8));
        Color color1 = Color.LIGHT_GRAY;
        Color color2 = Color.DARK_GRAY;
        
        for (int i = 0; i < pn.length; i++) {
            pn[i] = new JPanel();
            int j=i;
			j+=(i/8);
			if(j%2==1) {
                pn[i].setBackground(color1);
            } else {
                pn[i].setBackground(color2);
            }
            panel.add(pn[i]);
        }

       

        
        this.add(panel);
    }

    public static void main(String[] args) {
        MoveTest2 mt = new MoveTest2();
        mt.setVisible(true);
    }
}

