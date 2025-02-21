package moveTest;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Test extends JFrame {
	int row, col;
	JPanel p_board;
	JPanel boards[][];
	JButton bt_pawn;

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

		p_board = new JPanel(new GridLayout(8, 8));

		boardSet();

		JButton movepin[][] = new JButton[9][9];

		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				movepin[i][j] = new JButton("0");
				boards[i][j].add(movepin[i][j]);
				movepin[i][j].setVisible(false);
			}
		}

		this.add(p_board, "Center");
		bt_pawn = new JButton("123");

		boards[2][2].add(bt_pawn);

		bt_pawn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				bt_pawn.setBackground(Color.red);

				row = 0;
				col = 0;
				for (int i = 1; i <= 8; i++) {
					if (Arrays.asList(boards[i]).indexOf(bt_pawn.getParent()) != -1) {
						row = i;
						col = Arrays.asList(boards[i]).indexOf(bt_pawn.getParent());
						break;
					}
				}

				movepin[row + 1][col].setVisible(true);
				movepin[row + 2][col].setVisible(true);
				System.out.println(row + " " + col);

			}
		});

		this.validate();
	}

	public void boardSet() {
		boards = new JPanel[9][9];

		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				boards[i][j] = new JPanel();
				if (i % 2 == 0) {
					if (j % 2 == 0) {
						boards[i][j].setBackground(Color.yellow);
					} else {
						boards[i][j].setBackground(Color.gray);
					}
				} else {
					if (j % 2 == 0) {
						boards[i][j].setBackground(Color.gray);
					} else {
						boards[i][j].setBackground(Color.yellow);
					}
				}
				p_board.add(boards[i][j]);
			}
		}
	}

	public static void main(String[] args) {
		new Test();

	}

}
