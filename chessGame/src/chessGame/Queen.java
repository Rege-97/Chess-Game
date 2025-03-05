package chessGame;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Queen extends ChessPiece {
	Queen queen;

	public Queen(String side, int row, int col, ChessBoard chessBoard, JPanel boards[][], JButton movepins[][],
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

		black_icon = new ImageIcon("image/Queen-black.png");
		black_icon.setDescription("black_icon");

		white_icon = new ImageIcon("image/Queen-white.png");
		white_icon.setDescription("white_icon");

		black_icon_select = new ImageIcon("image/Queen-black_s.png");
		black_icon_select.setDescription("black_icon_select");

		white_icon_select = new ImageIcon("image/Queen-white_s.png");
		white_icon_select.setDescription("white_icon_select");

		black_icon_attack = new ImageIcon("image/Queen-black_a.png");
		black_icon_attack.setDescription("black_icon_attack");

		white_icon_attack = new ImageIcon("image/Queen-white_a.png");
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

		movecount = 0;
	}

	@Override
	public void blackMove() {

		queen = this;

		attack = false;
		attackListener = null;

		// 버튼 클릭 시 아이콘과 공격 액션 초기화
		for (int i = 0; i < chesspiece_black.size(); i++) {
			if (chesspiece_black.get(i) != queen) {
				chesspiece_black.get(i).setIcon(chesspiece_black.get(i).black_icon);
			}
			chesspiece_black.get(i).removeAttackBlack();
		}
		for (int i = 0; i < chesspiece_white.size(); i++) {
			chesspiece_white.get(i).setIcon(chesspiece_white.get(i).white_icon);
			chesspiece_white.get(i).removeAttackWhite();
		}

		this.setIcon(black_icon_select);

		movepinsNotVisible();
		removeAction();

		// 이동 경로와 공격 세팅
		setMovePinBlack();

		// 공격 액션
		attackBlack(queen);

		// 무브핀의 중복 액션 이벤트를 막기 위해 이벤트 초기화
		removeAction();

		// 공격을 안했을 시 일반 이동 액션
		moveBlack(queen);

	}

	@Override
	public void whiteMove() {
		queen = this;

		attack = false;
		attackListener = null;

		// 버튼 클릭 시 아이콘과 공격 액션 초기화
		for (int i = 0; i < chesspiece_white.size(); i++) {
			if (chesspiece_white.get(i) != queen) {
				chesspiece_white.get(i).setIcon(chesspiece_white.get(i).white_icon);
			}
			chesspiece_white.get(i).removeAttackWhite();
		}
		for (int i = 0; i < chesspiece_black.size(); i++) {
			chesspiece_black.get(i).setIcon(chesspiece_black.get(i).black_icon);
			chesspiece_black.get(i).removeAttackBlack();
		}

		this.setIcon(white_icon_select);

		movepinsNotVisible();
		removeAction();

		// 이동 경로와 공격 세팅
		setMovePinWhite();

		// 공격 액션
		attackWhite(queen);

		// 무브핀의 중복 액션 이벤트를 막기 위해 이벤트 초기화
		removeAction();

		// 공격을 안했을 시 일반 이동 액션
		moveWhite(queen);

	}

	@Override
	public void setMovePinBlack() {
		// ↖ 이동 무브포인트 및 공격 말 탐색
		for (int i = 1; i < 8; i++) {
			if (row - i >= 1 && col - i >= 1 && boards[row - i][col - i].getComponentCount() == 1) {
				movepins[row - i][col - i].setVisible(true);
			} else if (row - i >= 1 && col - i >= 1 && boards[row - i][col - i].getComponentCount() == 2) {
				if (((ChessPiece) boards[row - i][col - i].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row - i][col - i].getComponent(1))
							.setIcon(((ChessPiece) boards[row - i][col - i].getComponent(1)).white_icon_attack);
					break;
				} else {
					break;
				}
			}
		}

		// ↗ 이동 무브포인트 및 공격 말 탐색
		for (int i = 1; i < 8; i++) {
			if (row - i >= 1 && col + i <= 8 && boards[row - i][col + i].getComponentCount() == 1) {
				movepins[row - i][col + i].setVisible(true);
			} else if (row - i >= 1 && col + i <= 8 && boards[row - i][col + i].getComponentCount() == 2) {
				if (((ChessPiece) boards[row - i][col + i].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row - i][col + i].getComponent(1))
							.setIcon(((ChessPiece) boards[row - i][col + i].getComponent(1)).white_icon_attack);
					break;
				} else {
					break;
				}
			}
		}

		// ↙ 이동 무브포인트 및 공격 말 탐색
		for (int i = 1; i < 8; i++) {
			if (row + i <= 8 && col - i >= 1 && boards[row + i][col - i].getComponentCount() == 1) {
				movepins[row + i][col - i].setVisible(true);
			} else if (row + i <= 8 && col - i >= 1 && boards[row + i][col - i].getComponentCount() == 2) {
				if (((ChessPiece) boards[row + i][col - i].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row + i][col - i].getComponent(1))
							.setIcon(((ChessPiece) boards[row + i][col - i].getComponent(1)).white_icon_attack);
					break;
				} else {
					break;
				}
			}
		}

		// ↘ 이동 무브포인트 및 공격 말 탐색
		for (int i = 1; i < 8; i++) {
			if (row + i <= 8 && col + i <= 8 && boards[row + i][col + i].getComponentCount() == 1) {
				movepins[row + i][col + i].setVisible(true);
			} else if (row + i <= 8 && col + i <= 8 && boards[row + i][col + i].getComponentCount() == 2) {
				if (((ChessPiece) boards[row + i][col + i].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row + i][col + i].getComponent(1))
							.setIcon(((ChessPiece) boards[row + i][col + i].getComponent(1)).white_icon_attack);
					break;
				} else {
					break;
				}
			}
		}

		// 위쪽 이동 무브포인트 및 공격 말 탐색
		for (int i = row - 1; i >= 1; i--) {
			if (boards[i][col].getComponentCount() == 1) {
				movepins[i][col].setVisible(true);
			} else if (boards[i][col].getComponentCount() == 2) {
				if (((ChessPiece) boards[i][col].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[i][col].getComponent(1))
							.setIcon(((ChessPiece) boards[i][col].getComponent(1)).white_icon_attack);
					break;
				} else {
					break;
				}
			}
		}

		// 아래쪽 이동 무브포인트 및 공격 말 탐색
		for (int i = row + 1; i <= 8; i++) {
			if (boards[i][col].getComponentCount() == 1) {
				movepins[i][col].setVisible(true);
			} else if (boards[i][col].getComponentCount() == 2) {
				if (((ChessPiece) boards[i][col].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[i][col].getComponent(1))
							.setIcon(((ChessPiece) boards[i][col].getComponent(1)).white_icon_attack);
					break;
				} else {
					break;
				}
			}
		}

		// 왼쪽 이동 무브포인트 및 공격 말 탐색
		for (int i = col - 1; i >= 1; i--) {
			if (boards[row][i].getComponentCount() == 1) {
				movepins[row][i].setVisible(true);
			} else if (boards[row][i].getComponentCount() == 2) {
				if (((ChessPiece) boards[row][i].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row][i].getComponent(1))
							.setIcon(((ChessPiece) boards[row][i].getComponent(1)).white_icon_attack);
					break;
				} else {
					break;
				}
			}
		}

		// 오른쪽 이동 무브포인트 및 공격 말 탐색
		for (int i = col + 1; i <= 8; i++) {
			if (boards[row][i].getComponentCount() == 1) {
				movepins[row][i].setVisible(true);
			} else if (boards[row][i].getComponentCount() == 2) {
				if (((ChessPiece) boards[row][i].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row][i].getComponent(1))
							.setIcon(((ChessPiece) boards[row][i].getComponent(1)).white_icon_attack);
					break;
				} else {
					break;
				}
			}
		}
		// 체크 시 체크 해제만을 위한 이동경로로 제한
		removeCheckMovepin();
		
		p_board.getParent().validate();
		p_board.getParent().repaint();
	}

	@Override
	public void setMovePinWhite() {
		// ↖ 이동 무브포인트 및 공격 말 탐색
		for (int i = 1; i < 8; i++) {
			if (row - i >= 1 && col - i >= 1 && boards[row - i][col - i].getComponentCount() == 1) {
				movepins[row - i][col - i].setVisible(true);
			} else if (row - i >= 1 && col - i >= 1 && boards[row - i][col - i].getComponentCount() == 2) {
				if (((ChessPiece) boards[row - i][col - i].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row - i][col - i].getComponent(1))
							.setIcon(((ChessPiece) boards[row - i][col - i].getComponent(1)).black_icon_attack);
					break;
				} else {
					break;
				}
			}
		}

		// ↗ 이동 무브포인트 및 공격 말 탐색
		for (int i = 1; i < 8; i++) {
			if (row - i >= 1 && col + i <= 8 && boards[row - i][col + i].getComponentCount() == 1) {
				movepins[row - i][col + i].setVisible(true);
			} else if (row - i >= 1 && col + i <= 8 && boards[row - i][col + i].getComponentCount() == 2) {
				if (((ChessPiece) boards[row - i][col + i].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row - i][col + i].getComponent(1))
							.setIcon(((ChessPiece) boards[row - i][col + i].getComponent(1)).black_icon_attack);
					break;
				} else {
					break;
				}
			}
		}

		// ↙ 이동 무브포인트 및 공격 말 탐색
		for (int i = 1; i < 8; i++) {
			if (row + i <= 8 && col - i >= 1 && boards[row + i][col - i].getComponentCount() == 1) {
				movepins[row + i][col - i].setVisible(true);
			} else if (row + i <= 8 && col - i >= 1 && boards[row + i][col - i].getComponentCount() == 2) {
				if (((ChessPiece) boards[row + i][col - i].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row + i][col - i].getComponent(1))
							.setIcon(((ChessPiece) boards[row + i][col - i].getComponent(1)).black_icon_attack);
					break;
				} else {
					break;
				}
			}
		}

		// ↘ 이동 무브포인트 및 공격 말 탐색
		for (int i = 1; i < 8; i++) {
			if (row + i <= 8 && col + i <= 8 && boards[row + i][col + i].getComponentCount() == 1) {
				movepins[row + i][col + i].setVisible(true);
			} else if (row + i <= 8 && col + i <= 8 && boards[row + i][col + i].getComponentCount() == 2) {
				if (((ChessPiece) boards[row + i][col + i].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row + i][col + i].getComponent(1))
							.setIcon(((ChessPiece) boards[row + i][col + i].getComponent(1)).black_icon_attack);
					break;
				} else {
					break;
				}
			}
		}

		// 위쪽 이동 무브포인트 및 공격 말 탐색
		for (int i = row - 1; i >= 1; i--) {
			if (boards[i][col].getComponentCount() == 1) {
				movepins[i][col].setVisible(true);
			} else if (boards[i][col].getComponentCount() == 2) {
				if (((ChessPiece) boards[i][col].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[i][col].getComponent(1))
							.setIcon(((ChessPiece) boards[i][col].getComponent(1)).black_icon_attack);
					break;
				} else {
					break;
				}
			}
		}

		// 아래쪽 이동 무브포인트 및 공격 말 탐색
		for (int i = row + 1; i <= 8; i++) {
			if (boards[i][col].getComponentCount() == 1) {
				movepins[i][col].setVisible(true);
			} else if (boards[i][col].getComponentCount() == 2) {
				if (((ChessPiece) boards[i][col].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[i][col].getComponent(1))
							.setIcon(((ChessPiece) boards[i][col].getComponent(1)).black_icon_attack);
					break;
				} else {
					break;
				}
			}
		}

		// 왼쪽 이동 무브포인트 및 공격 말 탐색
		for (int i = col - 1; i >= 1; i--) {
			if (boards[row][i].getComponentCount() == 1) {
				movepins[row][i].setVisible(true);
			} else if (boards[row][i].getComponentCount() == 2) {
				if (((ChessPiece) boards[row][i].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row][i].getComponent(1))
							.setIcon(((ChessPiece) boards[row][i].getComponent(1)).black_icon_attack);
					break;
				} else {
					break;
				}
			}
		}

		// 오른쪽 이동 무브포인트 및 공격 말 탐색
		for (int i = col + 1; i <= 8; i++) {
			if (boards[row][i].getComponentCount() == 1) {
				movepins[row][i].setVisible(true);
			} else if (boards[row][i].getComponentCount() == 2) {
				if (((ChessPiece) boards[row][i].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row][i].getComponent(1))
							.setIcon(((ChessPiece) boards[row][i].getComponent(1)).black_icon_attack);
					break;
				} else {
					break;
				}
			}
		}
		// 체크 시 체크 해제만을 위한 이동경로로 제한
		removeCheckMovepin();
		
		p_board.getParent().validate();
		p_board.getParent().repaint();
	}

	// 현재 킹을 공격할 수 있는지 확인하는 메서드
	@Override
	public void isAttackKing() {
		// 위쪽 탐색
		for (int i = row - 1; i >= 1; i--) {
			if (setAttackIconIfKing(i, col)) {
				break;
			}
			if (boards[i][col].getComponentCount() == 2) {
				break; // 기물이 있으면 탐색 종료
			}
		}

		// 아래쪽 탐색
		for (int i = row + 1; i <= 8; i++) {
			if (setAttackIconIfKing(i, col)) {
				break;
			}
			if (boards[i][col].getComponentCount() == 2) {
				break; // 기물이 있으면 탐색 종료
			}
		}

		// 왼쪽 탐색
		for (int i = col - 1; i >= 1; i--) {
			if (setAttackIconIfKing(row, i)) {
				break;
			}
			if (boards[row][i].getComponentCount() == 2) {
				break; // 기물이 있으면 탐색 종료
			}
		}

		// 오른쪽 탐색
		for (int i = col + 1; i <= 8; i++) {
			if (setAttackIconIfKing(row, i)) {
				break;
			}
			if (boards[row][i].getComponentCount() == 2) {
				break; // 기물이 있으면 탐색 종료
			}
		}
		// ↖ 탐색
		for (int i = 1; i < 8; i++) {
			if (row - i >= 1 && col - i >= 1) {
				if (setAttackIconIfKing(row - i, col - i)) {
					break;
				}
				if (boards[row - i][col - i].getComponentCount() == 2) {
					break; // 기물이 있으면 탐색 종료
				}
			}
		}
		// ↗ 탐색
		for (int i = 1; i < 8; i++) {
			if (row - i >= 1 && col + i <= 8) {
				if (setAttackIconIfKing(row - i, col + i)) {
					break;
				}
				if (boards[row - i][col + i].getComponentCount() == 2) {
					break; // 기물이 있으면 탐색 종료
				}
			}
		}
		// ↙ 탐색
		for (int i = 1; i < 8; i++) {
			if (row + i <= 8 && col - i >= 1) {
				if (setAttackIconIfKing(row + i, col - i)) {
					break;
				}
				if (boards[row + i][col - i].getComponentCount() == 2) {
					break; // 기물이 있으면 탐색 종료
				}
			}
		}
		// ↙ 탐색
		for (int i = 1; i < 8; i++) {
			if (row + i <= 8 && col + i <= 8) {
				if (setAttackIconIfKing(row + i, col + i)) {
					break;
				}
				if (boards[row + i][col + i].getComponentCount() == 2) {
					break; // 기물이 있으면 탐색 종료
				}
			}
		}
	}
}
