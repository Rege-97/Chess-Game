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
	ChessBoard chessBoard;
	ArrayList<ChessPiece> chesspiece_black, chesspiece_white;
	JPanel boards[][]; 
	JButton movepins[][]; 
	JPanel p_board;
	ImageIcon black_icon, white_icon, black_icon_select, white_icon_select, black_icon_attack, white_icon_attack;

	public abstract void blackMove();

	public abstract void whiteMove();

	public void movepinsNotVisible() {
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				movepins[i][j].setVisible(false);
			}
		}
	}

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
}
