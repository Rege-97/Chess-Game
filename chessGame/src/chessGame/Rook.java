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
		

	}

	@Override
	public void whiteMove() {
		// TODO Auto-generated method stub

	}

}
