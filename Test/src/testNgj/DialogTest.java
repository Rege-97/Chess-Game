package testNgj;

import javax.swing.JOptionPane;

public class DialogTest {
    public static void main(String[] args) {
        // 메시지 다이얼로그
        JOptionPane.showMessageDialog(null, "This is a message", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
        
        // 입력 다이얼로그
        String input = JOptionPane.showInputDialog(null, "Enter something:");
        System.out.println("User input: " + input);
        
        // 옵션 다이얼로그
        int option = JOptionPane.showOptionDialog(null, 
                                                  "Select an option",
                                                  "Option Dialog",
                                                  JOptionPane.DEFAULT_OPTION,
                                                  JOptionPane.INFORMATION_MESSAGE,
                                                  null, 
                                                  new String[] {"Option 1", "Option 2", "Option 3"}, 
                                                  "Option 1");
        System.out.println("Selected option: " + option);
        
        // 확인 다이얼로그
        int result = JOptionPane.showConfirmDialog(null, "Do you confirm?", "Confirm Dialog", JOptionPane.YES_NO_OPTION);
        System.out.println("User confirmation: " + (result == JOptionPane.YES_OPTION ? "Yes" : "No"));
    }
}
