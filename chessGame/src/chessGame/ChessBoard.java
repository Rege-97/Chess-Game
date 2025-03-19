package chessGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ChessBoard extends JFrame {
	boolean white_check, black_check;
	ChessBoard main;
	JPanel p_board;
	JPanel boards[][];
	JButton movepins[][];
	String turn;
	ImageIcon movepin;
	ArrayList<ChessPiece> chesspiece_black, chesspiece_white;
	JLabel lb_check;
	JLabel lb_w_turn;
	JLabel lb_turn_count;
	String checkpiece;
	int turn_count;
	int gameno;

	Connection conn;
	PreparedStatement ps;
	ResultSet rs;

	final int TIMER_DURATION = 900;
	Timer b_timer, w_timer;
	int b_remainingTime = TIMER_DURATION;
	int w_remainingTime = TIMER_DURATION;

	JLabel lb_b_timer, lb_w_timer;
	JLabel lb_b_turn;

	public ChessBoard() {

		super("테스트");
		main = this;

		this.setSize(1280, 800);
		this.setVisible(true);
		this.setLayout(new BorderLayout());

		// 화면 중앙 출력
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		int x = (screenSize.width - frameSize.width) / 2;
		int y = (screenSize.height - frameSize.height) / 2;
		setLocation(x, y);

		// 창 닫기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 초기 턴 화이트
		turn = "white";
		turn_count = 0;

		// 보드 세팅
		boardSet();

		JPanel p_east = new JPanel(new BorderLayout());
		p_east.setPreferredSize(new Dimension(480, 800));
		this.add(p_east, "East");

		// 체크 상태를 표시하기 위한 임시 라벨
		lb_check = new JLabel("Play", JLabel.CENTER);
		lb_check.setFont(new Font("Default Font", Font.PLAIN, 50));
		p_east.add(lb_check, "North");

		// 턴을 표시하기 위한 임시 라벨
		JPanel p_east_center = new JPanel(new GridLayout(2, 2));
		p_east.add(p_east_center, "Center");
		lb_w_turn = new JLabel("White", JLabel.CENTER);
		lb_w_turn.setFont(new Font("Default Font", Font.PLAIN, 50));
		p_east_center.add(lb_w_turn);
		lb_b_turn = new JLabel("Black", JLabel.CENTER);
		lb_b_turn.setFont(new Font("Default Font", Font.PLAIN, 50));
		p_east_center.add(lb_b_turn);

		// 타이머 배치
		lb_w_timer = new JLabel(formatTime(w_remainingTime), JLabel.CENTER);
		lb_w_timer.setFont(new Font("Default Font", Font.PLAIN, 50));
		p_east_center.add(lb_w_timer);

		lb_b_timer = new JLabel(formatTime(b_remainingTime), JLabel.CENTER);
		lb_b_timer.setFont(new Font("Default Font", Font.PLAIN, 50));
		p_east_center.add(lb_b_timer);

		// 턴수를 표기하기 위한 임시 라벨
		JPanel p_east_south = new JPanel(new GridLayout(1, 2));
		p_east.add(p_east_south, "South");
		lb_turn_count = new JLabel(turn_count + "", JLabel.CENTER);
		lb_turn_count.setFont(new Font("Default Font", Font.PLAIN, 50));
		p_east_south.add(lb_turn_count);
		JButton bt_back = new JButton("⬅️");
		bt_back.setFont(new Font("Default Font", Font.PLAIN, 50));
		p_east_south.add(bt_back);

		// 타이머 초기화 및 시작
		b_timer = blacktimer();
		w_timer = whitetimer();

		turnTimer();

		// 말 배치
		chesspiece_white = new ArrayList<ChessPiece>();
		chesspiece_black = new ArrayList<ChessPiece>();
		setGameInfo();
		setChessPieceWhite();
		setChessPieceBlack();
		loadMoveBoard();

		// 되돌리기 버튼 액션리스너
		bt_back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 턴카운트를 1 줄이고 보드를 제거한 뒤에 다시 생성
				if (turn_count != 0) {
					turn_count--;
					if (turn.equals("white")) {
						turn = "black";
					} else {
						turn = "white";
					}
					main.remove(p_board);
					boardSet();

					// 체스말과 이동 경로를 DB의 정보에 맞게 다시 생성
					chesspiece_white = new ArrayList<ChessPiece>();
					chesspiece_black = new ArrayList<ChessPiece>();
					setChessPieceWhite();
					setChessPieceBlack();
					loadMoveBoard();
					lb_turn_count.setText(turn_count + "");
					turnTimer();

					try {
						// 기존 턴 정보 삭제
						setDB();
						String sql = "delete from white where gameno=? and turn=?";
						ps = conn.prepareStatement(sql);
						ps.setInt(1, gameno);
						ps.setInt(2, turn_count + 1);
						ps.executeUpdate();

						sql = "delete from black where gameno=? and turn=?";
						ps = conn.prepareStatement(sql);
						ps.setInt(1, gameno);
						ps.setInt(2, turn_count + 1);
						ps.executeUpdate();

					} catch (Exception e2) {
						e2.printStackTrace();
					} finally {
						try {
							if (ps != null)
								ps.close();
							if (conn != null)
								conn.close();
						} catch (Exception e3) {

						}
					}

				}

			}
		});

		this.validate();

	}

	// 체스 보드 그리기;
	public void boardSet() {
		p_board = new JPanel(new GridLayout(8, 8));
		this.add(p_board, "Center");
		boards = new JPanel[9][9];
		movepins = new JButton[9][9];
		movepin = new ImageIcon("image/MovePoint.png");

		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				boards[i][j] = new JPanel(new BorderLayout());
				if (i % 2 == 0) {
					if (j % 2 == 0) {
						boards[i][j].setBackground(Color.white);
					} else {
						boards[i][j].setBackground(Color.darkGray);
					}
				} else {
					if (j % 2 == 0) {
						boards[i][j].setBackground(Color.darkGray);
					} else {
						boards[i][j].setBackground(Color.white);
					}
				}
				movepins[i][j] = new JButton();
				movepins[i][j].setContentAreaFilled(false);
				movepins[i][j].setFocusPainted(false);
				movepins[i][j].setOpaque(false);
				movepins[i][j].setIcon(movepin);
				boards[i][j].add(movepins[i][j], "North");
				movepins[i][j].setVisible(false);
				p_board.add(boards[i][j]);
			}
		}
	}

	// 화이트 체스 말 그리기
	public void setChessPieceWhite() {
		try {
			setDB();

			String sql = "SELECT * FROM white WHERE gameno=? AND turn=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, gameno);
			ps.setInt(2, turn_count);
			rs = ps.executeQuery();
			rs.next();

			w_remainingTime = rs.getInt("w_time");

			for (int i = 1; i <= 8; i++) {
				String pawnPromotion = rs.getString("w_pawn" + i + "_promotion");
				int row = rs.getInt("w_pawn" + i + "_row");
				int col = rs.getInt("w_pawn" + i + "_col");
				int moveCount = rs.getInt("w_pawn" + i + "_move");

				if (row == -1) {
					chesspiece_white.add(new Pawn("white", row, col, this, boards, movepins, p_board, chesspiece_black,
							chesspiece_white));
					chesspiece_white.get(chesspiece_white.size() - 1).setEnabled(false);
					chesspiece_white.get(chesspiece_white.size() - 1).movecount = moveCount;
				} else {
					if (pawnPromotion.equals("pawn")) {
						chesspiece_white.add(new Pawn("white", row, col, this, boards, movepins, p_board,
								chesspiece_black, chesspiece_white));
					} else if (pawnPromotion.equals("rook")) {
						chesspiece_white.add(new Rook("white", row, col, this, boards, movepins, p_board,
								chesspiece_black, chesspiece_white));
					} else if (pawnPromotion.equals("bishop")) {
						chesspiece_white.add(new Bishop("white", row, col, this, boards, movepins, p_board,
								chesspiece_black, chesspiece_white));
					} else if (pawnPromotion.equals("knight")) {
						chesspiece_white.add(new Knight("white", row, col, this, boards, movepins, p_board,
								chesspiece_black, chesspiece_white));
					} else if (pawnPromotion.equals("queen")) {
						chesspiece_white.add(new Queen("white", row, col, this, boards, movepins, p_board,
								chesspiece_black, chesspiece_white));
					}
					chesspiece_white.get(chesspiece_white.size() - 1).movecount = moveCount;
					boards[row][col].add(chesspiece_white.get(chesspiece_white.size() - 1), "Center");
				}

			}

			for (int i = 1; i <= 2; i++) {
				int row = rs.getInt("w_rook" + i + "_row");
				int col = rs.getInt("w_rook" + i + "_col");
				int moveCount = rs.getInt("w_rook" + i + "_move");

				if (row == -1) {
					chesspiece_white.add(new Rook("white", row, col, this, boards, movepins, p_board, chesspiece_black,
							chesspiece_white));
					chesspiece_white.get(chesspiece_white.size() - 1).setEnabled(false);
					chesspiece_white.get(chesspiece_white.size() - 1).movecount = moveCount;
				} else {
					chesspiece_white.add(new Rook("white", row, col, this, boards, movepins, p_board, chesspiece_black,
							chesspiece_white));
					chesspiece_white.get(chesspiece_white.size() - 1).movecount = moveCount;
					boards[row][col].add(chesspiece_white.get(chesspiece_white.size() - 1), "Center");
				}
			}

			for (int i = 1; i <= 2; i++) {
				int row = rs.getInt("w_bishop" + i + "_row");
				int col = rs.getInt("w_bishop" + i + "_col");
				int moveCount = rs.getInt("w_bishop" + i + "_move");

				if (row == -1) {
					chesspiece_white.add(new Bishop("white", row, col, this, boards, movepins, p_board,
							chesspiece_black, chesspiece_white));
					chesspiece_white.get(chesspiece_white.size() - 1).setEnabled(false);
					chesspiece_white.get(chesspiece_white.size() - 1).movecount = moveCount;
				} else {
					chesspiece_white.add(new Bishop("white", row, col, this, boards, movepins, p_board,
							chesspiece_black, chesspiece_white));
					chesspiece_white.get(chesspiece_white.size() - 1).movecount = moveCount;
					boards[row][col].add(chesspiece_white.get(chesspiece_white.size() - 1), "Center");
				}
			}

			for (int i = 1; i <= 2; i++) {
				int row = rs.getInt("w_knight" + i + "_row");
				int col = rs.getInt("w_knight" + i + "_col");
				int moveCount = rs.getInt("w_knight" + i + "_move");

				if (row == -1) {
					chesspiece_white.add(new Knight("white", row, col, this, boards, movepins, p_board,
							chesspiece_black, chesspiece_white));
					chesspiece_white.get(chesspiece_white.size() - 1).setEnabled(false);
					chesspiece_white.get(chesspiece_white.size() - 1).movecount = moveCount;
				} else {
					chesspiece_white.add(new Knight("white", row, col, this, boards, movepins, p_board,
							chesspiece_black, chesspiece_white));
					chesspiece_white.get(chesspiece_white.size() - 1).movecount = moveCount;
					boards[row][col].add(chesspiece_white.get(chesspiece_white.size() - 1), "Center");
				}
			}

			int row = rs.getInt("w_queen_row");
			int col = rs.getInt("w_queen_col");
			int moveCount = rs.getInt("w_queen_move");

			if (row == -1) {
				chesspiece_white.add(new Queen("white", row, col, this, boards, movepins, p_board, chesspiece_black,
						chesspiece_white));
				chesspiece_white.get(chesspiece_white.size() - 1).setEnabled(false);
				chesspiece_white.get(chesspiece_white.size() - 1).movecount = moveCount;
			} else {
				chesspiece_white.add(new Queen("white", row, col, this, boards, movepins, p_board, chesspiece_black,
						chesspiece_white));
				chesspiece_white.get(chesspiece_white.size() - 1).movecount = moveCount;
				boards[row][col].add(chesspiece_white.get(chesspiece_white.size() - 1), "Center");
			}

			row = rs.getInt("w_king_row");
			col = rs.getInt("w_king_col");
			moveCount = rs.getInt("w_king_move");

			if (row == -1) {
				chesspiece_white.add(new King("white", row, col, this, boards, movepins, p_board, chesspiece_black,
						chesspiece_white));
				chesspiece_white.get(chesspiece_white.size() - 1).setEnabled(false);
				chesspiece_white.get(chesspiece_white.size() - 1).movecount = moveCount;
			} else {
				chesspiece_white.add(new King("white", row, col, this, boards, movepins, p_board, chesspiece_black,
						chesspiece_white));
				chesspiece_white.get(chesspiece_white.size() - 1).movecount = moveCount;
				boards[row][col].add(chesspiece_white.get(chesspiece_white.size() - 1), "Center");
			}

			// 화이트 체스말 이벤트
			for (int i = 0; i < chesspiece_white.size(); i++) {
				final int index = i;
				chesspiece_white.get(index).addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (turn.equals("white") && chesspiece_white.get(index).isEnabled()) {
							chesspiece_white.get(index).whiteMove();
						}
					}

				});
			}
			this.validate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {

			}
		}

	}

	// 블랙 체스말 그리기
	public void setChessPieceBlack() {
		try {
			setDB();

			String sql = "SELECT * FROM black WHERE gameno=? AND turn=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, gameno);
			ps.setInt(2, turn_count);
			rs = ps.executeQuery();
			rs.next();

			b_remainingTime = rs.getInt("b_time");

			for (int i = 1; i <= 8; i++) {
				String pawnPromotion = rs.getString("b_pawn" + i + "_promotion");
				int row = rs.getInt("b_pawn" + i + "_row");
				int col = rs.getInt("b_pawn" + i + "_col");
				int moveCount = rs.getInt("b_pawn" + i + "_move");

				if (row == -1) {
					chesspiece_black.add(new Pawn("black", row, col, this, boards, movepins, p_board, chesspiece_black,
							chesspiece_white));
					chesspiece_black.get(chesspiece_black.size() - 1).setEnabled(false);
					chesspiece_black.get(chesspiece_black.size() - 1).movecount = moveCount;
				} else {
					if (pawnPromotion.equals("pawn")) {
						chesspiece_black.add(new Pawn("black", row, col, this, boards, movepins, p_board,
								chesspiece_black, chesspiece_white));
					} else if (pawnPromotion.equals("rook")) {
						chesspiece_black.add(new Rook("black", row, col, this, boards, movepins, p_board,
								chesspiece_black, chesspiece_white));
					} else if (pawnPromotion.equals("bishop")) {
						chesspiece_black.add(new Bishop("black", row, col, this, boards, movepins, p_board,
								chesspiece_black, chesspiece_white));
					} else if (pawnPromotion.equals("knight")) {
						chesspiece_black.add(new Knight("black", row, col, this, boards, movepins, p_board,
								chesspiece_black, chesspiece_white));
					} else if (pawnPromotion.equals("queen")) {
						chesspiece_black.add(new Queen("black", row, col, this, boards, movepins, p_board,
								chesspiece_black, chesspiece_white));
					}
					chesspiece_black.get(chesspiece_black.size() - 1).movecount = moveCount;
					boards[row][col].add(chesspiece_black.get(chesspiece_black.size() - 1), "Center");
				}
			}

			for (int i = 1; i <= 2; i++) {
				int row = rs.getInt("b_rook" + i + "_row");
				int col = rs.getInt("b_rook" + i + "_col");
				int moveCount = rs.getInt("b_rook" + i + "_move");

				if (row == -1) {
					chesspiece_black.add(new Rook("black", row, col, this, boards, movepins, p_board, chesspiece_black,
							chesspiece_white));
					chesspiece_black.get(chesspiece_black.size() - 1).setEnabled(false);
					chesspiece_black.get(chesspiece_black.size() - 1).movecount = moveCount;
				} else {
					chesspiece_black.add(new Rook("black", row, col, this, boards, movepins, p_board, chesspiece_black,
							chesspiece_white));
					chesspiece_black.get(chesspiece_black.size() - 1).movecount = moveCount;
					boards[row][col].add(chesspiece_black.get(chesspiece_black.size() - 1), "Center");
				}
			}

			for (int i = 1; i <= 2; i++) {
				int row = rs.getInt("b_bishop" + i + "_row");
				int col = rs.getInt("b_bishop" + i + "_col");
				int moveCount = rs.getInt("b_bishop" + i + "_move");

				if (row == -1) {
					chesspiece_black.add(new Bishop("black", row, col, this, boards, movepins, p_board,
							chesspiece_black, chesspiece_white));
					chesspiece_black.get(chesspiece_black.size() - 1).setEnabled(false);
					chesspiece_black.get(chesspiece_black.size() - 1).movecount = moveCount;
				} else {
					chesspiece_black.add(new Bishop("black", row, col, this, boards, movepins, p_board,
							chesspiece_black, chesspiece_white));
					chesspiece_black.get(chesspiece_black.size() - 1).movecount = moveCount;
					boards[row][col].add(chesspiece_black.get(chesspiece_black.size() - 1), "Center");
				}
			}

			for (int i = 1; i <= 2; i++) {
				int row = rs.getInt("b_knight" + i + "_row");
				int col = rs.getInt("b_knight" + i + "_col");
				int moveCount = rs.getInt("b_knight" + i + "_move");

				if (row == -1) {
					chesspiece_black.add(new Knight("black", row, col, this, boards, movepins, p_board,
							chesspiece_black, chesspiece_white));
					chesspiece_black.get(chesspiece_black.size() - 1).setEnabled(false);
					chesspiece_black.get(chesspiece_black.size() - 1).movecount = moveCount;
				} else {
					chesspiece_black.add(new Knight("black", row, col, this, boards, movepins, p_board,
							chesspiece_black, chesspiece_white));
					chesspiece_black.get(chesspiece_black.size() - 1).movecount = moveCount;
					boards[row][col].add(chesspiece_black.get(chesspiece_black.size() - 1), "Center");
				}
			}

			int row = rs.getInt("b_queen_row");
			int col = rs.getInt("b_queen_col");
			int moveCount = rs.getInt("b_queen_move");

			if (row == -1) {
				chesspiece_black.add(new Queen("black", row, col, this, boards, movepins, p_board, chesspiece_black,
						chesspiece_white));
				chesspiece_black.get(chesspiece_black.size() - 1).setEnabled(false);
				chesspiece_black.get(chesspiece_black.size() - 1).movecount = moveCount;
			} else {
				chesspiece_black.add(new Queen("black", row, col, this, boards, movepins, p_board, chesspiece_black,
						chesspiece_white));
				chesspiece_black.get(chesspiece_black.size() - 1).movecount = moveCount;
				boards[row][col].add(chesspiece_black.get(chesspiece_black.size() - 1), "Center");
			}

			row = rs.getInt("b_king_row");
			col = rs.getInt("b_king_col");
			moveCount = rs.getInt("b_king_move");

			if (row == -1) {
				chesspiece_black.add(new King("black", row, col, this, boards, movepins, p_board, chesspiece_black,
						chesspiece_white));
				chesspiece_black.get(chesspiece_black.size() - 1).setEnabled(false);
				chesspiece_black.get(chesspiece_black.size() - 1).movecount = moveCount;
			} else {
				chesspiece_black.add(new King("black", row, col, this, boards, movepins, p_board, chesspiece_black,
						chesspiece_white));
				chesspiece_black.get(chesspiece_black.size() - 1).movecount = moveCount;
				boards[row][col].add(chesspiece_black.get(chesspiece_black.size() - 1), "Center");
			}

			// 블랙 체스말 이벤트
			for (int i = 0; i < chesspiece_black.size(); i++) {
				final int index = i;
				chesspiece_black.get(index).addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (turn.equals("black") && chesspiece_black.get(index).isEnabled()) {
							chesspiece_black.get(index).blackMove();
						}
					}
				});
			}
			this.validate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {

			}
		}
	}

	// 타이머 설정
	// 턴이 바뀔때마다 10초 추가 / 시간 다 끝나면 승리 패배 표시
	public void turnTimer() {
		if (turn.equals("white")) {
			b_remainingTime += 10;
			lb_b_timer.setText(formatTime(b_remainingTime));
			w_timer.start();
			b_timer.stop();

		} else if (turn.equals("black")) {
			w_remainingTime += 10;
			lb_w_timer.setText(formatTime(w_remainingTime));
			b_timer.start();
			w_timer.stop();

		}
	}

	public Timer blacktimer() {
		return new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				b_remainingTime--;
				lb_b_timer.setText(formatTime(b_remainingTime));
				if (b_remainingTime <= 0) {
					((Timer) e.getSource()).stop();
					JOptionPane.showMessageDialog(lb_b_timer, "white win!");
				}
			}
		});
	}

	public Timer whitetimer() {
		return new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				w_remainingTime--;
				lb_w_timer.setText(formatTime(w_remainingTime));
				if (w_remainingTime <= 0) {
					((Timer) e.getSource()).stop();
					JOptionPane.showMessageDialog(lb_w_timer, "black win!");
				}
			}
		});
	}

	private String formatTime(int seconds) {
		int minutes = seconds / 60;
		int secs = seconds % 60;
		return String.format("%02d:%02d", minutes, secs);
	}

	// 킹이 체크상태인지 확인하는 메서드
	public boolean isKingInCheck(String kingSide) {
		King king = null;
		ArrayList<ChessPiece> opponentPieces;

		// 상대 기물 리스트 설정
		if (kingSide.equals("white")) {
			opponentPieces = chesspiece_black;
		} else {
			opponentPieces = chesspiece_white;
		}

		// 킹 찾기
		if (kingSide.equals("white")) {
			for (int i = 0; i < chesspiece_white.size(); i++) {
				if (chesspiece_white.get(i) instanceof King) {
					king = (King) chesspiece_white.get(i);
					break;
				}
			}
		} else {
			for (int i = 0; i < chesspiece_black.size(); i++) {
				if (chesspiece_black.get(i) instanceof King) {
					king = (King) chesspiece_black.get(i);
					break;
				}
			}
		}

		if (king == null) {
			return false; // 킹이 없으면 체크 불가
		}

		// 상대 기물들의 공격 가능 위치 확인
		for (int i = 0; i < opponentPieces.size(); i++) {
			// 활성화가 되어있는 말만 적용되도록 해야함(아니면 죽은 말도 체크 여부에 포함됨)
			if (opponentPieces.get(i).isEnabled()) {
				opponentPieces.get(i).isAttackKing();
			}
		}

		// 킹의 아이콘이 공격 아이콘으로 변경되었는지 확인
		if (boards[king.row][king.col].getComponentCount() == 2) {
			if (kingSide.equals("white")) {
				if (((ImageIcon) ((ChessPiece) boards[king.row][king.col].getComponent(1)).getIcon()).getDescription()
						.equals("white_icon_attack")) {
					((ChessPiece) boards[king.row][king.col].getComponent(1))
							.setIcon(((ChessPiece) boards[king.row][king.col].getComponent(1)).white_icon);
					return true; // 화이트 킹이 체크 상태
				}
			} else {
				if (((ImageIcon) ((ChessPiece) boards[king.row][king.col].getComponent(1)).getIcon()).getDescription()
						.equals("black_icon_attack")) {
					((ChessPiece) boards[king.row][king.col].getComponent(1))
							.setIcon(((ChessPiece) boards[king.row][king.col].getComponent(1)).black_icon);
					return true; // 블랙 킹이 체크 상태
				}
			}

		}
		return false;
	}

	// 체크 상태가 되면 화면 업데이트 메서드
	// 체크 상태에서 이동이 불가능하면 체크메이트 선언
	// 체크가 아닌데 이동이 불가능하면 스테일메이트 선언
	public void updateCheckStatus() {
		if (b_remainingTime == 0 || w_remainingTime == 0) {
			if (b_remainingTime == 0) {
				lb_check.setText("white win!");
			} else if (w_remainingTime == 0) {
				lb_check.setText("black win!");
			}
		} else if (isKingInCheck("white")) {
			if (!isCanMove("white")) {
				lb_check.setText("White Checkmate!");
			} else {
				lb_check.setText("White King in Check!");
			}
		} else if (isKingInCheck("black")) {
			if (!isCanMove("black")) {
				lb_check.setText("Black Checkmate!");
			} else {
				lb_check.setText("Black King in Check!");
			}
		} else {
			if (!isCanMove("black") || !isCanMove("white")) {
				lb_check.setText("Stalemate!");
			} else {
				lb_check.setText("Play");
			}
		}
		// 확인을 위해 깔아놨던 무브핀들 제거
		for (int i = 0; i < chesspiece_white.size(); i++) {
			chesspiece_white.get(i).movepinsNotVisible();
		}
		for (int i = 0; i < chesspiece_black.size(); i++) {
			chesspiece_black.get(i).movepinsNotVisible();
		}

		// UI 즉시 새로고침
		lb_check.repaint();
		this.validate();
		this.repaint();
	}

	// 체크해제를 위해 이동이 가능한지 확인 메서드
	public boolean isCanMove(String kingSide) {
		int movepincount = 0;
		int attackcount = 0;

		// 화이트 확인
		if (kingSide.equals("white")) {
			// 화이트 기물 중 활성화 되어있는 기물의 체크 해제가 가능한 무브핀을 체크
			for (int z = 0; z < chesspiece_white.size(); z++) {
				if (chesspiece_white.get(z).isEnabled()) {
					chesspiece_white.get(z).whiteMove();
				}
				// 무브핀이 깔린 후 무브핀을 카운트
				for (int i = 1; i <= 8; i++) {
					for (int j = 1; j <= 8; j++) {
						if (movepins[i][j].isVisible()) {
							movepincount++;
						}
						// 체크 해제가 가능한 공격 아이콘을 카운트
						if (boards[i][j].getComponentCount() == 2) {
							if (((ImageIcon) ((ChessPiece) boards[i][j].getComponent(1)).getIcon()).getDescription()
									.equals("black_icon_attack")) {
								attackcount++;
							}
							// 공격 아이콘 기본 아이콘으로 복구
							if (((ChessPiece) boards[i][j].getComponent(1)).side.equals("black")) {
								((ChessPiece) boards[i][j].getComponent(1))
										.setIcon(((ChessPiece) boards[i][j].getComponent(1)).black_icon);
							} else {
								((ChessPiece) boards[i][j].getComponent(1))
										.setIcon(((ChessPiece) boards[i][j].getComponent(1)).white_icon);
							}
						}
					}
				}
			}
			// 확인 후 각 기물의 공격 리스너들 제거
			for (int i = 0; i < chesspiece_black.size(); i++) {
				chesspiece_black.get(i).removeAttackBlack();
			}
			for (int i = 0; i < chesspiece_white.size(); i++) {
				chesspiece_white.get(i).removeAttackWhite();
				;
			}

			// 무브핀과 공격아이콘이 전혀 카운트되어 있지 않으면 true(이동불가) 상태를 반환
			if (movepincount == 0 && attackcount == 0) {
				return false;
			} else {
				return true;
			}
			// 블랙 확인
		} else if (kingSide.equals("black")) {
			// 블랙 기물 중 활성화 되어있는 기물의 체크 해제가 가능한 무브핀을 체크
			for (int z = 0; z < chesspiece_black.size(); z++) {
				if (chesspiece_black.get(z).isEnabled()) {
					chesspiece_black.get(z).blackMove();
				}
				// 무브핀이 깔린 후 무브핀을 카운트
				for (int i = 1; i <= 8; i++) {
					for (int j = 1; j <= 8; j++) {
						if (movepins[i][j].isVisible()) {
							movepincount++;
						}
						// 체크 해제가 가능한 공격 아이콘을 카운트
						if (boards[i][j].getComponentCount() == 2) {
							if (((ImageIcon) ((ChessPiece) boards[i][j].getComponent(1)).getIcon()).getDescription()
									.equals("white_icon_attack")) {
								attackcount++;
							}
							// 공격 아이콘 기본 아이콘으로 복구
							if (((ChessPiece) boards[i][j].getComponent(1)).side.equals("white")) {
								((ChessPiece) boards[i][j].getComponent(1))
										.setIcon(((ChessPiece) boards[i][j].getComponent(1)).white_icon);
							} else {
								((ChessPiece) boards[i][j].getComponent(1))
										.setIcon(((ChessPiece) boards[i][j].getComponent(1)).black_icon);
							}
						}
					}
				}
			}

			// 확인 후 각 기물의 공격 리스너들 제거
			for (int i = 0; i < chesspiece_black.size(); i++) {
				chesspiece_black.get(i).removeAttackBlack();
			}
			for (int i = 0; i < chesspiece_white.size(); i++) {
				chesspiece_white.get(i).removeAttackWhite();
				;
			}
			// 무브핀과 공격아이콘이 전혀 카운트되어 있지 않으면 true(이동불가) 상태를 반환
			if (movepincount == 0 && attackcount == 0) {
				return false;
			} else {
				return true;
			}
		}
		return true;
	}

	// DB 연결 전용 메서드
	public void setDB() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "chess";
			String pwd = "1234";

			conn = DriverManager.getConnection(url, user, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 게임 첫 시작 시 게임 정보 DB 기록 메서드
	public void setGameInfo() {
		try {
			setDB();
			Calendar now = Calendar.getInstance();
			java.sql.Timestamp jst = new java.sql.Timestamp(now.getTimeInMillis());
			String sql = "insert into history values(history_gameno_sq.nextval,'playing',?)";
			ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, jst);
			ps.executeUpdate();

			sql = "select gameno from history order by gameno";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				gameno = rs.getInt("gameno");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
			}
		}

	}

	// 화이트 기물 이동 시 DB 저장 메서드
	public void insertGamePlayWhite() {
		try {
			setDB();
			String sql = "insert into white values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, gameno);
			ps.setInt(2, turn_count);
			ps.setInt(3, w_remainingTime);
			int j = 4;
			for (int i = 0; i < chesspiece_white.size(); i++) {
				if (chesspiece_white.get(i).isEnabled()) {
					if (i < 8) {
						ps.setInt(j, chesspiece_white.get(i).row);
						j++;
						ps.setInt(j, chesspiece_white.get(i).col);
						j++;
						ps.setInt(j, chesspiece_white.get(i).movecount);
						j++;
						if (chesspiece_white.get(i) instanceof Pawn) {
							ps.setString(j, "pawn");
							j++;
						} else {
							String promotion = chesspiece_white.get(i).getClass().getSimpleName();
							ps.setString(j, promotion);
							j++;
						}
					} else {
						ps.setInt(j, chesspiece_white.get(i).row);
						j++;
						ps.setInt(j, chesspiece_white.get(i).col);
						j++;
						ps.setInt(j, chesspiece_white.get(i).movecount);
						j++;
					}
				} else {
					if (chesspiece_white.get(i) instanceof Pawn) {
						ps.setInt(j, -1);
						j++;
						ps.setInt(j, -1);
						j++;
						ps.setInt(j, -1);
						j++;
						ps.setString(j, "death");
						j++;
					} else {
						ps.setInt(j, -1);
						j++;
						ps.setInt(j, -1);
						j++;
						ps.setInt(j, -1);
						j++;
					}

				}
			}

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {

			}
		}
	}
	// 블랙 기물 이동 시 DB 저장 메서드
	public void insertGamePlayBlack() {
		try {
			setDB();
			String sql = "insert into black values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, gameno);
			ps.setInt(2, turn_count);
			ps.setInt(3, b_remainingTime);
			int j = 4;
			for (int i = 0; i < chesspiece_black.size(); i++) {
				if (chesspiece_black.get(i).isEnabled()) {
					if (i < 8) {
						ps.setInt(j, chesspiece_black.get(i).row);
						j++;
						ps.setInt(j, chesspiece_black.get(i).col);
						j++;
						ps.setInt(j, chesspiece_black.get(i).movecount);
						j++;
						if (chesspiece_black.get(i) instanceof Pawn) {
							ps.setString(j, "pawn");
							j++;
						} else {
							String promotion = chesspiece_black.get(i).getClass().getSimpleName();
							ps.setString(j, promotion);
							j++;
						}
					} else {
						ps.setInt(j, chesspiece_black.get(i).row);
						j++;
						ps.setInt(j, chesspiece_black.get(i).col);
						j++;
						ps.setInt(j, chesspiece_black.get(i).movecount);
						j++;
					}
				} else {
					if (chesspiece_black.get(i) instanceof Pawn) {
						ps.setInt(j, -1);
						j++;
						ps.setInt(j, -1);
						j++;
						ps.setInt(j, -1);
						j++;
						ps.setString(j, "death");
						j++;
					} else {
						ps.setInt(j, -1);
						j++;
						ps.setInt(j, -1);
						j++;
						ps.setInt(j, -1);
						j++;
					}

				}
			}

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {

			}
		}
	}

	// 이동 경로 불러와 색상 다시 칠하는 메서드
	public void loadMoveBoard() {
		try {
			setDB();

			String sql = "select * from move_board where gameno=? and turn=?";
			ps = conn.prepareStatement(sql);

			ps.setInt(1, gameno);
			ps.setInt(2, turn_count);

			rs = ps.executeQuery();
			rs.next();
			
			int row1=rs.getInt("row1");
			int col1=rs.getInt("col1");
			int row2=rs.getInt("row2");
			int col2=rs.getInt("col2");
			
			if(row1>0) {
				setMoveBoard(row1, col1, row2, col2);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {

			}
		}

	}

	// 기물 이동 시 원래 위치와 이동한 위치를 색상으로 표시하는 메서드
	public void setMoveBoard(int originalrow, int originalcol, int moverow, int movecol) {
		// 보드를 기본 색상으로 초기화
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				if (i % 2 == 0) {
					if (j % 2 == 0) {
						boards[i][j].setBackground(Color.white);
					} else {
						boards[i][j].setBackground(Color.darkGray);
					}
				} else {
					if (j % 2 == 0) {
						boards[i][j].setBackground(Color.darkGray);
					} else {
						boards[i][j].setBackground(Color.white);
					}
				}
			}
		}

		// 원래 있던 위치 색 변경
		if (boards[originalrow][originalcol].getBackground().equals(Color.white)) {
			boards[originalrow][originalcol].setBackground(new Color(209, 254, 164));
		} else {
			boards[originalrow][originalcol].setBackground(new Color(124, 188, 0));
		}

		// 이동한 위치 색 변경
		if (boards[moverow][movecol].getBackground().equals(Color.white)) {
			boards[moverow][movecol].setBackground(new Color(209, 254, 164));
		} else {
			boards[moverow][movecol].setBackground(new Color(124, 188, 0));
		}
	}

	// 이동 위치 색상이 어딘지 DB 저장 메서드
	public void insertMoveBoard() {
		try {
			int row1 = 0;
			int col1 = 0;
			int row2 = 0;
			int col2 = 0;

			Color c1 = new Color(209, 254, 164);
			Color c2 = new Color(124, 188, 0);
			int count = 0;
			for (int i = 1; i <= 8; i++) {
				for (int j = 1; j <= 8; j++) {
					if (boards[i][j].getBackground().equals(c1) || boards[i][j].getBackground().equals(c2)) {
						count++;
						if (count == 1) {
							row1 = i;
							col1 = j;
						} else if (count == 2) {
							row2 = i;
							col2 = j;
						}
					}
				}
			}

			setDB();
			String sql = "insert into move_board values(?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, gameno);
			ps.setInt(2, turn_count);
			ps.setInt(3, row1);
			ps.setInt(4, col1);
			ps.setInt(5, row2);
			ps.setInt(6, col2);

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {

			}
		}

	}

	public static void main(String[] args) throws Exception {

		System.setProperty("sun.java2d.uiScale", "1");
		new ChessBoard();

	}

}