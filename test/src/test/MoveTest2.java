package test;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MoveTest2 extends JFrame {

	private JPanel pn[] = new JPanel[64];
	private JButton pone1[] = new JButton[8];
	private JPanel panel = new JPanel();
	private JButton room=new JButton("가능");
	int count;

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

		// 버튼 초기화
		for (int i = 0; i < pone1.length; i++) {
			pone1[i] = new JButton("Button " + (i + 1));
			pone1[i].addActionListener(new ButtonActionListener());
			pn[i + 8].add(pone1[i]);
		}
		room.addActionListener(new ButtonActionListener());
		
		this.add(panel);
	}

	private class ButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			
			for(int i=0;i<pone1.length;i++) {
				if(button == pone1[i]) {

				}
			}
			
			if (button == pone1[count]) {
				pn[count+16].add(room); // "가능" 버튼 추가
                revalidate();
                repaint();
			} 
			
			if(button==room) {
				panel.remove(pone1[count]);
				panel.remove(room);
				pn[count+16].add(pone1[count]);
				revalidate();
				repaint();
			}
			
		}

	}
	

    private int findPanelIndex(JButton button) {
        for (int i = 0; i < pn.length; i++) {
            if (isButtonInPanel(button, pn[i])) {
                return i;
            }
        }
        return -1; // 버튼이 패널에 없을 경우
    }
    


	private boolean isButtonInPanel(JButton button, JPanel panel) {
	    for (Component comp : panel.getComponents())//:을 사용하면 배열을 순회함
	    	{
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