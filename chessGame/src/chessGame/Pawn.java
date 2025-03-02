package chessGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Pawn extends ChessPiece {
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
		black_icon.setDescription("black_icon");
		white_icon = new ImageIcon("image/Pawn-white.png");
		white_icon.setDescription("white_icon");
		black_icon_select = new ImageIcon("image/Pawn-black_s.png");
		black_icon_select.setDescription("black_icon_select");
		white_icon_select = new ImageIcon("image/Pawn-white_s.png");
		white_icon_select.setDescription("white_icon_select");
		black_icon_attack = new ImageIcon("image/Pawn-black_a.png");
		black_icon_attack.setDescription("black_icon_attack");
		white_icon_attack = new ImageIcon("image/Pawn-white_a.png");
		white_icon_attack.setDescription("white_icon_attack");

		setContentAreaFilled(false);// 내용을 채우지 않음
		setFocusPainted(false);// 포커스테두리가 안그려지도록
		setOpaque(false);// 버튼을 투명하게

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
		pawn = this;

		attack = false;
		attackListener = null;

		// size가 뭘까
		for (int i = 0; i < chesspiece_black.size(); i++) {
			if (chesspiece_black.get(i) != pawn) {
				chesspiece_black.get(i).setIcon(chesspiece_black.get(i).black_icon);
			}
		}
		for (int i = 0; i < chesspiece_white.size(); i++) {
			chesspiece_white.get(i).setIcon(chesspiece_white.get(i).white_icon);
		}

		this.setIcon(black_icon_select);

		movepinsNotVisible();
		removeAction();

		// 처음 움직일 때 무브포인트 탐색
		if (row == 2) {
			for (int i = row + 1; i <= row + 2; i++) {
				if (boards[i][col].getComponentCount() == 1) {
					movepins[i][col].setVisible(true);
				}
				if (boards[row + 1][col].getComponentCount() == 2) {
					movepins[row + 2][col].setVisible(false);
				}
			}
			for (int i = row + 1; i <= row + 1; i++) {
				if (col + 1 <= 8 && boards[i][col + 1].getComponentCount() == 2) {
					if (((ChessPiece) boards[i][col + 1].getComponent(1)).side.equals("white")) {
						((ChessPiece) boards[i][col + 1].getComponent(1))
								.setIcon(((ChessPiece) boards[i][col + 1].getComponent(1)).white_icon_attack);
						break;
					} else {
						break;
					}

				}
			}

			for (int i = row + 1; i <= row + 1; i++) {
				if (col - 1 >= 1 && boards[i][col - 1].getComponentCount() == 2) {
					if (((ChessPiece) boards[i][col - 1].getComponent(1)).side.equals("white")) {
						((ChessPiece) boards[i][col - 1].getComponent(1))
								.setIcon(((ChessPiece) boards[i][col - 1].getComponent(1)).white_icon_attack);
						break;
					} else {
						break;
					}

				}
			}
		} else if (row != 2) {
			// 처음 이후 무브 포인트 탐색
			for (int i = row + 1; i <= row + 1; i++) {
				if (boards[i][col].getComponentCount() == 1) {
					movepins[i][col].setVisible(true);
				}
			}
			for (int i = row + 1; i <= row + 1; i++) {
				if (col + 1 <= 8 && boards[i][col + 1].getComponentCount() == 2) {
					if (((ChessPiece) boards[i][col + 1].getComponent(1)).side.equals("white")) {
						((ChessPiece) boards[i][col + 1].getComponent(1))
								.setIcon(((ChessPiece) boards[i][col + 1].getComponent(1)).white_icon_attack);
						break;
					} else {
						break;
					}

				}
			}

			for (int i = row + 1; i <= row + 1; i++) {
				if (col - 1 >= 1 && boards[i][col - 1].getComponentCount() == 2) {
					if (((ChessPiece) boards[i][col - 1].getComponent(1)).side.equals("white")) {
						((ChessPiece) boards[i][col - 1].getComponent(1))
								.setIcon(((ChessPiece) boards[i][col - 1].getComponent(1)).white_icon_attack);
						break;
					} else {
						break;
					}

				}
			}
		}

		// 앙파상 무브포인트 탐색
		if (row == 5) {
			if (col - 1 > 0) {
				if (boards[row][col - 1].getComponentCount() == 2) {
					if (((ChessPiece) boards[row][col - 1].getComponent(1)).side.equals("white")
							&& ((ChessPiece) boards[row][col - 1].getComponent(1)) instanceof Pawn) {
						if (((ChessPiece) boards[row][col - 1].getComponent(1)).movecount == 1) {
							movepins[row + 1][col - 1].setVisible(true);
						}
					}
				}
			}
			if (col + 1 < 9) {
				if (boards[row][col + 1].getComponentCount() == 2) {
					if (((ChessPiece) boards[row][col + 1].getComponent(1)).side.equals("white")
							&& ((ChessPiece) boards[row][col + 1].getComponent(1)) instanceof Pawn) {
						if (((ChessPiece) boards[row][col + 1].getComponent(1)).movecount == 1) {
							movepins[row + 1][col + 1].setVisible(true);
						}
					}
				}
			}
		}

		// 공격
		attackBlack(pawn);

		// 무브핀의 중복 액션 이벤트를 막기 위해 이벤트 초기화
		removeAction();

		// 공격을 안했을 시 일반 이동 액션
		moveBlack(pawn);
	}

	@Override
	public void whiteMove() {
		pawn = this;

		attack = false;
		attackListener = null;

		// 버튼 클릭시 초기화
		for (int i = 0; i < chesspiece_white.size(); i++) {
			if (chesspiece_white.get(i) != pawn) {
				chesspiece_white.get(i).setIcon(chesspiece_white.get(i).white_icon);
			}
		}
		for (int i = 0; i < chesspiece_black.size(); i++) {
			chesspiece_black.get(i).setIcon(chesspiece_black.get(i).black_icon);
		}

		this.setIcon(white_icon_select);

		movepinsNotVisible();
		removeAction();

		// 처음 움직일 때 무브포인트 탐색
		if (row == 7) {
			for (int i = row - 1; i >= row - 2; i--) {
				if (boards[i][col].getComponentCount() == 1) {
					movepins[i][col].setVisible(true);
				}
				if (boards[row - 1][col].getComponentCount() == 2) {
					movepins[row - 2][col].setVisible(false);
				}
			}
			for (int i = row - 1; i >= row - 1; i--) {
				if (col + 1 <= 8 && boards[i][col + 1].getComponentCount() == 2) {
					if (((ChessPiece) boards[i][col + 1].getComponent(1)).side.equals("black")) {
						((ChessPiece) boards[i][col + 1].getComponent(1))
								.setIcon(((ChessPiece) boards[i][col + 1].getComponent(1)).black_icon_attack);
						break;
					} else {
						break;
					}

				}
			}
			for (int i = row - 1; i >= row - 1; i--) {
				if (col - 1 >= 1 && boards[i][col - 1].getComponentCount() == 2) {
					if (((ChessPiece) boards[i][col - 1].getComponent(1)).side.equals("black")) {
						((ChessPiece) boards[i][col - 1].getComponent(1))
								.setIcon(((ChessPiece) boards[i][col - 1].getComponent(1)).black_icon_attack);
						break;
					} else {
						break;
					}
				}
			}
		} else if (row != 7) {
			// 처음 이후 무브 포인트 탐색
			for (int i = row - 1; i >= row - 1; i--) {
				if (boards[i][col].getComponentCount() == 1) {
					movepins[i][col].setVisible(true);
				}
			}
			for (int i = row - 1; i >= row - 1; i--) {
				if (col + 1 <= 8 && boards[i][col + 1].getComponentCount() == 2) {
					if (((ChessPiece) boards[i][col + 1].getComponent(1)).side.equals("black")) {
						((ChessPiece) boards[i][col + 1].getComponent(1))
								.setIcon(((ChessPiece) boards[i][col + 1].getComponent(1)).black_icon_attack);
						break;
					} else {
						break;
					}

				}
			}
			for (int i = row - 1; i >= row - 1; i--) {
				if (col - 1 >= 1 && boards[i][col - 1].getComponentCount() == 2) {
					if (((ChessPiece) boards[i][col - 1].getComponent(1)).side.equals("black")) {
						((ChessPiece) boards[i][col - 1].getComponent(1))
								.setIcon(((ChessPiece) boards[i][col - 1].getComponent(1)).black_icon_attack);
						break;
					} else {
						break;
					}
				}
			}
		}

		// 공격
		attackWhite(pawn);

		// 무브핀의 중복 액션 이벤트를 막기 위해 이벤트 초기화
		removeAction();

		// 공격을 안했을 시 일반 이동 액션
		moveWhite(pawn);

	}

	@Override
	public void moveBlack(ChessPiece me) {
		if (!attack) {
			for (int i = 1; i <= 8; i++) {
				for (int j = 1; j <= 8; j++) {
					final int indexrow = i;
					final int indexcol = j;

					// 조건 : 현재 보여지고 있는 무브핀
					if (movepins[indexrow][indexcol].isVisible()) {
						if ((indexrow == row + 1 && indexcol == col - 1)
								|| (indexrow == row + 1 && indexcol == col + 1)) {
							movepins[indexrow][indexcol].addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {

									if (indexcol == col - 1) {
										boards[row][col - 1].getComponent(1).setEnabled(false);
										boards[row][col - 1].remove(1);
									} else if (indexcol == col + 1) {
										boards[row][col + 1].getComponent(1).setEnabled(false);
										boards[row][col + 1].remove(1);
									}

									// 기준 위치에서 아군말 제거 후 이동할 위치에 아군 말 추가 후 일반 아이콘으로 변경
									boards[row][col].remove(me);
									boards[indexrow][indexcol].add(me, "Center");

									// 이동이 끝나면 모든 아군말을 일반 아이콘으로 전환
									for (int k = 0; k < chesspiece_black.size(); k++) {
										chesspiece_black.get(k).setIcon(chesspiece_black.get(k).black_icon);
									}

									// 사용 안한 어택 리스너 제거
									removeAttackBlack();

									// 무브핀 초기화
									movepinsNotVisible();

									// 현재 위치 값 저장
									row = indexrow;
									col = indexcol;

									// 턴 정보를 상대 턴으로 변경
									chessBoard.turn = "white";

									// 이동 횟수 증가
									movecount++;

									p_board.getParent().validate();
									p_board.getParent().repaint();

								}
							});
						} else {
							movepins[indexrow][indexcol].addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {

									// 기준 위치에서 아군말 제거 후 이동할 위치에 아군 말 추가 후 일반 아이콘으로 변경
									boards[row][col].remove(me);
									boards[indexrow][indexcol].add(me, "Center");

									// 이동이 끝나면 모든 아군말을 일반 아이콘으로 전환
									for (int k = 0; k < chesspiece_black.size(); k++) {
										chesspiece_black.get(k).setIcon(chesspiece_black.get(k).black_icon);
									}

									// 사용 안한 어택 리스너 제거
									removeAttackBlack();

									// 무브핀 초기화
									movepinsNotVisible();

									// 현재 위치 값 저장
									row = indexrow;
									col = indexcol;

									// 턴 정보를 상대 턴으로 변경
									chessBoard.turn = "white";

									// 이동 횟수 증가
									movecount++;

									p_board.getParent().validate();
									p_board.getParent().repaint();

								}
							});
						}

					}
				}
			}
		}
	}
}
