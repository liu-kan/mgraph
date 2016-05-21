package org.liukan.mgraph;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.CellRendererPane;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.liukan.mgraph.test.CustomCanvas.SwingCanvas;
import org.liukan.mgraph.ui.editEdge;
import org.liukan.mgraph.ui.editNote;
import org.liukan.mgraph.util.strParts;
import org.liukan.mgraph.util.strUtil;

import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.canvas.mxImageCanvas;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxKeyboardHandler;
import com.mxgraph.swing.handler.mxRubberband;
import com.mxgraph.swing.view.mxInteractiveCanvas;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

public class mgraphx extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -844106998814982739L;
	private boolean mouseModeAddNode,mouseModeAddEdge,nodesConnectable;
	private int fontSize, nodeFontSize;
	private mxGraph graph ;
	ArrayList<mxCell> tmpCellList;
	Object parent;
	mxGraphComponent graphComponent;
	private int selectedNodeNum;
	public void setupGraph(mxStylesheet stylesheet) {
		// graph setup
		fontSize=45;
		nodeFontSize=22;
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
		
		Hashtable<String, Object> style = new Hashtable<String, Object>();
		style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
		style.put(mxConstants.STYLE_OPACITY, 50);
		style.put(mxConstants.STYLE_FONTCOLOR, "#774400");
		stylesheet.putCellStyle("ROUNDED", style);
		//return stylesheet;
	}
	
	public mgraphx(boolean _nodesConnectable) {
		super();
		selectedNodeNum=0;
		graph=null;
		mouseModeAddNode = false;
		mouseModeAddEdge=false;
		nodesConnectable=_nodesConnectable;
		tmpCellList=new ArrayList<mxCell>();
		setLayout(new BorderLayout());
		mxConstants.DEFAULT_FONTSIZE = 36;
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
			Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 100, 46);
			Object v2 = graph.insertVertex(parent, null, "World!", 340, 250, 100, 46);
			graph.insertEdge(parent, null, "Edge1", v1, v2);
			graph.insertEdge(parent, null, "2", v1, v1);
			graph.insertEdge(parent, null, "2Edge", v2, v1);
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
	public mgraphx(){
		this(true);
	}
	

	public Object  addNode(String ls,int x,int y){
		graph.getModel().beginUpdate();
		Object v1=null;
		try {
			strParts sp= strUtil.treatString(ls,nodeFontSize);					
			v1 = graph.insertVertex(parent, null, ls, x, y,sp.maxlen ,sp.h);			
			//graph.updateCellSize(v1);
			
		} finally {
			graph.getModel().endUpdate();
		}
		return v1;
	}
	public class SwingCanvas extends mxInteractiveCanvas {
		protected CellRendererPane rendererPane = new CellRendererPane();

		protected JLabel vertexRenderer = new JLabel();

		protected mxGraphComponent graphComponent;

		public SwingCanvas(mxGraphComponent graphComponent) {
			this.graphComponent = graphComponent;

			vertexRenderer.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			vertexRenderer.setHorizontalAlignment(JLabel.CENTER);
			//vertexRenderer.setBackground(graphComponent.getBackground().darker());
			vertexRenderer.setOpaque(true);
		}

		
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

	public void setMouseModeAddNode(boolean e) {
		mouseModeAddNode = e;
	}
	public void setMouseModeAddEdge(boolean e) {
		mouseModeAddEdge = e;
		if(mouseModeAddEdge)
			graphComponent.setConnectable(false);
		else
			graphComponent.setConnectable(mouseModeAddEdge);
	}
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
	public void removeSelectionCells(){
		Object[] cells=graph.getSelectionCells();
		graph.removeSelectionCells(cells);		
	}
	public void selectTowNodes(){
		//removeSelectionCells();
		selectedNodeNum=0;
		tmpCellList.clear();	
		setMouseModeAddEdge(true);
	}
	public Object  addEdge(String ls,mxCell s,mxCell e){
		graph.getModel().beginUpdate();
		Object v1=null;
		try {
			v1=graph.insertEdge(parent, null,ls, s, e);
			
		} finally {
			graph.getModel().endUpdate();
		}
		return v1;
	}
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
}
