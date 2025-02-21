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
		Pawn pawn = this;

		boolean attack = false;

		if (row + 1 > 8) {
			return;

		} else {
			removeAction(movepins);

			// 왼쪽 대각선
			if (row + 1 <= 8 && col - 1 >= 1) {
				if (boards[row + 1][col - 1].getComponentCount() == 2) {
					if (((Pawn) boards[row + 1][col - 1].getComponent(1)).side.equals("white")) {

						movepins[row + 1][col - 1].setVisible(true);
						attack = true;

						movepins[row + 1][col - 1].addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								boards[row][col].remove(pawn);

								boards[row + 1][col - 1].add(pawn);
								pawn.setBackground(Color.white);

								movepinsNotVisible(movepins);

								boards[row + 1][col - 1].remove(boards[row + 1][col - 1].getComponent(1));

								movecount++;

								row = row + 1;
								col = col - 1;

								pawn.click = false;

								p_board.getParent().validate();
								p_board.getParent().repaint();

							}
						});

					}
				}
			}
			if (row + 1 <= 8 && col + 1 <= 8) {
				// 오른쪽 대각선
				if (boards[row + 1][col + 1].getComponentCount() == 2) {
					if (((Pawn) boards[row + 1][col + 1].getComponent(1)).side.equals("white")) {

						movepins[row + 1][col + 1].setVisible(true);
						attack = true;

						movepins[row + 1][col + 1].addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								boards[row][col].remove(pawn);

								boards[row + 1][col + 1].add(pawn);
								pawn.setBackground(Color.white);

								boards[row + 1][col + 1].remove(boards[row + 1][col + 1].getComponent(1));

								movecount++;

								movepinsNotVisible(movepins);

								row = row + 1;
								col = col + 1;

								pawn.click = false;

								p_board.getParent().validate();
								p_board.getParent().repaint();

							}
						});

					}
				}
			}

			if (boards[row + 1][col].getComponentCount() == 2 && !attack) {
				return;
			}

			// 앞으로 한칸
			click = true;

			for (int i = 0; i < pawns.length; i++) {
				if (pawns[i] != this) {
					pawns[i].click = false;
				}

			}

			if (boards[row + 1][col].getComponentCount() != 2) {

				movepins[row + 1][col].setVisible(true);
				this.setBackground(Color.red);
				movepins[row + 1][col].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						boards[row][col].remove(pawn);
						boards[row + 1][col].add(pawn);
						pawn.setBackground(Color.white);

						for (int i = 1; i <= 8; i++) {
							for (int j = 1; j <= 8; j++) {
								movepins[i][j].setVisible(false);
							}
						}

						movepinsNotVisible(movepins);

						System.out.println("1칸");

						movecount++;

						row = row + 1;

						pawn.click = false;

						p_board.getParent().validate();
						p_board.getParent().repaint();
					}
				});
			}
		}

		// 앞으로 두칸
		if (movecount == 0 && boards[row + 2][col].getComponentCount() != 2) {
			movepins[row + 2][col].setVisible(true);

			movepins[row + 2][col].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					boards[row][col].remove(pawn);

					boards[row + 2][col].add(pawn);
					pawn.setBackground(Color.white);

					movepinsNotVisible(movepins);

					System.out.println("2칸");

					movecount++;

					row = row + 2;

					pawn.click = false;

					p_board.getParent().validate();
					p_board.getParent().repaint();

				}
			});
		}
	}

	public void movepinsNotVisible(JButton movepins[][]) {
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				movepins[i][j].setVisible(false);
			}
		}
	}

	public void removeAction(JButton movepins[][]) {
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				ActionListener[] listeners = movepins[i][j].getActionListeners();
				for (int k = 0; k < listeners.length; k++) {
					movepins[i][j].removeActionListener(listeners[k]);
				}
			}
		}

	}
}
