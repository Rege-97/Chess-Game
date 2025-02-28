package chessGame;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

abstract public class ChessPiece extends JButton {
	String side;
	int row;
	int col;
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
