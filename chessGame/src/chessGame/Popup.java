package chessGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Popup {
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItem;
    
    public Popup() {
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        menuBar.add(menu);

        menuItem = new JMenuItem("Popup");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "팝업 내용", "팝업 제목", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menu.add(menuItem);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
