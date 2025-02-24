package chessGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChessBoard extends JFrame {
	int row, col;
	JPanel p_board;
	JPanel boards[][];
	JButton bt_pawn;
	JButton movepins[][];
	String turn;

	public ChessBoard() {
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

		turn = "black";

		this.setSize(800, 800);
		this.setVisible(true);
		this.setLayout(new BorderLayout());

		p_board = new JPanel(new GridLayout(8, 8));

		boardSet();

		movepins = new JButton[9][9];

		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				movepins[i][j] = new JButton("0");
				boards[i][j].add(movepins[i][j],"North");
				movepins[i][j].setVisible(false);
			}
		}

		this.add(p_board, "Center");

	

		this.validate();
	}

	public void boardSet() {
		boards = new JPanel[9][9];

		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				boards[i][j] = new JPanel(new BorderLayout());
				if (i % 2 == 0) {
					if (j % 2 == 0) {
						boards[i][j].setBackground(Color.white);
					} else {
						boards[i][j].setBackground(Color.darkGray);
					}
				} else {
					if (j % 2 == 0) {
						boards[i][j].setBackground(Color.darkGray);
					} else {
						boards[i][j].setBackground(Color.white);
					}
				}
				p_board.add(boards[i][j]);
			}
		}
	}

	public static void main(String[] args) {
		System.setProperty("sun.java2d.uiScale", "1");
		new ChessBoard();

	}

}
