package chessGame;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Pawn extends ChessPiece{
	Pawn pawn;
	
	public Pawn(String side, int row, int col, ChessBoard chessBoard, JPanel boards[][], JButton movepins[][],
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
		
		 black_icon = new ImageIcon("image/Pawn-black.png");
		 white_icon = new ImageIcon("image/Pawn-white.png");
		 black_icon_select = new ImageIcon("image/Pawn-black_s.png");
		 white_icon_select = new ImageIcon("image/Pawn-white_s.png");
		 black_icon_attack = new ImageIcon("image/Pawn-black_a.png");
		 white_icon_attack = new ImageIcon("image/Pawn-white_a.png");
		 
		 setContentAreaFilled(false);//내용을 채우지 않음
		 setFocusPainted(false);//포커스테두리가 안그려지도록
		 setOpaque(false);//버튼을 투명하게
		 
		 if(side.equals("black")) {
			 setIcon(black_icon);
		 } else if(side.equals("white")) {
			 setIcon(white_icon);
		 }
	}
	
	
	
	
	
	
	
	
	@Override
	public void blackMove() {
		// TODO Auto-generated method stub
		
	}@Override
		public void whiteMove() {
			// TODO Auto-generated method stub
			
		}
	
	

}
