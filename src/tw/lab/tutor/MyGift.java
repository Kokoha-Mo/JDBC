package tw.lab.tutor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import tw.lab.apis.GiftTable;

public class MyGift extends JFrame{
	private GiftTable giftTable;
	private JButton del;
	public MyGift() {
		super("伴手禮");
		
		setLayout(new BorderLayout());
		giftTable = new GiftTable();
		JScrollPane jsp = new JScrollPane(giftTable); 
		add(jsp,BorderLayout.CENTER);
		
		del = new JButton("刪除");
		JPanel top = new JPanel(new FlowLayout());
		top.add(del);
		add(top,BorderLayout.NORTH);
		
		del.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				giftTable.delData();	
			}
		});
		
		setSize(640,480);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		new MyGift();

	}

}
