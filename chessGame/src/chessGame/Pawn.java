package chessGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Pawn extends ChessPiece {
	Pawn pawn;
	int movecount;
	boolean attack;
	ActionListener attackListener;
	ArrayList<ActionListener> attackListeners;

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
				} else if (boards[i][col + 1].getComponentCount() == 2 || boards[i][col - 1].getComponentCount() == 2) {
					if (((ChessPiece) boards[i][col + 1].getComponent(1)).side.equals("white")) {
						((ChessPiece) boards[i][col + 1].getComponent(1)).setIcon(white_icon_attack);

						if (((ChessPiece) boards[i][col - 1].getComponent(1)).side.equals("white")) {
							((ChessPiece) boards[i][col - 1].getComponent(1)).setIcon(white_icon_attack);
							break;
						} else {
							break;
						}
					} else {
						if (((ChessPiece) boards[i][col - 1].getComponent(1)).side.equals("white")) {
							((ChessPiece) boards[i][col - 1].getComponent(1)).setIcon(white_icon_attack);
							break;
						} else {
							break;
						}
					}

				}
			}
		} else if (row != 2) {
			// 처음 이후 무브 포인트 탐색
			for (int i = row + 1; i <= row + 1; i++) {
				if (boards[i][col].getComponentCount() == 1) {
					movepins[i][col].setVisible(true);
				} else if (boards[i][col + 1].getComponentCount() == 2 || boards[i][col - 1].getComponentCount() == 2) {
					if (((ChessPiece) boards[i][col + 1].getComponent(1)).side.equals("white")) {
						((ChessPiece) boards[i][col + 1].getComponent(1)).setIcon(white_icon_attack);

						if (((ChessPiece) boards[i][col - 1].getComponent(1)).side.equals("white")) {
							((ChessPiece) boards[i][col - 1].getComponent(1)).setIcon(white_icon_attack);
							break;
						} else {
							break;
						}
					} else {
						if (((ChessPiece) boards[i][col - 1].getComponent(1)).side.equals("white")) {
							((ChessPiece) boards[i][col - 1].getComponent(1)).setIcon(white_icon_attack);
							break;
						} else {
							break;
						}
					}

				}
			}
		}

		// 공격
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				if (boards[i][j].getComponentCount() == 2) {
					final int indexrow = i;
					final int indexcol = j;
					// 조건 : 현재 보드에 아이콘이 공격으로 바뀐 상대 말의 위치
					if (((ImageIcon) ((ChessPiece) boards[i][j].getComponent(1)).getIcon()).getDescription()
							.equals("white_icon_attack")) {

						// 해당 액션리스너를 나중에 지우기 위해 따로 공격용 이벤트 변수를 만들어 저장
						attackListener = new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {

								int check = 0;
								// 조건에 충족된 위치의 말이 체스말 모음에서 몇번 인덱스인지 확인 후 해당 말 비활성화
								for (int z = 0; z < chesspiece_white.size(); z++) {
									if (chesspiece_white
											.get(z) == ((ChessPiece) boards[indexrow][indexcol].getComponent(1))) {
										chesspiece_white.get(z).setEnabled(false);
										check = z;
									}
								}

								// 기준 위치에서 아군말 제거 후 이동할 위치에 상대말 제거 후 아군말 이동 위치로 추가
								boards[row][col].remove(pawn);
								boards[indexrow][indexcol].remove(chesspiece_white.get(check));
								boards[indexrow][indexcol].add(pawn, "Center");

								// 공격이 끝나면 모든 아군말을 일반 아이콘으로 전환
								for (int k = 0; k < chesspiece_black.size(); k++) {
									chesspiece_black.get(k).setIcon(chesspiece_black.get(k).black_icon);
								}

								// 무브핀 초기화
								movepinsNotVisible();

								// 현재 위치 값 저장
								row = indexrow;
								col = indexcol;

								// 턴 정보를 상대 턴으로 변경
								chessBoard.turn = "white";

								p_board.getParent().validate();
								p_board.getParent().repaint();

								// 공격을 한 상태로 변경
								attack = true;

								// 사용 했거나 하지 않은 어택 리스너 제거
								removeAttackBlack();

							}
						};

						// 조건에 맞는 위치의 상대 말에 액션 이벤트 추가 후 어택 리스너 모음에 저장
						((ChessPiece) boards[i][j].getComponent(1)).addActionListener(attackListener);
						attackListeners.add(attackListener);

					}
				}

			}

		}

		// 무브핀의 중복 액션 이벤트를 막기 위해 이벤트 초기화
		removeAction();

		// 공격을 안했을 시 일반 이동 액션
		if (!attack) {
			for (int i = 1; i <= 8; i++) {
				for (int j = 1; j <= 8; j++) {
					final int indexrow = i;
					final int indexcol = j;

					// 조건 : 현재 보여지고 있는 무브핀
					if (movepins[indexrow][indexcol].isVisible()) {
						movepins[indexrow][indexcol].addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {

								// 기준 위치에서 아군말 제거 후 이동할 위치에 아군 말 추가 후 일반 아이콘으로 변경
								boards[row][col].remove(pawn);
								boards[indexrow][indexcol].add(pawn, "Center");

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

								p_board.getParent().validate();
								p_board.getParent().repaint();

							}
						});

					}
				}
			}
		}

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
				} else if (boards[i][col + 1].getComponentCount() == 2 || boards[i][col - 1].getComponentCount() == 2) {
					if (((ChessPiece) boards[i][col + 1].getComponent(1)).side.equals("black")) {
						((ChessPiece) boards[i][col + 1].getComponent(1)).setIcon(black_icon_attack);
						if (((ChessPiece) boards[i][col - 1].getComponent(1)).side.equals("black")) {
							((ChessPiece) boards[i][col - 1].getComponent(1)).setIcon(black_icon_attack);
						} else {
							break;
						}
					} else {
						if (((ChessPiece) boards[i][col - 1].getComponent(1)).side.equals("black")) {
							((ChessPiece) boards[i][col - 1].getComponent(1)).setIcon(black_icon_attack);
						} else {
							break;
						}
					}

				}
			}
		} else if (row != 7) {
			// 처음 이후 무브 포인트 탐색
			for (int i = row - 1; i >= row - 1; i--) {
				if (boards[i][col].getComponentCount() == 1) {
					movepins[i][col].setVisible(true);
				} else if (boards[i][col + 1].getComponentCount() == 2 || boards[i][col - 1].getComponentCount() == 2) {
					if (((ChessPiece) boards[i][col + 1].getComponent(1)).side.equals("black")) {
						((ChessPiece) boards[i][col + 1].getComponent(1)).setIcon(black_icon_attack);
						if (((ChessPiece) boards[i][col - 1].getComponent(1)).side.equals("black")) {
							((ChessPiece) boards[i][col - 1].getComponent(1)).setIcon(black_icon_attack);
						} else {
							break;
						}
					} else {
						if (((ChessPiece) boards[i][col - 1].getComponent(1)).side.equals("black")) {
							((ChessPiece) boards[i][col - 1].getComponent(1)).setIcon(black_icon_attack);
						} else {
							break;
						}
					}

				}
			}
		}

		// 공격
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				if (boards[i][j].getComponentCount() == 2) {
					final int indexrow = i;
					final int indexcol = j;
					// 조건 : 현재 보드에 아이콘이 공격으로 바뀐 상대 말의 위치
					if (((ImageIcon) ((ChessPiece) boards[i][j].getComponent(1)).getIcon()).getDescription()
							.equals("black_icon_attack")) {

						// 해당 액션리스너를 나중에 지우기 위해 따로 공격용 이벤트 변수를 만들어 저장
						attackListener = new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {

								int check = 0;
								// 조건에 충족된 위치의 말이 체스말 모음에서 몇번 인덱스인지 확인 후 해당 말 비활성화
								for (int z = 0; z < chesspiece_black.size(); z++) {
									if (chesspiece_black
											.get(z) == ((ChessPiece) boards[indexrow][indexcol].getComponent(1))) {
										chesspiece_black.get(z).setEnabled(false);
										check = z;
									}
								}

								// 기준 위치에서 아군말 제거 후 이동할 위치에 상대말 제거 후 아군말 이동 위치로 추가
								boards[row][col].remove(pawn);
								boards[indexrow][indexcol].remove(chesspiece_black.get(check));
								boards[indexrow][indexcol].add(pawn, "Center");

								// 공격이 끝나면 모든 아군말을 일반 아이콘으로 전환
								for (int k = 0; k < chesspiece_white.size(); k++) {
									chesspiece_white.get(k).setIcon(chesspiece_white.get(k).white_icon);
								}

								// 무브핀 초기화
								movepinsNotVisible();

								// 현재 위치 값 저장
								row = indexrow;
								col = indexcol;

								// 턴 정보를 상대 턴으로 변경
								chessBoard.turn = "black";

								p_board.getParent().validate();
								p_board.getParent().repaint();

								// 공격을 한 상태로 변경
								attack = true;

								// 사용 했거나 하지 않은 어택 리스너 제거
								removeAttackBlack();

							}
						};

						// 조건에 맞는 위치의 상대 말에 액션 이벤트 추가 후 어택 리스너 모음에 저장
						((ChessPiece) boards[i][j].getComponent(1)).addActionListener(attackListener);
						attackListeners.add(attackListener);

					}
				}

			}

		}

		// 무브핀의 중복 액션 이벤트를 막기 위해 이벤트 초기화
		removeAction();

		// 공격을 안했을 시 일반 이동 액션
		if (!attack) {
			for (int i = 1; i <= 8; i++) {
				for (int j = 1; j <= 8; j++) {
					final int indexrow = i;
					final int indexcol = j;

					// 조건 : 현재 보여지고 있는 무브핀
					if (movepins[indexrow][indexcol].isVisible()) {
						movepins[indexrow][indexcol].addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {

								// 기준 위치에서 아군말 제거 후 이동할 위치에 아군 말 추가 후 일반 아이콘으로 변경
								boards[row][col].remove(pawn);
								boards[indexrow][indexcol].add(pawn, "Center");

								// 이동이 끝나면 모든 아군말을 일반 아이콘으로 전환
								for (int k = 0; k < chesspiece_white.size(); k++) {
									chesspiece_white.get(k).setIcon(chesspiece_white.get(k).white_icon);
								}

								// 사용 안한 어택 리스너 제거
								removeAttackBlack();

								// 무브핀 초기화
								movepinsNotVisible();

								// 현재 위치 값 저장
								row = indexrow;
								col = indexcol;

								// 턴 정보를 상대 턴으로 변경
								chessBoard.turn = "black";

								p_board.getParent().validate();
								p_board.getParent().repaint();

							}
						});

					}
				}
			}
		}

	}

	// blackMove()용 어택 리스너 제거 메서드
	public void removeAttackBlack() {
		// 공격대상이 여러개일 경우 리스너는 등록되지만 수행이 안되는 경우가 있기 떄문에
		// 모든 공격 기능을 수행 후 현재 등록된 모든 리스너는 삭제
		for (int i = 0; i < attackListeners.size(); i++) {
			ActionListener listener = attackListeners.get(i);
			// 모든 상대 체스말을 순회하며 해당 리스너가 존재하면 제거 후 기본 아이콘으로 변경
			for (int j = 0; j < chesspiece_white.size(); j++) {
				chesspiece_white.get(j).removeActionListener(listener);
				chesspiece_white.get(j).setIcon(white_icon);
			}
		}
		// 리스트 초기화
		attackListeners.clear();
	}

	// whiteMove()용 어택 리스너 제거 메서드
	public void removeAttackWhite() {
		// 공격대상이 여러개일 경우 리스너는 등록되지만 수행이 안되는 경우가 있기 떄문에
		// 모든 공격 기능을 수행 후 현재 등록된 모든 리스너는 삭제
		for (int i = 0; i < attackListeners.size(); i++) {
			ActionListener listener = attackListeners.get(i);
			// 모든 상대 체스말을 순회하며 해당 리스너가 존재하면 제거 후 기본 아이콘으로 변경
			for (int j = 0; j < chesspiece_black.size(); j++) {
				chesspiece_black.get(j).removeActionListener(listener);
				chesspiece_black.get(j).setIcon(black_icon);
			}
		}
		// 리스트 초기화
		attackListeners.clear();

	}
}
