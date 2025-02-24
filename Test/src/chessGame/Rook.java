package chessGame;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Rook extends ChessPiece {

	ImageIcon icon1,icon2,icon3,icon4;
	
	public Rook(String side, int row, int col, ChessBoard chessBoard) {
		this.side = side;
		this.row = row;
		this.col = col;
		live = true;
		this.chessBoard = chessBoard;

		setSize(chessBoard.getSize());

		 icon1 = new ImageIcon("image/Rook-black.png");
		 icon2 = new ImageIcon("image/Rook-white.png");
		 icon3 = new ImageIcon("image/Rook-black_s.png");
		 icon4 = new ImageIcon("image/Rook-white_s.png");

		setContentAreaFilled(false);
		setFocusPainted(false);
		setOpaque(false);

		if (side.equals("black")) {
			setIcon(icon1);
		} else if (side.equals("white")) {
			setIcon(icon2);
		}

	}
	
	public void blackMove(JPanel boards[][], JButton movepins[][], JPanel p_board, Rook rooks) {
		
		
		
		
		
		
		
	}


}
