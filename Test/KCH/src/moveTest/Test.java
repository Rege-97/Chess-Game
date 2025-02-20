package moveTest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Test extends JFrame {

	public Test() {
		super("테스트");

		// 화면 중앙 출력
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		int x = (screenSize.width - frameSize.width) / 2;
		int y = (screenSize.height - frameSize.height) / 2;
		setLocation(x, y);

		// 창 닫기
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		this.setSize(800, 800);
		this.setVisible(true);
		this.setLayout(new BorderLayout());

		JPanel p_board = new JPanel(new GridLayout(8, 8));

		JPanel board[][] = new JPanel[9][9];

		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <=8; j++) {
				board[i][j] = new JPanel();
				if (i % 2 == 0) {
					if (j % 2 == 0) {
						board[i][j].setBackground(Color.yellow);
					} else {
						board[i][j].setBackground(Color.gray);
					}
				}else {
					if (j % 2 == 0) {
						board[i][j].setBackground(Color.gray);
					} else {
						board[i][j].setBackground(Color.yellow);
					}
				}
				p_board.add(board[i][j]);
			}
		}
		this.add(p_board, "Center");
		JButton bt_pawn=new JButton("123");
		board[1][1].add(bt_pawn);
		
		bt_pawn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bt_pawn.setBackground(Color.red);
				
			}
		});
		
		this.validate();
	}

	public static void main(String[] args) {
		new Test();

	}

}
