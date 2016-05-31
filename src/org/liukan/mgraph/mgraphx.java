/*
 * 
 */
package org.liukan.mgraph;
/**
* @author liukan
* <a href="mailto:liukan@126.com">liukan@126.com</a>
*  @version 0.1
*/

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.Font;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.CellRendererPane;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

//import org.liukan.mgraph.CustomCanvas.SwingCanvas;
import org.liukan.mgraph.ui.editEdge;
import org.liukan.mgraph.ui.editNote;
import org.liukan.mgraph.util.dbIO;
import org.liukan.mgraph.util.strParts;
import org.liukan.mgraph.util.strUtil;

import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxEdgeLabelLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.layout.mxStackLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.orthogonal.mxOrthogonalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxKeyboardHandler;
import com.mxgraph.swing.handler.mxRubberband;
import com.mxgraph.swing.view.mxInteractiveCanvas;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

// TODO: Auto-generated Javadoc
/**
 * 继承JPanel并在其上绘图.
 *
 * @author liukan
 * <a href="mailto:liukan@126.com">liukan@126.com</a>
 * @version 0.1
 */
public class mgraphx extends JPanel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -844106998814982739L;
	
	/** The nodes connectable. */
	private boolean mouseModeAddNode,mouseModeAddEdge,nodesConnectable;
	
	/** The node font size. */
	private int edgeFontSize, nodeFontSize;
	
	/** The graph. */
	private mxGraph graph ;
	
	/** The dy. */
	public double dx,dy;
	
	/** The tmp cell list. */
	ArrayList<mxCell> tmpCellList;
	
	/** The parent. */
	Object parent;
	
	/** The graph component. */
	mxGraphComponent graphComponent;
	
	/** The selected node num. */
	private int selectedNodeNum;
	
	private boolean centerNode;

	/**
	 * Gets the graph x.
	 *
	 * @return the graph x
	 */
	public mxGraph getGraphX(){
		return graph;
	}
	
	/**
	 * setupGraphStyle 用来设置图像的风格和字体大小.
	 *
	 * @param _edgeFontSize 设置边上标签的字体大小
	 * @param _nodeFontSize 设置节点标签的字体大小
	 */
	public void setupGraphStyle(int _edgeFontSize, int _nodeFontSize){
		mxStylesheet stylesheet = new mxStylesheet();
		edgeFontSize=_edgeFontSize;
		nodeFontSize=_nodeFontSize;
		mxConstants.DEFAULT_FONTSIZE = edgeFontSize;
		setupGraph(stylesheet);
		graph.setStylesheet(stylesheet);
	}
	
	/**
	 * Sets the up graph.
	 *
	 * @param stylesheet the new up graph
	 */
	private void setupGraph(mxStylesheet stylesheet) {
		// graph setup
		
		Map<String, Object> edgeStyle = stylesheet.getDefaultEdgeStyle();
		// edgeStyle.put(mxConstants.STYLE_NOLABEL, "1");
		// edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "000000");
		Object styleEdge = mxConstants.EDGESTYLE_TOPTOBOTTOM;
		//edgeStyle.put(mxConstants.STYLE_EDGE, styleEdge);
		// edgeStyle.put(mxConstants.STYLE_SHAPE, STYLE_SHAPE);
		edgeStyle.put(mxConstants.STYLE_ROUNDED, "1");
		//edgeStyle.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ENTITY_RELATION);

		// NODE STYLE
		Map<String, Object> processStyle = stylesheet.getDefaultVertexStyle();
		// Hashtable<String, Object> processStyle = new Hashtable<>();
		processStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
		processStyle.put(mxConstants.STYLE_OPACITY, 80);
		processStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
		processStyle.put(mxConstants.STYLE_FILLCOLOR, "#ffffff");
		processStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000");
		processStyle.put(mxConstants.STYLE_FONTSIZE, nodeFontSize);
		//processStyle.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
		processStyle.put(mxConstants.STYLE_GRADIENTCOLOR, "#f6f6f6");
		processStyle.put(mxConstants.STYLE_SPACING_TOP, nodeFontSize/4);
		//processStyle.put(mxConstants.STYLE_SPACING_BOTTOM, 2);
		//processStyle.put(mxConstants.STYLE_SPACING_LEFT, 2);
		//processStyle.put(mxConstants.STYLE_SPACING_RIGHT, 2);
		processStyle.put(mxConstants.STYLE_VERTICAL_ALIGN  , mxConstants.ALIGN_TOP);
		// stylesheet.putCellStyle(STYLENAME_PROCESS, processStyle);
		// graph.setStylesheet(stylesheet);
		
	}
	
	/**
	 * mgraphx 构造函数.
	 *
	 * @param _nodesConnectable 设置是否可以通过鼠标直接点击节点添加边
	 *  
	 */
	public mgraphx(boolean _nodesConnectable){
		this(_nodesConnectable,22,45,false);
	}
	
	/**
	 * mgraphx 构造函数.
	 *
	 * @param _nodesConnectable 设置是否可以通过鼠标直接点击节点添加边
	 * @param _edgeFontSize 设置边上标签的字体大小
	 * @param _nodeFontSize 设置节点标签的字体大小
	 * @param _centerNode 图中的节点坐标是否是节点的中心而不是左上角
	 * 
	 */
	public mgraphx(boolean _nodesConnectable,int _edgeFontSize, int _nodeFontSize
			,boolean _centerNode) {
		super();
		centerNode=_centerNode;
		edgeFontSize=_edgeFontSize;
		nodeFontSize=_nodeFontSize;
		dx=0;dy=0;
		selectedNodeNum=0;
		graph=null;
		mouseModeAddNode = false;
		mouseModeAddEdge=false;
		nodesConnectable=_nodesConnectable;
		tmpCellList=new ArrayList<mxCell>();
		setLayout(new BorderLayout());
		mxConstants.DEFAULT_FONTSIZE = edgeFontSize;
		mxStylesheet stylesheet = new mxStylesheet();
		setupGraph(stylesheet);
		graph = new mxGraph(stylesheet) {
			public void drawState(mxICanvas canvas, mxCellState state, boolean drawLabel) {
				/*String label = (drawLabel) ? state.getLabel() : "";
				if (getModel().isVertex(state.getCell())
						&& canvas instanceof mxImageCanvas
						&& ((mxImageCanvas) canvas).getGraphicsCanvas() instanceof SwingCanvas)
				{
					((SwingCanvas) ((mxImageCanvas) canvas).getGraphicsCanvas())
							.drawVertex(state, label);
				}
				// Redirection of drawing vertices in SwingCanvas
				else if (getModel().isVertex(state.getCell())
						&& canvas instanceof SwingCanvas)
				{
					((SwingCanvas) canvas).drawVertex(state, label);
				}else*/
				{
					super.drawState(canvas, state, drawLabel);
				}
			}
		};
		//
		parent = graph.getDefaultParent();
		graph.setAutoSizeCells(true);
		graph.getModel().beginUpdate();
		try {
			
		} finally {
			graph.getModel().endUpdate();
		}

		graphComponent = new mxGraphComponent(graph) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4683716829748931448L;

			public mxInteractiveCanvas createCanvas() {
				return new SwingCanvas(this);
			}
		};

		add(graphComponent);


		// Adds rubberband selection
		new mxRubberband(graphComponent);
		new mxKeyboardHandler(graphComponent);
		graphComponent.setConnectable(nodesConnectable);
		
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				Object cell = graphComponent.getCellAt(e.getX(), e.getY());
				if (cell != null) {
					//System.out.println("cell=" + graph.getLabel(cell));
					if(mouseModeAddEdge){
						mxCell mc=(mxCell)cell;
						if(mc.isVertex()){
							if(selectedNodeNum<2){
								selectedNodeNum++;
								tmpCellList.add(mc);
								if(selectedNodeNum==2){
									editEdge dialog = new editEdge(graph,tmpCellList);	
									final Toolkit toolkit = Toolkit.getDefaultToolkit();
									final Dimension screenSize = toolkit.getScreenSize();
									final int x = (screenSize.width - dialog.getWidth()) / 2;
									final int y = (screenSize.height - dialog.getHeight()) / 2;
									dialog.setLocation(x, y);
									dialog.setVisible(true);				
									if(!dialog.cancel){
										if(dialog.startNode1.isSelected())
											addEdge(dialog.textField.getText(),
													tmpCellList.get(0),tmpCellList.get(1));
										else
											addEdge(dialog.textField.getText(),
													tmpCellList.get(1),tmpCellList.get(0));
									}
									removeSelectionCells();
								}
							}
						}
					}
				} else {
					if(mouseModeAddNode){
						editNote dialog = new editNote();
						final Toolkit toolkit = Toolkit.getDefaultToolkit();
						final Dimension screenSize = toolkit.getScreenSize();
						final int x = (screenSize.width - dialog.getWidth()) / 2;
						final int y = (screenSize.height - dialog.getHeight()) / 2;
						dialog.setLocation(x, y);
						dialog.setVisible(true);						
						String la=dialog.getNodeText();
						if(la.length()>0)
							addNode(la,e.getX(),e.getY());
						else
							System.out.println("No input!");
					}
				}
			}
		});
	}
	
	/**
	 * mgraphx 构造函数.
	 */
	public mgraphx(){
		this(true);
	}
	
	/**
	 * hLayout 垂直方向自动布局.
	 */
	public void hLayout(){
	    mxIGraphLayout layout = new mxHierarchicalLayout(graph);
        layout.execute(graph.getDefaultParent());
       
	}
	
	/**
	 * labelLayout 连接边的标签自动布局.
	 */
	public void labelLayout(){
		mxIGraphLayout layout = new mxEdgeLabelLayout(graph);
    	layout.execute(graph.getDefaultParent());
	}
	
	/**
	 * centerGraph() 图像自动居中.
	 */
	public void centerGraph(){
		
		graph.getModel().beginUpdate();
	    double widthLayout = graphComponent.getLayoutAreaSize().getWidth();
	    double heightLayout = graphComponent.getLayoutAreaSize().getHeight();
	    Object[] nodes = graph.getChildCells(graph.getDefaultParent(),true,false);
	    double mleft=widthLayout,mright=0,mtop=heightLayout,mb=0;
	    for( Object node:nodes){
	    	mxCell _node=(mxCell)node;
	    	double left=_node.getGeometry().getX();//_node.getGeometry().getCenterX()-_node.getGeometry().getWidth()/2;
	    	if(left<mleft)
	    		mleft=left;
	    	double right=_node.getGeometry().getX()+_node.getGeometry().getWidth();
	    	if(right>mright)
	    		mright=right;
	    	double top=_node.getGeometry().getY();//_node.getGeometry().getCenterY()-_node.getGeometry().getHeight()/2;
	    	if(top<mtop)
	    		mtop=top;
	    	double b=_node.getGeometry().getY()+_node.getGeometry().getHeight();
	    	if(b>mb)
	    		mb=b;
	    	//System.out.println("top:"+top+",b:"+b);
	    }
	    //double width = graph.getGraphBounds().getWidth();
	    //double height = graph.getGraphBounds().getHeight();
	    double width =mright-mleft;
	    double height=mb-mtop;
	    graph.moveCells(nodes, (widthLayout - width)/2, (heightLayout - height)/2,false);
	    /*//set new geometry
	    for (int i = 0; i < roots.length; i++) {
	        Object root = roots[i];
	        mxGeometry geometry = graph.getModel().getGeometry(root); 
	        if(geometry!=null){
	    geometry = (mxGeometry) geometry.clone(); 
	    geometry.setX(geometry.getX()+(widthLayout - width)/2);
	    geometry.setY(geometry.getY()+(heightLayout - height)/2);
	    graph.getModel().setGeometry(root, geometry);
	        }
	    }*/
	    
	    graph.getModel().endUpdate();
	   /* double width = graph.getGraphBounds().getWidth();
	    double height = graph.getGraphBounds().getHeight();

	    //set new geometry
	    graph.getModel().setGeometry(graph.getDefaultParent(), 
	            new mxGeometry((widthLayout - width)/2, (heightLayout - height)/2,
	                    widthLayout, heightLayout));
	    graph.getModel().endUpdate();
	    dx=dx-(widthLayout - width)/2;
	    dy=dy-(heightLayout - height)/2;*/
	    peLayout();
	    
	}
	
	/**
	 * addNode 向图像中添加节点.
	 *
	 * @param ls 节点标签
	 * @param x 节点坐标x
	 * @param y 节点坐标y
	 * @return 返回被添加的节点
	 */
	public Object  addNode(String ls,int x,int y){
		graph.getModel().beginUpdate();
		Object v1=null;
		try {
			strParts sp= strUtil.treatString(ls,nodeFontSize);	
			double w=sp.maxlen*1.05;
			double h=sp.h*1.05;
			if(centerNode)
				v1 = graph.insertVertex(parent, null, ls, x-w/2, y-h/2,w ,h);
			else
				v1 = graph.insertVertex(parent, null, ls, x, y,w ,h);			
			//graph.updateCellSize(v1);
			
		} finally {
			graph.getModel().endUpdate();
		}
		return v1;
	}
	
	/**
	 * addNode 向图像中添加节点.
	 *
	 * @param id 节点id
	 * @param ls 节点标签
	 * @param x 节点坐标x
	 * @param y 节点坐标y
	 * @return 返回被添加的节点
	 */
	public Object  addNode(String id,String ls,double x,double y){
		graph.getModel().beginUpdate();
		Object v1=null;
		try {
			strParts sp= strUtil.treatString(ls,nodeFontSize);					
			v1 = graph.insertVertex(parent, id, ls, x, y,sp.maxlen*1.05 ,sp.h*1.05);			
			//graph.updateCellSize(v1);
			
		} finally {
			graph.getModel().endUpdate();
		}
		return v1;
	}
	
	/**
	 * The Class SwingCanvas.
	 */
	private class SwingCanvas extends mxInteractiveCanvas {
		
		/** The renderer pane. */
		protected CellRendererPane rendererPane = new CellRendererPane();

		/** The vertex renderer. */
		protected JLabel vertexRenderer = new JLabel();

		/** The graph component. */
		protected mxGraphComponent graphComponent;

		/**
		 * Instantiates a new swing canvas.
		 *
		 * @param graphComponent the graph component
		 */
		public SwingCanvas(mxGraphComponent graphComponent) {
			this.graphComponent = graphComponent;

			vertexRenderer.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			vertexRenderer.setHorizontalAlignment(JLabel.CENTER);
			//vertexRenderer.setBackground(graphComponent.getBackground().darker());
			vertexRenderer.setOpaque(true);
		}

		
		/**
		 * Draw vertex.
		 *
		 * @param state the state
		 * @param label the label
		 */
		public void drawVertex(mxCellState state, String label)
		{
			vertexRenderer.setText(label);
			Font f = new Font("SimSun", Font.PLAIN, nodeFontSize);
			
			vertexRenderer.setFont(f);
			
			// TODO: Configure other properties...
			rendererPane.paintComponent(g, vertexRenderer, graphComponent,
					(int) state.getX() + translate.x, (int) state.getY()
							+ translate.y, (int) state.getWidth(),
					(int) state.getHeight(), true);
		}

	}

	/**
	 * setMouseModeAddNode 设置mgraphx进入点击鼠标添加节点模式.
	 *
	 * @param e the new mouse mode add node
	 */
	public void setMouseModeAddNode(boolean e) {
		mouseModeAddNode = e;
	}
	
	/**
	 * setMouseModeAddEdge 设置设置mgraphx进入点击鼠标添加边模式.
	 *
	 * @param e the new mouse mode add edge
	 * @deprecated 
	 */
	public void setMouseModeAddEdge(boolean e) {
		mouseModeAddEdge = e;
		if(mouseModeAddEdge)
			graphComponent.setConnectable(false);
		else
			graphComponent.setConnectable(mouseModeAddEdge);
	}
	
	/**
	 * removeSelectedCells 删除已选择的节点或边.
	 */
	public void removeSelectedCells(){
		Object[] cells=graph.getSelectionCells();
		graph.getModel().beginUpdate();
		try {
			
			//System.out.println(arg0.toString());
			//graph.removeSelectionCell(arg0);
			 for( Object cell: cells) {
                 graph.getModel().remove( cell);
             }
			
		} finally {
			graph.getModel().endUpdate();
		}
	}
	
	/**
	 * 把已选节点或边设置为空.
	 */
	public void removeSelectionCells(){
		Object[] cells=graph.getSelectionCells();
		graph.removeSelectionCells(cells);		
	}
	
	/**
	 * 鼠标点选添加边的辅助函数，勿用！.
	 */
	public void selectTowNodes(){
		//removeSelectionCells();
		selectedNodeNum=0;
		tmpCellList.clear();	
		setMouseModeAddEdge(true);
	}

	/**
	 * 添加边.
	 *
	 * @param ls 边的标签
	 * @param s 边的起始节点对象，可由addNode获得
	 * @param e 边的终止节点对象，可由addNode获得
	 * @return 返回边的对象
	 */
	public Object addEdge(String ls,Object s,Object e){
		graph.getModel().beginUpdate();
		Object v1=null;
		try {
			v1=graph.insertEdge(parent, null,ls, s, e);
			
		} finally {
			graph.getModel().endUpdate();
		}
		return v1;
	}
	
	/**
	 * 添加边.
	 *
	 * @param ls 边的标签
	 * @param s 边的起始节点对象，可由addNode强制转换获得
	 * @param e 边的终止节点对象，可由addNode强制转换获得
	 * @return 返回边的对象
	 */
	private Object  addEdge(String ls,mxCell s,mxCell e){
		graph.getModel().beginUpdate();
		Object v1=null;
		try {
			v1=graph.insertEdge(parent, null,ls, s, e);
			
		} finally {
			graph.getModel().endUpdate();
		}
		return v1;
	}
	
	/**
	 * 测试时使用，该控件的使用者无需理会.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		JPanel p = new JPanel(new BorderLayout());
		mgraphx c = new mgraphx();
		c.setSize(580, 800);
		p.add(c, BorderLayout.CENTER);

		// frame.getContentPane().add(c,BorderLayout.CENTER);
		// frame.getContentPane().add(new JButton("OK"),BorderLayout.SOUTH);
		JButton button = new JButton("Add");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (button.getText().equals("Add")) {
					//c.setMouseModeAddNode(true);
					c.removeSelectedCells();
					button.setText("Default");
				} else {
					c.setMouseModeAddNode(false);
					button.setText("Add");
				}
			}

		});
		p.add(button, BorderLayout.SOUTH);
		frame.getContentPane().add(p, BorderLayout.CENTER);
		frame.pack();
		// c.setSize(1000, 720);
		frame.setSize(600, 820);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

/**
 * 自动环形布局.
 */
	public void cLayout() {
		// TODO Auto-generated method stub
		mxIGraphLayout layout = new mxCircleLayout(graph);
	    layout.execute(graph.getDefaultParent());	       
	}

/**
 * 自动树形布局.
 */
	public void tLayout() {
		// TODO Auto-generated method stub
		mxIGraphLayout layout = new mxCompactTreeLayout(graph);
	    layout.execute(graph.getDefaultParent());	  
	}

/**
 * 自动退火布局.
 */
	public void oLayout() {
		// TODO Auto-generated method stub
			  
		mxIGraphLayout layout = new mxOrganicLayout(graph);
	    layout.execute(graph.getDefaultParent());
		
	}

/**
 * 对边进行重新布局.
 */
	public void peLayout() {
		// TODO Auto-generated method stub
		//mxIGraphLayout layout = new mxParallelEdgeLayout(graph);
	    //layout.execute(graph.getDefaultParent());
		Object[] edges = graph.getChildCells(graph.getDefaultParent(),false,true);

		    for(Object edge:edges){
		    	graph.getModel().beginUpdate();
		    	mxCell me=(mxCell)edge;
		    	mxCell sc=(mxCell) me.getSource();
		    	mxCell ec=(mxCell) me.getTarget();
		    	String la=(String)me.getValue();
		    	graph.removeCells(edges);
		    	graph.getModel().endUpdate();

		    	addEdge(la,sc,ec);
		    }
					
	}

/**
 * 自动快速退火布局.
 */
	public void foLayout() {
		mxIGraphLayout layout = new mxFastOrganicLayout(graph);
	    layout.execute(graph.getDefaultParent());
		
		
	}

/**
 * 自动堆叠布局（不建议使用）.
 */
	public void sLayout() {
		mxIGraphLayout layout = new mxStackLayout(graph);
	    layout.execute(graph.getDefaultParent());
	}

/**
 * 正交布局.
 */
	public void orLayout() {
		mxIGraphLayout layout = new mxOrthogonalLayout(graph);
	    layout.execute(graph.getDefaultParent());	
	}
	
	/**
	 * 从数据库读取特定图.
	 *
	 * @param dbio 数据库操作类实例
	 * @param gid 图id
	 */
	public void readGfromDB(dbIO dbio,int gid){
		graphStru gs=null;
		try {
			gs = dbio.readGraph(gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(gs==null){
			System.out.println("No.:"+gid+" graph no found!");
			return;	
		}
		if(gs.nodes.size()<1)
			return;
		graph.removeCells(graph.getChildVertices(graph.getDefaultParent()));
		setupGraphStyle(gs.edgeFontSize,gs.nodeFontSize);
		for(mnode node:gs.nodes){
			addNode(node.id,node.label, node.x, node.y);
		}
		for(medge edge:gs.edges){
			mxCell sc=(mxCell)((mxGraphModel) graph.getModel()).getCell(edge.source);
	        mxCell ec=(mxCell)((mxGraphModel) graph.getModel()).getCell(edge.target);
	        addEdge( edge.label,sc, ec);
		}
	}

/**
 * 保存图到数据库<br>
 * 边的标签如果可以转换成double者会被作为边的权重<br>
 * 否则的话边的权重为1.0
 *
 * @param name 图的名字，可以为""
 * @param gid 图id（必须&gt;=0）,当id&gt;0函数将覆盖数据库中原gid对应的图，当id=0函数将自动保存到新图
 * @param dbio 数据库操作类实例
 * @return 返回所保存的gid的值
 * @throws SQLException 抛出sql异常
 */
	public int saveG2DB(String name,int gid,dbIO dbio) throws SQLException{
		graphStru gs=new graphStru();
		gs.name=name;gs.edgeFontSize=edgeFontSize;
		gs.nodeFontSize=nodeFontSize;
		gs.id=gid;
		Object[] nodes = graph.getChildCells(graph.getDefaultParent(),true,false);	    
	    for( Object node:nodes){
	    	mxCell _node=(mxCell)node;
	    	mxGeometry r = _node.getGeometry();
	    	double x,y;
	    	if(centerNode){
	    		x=r.getCenterX();
	    		y=r.getCenterY();
	    	}else{
	    		x=r.getX();
	    		y=r.getY();
	    	}
	    	String label=_node.getValue().toString();
	    	String id=_node.getId();
	    	gs.addNode(id, label, x, y, gid);
	    }
	    Object[] edges = graph.getChildCells(graph.getDefaultParent(),false,true);	    
	    for( Object edge:edges){
	    	mxCell _edge=(mxCell)edge;
	    	mxICell s=_edge.getSource();
	    	String sid=s.getId(),tid=_edge.getTarget().getId(),id=_edge.getId();
	    	String label=_edge.getValue().toString();
	    	double w=1;
	    	try{
	    		w=Double.parseDouble(label);
	    	}catch(java.lang.NumberFormatException e){
	    		w=1;
	    	}
	    	gs.addEdge(id, label, w, sid, tid, gid);
	    }
	    int _gid=0;
	    try{ 
	    	_gid=dbio.saveG2DB(gs);
	    }catch(Exception e){
	    	
	    	e.printStackTrace();
	    }
	    return _gid;
	}
}
