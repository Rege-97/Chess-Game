package chessGame;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Knight extends ChessPiece {
	Knight knight;

	public Knight(String side, int row, int col, ChessBoard chessBoard, JPanel boards[][], JButton movepins[][],
			JPanel p_board, ArrayList<ChessPiece> chesspiece_black, ArrayList<ChessPiece> chesspiece_white) {
		// 초기 세팅
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

		black_icon = new ImageIcon("image/Knight-black.png");
		black_icon.setDescription("black_icon");

		white_icon = new ImageIcon("image/Knight-white.png");
		white_icon.setDescription("white_icon");

		black_icon_select = new ImageIcon("image/Knight-black_s.png");
		black_icon_select.setDescription("black_icon_select");

		white_icon_select = new ImageIcon("image/Knight-white_s.png");
		white_icon_select.setDescription("white_icon_select");

		black_icon_attack = new ImageIcon("image/Knight-black_a.png");
		black_icon_attack.setDescription("black_icon_attack");

		white_icon_attack = new ImageIcon("image/Knight-white_a.png");
		white_icon_attack.setDescription("white_icon_attack");

		setContentAreaFilled(false);
		setFocusPainted(false);
		setOpaque(false);

		if (side.equals("black")) {
			setIcon(black_icon);
		} else if (side.equals("white")) {
			setIcon(white_icon);
		}

		attackListeners = new ArrayList<ActionListener>();
	}

	@Override
	public void blackMove() {
		// 나 자신 세팅
		knight = this;

		// 공격 유무 세팅
		attack = false;
		attackListener = null;

		// 버튼 클릭 시 초기화
		for (int i = 0; i < chesspiece_black.size(); i++) {
			if (chesspiece_black.get(i) != knight) {
				chesspiece_black.get(i).setIcon(chesspiece_black.get(i).black_icon);
			}
		}
		for (int i = 0; i < chesspiece_white.size(); i++) {
			chesspiece_white.get(i).setIcon(chesspiece_white.get(i).white_icon);
		}

		this.setIcon(black_icon_select);

		movepinsNotVisible();
		removeAction();

		// ↖️방향 이동 무브포인트 및 공격 말 탐색
		if (row - 2 > 0 && col - 1 > 0) {
			if (boards[row - 2][col - 1].getComponentCount() == 1) {
				movepins[row - 2][col - 1].setVisible(true);
			} else if (boards[row - 2][col - 1].getComponentCount() == 2) {
				if (((ChessPiece) boards[row - 2][col - 1].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row - 2][col - 1].getComponent(1))
							.setIcon(((ChessPiece) boards[row - 2][col - 1].getComponent(1)).white_icon_attack);
				}
			}
		}

		if (row - 1 > 0 && col - 2 > 0) {
			if (boards[row - 1][col - 2].getComponentCount() == 1) {
				movepins[row - 1][col - 2].setVisible(true);
			} else if (boards[row - 1][col - 2].getComponentCount() == 2) {
				if (((ChessPiece) boards[row - 1][col - 2].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row - 1][col - 2].getComponent(1))
							.setIcon(((ChessPiece) boards[row - 1][col - 2].getComponent(1)).white_icon_attack);
				}
			}
		}

		// ↗️방향 이동 무브포인트 및 공격 말 탐색
		if (row - 2 > 0 && col + 1 < 9) {
			if (boards[row - 2][col + 1].getComponentCount() == 1) {
				movepins[row - 2][col + 1].setVisible(true);
			} else if (boards[row - 2][col + 1].getComponentCount() == 2) {
				if (((ChessPiece) boards[row - 2][col + 1].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row - 2][col + 1].getComponent(1))
							.setIcon(((ChessPiece) boards[row - 2][col + 1].getComponent(1)).white_icon_attack);
				}
			}
		}

		if (row - 1 > 0 && col + 2 < 9) {
			if (boards[row - 1][col + 2].getComponentCount() == 1) {
				movepins[row - 1][col + 2].setVisible(true);
			} else if (boards[row - 1][col + 2].getComponentCount() == 2) {
				if (((ChessPiece) boards[row - 1][col + 2].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row - 1][col + 2].getComponent(1))
							.setIcon(((ChessPiece) boards[row - 1][col + 2].getComponent(1)).white_icon_attack);
				}
			}
		}

		// ↙️방향 이동 무브포인트 및 공격 말 탐색
		if (row + 2 < 9 && col - 1 > 0) {
			if (boards[row + 2][col - 1].getComponentCount() == 1) {
				movepins[row + 2][col - 1].setVisible(true);
			} else if (boards[row + 2][col - 1].getComponentCount() == 2) {
				if (((ChessPiece) boards[row + 2][col - 1].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row + 2][col - 1].getComponent(1))
							.setIcon(((ChessPiece) boards[row + 2][col - 1].getComponent(1)).white_icon_attack);
				}
			}
		}

		if (row + 1 < 9 && col - 2 > 0) {
			if (boards[row + 1][col - 2].getComponentCount() == 1) {
				movepins[row + 1][col - 2].setVisible(true);
			} else if (boards[row + 1][col - 2].getComponentCount() == 2) {
				if (((ChessPiece) boards[row + 1][col - 2].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row + 1][col - 2].getComponent(1))
							.setIcon(((ChessPiece) boards[row + 1][col - 2].getComponent(1)).white_icon_attack);
				}
			}
		}

		// ↘️방향 이동 무브포인트 및 공격 말 탐색
		if (row + 2 < 9 && col + 1 < 9) {
			if (boards[row + 2][col + 1].getComponentCount() == 1) {
				movepins[row + 2][col + 1].setVisible(true);
			} else if (boards[row + 2][col + 1].getComponentCount() == 2) {
				if (((ChessPiece) boards[row + 2][col + 1].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row + 2][col + 1].getComponent(1))
							.setIcon(((ChessPiece) boards[row + 2][col + 1].getComponent(1)).white_icon_attack);
				}
			}
		}

		if (row + 1 < 9 && col + 2 < 9) {
			if (boards[row + 1][col + 2].getComponentCount() == 1) {
				movepins[row + 1][col + 2].setVisible(true);
			} else if (boards[row + 1][col + 2].getComponentCount() == 2) {
				if (((ChessPiece) boards[row + 1][col + 2].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row + 1][col + 2].getComponent(1))
							.setIcon(((ChessPiece) boards[row + 1][col + 2].getComponent(1)).white_icon_attack);
				}
			}
		}

		// 공격 액션
		attackBlack(knight);

		// 무브핀의 중복 액션 이벤트를 막기 위해 이벤트 초기화
		removeAction();

		// 공격을 안했을 시 일반 이동 액션
		moveBlack(knight);

	}

	@Override
	public void whiteMove() {
		// 나 자신 세팅
		knight = this;

		// 공격 유무 세팅
		attack = false;
		attackListener = null;

		// 버튼 클릭 시 초기화
		for (int i = 0; i < chesspiece_white.size(); i++) {
			if (chesspiece_white.get(i) != knight) {
				chesspiece_white.get(i).setIcon(chesspiece_white.get(i).white_icon);
			}
		}
		for (int i = 0; i < chesspiece_black.size(); i++) {
			chesspiece_black.get(i).setIcon(chesspiece_black.get(i).black_icon);
		}

		this.setIcon(white_icon_select);

		movepinsNotVisible();
		removeAction();

		// ↖️방향 이동 무브포인트 및 공격 말 탐색
		if (row - 2 > 0 && col - 1 > 0) {
			if (boards[row - 2][col - 1].getComponentCount() == 1) {
				movepins[row - 2][col - 1].setVisible(true);
			} else if (boards[row - 2][col - 1].getComponentCount() == 2) {
				if (((ChessPiece) boards[row - 2][col - 1].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row - 2][col - 1].getComponent(1))
							.setIcon(((ChessPiece) boards[row - 2][col - 1].getComponent(1)).black_icon_attack);
				}
			}
		}

		if (row - 1 > 0 && col - 2 > 0) {
			if (boards[row - 1][col - 2].getComponentCount() == 1) {
				movepins[row - 1][col - 2].setVisible(true);
			} else if (boards[row - 1][col - 2].getComponentCount() == 2) {
				if (((ChessPiece) boards[row - 1][col - 2].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row - 1][col - 2].getComponent(1))
							.setIcon(((ChessPiece) boards[row - 1][col - 2].getComponent(1)).black_icon_attack);
				}
			}
		}

		// ↗️방향 이동 무브포인트 및 공격 말 탐색
		if (row - 2 > 0 && col + 1 < 9) {
			if (boards[row - 2][col + 1].getComponentCount() == 1) {
				movepins[row - 2][col + 1].setVisible(true);
			} else if (boards[row - 2][col + 1].getComponentCount() == 2) {
				if (((ChessPiece) boards[row - 2][col + 1].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row - 2][col + 1].getComponent(1))
							.setIcon(((ChessPiece) boards[row - 2][col + 1].getComponent(1)).black_icon_attack);
				}
			}
		}

		if (row - 1 > 0 && col + 2 < 9) {
			if (boards[row - 1][col + 2].getComponentCount() == 1) {
				movepins[row - 1][col + 2].setVisible(true);
			} else if (boards[row - 1][col + 2].getComponentCount() == 2) {
				if (((ChessPiece) boards[row - 1][col + 2].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row - 1][col + 2].getComponent(1))
							.setIcon(((ChessPiece) boards[row - 1][col + 2].getComponent(1)).black_icon_attack);
				}
			}
		}

		// ↙️방향 이동 무브포인트 및 공격 말 탐색
		if (row + 2 < 9 && col - 1 > 0) {
			if (boards[row + 2][col - 1].getComponentCount() == 1) {
				movepins[row + 2][col - 1].setVisible(true);
			} else if (boards[row + 2][col - 1].getComponentCount() == 2) {
				if (((ChessPiece) boards[row + 2][col - 1].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row + 2][col - 1].getComponent(1))
							.setIcon(((ChessPiece) boards[row + 2][col - 1].getComponent(1)).black_icon_attack);
				}
			}
		}

		if (row + 1 < 9 && col - 2 > 0) {
			if (boards[row + 1][col - 2].getComponentCount() == 1) {
				movepins[row + 1][col - 2].setVisible(true);
			} else if (boards[row + 1][col - 2].getComponentCount() == 2) {
				if (((ChessPiece) boards[row + 1][col - 2].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row + 1][col - 2].getComponent(1))
							.setIcon(((ChessPiece) boards[row + 1][col - 2].getComponent(1)).black_icon_attack);
				}
			}
		}

		// ↘️방향 이동 무브포인트 및 공격 말 탐색
		if (row + 2 < 9 && col + 1 < 9) {
			if (boards[row + 2][col + 1].getComponentCount() == 1) {
				movepins[row + 2][col + 1].setVisible(true);
			} else if (boards[row + 2][col + 1].getComponentCount() == 2) {
				if (((ChessPiece) boards[row + 2][col + 1].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row + 2][col + 1].getComponent(1))
							.setIcon(((ChessPiece) boards[row + 2][col + 1].getComponent(1)).black_icon_attack);
				}
			}
		}

		if (row + 1 < 9 && col + 2 < 9) {
			if (boards[row + 1][col + 2].getComponentCount() == 1) {
				movepins[row + 1][col + 2].setVisible(true);
			} else if (boards[row + 1][col + 2].getComponentCount() == 2) {
				if (((ChessPiece) boards[row + 1][col + 2].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row + 1][col + 2].getComponent(1))
							.setIcon(((ChessPiece) boards[row + 1][col + 2].getComponent(1)).black_icon_attack);
				}
			}
		}

		// 공격 액션
		attackWhite(knight);

		// 무브핀의 중복 액션 이벤트를 막기 위해 이벤트 초기화
		removeAction();

		// 공격을 안했을 시 일반 이동 액션
		moveWhite(knight);

	}
}
