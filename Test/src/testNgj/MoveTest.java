package testNgj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MoveTest extends JFrame {
    private JPanel panel;
    private JButton button;
    private boolean isButtonClicked;

    public MoveTest() {
        setTitle("Click to Move Example");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8,8));

        panel = new JPanel();
        panel.setBounds(50, 50, 300, 300);
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(new FlowLayout());
        

        button = new JButton("Click Me");
        button.setBounds(150, 125, 100, 50);
        isButtonClicked = false;

        // ��ư Ŭ�� ������
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isButtonClicked = true;
            }
        });

        // �г� Ŭ�� ������
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isButtonClicked) {
                    button.setLocation(e.getX() - button.getWidth() / 2, e.getY() - button.getHeight() / 2);
                    isButtonClicked = false;
                }
            }
        });

        panel.add(button);
        add(panel);
    }

    public static void main(String[] args) {
            MoveTest ex = new MoveTest();
            ex.setVisible(true);

    }
}        