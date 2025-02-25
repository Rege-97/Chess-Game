package chessGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Rook extends ChessPiece {
	Rook rook;

	public Rook(String side, int row, int col, ChessBoard chessBoard) {
		this.side = side;
		this.row = row;
		this.col = col;
		live = true;
		this.chessBoard = chessBoard;

		setSize(chessBoard.getSize());

		black_icon = new ImageIcon("image/Rook-black.png");
		white_icon = new ImageIcon("image/Rook-white.png");
		black_icon_select = new ImageIcon("image/Rook-black_s.png");
		white_icon_select = new ImageIcon("image/Rook-white_s.png");

		setContentAreaFilled(false);
		setFocusPainted(false);
		setOpaque(false);

		if (side.equals("black")) {
			setIcon(black_icon);
		} else if (side.equals("white")) {
			setIcon(white_icon);
		}

	}

	public void blackMove(JPanel boards[][], JButton movepins[][], JPanel p_board, ArrayList<ChessPiece> chesspiece) {

		rook = this;

		for (int i = 0; i < chesspiece.size(); i++) {
			if (chesspiece.get(i) != rook) {
				chesspiece.get(i).setIcon(chesspiece.get(i).black_icon);
			}
		}

		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				movepins[i][j].setVisible(false);
			}
		}
		this.setIcon(black_icon_select);

		for (int i = 1; i <= 8; i++) {
			if (boards[i][col].getComponentCount() != 2 || boards[row][i].getComponentCount() != 2) {
				movepins[i][col].setVisible(true);
				movepins[row][i].setVisible(true);
			}
		}

		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				final int indexrow = i;
				final int indexcol = j;

				if (movepins[indexrow][indexcol].isVisible()) {
					movepins[indexrow][indexcol].addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							boards[row][col].remove(rook);
							boards[indexrow][indexcol].add(rook, "Center");
							rook.setIcon(black_icon);

							for (int i = 1; i <= 8; i++) {
								for (int j = 1; j <= 8; j++) {
									movepins[i][j].setVisible(false);
								}
							}

							movepinsNotVisible(movepins);

							row = indexrow;
							col = indexcol;

							chessBoard.turn = "white";

							p_board.getParent().validate();
							p_board.getParent().repaint();

						}
					});
				}
			}
		}

	}

	@Override
	public void whiteMove(JPanel[][] boards, JButton[][] movepins, JPanel p_board, ArrayList<ChessPiece> chesspiece) {
		// TODO Auto-generated method stub

	}

}
