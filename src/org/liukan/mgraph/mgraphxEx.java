/*
 * 
 */
package org.liukan.mgraph;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

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

	private Locale currLocale;

	private ResourceBundle messagesRes;

	private JButton btnNewNodeButton;

	private JButton btnNewEdgeButton;

	private JButton btnDelButton;

	private JMenuBar menuBar;
	
	/**
	 * Instantiates a new mgraphx ex.<br>
	 * 等于调用 mgraphxEx(false,22,45,false);
	 */
	public mgraphxEx(){
		this(false,22,45,false,null);
	}
	private String i18n(String s){
		return messagesRes.getString(s);
	}
	public mgraphxEx(Locale _currLocale){
		this(false,22,45,false,_currLocale);
	}
	public mgraphxEx(boolean _nodesConnectable,int _edgeFontSize, int _nodeFontSize
			,boolean _centerNode){
		this(_nodesConnectable, _edgeFontSize,  _nodeFontSize
				, _centerNode,null);
	}
	/**
	 * mgraphxEx构造函数
	* @param _nodesConnectable 设置是否可以通过鼠标直接点击节点添加边
	 * @param _edgeFontSize 设置边上标签的字体大小
	 * @param _nodeFontSize 设置节点标签的字体大小
	 * @param _centerNode 设置节点坐标是否是节点的中心位置
	 * @param _currLocale Locale
	 * @see mgraphx
	 */
	public mgraphxEx(boolean _nodesConnectable,int _edgeFontSize, int _nodeFontSize
			,boolean _centerNode, Locale _currLocale)
	{
		super();
		if(_currLocale==null)
			currLocale = Locale.getDefault();
		else
			currLocale = _currLocale;        
        messagesRes = ResourceBundle.getBundle("org.liukan.mgraph.ui.i18n.MessagesBundle", currLocale);
		hintAddEdge=true;
		setLayout(new BorderLayout()); 
		gpanel = new mgraphx(_nodesConnectable, _edgeFontSize,  _nodeFontSize
				, _centerNode,currLocale);
		add(gpanel, BorderLayout.CENTER);
		
		JPanel panel_button = new JPanel();
		add(panel_button, BorderLayout.SOUTH);
		
		btnNewNodeButton = new JButton(messagesRes.getString("addNodeMode"));
		btnNewNodeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (btnNewNodeButton.getText().equals(messagesRes.getString("addNodeMode"))) {
					gpanel.setMouseModeAddNode(true);
					gpanel.removeSelectionCells();
					btnNewNodeButton.setText(messagesRes.getString("browserMode"));
				} else {
					gpanel.setMouseModeAddNode(false);
					btnNewNodeButton.setText(messagesRes.getString("addNodeMode"));
				}
			}

		});
		panel_button.add(btnNewNodeButton);
		
		btnNewEdgeButton = new JButton(messagesRes.getString("addEdge"));
/**
 * 点击后可添加边		
 */
		btnNewEdgeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				if(hintAddEdge){
					mesDlgAddEdge dialog=new mesDlgAddEdge(messagesRes);
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
		
		btnDelButton = new JButton(i18n("delObj"));
		btnDelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {				
				gpanel.removeSelectedCells();				
			}
		});
		panel_button.add(btnDelButton);
		
		menuBar = new JMenuBar();
		panel_button.add(menuBar);
		
		JMenu Menu = new JMenu(i18n("autoLayout"));
		menuBar.add(Menu);
		
		JMenuItem menuVx = new JMenuItem(i18n("hLayout"));
		menuVx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gpanel.hLayout();
				gpanel.centerGraph();
			}
		});
		Menu.add(menuVx);
		JMenuItem menuCircle = new JMenuItem(i18n("cLayout"));
		menuCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gpanel.cLayout();
				gpanel.centerGraph();
			}
		});
		Menu.add(menuCircle);
		JMenuItem menuTree= new JMenuItem(i18n("tLayout"));
		menuTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gpanel.tLayout();
				gpanel.centerGraph();
			}
		});
		Menu.add(menuTree);
		JMenuItem menuFO= new JMenuItem(i18n("foLayout"));
		menuFO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gpanel.foLayout();
				gpanel.centerGraph();
			}
		});
		Menu.add(menuFO);
		JMenuItem menuO= new JMenuItem(i18n("oLayout"));
		menuO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gpanel.oLayout();
				gpanel.centerGraph();
			}
		});
		Menu.add(menuO);		
		
		JMenuItem menuS= new JMenuItem(i18n("sLayout"));
		menuS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gpanel.sLayout();
				gpanel.centerGraph();
			}
		});
		Menu.add(menuS);	
		
		JMenuItem menuOr= new JMenuItem(i18n("orLayout"));
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
		JFrame  frame = new JFrame("XPLOR-NIH交联脚本生成器");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		Locale cl=new Locale("en", "US");
		mgraphxEx c=new mgraphxEx(false,22,45,false,cl);
		c.setSize(580, 800);
		
		JPanel panel_button = new JPanel();
		frame.add(panel_button, BorderLayout.SOUTH);
		
		JButton btnNewNodeButton = new JButton("生成脚本");
		btnNewNodeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 try {
				    	dbIO dbio=new dbIO("org.sqlite.JDBC","jdbc:sqlite:db.sqlite",null,null);	      
				    	//dbio.readGraph(1,c.gpanel);
				    	c.gpanel.readGfromDB(dbio,1);
				    	
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
		
		JButton btnSaveButton = new JButton("运行XPLOR");
		btnSaveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 try {
				    	dbIO dbio=new dbIO("org.sqlite.JDBC","jdbc:sqlite:db.sqlite",null,null);	      
				    	//dbio.readGraph(1,c.gpanel);
				    	c.gpanel.saveG2DB("hoho",1,dbio);
				    	
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
		JButton delButton = new JButton("删除");
		delButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 try {
				    	dbIO dbio=new dbIO("org.sqlite.JDBC","jdbc:sqlite:db.sqlite",null,null);	      
				    	//dbio.readGraph(1,c.gpanel);
				    	dbio.delGinDB(1);
				    	
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
		panel_button.add(delButton);
		frame.getContentPane().add(c,BorderLayout.CENTER);
		frame.pack();
		//c.setSize(1000, 720);
		frame.setSize(600, 820);
        frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	@Override
	public void setEnabled(boolean e){
		btnNewNodeButton.setEnabled(e);
		btnNewEdgeButton.setEnabled(e);
		btnDelButton.setEnabled(e);
		menuBar.setEnabled(e);
		super.setEnabled(e);
		
	}
}
