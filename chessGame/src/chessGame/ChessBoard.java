package chessGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChessBoard extends JFrame {
	int row, col;
	JPanel p_board;
	JPanel boards[][];
	JButton bt_pawn;
	JButton movepins[][];
	String turn;
	ImageIcon movepin;
	ArrayList<ChessPiece> chesspiece_black, chesspiece_white;

	public ChessBoard() {
		
		super("테스트");

		this.setSize(1280, 800);
		this.setVisible(true);
		this.setLayout(new BorderLayout());

		// 화면 중앙 출력
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		int x = (screenSize.width - frameSize.width) / 2;
		int y = (screenSize.height - frameSize.height) / 2;
		setLocation(x, y);

		// 창 닫기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 초기 턴 화이트
		turn = "white";

		// 보드 세팅
		p_board = new JPanel(new GridLayout(8, 8));
		boardSet();
		this.add(p_board, "Center");
		
		JPanel p_west=new JPanel(new BorderLayout());
		p_west.setPreferredSize(new Dimension(480,800));
		this.add(p_west,"East");
		
		// 팝업 전 임시 상태 라벨
		JLabel check=new JLabel("play",JLabel.CENTER);
		check.setFont(new Font("Default Font",Font.PLAIN,50));
		p_west.add(check,"North");

		// 말 배치
		setChessPiece();
		


		// 블랙 체스말 이벤트
		for (int i = 0; i < chesspiece_black.size(); i++) {
			final int index = i;
			chesspiece_black.get(index).addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (turn.equals("black") &&chesspiece_black.get(index).isEnabled()) {
						chesspiece_black.get(index).blackMove();
					}
				}
			});
		}
		
		// 화이트 체스말 이벤트
		for (int i = 0; i < chesspiece_white.size(); i++) {
			final int index = i;
			chesspiece_white.get(index).addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (turn.equals("white") &&chesspiece_white.get(index).isEnabled()) {
			                chesspiece_white.get(index).whiteMove();
					}
				}
			});
		}
		this.validate();
	}

	// 체스 보드 그리기;
	public void boardSet() {

		boards = new JPanel[9][9];
		movepins = new JButton[9][9];
		movepin = new ImageIcon("image/MovePoint.png");

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
				movepins[i][j] = new JButton();
				movepins[i][j].setContentAreaFilled(false);
				movepins[i][j].setFocusPainted(false);
				movepins[i][j].setOpaque(false);
				movepins[i][j].setIcon(movepin);
				boards[i][j].add(movepins[i][j], "North");
				movepins[i][j].setVisible(false);
				p_board.add(boards[i][j]);
			}
		}
	}

	// 체스 말 그리기
	public void setChessPiece() {
		chesspiece_black = new ArrayList<ChessPiece>();
		chesspiece_white = new ArrayList<ChessPiece>();

		
		chesspiece_black.add(new Rook("black", 1, 1, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_black.add(new Rook("black", 1, 8, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_black.add(new Pawn("black", 2, 1, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_black.add(new Pawn("black", 2, 2, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_black.add(new Pawn("black", 2, 3, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_black.add(new Pawn("black", 2, 4, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_black.add(new Pawn("black", 2, 5, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_black.add(new Pawn("black", 2, 6, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_black.add(new Pawn("black", 2, 7, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_black.add(new Pawn("black", 2, 8, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_black.add(new Knight("black", 1, 2, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_black.add(new Knight("black", 1, 7, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_black.add(new King("black", 1, 5, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_black.add(new Bishop("black", 1, 3, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_black.add(new Bishop("black", 1, 6, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_black.add(new Queen("black", 1, 4, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));

		boards[1][1].add(chesspiece_black.get(0), "Center");
		boards[1][8].add(chesspiece_black.get(1), "Center");
		boards[2][1].add(chesspiece_black.get(2), "Center");
		boards[2][2].add(chesspiece_black.get(3), "Center");
		boards[2][3].add(chesspiece_black.get(4), "Center");
		boards[2][4].add(chesspiece_black.get(5), "Center");
		boards[2][5].add(chesspiece_black.get(6), "Center");
		boards[2][6].add(chesspiece_black.get(7), "Center");
		boards[2][7].add(chesspiece_black.get(8), "Center");
		boards[2][8].add(chesspiece_black.get(9), "Center");
		boards[1][2].add(chesspiece_black.get(10), "Center");
		boards[1][7].add(chesspiece_black.get(11), "Center");
		boards[1][5].add(chesspiece_black.get(12), "Center");
		boards[1][3].add(chesspiece_black.get(13), "Center");
		boards[1][6].add(chesspiece_black.get(14), "Center");
		boards[1][4].add(chesspiece_black.get(15), "Center");

		chesspiece_white.add(new Rook("white", 8, 1, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_white.add(new Rook("white", 8, 8, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_white.add(new Pawn("white", 7, 1, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_white.add(new Pawn("white", 7, 2, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_white.add(new Pawn("white", 7, 3, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_white.add(new Pawn("white", 7, 4, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_white.add(new Pawn("white", 7, 5, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_white.add(new Pawn("white", 7, 6, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_white.add(new Pawn("white", 7, 7, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_white.add(new Pawn("white", 7, 8, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_white.add(new Knight("white", 8, 2, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_white.add(new Knight("white", 8, 7, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_white.add(new King("white", 8, 5, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_white.add(new Bishop("white", 8, 3, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_white.add(new Bishop("white", 8, 6, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));
		chesspiece_white.add(new Queen("white", 8, 4, this, boards, movepins, p_board, chesspiece_black, chesspiece_white));

		boards[8][1].add(chesspiece_white.get(0), "Center");
		boards[8][8].add(chesspiece_white.get(1), "Center");
		boards[7][1].add(chesspiece_white.get(2), "Center");
		boards[7][2].add(chesspiece_white.get(3), "Center");
		boards[7][3].add(chesspiece_white.get(4), "Center");
		boards[7][4].add(chesspiece_white.get(5), "Center");
		boards[7][5].add(chesspiece_white.get(6), "Center");
		boards[7][6].add(chesspiece_white.get(7), "Center");
		boards[7][7].add(chesspiece_white.get(8), "Center");
		boards[7][8].add(chesspiece_white.get(9), "Center");
		boards[8][2].add(chesspiece_white.get(10), "Center");
		boards[8][7].add(chesspiece_white.get(11), "Center");
		boards[8][5].add(chesspiece_white.get(12), "Center");
		boards[8][3].add(chesspiece_white.get(13), "Center");
		boards[8][6].add(chesspiece_white.get(14), "Center");
		boards[8][4].add(chesspiece_white.get(15), "Center");
	}
	
	

	public static void main(String[] args) {
		System.setProperty("sun.java2d.uiScale", "1");
		new ChessBoard();

	}

}
