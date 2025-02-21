package moveTest;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Test extends JFrame {
	int row, col;
	JPanel p_board;
	JPanel boards[][];
	JButton bt_pawn;
	JButton movepins[][];

	public Test() {
		super("테스트");

		// 화면 중앙 출력
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		int x = (screenSize.width - frameSize.width) / 2;
		int y = (screenSize.height - frameSize.height) / 2;
		setLocation(x, y);

		// 창 닫기
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		this.setSize(800, 800);
		this.setVisible(true);
		this.setLayout(new BorderLayout());

		p_board = new JPanel(new GridLayout(8, 8));

		boardSet();

		movepins = new JButton[9][9];

		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				movepins[i][j] = new JButton("0");
				boards[i][j].add(movepins[i][j]);
				movepins[i][j].setVisible(false);
			}
		}

		this.add(p_board, "Center");
		Pawn pawns[]=new Pawn[8];
		
		for(int i=0;i<8;i++) {
			pawns[i]=new Pawn("black", 2, i+1);
			boards[2][i+1].add(pawns[i]);
		}
		
		for(int i=0;i<8;i++) {
			final int index=i;
			pawns[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					pawns[index].move(boards, movepins, p_board);
					
				}
			});
		}
		
//		pawns[1].addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				pawns[1].setBackground(Color.red);
//				
//				row = 0;
//				col = 0;
//
//				for (int i = 1; i <= 8; i++) {
//					if (Arrays.asList(boards[i]).indexOf(pawns[1].getParent()) != -1) {
//						row = i;
//						col = Arrays.asList(boards[i]).indexOf(pawns[1].getParent());
//						break;
//					}
//				}
//
//				movepins[row + 1][col].setVisible(true);
//				movepins[row + 2][col].setVisible(true);
//				
//				ActionListener[] a1=movepins[row + 1][col].getActionListeners();
//				
//				for(int i=0;i<a1.length;i++) {
//					movepins[row + 1][col].removeActionListener(a1[i]);
//				}
//				
//				ActionListener[] a2=movepins[row + 2][col].getActionListeners();
//				
//				for(int i=0;i<a2.length;i++) {
//					movepins[row + 2][col].removeActionListener(a2[i]);
//				}
//				
//				System.out.println(row + " " + col);
//
//				movepins[row + 1][col].addActionListener(new ActionListener() {
//
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						boards[row][col].remove(pawns[1]);
//						boards[row + 1][col].add(pawns[1]);
//						pawns[1].setBackground(Color.green);
//						
//						movepins[row + 1][col].setVisible(false);
//						movepins[row + 2][col].setVisible(false);
//						System.out.println("1칸");
//						
//						p_board.getParent().validate();
//						p_board.getParent().repaint();
//					}
//				});
//
//				movepins[row + 2][col].addActionListener(new ActionListener() {
//
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						boards[row][col].remove(pawns[1]);
//						
//						boards[row + 2][col].add(pawns[1]);
//						pawns[1].setBackground(Color.green);
//						
//						
//						movepins[row + 1][col].setVisible(false);
//						movepins[row + 2][col].setVisible(false);
//						System.out.println("2칸");
//						
//						p_board.getParent().validate();
//						p_board.getParent().repaint();
//
//					}
//				});
//
//			}
//		});

		this.validate();
	}

	public void boardSet() {
		boards = new JPanel[9][9];

		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				boards[i][j] = new JPanel();
				if (i % 2 == 0) {
					if (j % 2 == 0) {
						boards[i][j].setBackground(Color.yellow);
					} else {
						boards[i][j].setBackground(Color.gray);
					}
				} else {
					if (j % 2 == 0) {
						boards[i][j].setBackground(Color.gray);
					} else {
						boards[i][j].setBackground(Color.yellow);
					}
				}
				p_board.add(boards[i][j]);
			}
		}
	}


	public static void main(String[] args) {
		new Test();

	}

}
