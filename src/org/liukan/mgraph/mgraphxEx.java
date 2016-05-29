/*
 * 
 */
package org.liukan.mgraph;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import org.liukan.mgraph.ui.mesDlgAddEdge;
import org.liukan.mgraph.util.dbIO;

// TODO: Auto-generated Javadoc
/**
 * 继承JPanel并包含一个mgraphx控件，另外在底部配备常用的进行图编辑的按钮.
 *
 * @author liukan
 * <a href="mailto:liukan@126.com">liukan@126.com</a>
 * @version 0.1
 * @see mgraphx
 */
public class mgraphxEx extends JPanel 
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -844106998814982739L;
	
	/** The hint add edge. */
	protected boolean hintAddEdge;

	/** 被用来进行绘图的mgraphx控件. */	
	public mgraphx gpanel;
	
	/**
	 * Instantiates a new mgraphx ex.
	 */
	public mgraphxEx()
	{
		super();
		hintAddEdge=true;
		setLayout(new BorderLayout()); 
		gpanel = new mgraphx(false);
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
/**
 * 点击后可添加边		
 */
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

/**
 * 测试时使用，该控件的使用者无需理会.
 *
 * @param args the arguments
 */
	public static void main(String[] args)
	{
		JFrame  frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		mgraphxEx c=new mgraphxEx();
		c.setSize(580, 800);
		
		JPanel panel_button = new JPanel();
		frame.add(panel_button, BorderLayout.SOUTH);
		
		JButton btnNewNodeButton = new JButton("readdb");
		btnNewNodeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 try {
				    	dbIO dbio=new dbIO("org.sqlite.JDBC","jdbc:sqlite:db.sqlite",null,null);	      
				    	//dbio.readGraph(1,c.gpanel);
				    	c.gpanel.readGfromDB(dbio,2);
				    	
				    	//c.gpanel.hLayout();
				    	//c.gpanel.centerGraph();
				    	dbio.close();
				    	//dbio=null;
				    	//System.gc(); 
				    } catch ( Exception e ) {
				      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				      System.exit(0);
				    }
			}

		});
		panel_button.add(btnNewNodeButton);
		
		JButton btnSaveButton = new JButton("savedb");
		btnSaveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 try {
				    	dbIO dbio=new dbIO("org.sqlite.JDBC","jdbc:sqlite:db.sqlite",null,null);	      
				    	//dbio.readGraph(1,c.gpanel);
				    	c.gpanel.saveG2DB("hoho",0,dbio);
				    	
				    	//c.gpanel.hLayout();
				    	//c.gpanel.centerGraph();
				    	dbio.close();
				    	//dbio=null;
				    	//System.gc(); 
				    } catch ( Exception e ) {
				      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				      System.exit(0);
				    }
			}

		});
		panel_button.add(btnSaveButton);
		frame.getContentPane().add(c,BorderLayout.CENTER);
		frame.pack();
		//c.setSize(1000, 720);
		frame.setSize(600, 820);
        frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
