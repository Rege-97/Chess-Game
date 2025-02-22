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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test extends JFrame {
	int row, col;
	JPanel p_board;
	JPanel boards[][];
	JButton bt_pawn;
	JButton movepins[][];

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

		movepins = new JButton[9][9];

		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				movepins[i][j] = new JButton("0");
				boards[i][j].add(movepins[i][j]);
				movepins[i][j].setVisible(false);
			}
		}

		this.add(p_board, "Center");

		Pawn blackpawns[] = new Pawn[8];

		for (int i = 0; i < 8; i++) {
			blackpawns[i] = new Pawn("black", 2, i + 1);
			blackpawns[i].setBackground(Color.white);
			boards[2][i + 1].add(blackpawns[i]);
			
		}
		
		Pawn whitepawns[] = new Pawn[8];
		
		for (int i = 0; i < 8; i++) {
			whitepawns[i] = new Pawn("white", 7, i + 1);
			whitepawns[i].setBackground(Color.white);
			boards[7][i + 1].add(whitepawns[i]);
			
		}

		for (int i = 0; i < 8; i++) {
			final int index = i;
			blackpawns[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					blackpawns[index].BlackMove(boards, movepins, p_board, blackpawns);
				}
			});
		}
		
		for (int i = 0; i < 8; i++) {
			final int index = i;
			whitepawns[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					whitepawns[index].WhiteMove(boards, movepins, p_board, whitepawns);
				}
			});
		}

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
