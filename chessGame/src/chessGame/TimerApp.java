package chessGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerApp {
    private static final int TIMER_DURATION = 15 * 60; // 15분 (초)
    private int remainingTime = TIMER_DURATION;
    private JLabel timerLabel;
    private Timer timer;

    public TimerApp() {
        JFrame frame = new JFrame("15분 타이머");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new BorderLayout());

        timerLabel = new JLabel(formatTime(remainingTime), SwingConstants.CENTER);
        timerLabel.setFont(new Font("Serif", Font.BOLD, 32));
        frame.add(timerLabel, BorderLayout.CENTER);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTime--;
                timerLabel.setText(formatTime(remainingTime));
                if (remainingTime <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(frame, "타이머가 종료되었습니다!");
                }
            }
        });

        frame.setVisible(true);
        timer.start();
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TimerApp();
            }
        });
    }
}
