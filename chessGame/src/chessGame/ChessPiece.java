package chessGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

abstract public class ChessPiece extends JButton {
	String side;
	int row;
	int col;
	int movecount;
	boolean live;
	boolean attack;
	ActionListener attackListener;
	ArrayList<ActionListener> attackListeners;
	ChessBoard chessBoard;
	ArrayList<ChessPiece> chesspiece_black, chesspiece_white;
	JPanel boards[][]; 
	JButton movepins[][]; 
	JPanel p_board;
	ImageIcon black_icon, white_icon, black_icon_select, white_icon_select, black_icon_attack, white_icon_attack;

	public abstract void blackMove();

	public abstract void whiteMove();
	
	public abstract void setMovePinWhite();
	
	public abstract void setMovePinBlack();
	
	public abstract void isAttackKing();
	
	
	// 무브핀 일괄 비활성화 메서드
	public void movepinsNotVisible() {
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				movepins[i][j].setVisible(false);
			}
		}
	}

	// 무브핀 액션 리스너 일괄 제거 메서드
	public void removeAction() {
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				ActionListener[] listeners = movepins[i][j].getActionListeners();
				for (int k = 0; k < listeners.length; k++) {
					movepins[i][j].removeActionListener(listeners[k]);
				}
			}
		}

	}
	// blackMove()용 공격 메서드
	public void attackBlack(ChessPiece me) {
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

								// 조건에 충족된 위치의 말이 체스말 모음에서 몇번 인덱스인지 확인 후 해당 말 비활성화
								int check = 0;
								for (int z = 0; z < chesspiece_white.size(); z++) {
									if (chesspiece_white
											.get(z) == ((ChessPiece) boards[indexrow][indexcol].getComponent(1))) {
										chesspiece_white.get(z).setEnabled(false);
										check = z;
									}
								}

								// 기준 위치에서 아군말 제거 후 이동할 위치에 상대말 제거 후 아군말 이동 위치로 추가
								boards[row][col].remove(me);
								boards[indexrow][indexcol].remove(chesspiece_white.get(check));
								boards[indexrow][indexcol].add(me, "Center");

								// 공격이 끝나면 모든 아군말을 일반 아이콘으로 전환
								for (int k = 0; k < chesspiece_black.size(); k++) {
									chesspiece_black.get(k).setIcon(chesspiece_black.get(k).black_icon);
								}

								// 무브핀 초기화
								movepinsNotVisible();

								// 현재 위치 값 저장
								row = indexrow;
								col = indexcol;
								
								//프로모션 다이얼로그
								if(me instanceof Pawn) {
									if(row==8) {
										showImageDialog();
									}
								}
									
								
								// 턴 정보를 상대 턴으로 변경
								chessBoard.turn = "white";

								p_board.getParent().validate();
								p_board.getParent().repaint();

								// 공격을 한 상태로 변경
								attack = true;
								
								// 이동 횟수 증가
								movecount++;

								
								// 사용 했거나 하지 않은 어택 리스너 제거
								removeAttackBlack();
								
								// 기물이 이동한 후 킹이 체크 상태인지 다시 확인
								if (chessBoard.isKingInCheck("white")) {
								    System.out.println("White King is in Check!");
								}
								if (chessBoard.isKingInCheck("black")) {
								    System.out.println("Black King is in Check!");
								}

								// UI 업데이트 호출 (체크 상태 즉시 반영)
								chessBoard.updateCheckStatus();

							}

						};
						// 조건에 맞는 위치의 상대 말에 액션 이벤트 추가 후 어택 리스너 모음에 저장
						((ChessPiece) boards[i][j].getComponent(1)).addActionListener(attackListener);
						attackListeners.add(attackListener);

					}
				}
			}
		}
	}
	
	// blackMove()용 이동 메서드
	public void moveBlack(ChessPiece me) {
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
								
								// 모든 적군말을 일반 아이콘으로 전환
								for (int k = 0; k < chesspiece_white.size(); k++) {
									chesspiece_white.get(k).setIcon(chesspiece_white.get(k).white_icon);
								}

								// 기물이 이동한 후 킹이 체크 상태인지 다시 확인
								if (chessBoard.isKingInCheck("white")) {
								    System.out.println("White King is in Check!");
								}
								if (chessBoard.isKingInCheck("black")) {
								    System.out.println("Black King is in Check!");
								}

								// UI 업데이트 호출 (체크 상태 즉시 반영)
								chessBoard.updateCheckStatus();
							}
						});

					}
				}
			}
		}
	}
	
	// whiteMove()용 공격 메서드
	public void attackWhite(ChessPiece me) {
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

								// 조건에 충족된 위치의 말이 체스말 모음에서 몇번 인덱스인지 확인 후 해당 말 비활성화
								int check = 0;
								for (int z = 0; z < chesspiece_black.size(); z++) {
									if (chesspiece_black
											.get(z) == ((ChessPiece) boards[indexrow][indexcol].getComponent(1))) {
										chesspiece_black.get(z).setEnabled(false);
										check = z;
									}
								}

								// 기준 위치에서 아군말 제거 후 이동할 위치에 상대말 제거 후 아군말 이동 위치로 추가
								boards[row][col].remove(me);
								boards[indexrow][indexcol].remove(chesspiece_black.get(check));
								boards[indexrow][indexcol].add(me, "Center");

								// 공격이 끝나면 모든 아군말을 일반 아이콘으로 전환
								for (int k = 0; k < chesspiece_white.size(); k++) {
									chesspiece_white.get(k).setIcon(chesspiece_white.get(k).white_icon);
								}

								// 무브핀 초기화
								movepinsNotVisible();

								// 현재 위치 값 저장
								row = indexrow;
								col = indexcol;
								
								//프로모션 다이얼로그
								if(me instanceof Pawn) {
									if(row==1) {
										showImageDialog();
									}
								}
								
								// 턴 정보를 상대 턴으로 변경
								chessBoard.turn = "black";

								p_board.getParent().validate();
								p_board.getParent().repaint();

								// 공격을 한 상태로 변경
								attack = true;
								
								// 이동 횟수 증가
								movecount++;

								// 사용 했거나 하지 않은 어택 리스너 제거
								removeAttackWhite();
								
								// 기물이 이동한 후 킹이 체크 상태인지 다시 확인
								if (chessBoard.isKingInCheck("white")) {
								    System.out.println("White King is in Check!");
								}
								if (chessBoard.isKingInCheck("black")) {
								    System.out.println("Black King is in Check!");
								}

								// UI 업데이트 호출 (체크 상태 즉시 반영)
								chessBoard.updateCheckStatus();
							}

						};
						// 조건에 맞는 위치의 상대 말에 액션 이벤트 추가 후 어택 리스너 모음에 저장
						((ChessPiece) boards[i][j].getComponent(1)).addActionListener(attackListener);
						attackListeners.add(attackListener);

					}
				}
			}
		}
	}
	
	// whiteMove()용 이동 메서드
	public void moveWhite(ChessPiece me) {
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
								
								// 모든 적군말을 일반 아이콘으로 전환
								for (int k = 0; k < chesspiece_black.size(); k++) {
									chesspiece_black.get(k).setIcon(chesspiece_black.get(k).black_icon);
								}

								// 기물이 이동한 후 킹이 체크 상태인지 다시 확인
								if (chessBoard.isKingInCheck("white")) {
								    System.out.println("White King is in Check!");
								}
								if (chessBoard.isKingInCheck("black")) {
								    System.out.println("Black King is in Check!");
								}

								// UI 업데이트 호출 (체크 상태 즉시 반영)
								chessBoard.updateCheckStatus();
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
				chesspiece_white.get(j).setIcon(chesspiece_white.get(j).white_icon);
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
				chesspiece_black.get(j).setIcon(chesspiece_black.get(j).black_icon);
			}
		}
		// 리스트 초기화
		attackListeners.clear();
	}
	

	//프로모션 적용을 위한 다이얼 로그
    public void showImageDialog() {
        ImageIcon p_queen = new ImageIcon("image/your_image.png");
        ImageIcon p_bishop = new ImageIcon("image/your_image.png");
        ImageIcon p_rook = new ImageIcon("image/your_image.png");
        ImageIcon p_knight = new ImageIcon("image/your_image.png");
        ImageIcon p_pone = new ImageIcon("image/your_image.png");
        
        JLabel imageLabel = new JLabel("111");
        JButton selectButton = new JButton("선택");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(selectButton, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(this, panel, "이미지 선택", JOptionPane.PLAIN_MESSAGE);

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 이미지 선택 시 처리 로직
                JOptionPane.showMessageDialog(null, "이미지가 선택되었습니다!", "알림", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }


	// 체크 상태를 확인하기 위해 구분하는 킹 아이콘 변경 메서드
	public boolean setAttackIconIfKing(int targetRow, int targetCol) {
	    if (boards[targetRow][targetCol].getComponentCount() == 2) {
	        ChessPiece targetPiece = (ChessPiece) boards[targetRow][targetCol].getComponent(1);

	        // 대상이 상대 킹인지 확인
	        if (targetPiece instanceof King && !targetPiece.side.equals(this.side)) {
	            if (this.side.equals("white")) {
	                targetPiece.setIcon(targetPiece.black_icon_attack);
	            } else {
	                targetPiece.setIcon(targetPiece.white_icon_attack);
	            }
	            return true; // 킹이 발견되었으므로 탐색 종료
	        }
	    }
	    return false; // 킹이 아니면 계속 탐색 가능
	}

}

