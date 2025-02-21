package moveTest;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Pawn extends JButton {
	String side;
	int row;
	int col;
	int moverow;
	int movecol;
	int movecount;
	boolean click;
	boolean live;

	public Pawn(String side, int row, int col) {
		this.side = side;
		this.row = row;
		this.col = col;
		live = true;
		click = false;

		setFont(new Font("Default Font", Font.BOLD, 50));

		if (side.equals("black")) {
			setText("♟");
		} else if (side.equals("white")) {
			setText("♙");
		}
	}

	public void BlackMove(JPanel boards[][], JButton movepins[][], JPanel p_board, Pawn pawns[]) {
		if (boards[row + 1][col].getComponentCount() == 2) {
			return;
		}
		ActionListener[] al;

		click = true;

		for (int i = 0; i < pawns.length; i++) {
			if (pawns[i] != this) {
				pawns[i].click = false;
			}

		}

		Pawn pawn = this;

		this.setBackground(Color.red);

		row = 0;
		col = 0;

		for (int i = 1; i <= 8; i++) {
			if (Arrays.asList(boards[i]).indexOf(this.getParent()) != -1) {
				row = i;
				col = Arrays.asList(boards[i]).indexOf(this.getParent());
				break;
			}
		}

		movepins[row + 1][col].setVisible(true);

		al = movepins[row + 1][col].getActionListeners();

		for (int i = 0; i < al.length; i++) {
			movepins[row + 1][col].removeActionListener(al[i]);
		}

		// 앞으로 한칸
		movepins[row + 1][col].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boards[row][col].remove(pawn);
				boards[row + 1][col].add(pawn);
				pawn.setBackground(Color.green);

				movepins[row + 1][col].setVisible(false);
				movepins[row + 2][col].setVisible(false);
				System.out.println("1칸");

				movecount++;

				pawn.click = false;

				p_board.getParent().validate();
				p_board.getParent().repaint();
			}
		});

		// 앞으로 두칸
		if (movecount == 0 && boards[row + 2][col].getComponentCount() != 2) {
			movepins[row + 2][col].setVisible(true);
			al = movepins[row + 2][col].getActionListeners();

			for (int i = 0; i < al.length; i++) {
				movepins[row + 2][col].removeActionListener(al[i]);
			}

			movepins[row + 2][col].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					boards[row][col].remove(pawn);

					boards[row + 2][col].add(pawn);
					pawn.setBackground(Color.green);

					movepins[row + 1][col].setVisible(false);
					movepins[row + 2][col].setVisible(false);
					System.out.println("2칸");

					movecount++;
					pawn.click = false;

					p_board.getParent().validate();
					p_board.getParent().repaint();

				}
			});
		}
		
		// 왼쪽 대각선
		if (boards[row + 1][col - 1].getComponentCount() == 2) {
			if (((Pawn) boards[row + 1][col - 1].getComponent(1)).side.equals("white")) {

				movepins[row + 1][col - 1].setVisible(true);
				al = movepins[row + 1][col - 1].getActionListeners();

				for (int i = 0; i < al.length; i++) {
					movepins[row + 1][col - 1].removeActionListener(al[i]);
				}

				movepins[row + 1][col - 1].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						boards[row][col].remove(pawn);

						boards[row + 1][col - 1].add(pawn);
						pawn.setBackground(Color.green);

						movepins[row + 1][col].setVisible(false);
						movepins[row + 2][col].setVisible(false);
						movepins[row + 1][col - 1].setVisible(false);

						boards[row + 1][col - 1].remove(boards[row + 1][col - 1].getComponent(1));

						movecount++;
						pawn.click = false;

						p_board.getParent().validate();
						p_board.getParent().repaint();

					}
				});

			}
		}
	}
}
