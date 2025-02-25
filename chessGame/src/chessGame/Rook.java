package chessGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Rook extends ChessPiece {
	Rook rook;
	boolean attack;
	ActionListener tempListener;

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
		black_icon.setDescription("black_icon");

		white_icon = new ImageIcon("image/Rook-white.png");
		white_icon.setDescription("white_icon");

		black_icon_select = new ImageIcon("image/Rook-black_s.png");
		black_icon_select.setDescription("black_icon_select");

		white_icon_select = new ImageIcon("image/Rook-white_s.png");
		white_icon_select.setDescription("white_icon_select");

		black_icon_attack = new ImageIcon("image/Rook-black_a.png");
		black_icon_attack.setDescription("black_icon_attack");

		white_icon_attack = new ImageIcon("image/Rook-white_a.png");
		white_icon_attack.setDescription("white_icon_attack");

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
		// 나 자신 세팅
		rook = this;

		// 공격 유무 세팅
		attack = false;

		// 버튼 클릭 시 초기화
		for (int i = 0; i < chesspiece_black.size(); i++) {
			if (chesspiece_black.get(i) != rook) {
				chesspiece_black.get(i).setIcon(chesspiece_black.get(i).black_icon);
			}
		}
		for (int i = 0; i < chesspiece_white.size(); i++) {
			chesspiece_white.get(i).setIcon(chesspiece_white.get(i).white_icon);
		}

		movepinsNotVisible();

		this.setIcon(black_icon_select);
		removeAction();

		// 위쪽 이동 무브포인트
		for (int i = row - 1; i >= 1; i--) {
			if (boards[i][col].getComponentCount() == 1) {
				movepins[i][col].setVisible(true);
			} else if (boards[i][col].getComponentCount() == 2) {
				ChessPiece cp = (ChessPiece) boards[i][col].getComponent(1);
				if (cp.side.equals("white")) {
					cp.setIcon(white_icon_attack);
				}
				break;
			}
		}

		// 아래쪽 이동 무브포인트
		for (int i = row + 1; i <= 8; i++) {
			if (boards[i][col].getComponentCount() == 1) {
				movepins[i][col].setVisible(true);
			} else if (boards[i][col].getComponentCount() == 2) {
				if (((ChessPiece) boards[i][col].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[i][col].getComponent(1)).setIcon(white_icon_attack);
					break;
				} else {
					break;
				}
			}
		}

		// 왼쪽 이동 무브포인트
		for (int i = col - 1; i >= 1; i--) {
			if (boards[row][i].getComponentCount() == 1) {
				movepins[row][i].setVisible(true);
			} else if (boards[row][i].getComponentCount() == 2) {
				if (((ChessPiece) boards[row][i].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row][i].getComponent(1)).setIcon(white_icon_attack);
					break;
				} else {
					break;
				}
			}
		}

		// 오른쪽 이동 무브포인트
		for (int i = col + 1; i <= 8; i++) {
			if (boards[row][i].getComponentCount() == 1) {
				movepins[row][i].setVisible(true);
			} else if (boards[row][i].getComponentCount() == 2) {
				if (((ChessPiece) boards[row][i].getComponent(1)).side.equals("white")) {
					((ChessPiece) boards[row][i].getComponent(1)).setIcon(white_icon_attack);
					break;
				} else {
					break;
				}
			}
		}

		// 공격 액션
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				if (boards[i][j].getComponentCount() == 2) {

					final int indexrow = i;
					final int indexcol = j;
					if (((ImageIcon) ((ChessPiece) boards[i][j].getComponent(1)).getIcon()).getDescription()
							.equals("white_icon_attack")) {
						tempListener = new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								int check=0;
								for (int z = 0; z < chesspiece_white.size(); z++) {
									if (chesspiece_white
											.get(z) == ((ChessPiece) boards[indexrow][indexcol].getComponent(1))) {
										chesspiece_white.get(z).setEnabled(false);
										check=z;
									}
								}

								boards[row][col].remove(rook);
								boards[indexrow][indexcol]
										.remove(chesspiece_white.get(check));
								boards[indexrow][indexcol].add(rook, "Center");
								rook.setIcon(black_icon);

								for (int k = 0; k < chesspiece_black.size(); k++) {
									chesspiece_black.get(k).setIcon(chesspiece_black.get(k).black_icon);
								}

								movepinsNotVisible();

								row = indexrow;
								col = indexcol;

								chessBoard.turn = "white";

								p_board.getParent().validate();
								p_board.getParent().repaint();

								attack = true;

								chesspiece_white.get(check).removeActionListener(this);
							}

						};

						((ChessPiece) boards[i][j].getComponent(1)).addActionListener(tempListener);

					}
				}
			}
		}
		removeAction();

		// 공격을 안할 시 일반 이동 액션
		if (!attack) {
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

								for (int i = 0; i < chesspiece_white.size(); i++) {
									if (((ImageIcon) chesspiece_white.get(i).getIcon()).getDescription()
											.equals("white_icon_attack")) {
										chesspiece_white.get(i).removeActionListener(tempListener);
									}
									chesspiece_white.get(i).setIcon(chesspiece_white.get(i).white_icon);
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

	}

	@Override
	public void whiteMove() {
		// 나 자신 세팅
		rook = this;

		// 공격 유무 세팅
		attack = false;

		// 버튼 클릭 시 초기화
		for (int i = 0; i < chesspiece_white.size(); i++) {
			if (chesspiece_white.get(i) != rook) {
				chesspiece_white.get(i).setIcon(chesspiece_white.get(i).white_icon);
			}
		}
		for (int i = 0; i < chesspiece_black.size(); i++) {
			chesspiece_black.get(i).setIcon(chesspiece_black.get(i).black_icon);
		}

		movepinsNotVisible();

		this.setIcon(white_icon_select);
		removeAction();

		// 위쪽 이동 무브포인트
		for (int i = row - 1; i >= 1; i--) {
			if (boards[i][col].getComponentCount() == 1) {
				movepins[i][col].setVisible(true);
			} else if (boards[i][col].getComponentCount() == 2) {
				ChessPiece cp = (ChessPiece) boards[i][col].getComponent(1);
				if (cp.side.equals("black")) {
					cp.setIcon(black_icon_attack);
				}
				break;
			}
		}

		// 아래쪽 이동 무브포인트
		for (int i = row + 1; i <= 8; i++) {
			if (boards[i][col].getComponentCount() == 1) {
				movepins[i][col].setVisible(true);
			} else if (boards[i][col].getComponentCount() == 2) {
				if (((ChessPiece) boards[i][col].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[i][col].getComponent(1)).setIcon(black_icon_attack);
					break;
				} else {
					break;
				}
			}
		}

		// 왼쪽 이동 무브포인트
		for (int i = col - 1; i >= 1; i--) {
			if (boards[row][i].getComponentCount() == 1) {
				movepins[row][i].setVisible(true);
			} else if (boards[row][i].getComponentCount() == 2) {
				if (((ChessPiece) boards[row][i].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row][i].getComponent(1)).setIcon(black_icon_attack);
					break;
				} else {
					break;
				}
			}
		}

		// 오른쪽 이동 무브포인트
		for (int i = col + 1; i <= 8; i++) {
			if (boards[row][i].getComponentCount() == 1) {
				movepins[row][i].setVisible(true);
			} else if (boards[row][i].getComponentCount() == 2) {
				if (((ChessPiece) boards[row][i].getComponent(1)).side.equals("black")) {
					((ChessPiece) boards[row][i].getComponent(1)).setIcon(black_icon_attack);
					break;
				} else {
					break;
				}
			}
		}

		// 공격 액션
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				if (boards[i][j].getComponentCount() == 2) {

					final int indexrow = i;
					final int indexcol = j;
					if (((ImageIcon) ((ChessPiece) boards[i][j].getComponent(1)).getIcon()).getDescription()
							.equals("black_icon_attack")) {
						tempListener = new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {

								int check=0;
								for (int z = 0; z < chesspiece_black.size(); z++) {
									if (chesspiece_black
											.get(z) == ((ChessPiece) boards[indexrow][indexcol].getComponent(1))) {
										chesspiece_black.get(z).setEnabled(false);
										check=z;
									}
								}

								boards[row][col].remove(rook);
								boards[indexrow][indexcol]
										.remove(chesspiece_black.get(check));
								boards[indexrow][indexcol].add(rook, "Center");
								rook.setIcon(white_icon);

								for (int k = 0; k < chesspiece_white.size(); k++) {
									chesspiece_white.get(k).setIcon(chesspiece_white.get(k).white_icon);
								}

								movepinsNotVisible();

								row = indexrow;
								col = indexcol;

								chessBoard.turn = "black";

								p_board.getParent().validate();
								p_board.getParent().repaint();

								attack = true;

								chesspiece_black.get(check).removeActionListener(this);
							}

						};

						((ChessPiece) boards[i][j].getComponent(1)).addActionListener(tempListener);

					}
				}
			}
		}

		removeAction();

		// 공격을 안할 시 일반 이동 액션
		if (!attack) {
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
								rook.setIcon(white_icon);

								for (int i = 0; i < chesspiece_black.size(); i++) {
									if (((ImageIcon) chesspiece_black.get(i).getIcon()).getDescription()
											.equals("black_icon_attack")) {
										chesspiece_black.get(i).removeActionListener(tempListener);
									}
									chesspiece_black.get(i).setIcon(chesspiece_black.get(i).black_icon);
								}

								movepinsNotVisible();

								row = indexrow;
								col = indexcol;

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

}
