package testNgj;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class popup extends JFrame{
	JMenu menu;
	JMenuBar menubar;
	JMenuItem a,b,c,d;
	
	public popup() {
		
		this.add(menubar);
		menubar.add(menu);
		menu.add(a);
		menu.add(b);
		menu.add(c);
		menu.add(d);
		
		a = new JMenuItem("회원정보 등록");
		b = new JMenuItem("회원정보 수정");
		c = new JMenuItem("책 정보 등록");
		d = new JMenuItem("책 정보 삭제");
	}
	
    public static void main(String[] args) {
	popup p=new popup();
	p.setSize(800,800);
	p.setVisible(true);
	
    }
}