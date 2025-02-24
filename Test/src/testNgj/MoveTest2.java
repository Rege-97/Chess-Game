package testNgj;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MoveTest2 extends JFrame {

    private JPanel pn[] = new JPanel[64];
    private JButton pone1[] = new JButton[8];
    private JPanel panel = new JPanel();
    private JButton room = new JButton("����");
    private JButton tempButton; // �̵��� ��ư�� �ӽ÷� ������ ����

    public MoveTest2() {
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setLayout(new GridLayout(8, 8));
        Color color1 = Color.LIGHT_GRAY;
        Color color2 = Color.DARK_GRAY;

        for (int i = 0; i < pn.length; i++) {
            pn[i] = new JPanel();
            int j = i;
            j += (i / 8);
            if (j % 2 == 1) {
                pn[i].setBackground(color1);
            } else {
                pn[i].setBackground(color2);
            }
            panel.add(pn[i]);
        }

        // ��ư �ʱ�ȭ
        for (int i = 0; i < pone1.length; i++) {
            pone1[i] = new JButton("Button " + (i + 1));
            pone1[i].addActionListener(new ButtonActionListener());
            pn[i + 8].add(pone1[i]);
        }

        room.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = room.getParent();
                if (parent != null && tempButton != null) {
                    parent.remove(room); // "����" ��ư ����
                    parent.add(tempButton); // �ӽ� ��ư�� �ش� �гο� �߰�
                    tempButton = null; // �ӽ� ���� �ʱ�ȭ
                    revalidate();
                    repaint();
                }
            }
        });

        this.add(panel);
    }

    private class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();

            if (button == pone1[0]) {
                pn[16].add(room); // "����" ��ư �߰�
                tempButton = button; // �̵��� ��ư�� �ӽ÷� ����
                revalidate();
                repaint();

                int panelIndex = findPanelIndex(button);
                System.out.println("��ư�� �ִ� �г��� �ε���: " + panelIndex);
            }
        }
    }

    private int findPanelIndex(JButton button) {
        for (int i = 0; i < pn.length; i++) {
            if (isButtonInPanel(button, pn[i])) {
                return i;
            }
        }
        return -1; // ��ư�� �гο� ���� ���
    }

    private boolean isButtonInPanel(JButton button, JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp == button) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        MoveTest2 mt = new MoveTest2();
        mt.setVisible(true);
    }
}