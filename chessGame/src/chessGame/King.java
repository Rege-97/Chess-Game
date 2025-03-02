package chessGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale.Category;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class King extends ChessPiece {
	King king;

	public King(String side, int row, int col, ChessBoard chessBoard, JPanel boards[][], JButton movepins[][],
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

		black_icon = new ImageIcon("image/King-black.png");
		black_icon.setDescription("black_icon");

		white_icon = new ImageIcon("image/King-white.png");
		white_icon.setDescription("white_icon");

		black_icon_select = new ImageIcon("image/King-black_s.png");
		black_icon_select.setDescription("black_icon_select");

		white_icon_select = new ImageIcon("image/King-white_s.png");
		white_icon_select.setDescription("white_icon_select");

		black_icon_attack = new ImageIcon("image/King-black_a.png");
		black_icon_attack.setDescription("black_icon_attack");

		white_icon_attack = new ImageIcon("image/King-white_a.png");
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
		// 나 자신 세팅
		king = this;

		// 공격 유무 세팅
		attack = false;
		attackListener = null;

		// 버튼 클릭 시 초기화
		for (int i = 0; i < chesspiece_black.size(); i++) {
			if (chesspiece_black.get(i) != king) {
				chesspiece_black.get(i).setIcon(chesspiece_black.get(i).black_icon);
			}
		}
		for (int i = 0; i < chesspiece_white.size(); i++) {
			chesspiece_white.get(i).setIcon(chesspiece_white.get(i).white_icon);
		}

		this.setIcon(black_icon_select);

		movepinsNotVisible();
		removeAction();

		// ⬆️방향 이동 무브포인트 및 공격 말 탐색
		if (row - 1 > 0) {
			if (boards[row - 1][col].getComponentCount() == 1) {
				movepins[row - 1][col].setVisible(true);
			} else if (boards[row - 1][col].getComponentCount() == 2) {
				if (((ChessPiece) boards[row - 1][col].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row - 1][col].getComponent(1))
							.setIcon(((ChessPiece) boards[row - 1][col].getComponent(1)).white_icon_attack);
				}
			}
		}

		// ⬇️방향 이동 무브포인트 및 공격 말 탐색
		if (row + 1 < 9) {
			if (boards[row + 1][col].getComponentCount() == 1) {
				movepins[row + 1][col].setVisible(true);
			} else if (boards[row + 1][col].getComponentCount() == 2) {
				if (((ChessPiece) boards[row + 1][col].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row + 1][col].getComponent(1))
							.setIcon(((ChessPiece) boards[row + 1][col].getComponent(1)).white_icon_attack);
				}
			}
		}

		// ⬅️방향 이동 무브포인트 및 공격 말 탐색
		if (col - 1 > 0) {
			if (boards[row][col - 1].getComponentCount() == 1) {
				movepins[row][col - 1].setVisible(true);
			} else if (boards[row][col - 1].getComponentCount() == 2) {
				if (((ChessPiece) boards[row][col - 1].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row][col - 1].getComponent(1))
							.setIcon(((ChessPiece) boards[row][col - 1].getComponent(1)).white_icon_attack);
				}
			}
		}

		// ➡️방향 이동 무브포인트 및 공격 말 탐색
		if (col + 1 < 9) {
			if (boards[row][col + 1].getComponentCount() == 1) {
				movepins[row][col + 1].setVisible(true);
			} else if (boards[row][col + 1].getComponentCount() == 2) {
				if (((ChessPiece) boards[row][col + 1].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row][col + 1].getComponent(1))
							.setIcon(((ChessPiece) boards[row][col + 1].getComponent(1)).white_icon_attack);
				}
			}
		}

		// ↖️방향 이동 무브포인트 및 공격 말 탐색
		if (row - 1 > 0 && col - 1 > 0) {
			if (boards[row - 1][col - 1].getComponentCount() == 1) {
				movepins[row - 1][col - 1].setVisible(true);
			} else if (boards[row - 1][col - 1].getComponentCount() == 2) {
				if (((ChessPiece) boards[row - 1][col - 1].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row - 1][col - 1].getComponent(1))
							.setIcon(((ChessPiece) boards[row - 1][col - 1].getComponent(1)).white_icon_attack);
				}
			}
		}

		// ↗️방향 이동 무브포인트 및 공격 말 탐색
		if (row - 1 > 0 && col + 1 < 9) {
			if (boards[row - 1][col + 1].getComponentCount() == 1) {
				movepins[row - 1][col + 1].setVisible(true);
			} else if (boards[row - 1][col + 1].getComponentCount() == 2) {
				if (((ChessPiece) boards[row - 1][col + 1].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row - 1][col + 1].getComponent(1))
							.setIcon(((ChessPiece) boards[row - 1][col + 1].getComponent(1)).white_icon_attack);
				}
			}
		}

		// ↙️방향 이동 무브포인트 및 공격 말 탐색
		if (row + 1 < 9 && col - 1 > 0) {
			if (boards[row + 1][col - 1].getComponentCount() == 1) {
				movepins[row + 1][col - 1].setVisible(true);
			} else if (boards[row + 1][col - 1].getComponentCount() == 2) {
				if (((ChessPiece) boards[row + 1][col - 1].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row + 1][col - 1].getComponent(1))
							.setIcon(((ChessPiece) boards[row + 1][col - 1].getComponent(1)).white_icon_attack);
				}
			}
		}

		// ↘️방향 이동 무브포인트 및 공격 말 탐색
		if (row + 1 < 9 && col + 1 < 9) {
			if (boards[row + 1][col + 1].getComponentCount() == 1) {
				movepins[row + 1][col + 1].setVisible(true);
			} else if (boards[row + 1][col + 1].getComponentCount() == 2) {
				if (((ChessPiece) boards[row + 1][col + 1].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row + 1][col + 1].getComponent(1))
							.setIcon(((ChessPiece) boards[row + 1][col + 1].getComponent(1)).white_icon_attack);
				}
			}
		}

		// 킹사이드 캐슬링 무브포인트 탐색
		if (movecount == 0 && boards[row][col + 1].getComponentCount() == 1
				&& boards[row][col + 2].getComponentCount() == 1) {
			if (boards[row][col + 3].getComponentCount() == 2) {
				if (boards[row][col + 3].getComponent(1) instanceof Rook
						&& ((ChessPiece) boards[row][col + 3].getComponent(1)).movecount == 0) {
					movepins[row][col + 2].setVisible(true);
				}
			}
		}

		// 퀸사이드 캐슬링 무브포인트 탐색
		if (movecount == 0 && boards[row][col - 1].getComponentCount() == 1
				&& boards[row][col - 2].getComponentCount() == 1 && boards[row][col - 3].getComponentCount() == 1) {
			if (boards[row][col - 4].getComponentCount() == 2) {
				if (boards[row][col - 4].getComponent(1) instanceof Rook
						&& ((ChessPiece) boards[row][col - 4].getComponent(1)).movecount == 0) {
					movepins[row][col - 2].setVisible(true);
				}
			}
		}

		// 공격 액션
		attackBlack(king);

		// 무브핀의 중복 액션 이벤트를 막기 위해 이벤트 초기화
		removeAction();

		// 공격을 안했을 시 일반 이동 액션
		moveBlack(king);
	}

	@Override
	public void whiteMove() {
		// 나 자신 세팅
		king = this;

		// 공격 유무 세팅
		attack = false;
		attackListener = null;

		// 버튼 클릭 시 초기화
		for (int i = 0; i < chesspiece_white.size(); i++) {
			if (chesspiece_white.get(i) != king) {
				chesspiece_white.get(i).setIcon(chesspiece_white.get(i).white_icon);
			}
		}
		for (int i = 0; i < chesspiece_black.size(); i++) {
			chesspiece_black.get(i).setIcon(chesspiece_black.get(i).black_icon);
		}

		this.setIcon(white_icon_select);

		movepinsNotVisible();
		removeAction();

		// ⬆️방향 이동 무브포인트 및 공격 말 탐색
		if (row - 1 > 0) {
			if (boards[row - 1][col].getComponentCount() == 1) {
				movepins[row - 1][col].setVisible(true);
			} else if (boards[row - 1][col].getComponentCount() == 2) {
				if (((ChessPiece) boards[row - 1][col].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row - 1][col].getComponent(1))
							.setIcon(((ChessPiece) boards[row - 1][col].getComponent(1)).black_icon_attack);
				}
			}
		}

		// ⬇️방향 이동 무브포인트 및 공격 말 탐색
		if (row + 1 < 9) {
			if (boards[row + 1][col].getComponentCount() == 1) {
				movepins[row + 1][col].setVisible(true);
			} else if (boards[row + 1][col].getComponentCount() == 2) {
				if (((ChessPiece) boards[row + 1][col].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row + 1][col].getComponent(1))
							.setIcon(((ChessPiece) boards[row + 1][col].getComponent(1)).black_icon_attack);
				}
			}
		}

		// ⬅️방향 이동 무브포인트 및 공격 말 탐색
		if (col - 1 > 0) {
			if (boards[row][col - 1].getComponentCount() == 1) {
				movepins[row][col - 1].setVisible(true);
			} else if (boards[row][col - 1].getComponentCount() == 2) {
				if (((ChessPiece) boards[row][col - 1].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row][col - 1].getComponent(1))
							.setIcon(((ChessPiece) boards[row][col - 1].getComponent(1)).black_icon_attack);
				}
			}
		}

		// ➡️방향 이동 무브포인트 및 공격 말 탐색
		if (col + 1 < 9) {
			if (boards[row][col + 1].getComponentCount() == 1) {
				movepins[row][col + 1].setVisible(true);
			} else if (boards[row][col + 1].getComponentCount() == 2) {
				if (((ChessPiece) boards[row][col + 1].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row][col + 1].getComponent(1))
							.setIcon(((ChessPiece) boards[row][col + 1].getComponent(1)).black_icon_attack);
				}
			}
		}

		// ↖️방향 이동 무브포인트 및 공격 말 탐색
		if (row - 1 > 0 && col - 1 > 0) {
			if (boards[row - 1][col - 1].getComponentCount() == 1) {
				movepins[row - 1][col - 1].setVisible(true);
			} else if (boards[row - 1][col - 1].getComponentCount() == 2) {
				if (((ChessPiece) boards[row - 1][col - 1].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row - 1][col - 1].getComponent(1))
							.setIcon(((ChessPiece) boards[row - 1][col - 1].getComponent(1)).black_icon_attack);
				}
			}
		}

		// ↗️방향 이동 무브포인트 및 공격 말 탐색
		if (row - 1 > 0 && col + 1 < 9) {
			if (boards[row - 1][col + 1].getComponentCount() == 1) {
				movepins[row - 1][col + 1].setVisible(true);
			} else if (boards[row - 1][col + 1].getComponentCount() == 2) {
				if (((ChessPiece) boards[row - 1][col + 1].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row - 1][col + 1].getComponent(1))
							.setIcon(((ChessPiece) boards[row - 1][col + 1].getComponent(1)).black_icon_attack);
				}
			}
		}

		// ↙️방향 이동 무브포인트 및 공격 말 탐색
		if (row + 1 < 9 && col - 1 > 0) {
			if (boards[row + 1][col - 1].getComponentCount() == 1) {
				movepins[row + 1][col - 1].setVisible(true);
			} else if (boards[row + 1][col - 1].getComponentCount() == 2) {
				if (((ChessPiece) boards[row + 1][col - 1].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row + 1][col - 1].getComponent(1))
							.setIcon(((ChessPiece) boards[row + 1][col - 1].getComponent(1)).black_icon_attack);
				}
			}
		}

		// ↘️방향 이동 무브포인트 및 공격 말 탐색
		if (row + 1 < 9 && col + 1 < 9) {
			if (boards[row + 1][col + 1].getComponentCount() == 1) {
				movepins[row + 1][col + 1].setVisible(true);
			} else if (boards[row + 1][col + 1].getComponentCount() == 2) {
				if (((ChessPiece) boards[row + 1][col + 1].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row + 1][col + 1].getComponent(1))
							.setIcon(((ChessPiece) boards[row + 1][col + 1].getComponent(1)).black_icon_attack);
				}
			}
		}

		// 킹사이드 캐슬링 무브포인트 탐색
		if (movecount == 0 && boards[row][col + 1].getComponentCount() == 1
				&& boards[row][col + 2].getComponentCount() == 1) {
			if (boards[row][col + 3].getComponentCount() == 2) {
				if (boards[row][col + 3].getComponent(1) instanceof Rook
						&& ((ChessPiece) boards[row][col + 3].getComponent(1)).movecount == 0) {
					movepins[row][col + 2].setVisible(true);
				}
			}
		}

		// 퀸사이드 캐슬링 무브포인트 탐색
		if (movecount == 0 && boards[row][col - 1].getComponentCount() == 1
				&& boards[row][col - 2].getComponentCount() == 1 && boards[row][col - 3].getComponentCount() == 1) {
			if (boards[row][col - 4].getComponentCount() == 2) {
				if (boards[row][col - 4].getComponent(1) instanceof Rook
						&& ((ChessPiece) boards[row][col - 4].getComponent(1)).movecount == 0) {
					movepins[row][col - 2].setVisible(true);
				}
			}
		}

		// 공격 액션
		attackWhite(king);

		// 무브핀의 중복 액션 이벤트를 막기 위해 이벤트 초기화
		removeAction();

		// 공격을 안했을 시 일반 이동 액션
		moveWhite(king);

	}

	// 캐슬링 구현을 위해 체스피스 클래스에서 오버라이딩
	@Override
	public void moveBlack(ChessPiece me) {
		if (!attack) {
			for (int i = 1; i <= 8; i++) {
				for (int j = 1; j <= 8; j++) {
					final int indexrow = i;
					final int indexcol = j;

					// 조건 : 현재 보여지고 있는 무브핀
					if (movepins[indexrow][indexcol].isVisible()) {
						// 캐슬링 조건 달성 시 이동 액션
						if (indexcol == col + 2 || indexcol == col - 2) {

							movepins[indexrow][indexcol].addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									// 킹 사이드 캐슬링
									if (indexcol == col + 2) {
										ChessPiece castlingrook = null;
										for (int k = 0; k < chesspiece_black.size(); k++) {
											if (chesspiece_black.get(k) == boards[row][col + 3].getComponent(1)) {
												castlingrook = chesspiece_black.get(k);
												break;
											}
										}
										boards[row][col + 3].remove(1);
										boards[row][col + 1].add(castlingrook);
										// 퀸 사이드 캐슬링
									} else if (indexcol == col - 2) {
										ChessPiece castlingrook = null;
										for (int k = 0; k < chesspiece_black.size(); k++) {
											if (chesspiece_black.get(k) == boards[row][col - 4].getComponent(1)) {
												castlingrook = chesspiece_black.get(k);
												break;
											}
										}
										boards[row][col - 4].remove(1);
										boards[row][col - 1].add(castlingrook);
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

	// 캐슬링 구현을 위해 체스피스 클래스에서 오버라이딩
	@Override
	public void moveWhite(ChessPiece me) {
		if (!attack) {
			for (int i = 1; i <= 8; i++) {
				for (int j = 1; j <= 8; j++) {
					final int indexrow = i;
					final int indexcol = j;

					// 조건 : 현재 보여지고 있는 무브핀
					if (movepins[indexrow][indexcol].isVisible()) {
						// 캐슬링 조건 달성 시 이동 액션
						if (indexcol == col + 2 || indexcol == col - 2) {

							movepins[indexrow][indexcol].addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									// 킹 사이드 캐슬링
									if (indexcol == col + 2) {
										ChessPiece castlingrook = null;
										for (int k = 0; k < chesspiece_white.size(); k++) {
											if (chesspiece_white.get(k) == boards[row][col + 3].getComponent(1)) {
												castlingrook = chesspiece_white.get(k);
												break;
											}
										}
										boards[row][col + 3].remove(1);
										boards[row][col + 1].add(castlingrook);
										// 퀸 사이드 캐슬링
									} else if (indexcol == col - 2) {
										ChessPiece castlingrook = null;
										for (int k = 0; k < chesspiece_white.size(); k++) {
											if (chesspiece_white.get(k) == boards[row][col - 4].getComponent(1)) {
												castlingrook = chesspiece_white.get(k);
												break;
											}
										}
										boards[row][col - 4].remove(1);
										boards[row][col - 1].add(castlingrook);
									}

									// 기준 위치에서 아군말 제거 후 이동할 위치에 아군 말 추가 후 일반 아이콘으로 변경
									boards[row][col].remove(me);
									boards[indexrow][indexcol].add(me, "Center");

									// 이동이 끝나면 모든 아군말을 일반 아이콘으로 전환
									for (int k = 0; k < chesspiece_white.size(); k++) {
										chesspiece_white.get(k).setIcon(chesspiece_white.get(k).white_icon);
									}

									// 사용 안한 어택 리스너 제거
									removeAttackWhite();

									// 무브핀 초기화
									movepinsNotVisible();

									// 현재 위치 값 저장
									row = indexrow;
									col = indexcol;

									// 턴 정보를 상대 턴으로 변경
									chessBoard.turn = "black";

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
									for (int k = 0; k < chesspiece_white.size(); k++) {
										chesspiece_white.get(k).setIcon(chesspiece_white.get(k).white_icon);
									}

									// 사용 안한 어택 리스너 제거
									removeAttackWhite();

									// 무브핀 초기화
									movepinsNotVisible();

									// 현재 위치 값 저장
									row = indexrow;
									col = indexcol;

									// 턴 정보를 상대 턴으로 변경
									chessBoard.turn = "black";

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
