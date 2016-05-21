package org.liukan.mgraph.ui;

import javax.swing.JPanel;

import org.liukan.mgraph.mgraphxEx;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class mgraphui extends JPanel {

	/**
	 * Create the panel.
	 */
	public mgraphui() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		
		JPanel panel_button = new JPanel();
		add(panel_button, BorderLayout.SOUTH);
		
		JButton btnNewNodeButton = new JButton("添加节点");
		panel_button.add(btnNewNodeButton);
		
		JButton btnNewEdgeButton = new JButton("添加边");
		panel_button.add(btnNewEdgeButton);
		
		JButton btnDelButton = new JButton("删除选中对象");
		panel_button.add(btnDelButton);
		
		JMenuBar menuBar = new JMenuBar();
		panel_button.add(menuBar);
		
		JMenu Menu = new JMenu("自动布局");
		menuBar.add(Menu);
		
		JMenuItem menuVx = new JMenuItem("垂直布局");
		Menu.add(menuVx);

	}
	public static void main(String[] args)
	{
		JFrame  frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		mgraphui c=new mgraphui();
	
		c.setSize(580, 800);
		//frame.getContentPane().add(c,BorderLayout.CENTER);
		//frame.getContentPane().add(new JButton("OK"),BorderLayout.SOUTH);
		frame.getContentPane().add(c,BorderLayout.CENTER);
		frame.pack();
		//c.setSize(1000, 720);
		frame.setSize(600, 820);
        frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
