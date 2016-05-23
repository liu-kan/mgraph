package org.liukan.mgraph;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.CellRendererPane;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.liukan.mgraph.ui.mesDlgAddEdge;

import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxKeyboardHandler;
import com.mxgraph.swing.handler.mxRubberband;
import com.mxgraph.swing.view.mxInteractiveCanvas;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

public class mgraphxEx extends JPanel 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -844106998814982739L;
	protected boolean hintAddEdge;

	public mgraphxEx()
	{
		super();
		hintAddEdge=true;
		setLayout(new BorderLayout()); 
		mgraphx gpanel = new mgraphx(false);
		add(gpanel, BorderLayout.CENTER);
		
		JPanel panel_button = new JPanel();
		add(panel_button, BorderLayout.SOUTH);
		
		JButton btnNewNodeButton = new JButton("进入添加节点模式");
		btnNewNodeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (btnNewNodeButton.getText().equals("进入添加节点模式")) {
					gpanel.setMouseModeAddNode(true);
					gpanel.removeSelectionCells();
					btnNewNodeButton.setText("进入浏览模式");
				} else {
					gpanel.setMouseModeAddNode(false);
					btnNewNodeButton.setText("进入添加节点模式");
				}
			}

		});
		panel_button.add(btnNewNodeButton);
		
		JButton btnNewEdgeButton = new JButton("点击添加边");
		btnNewEdgeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				if(hintAddEdge){
					mesDlgAddEdge dialog=new mesDlgAddEdge();
					final Toolkit toolkit = Toolkit.getDefaultToolkit();
					final Dimension screenSize = toolkit.getScreenSize();
					final int x = (screenSize.width - dialog.getWidth()) / 2;
					final int y = (screenSize.height - dialog.getHeight()) / 2;
					dialog.setLocation(x, y);
					dialog.setVisible(true);			
					if(dialog.chckbx.isSelected())
						hintAddEdge=false;
				}
				gpanel.selectTowNodes();			
			}
		});
		panel_button.add(btnNewEdgeButton);
		
		JButton btnDelButton = new JButton("删除选中对象");
		btnDelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {				
				gpanel.removeSelectedCells();				
			}
		});
		panel_button.add(btnDelButton);
		
		JMenuBar menuBar = new JMenuBar();
		panel_button.add(menuBar);
		
		JMenu Menu = new JMenu("自动布局");
		menuBar.add(Menu);
		
		JMenuItem menuVx = new JMenuItem("垂直布局");
		menuVx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gpanel.hLayout();
				gpanel.centerGraph();
			}
		});
		Menu.add(menuVx);
		JMenuItem menuCircle = new JMenuItem("环形布局");
		menuCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gpanel.cLayout();
				gpanel.centerGraph();
			}
		});
		Menu.add(menuCircle);
		JMenuItem menuTree= new JMenuItem("树布局");
		menuTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gpanel.tLayout();
				gpanel.centerGraph();
			}
		});
		Menu.add(menuTree);
		JMenuItem menuFO= new JMenuItem("快速退火布局");
		menuFO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gpanel.foLayout();
				gpanel.centerGraph();
			}
		});
		Menu.add(menuFO);
		JMenuItem menuO= new JMenuItem("退火布局");
		menuO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gpanel.oLayout();
				gpanel.centerGraph();
			}
		});
		Menu.add(menuO);		
		
		JMenuItem menuS= new JMenuItem("堆栈布局");
		menuS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gpanel.sLayout();
				gpanel.centerGraph();
			}
		});
		Menu.add(menuS);	
		
		JMenuItem menuOr= new JMenuItem("正交布局");
		menuOr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gpanel.orLayout();
				gpanel.centerGraph();
			}
		});
		Menu.add(menuOr);
	}

	public static void main(String[] args)
	{
		JFrame  frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		mgraphxEx c=new mgraphxEx();
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
