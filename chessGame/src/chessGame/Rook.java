package chessGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Rook extends ChessPiece {
	Rook rook;

	public Rook(String side, int row, int col, ChessBoard chessBoard, JPanel boards[][], JButton movepins[][],
			JPanel p_board, ArrayList<ChessPiece> chesspiece_black, ArrayList<ChessPiece> chesspiece_white) {
		this.side = side;
		this.row = row;
		this.col = col;
		live = true;
		this.chessBoard = chessBoard;
		this.boards = boards;
		this.movepins = movepins;
		this.p_board = p_board;
		this.chesspiece_black = chesspiece_black;
		this.chesspiece_white = chesspiece_white;

		setSize(chessBoard.getSize());

		black_icon = new ImageIcon("image/Rook-black.png");
		white_icon = new ImageIcon("image/Rook-white.png");
		black_icon_select = new ImageIcon("image/Rook-black_s.png");
		white_icon_select = new ImageIcon("image/Rook-white_s.png");
		black_icon_attack = new ImageIcon("image/Rook-black_a.png");
		white_icon_attack = new ImageIcon("image/Rook-white_a.png");

		setContentAreaFilled(false);
		setFocusPainted(false);
		setOpaque(false);

		if (side.equals("black")) {
			setIcon(black_icon);
		} else if (side.equals("white")) {
			setIcon(white_icon);
		}

	}

	@Override
	public void blackMove() {
		rook = this;

		for (int i = 0; i < chesspiece_black.size(); i++) {
			if (chesspiece_black.get(i) != rook) {
				chesspiece_black.get(i).setIcon(chesspiece_black.get(i).black_icon);
			}
			chesspiece_white.get(i).setIcon(chesspiece_white.get(i).white_icon);
		}

		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				movepins[i][j].setVisible(false);
			}
		}
		this.setIcon(black_icon_select);

		
		// 위쪽 이동 무브포인트
		for (int i = row-1; i >= 1; i--) {
			if (boards[i][col].getComponentCount() != 2) {
				movepins[i][col].setVisible(true);
			}else if (boards[i][col].getComponentCount() == 2) {
				if (((ChessPiece) boards[i][col].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[i][col].getComponent(1)).setIcon(white_icon_attack);
					break;
				}else {
					break;
				}
			}
		}
		
		
		// 아래쪽 이동 무브포인트
		for (int i = row+1; i <= 8; i++) {
			if (boards[i][col].getComponentCount() != 2) {
				movepins[i][col].setVisible(true);
			}else if (boards[i][col].getComponentCount() == 2) {
				if (((ChessPiece) boards[i][col].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[i][col].getComponent(1)).setIcon(white_icon_attack);
					break;
				}else {
					break;
				}
			}
		}

		
		
		// 왼쪽 이동 무브포인트
		for (int i = col-1; i >= 1; i--) {
			if(boards[row][i].getComponentCount() != 2) {
				movepins[row][i].setVisible(true);
			}else if (boards[row][i].getComponentCount() == 2) {
				if (((ChessPiece) boards[row][i].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row][i].getComponent(1)).setIcon(white_icon_attack);
					break;
				}else {
					break;
				}
			}
		}

		
		// 오른쪽 이동 무브포인트
		for (int i = col+1; i <= 8; i++) {
			if(boards[row][i].getComponentCount() != 2) {
				movepins[row][i].setVisible(true);
			}else if (boards[row][i].getComponentCount() == 2) {
				if (((ChessPiece) boards[row][i].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row][i].getComponent(1)).setIcon(white_icon_attack);
					break;
				}else {
					break;
				}
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

							movepinsNotVisible();

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
	public void whiteMove() {
		// TODO Auto-generated method stub

	}

}
