package org.liukan.mgraph.test;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.CellRendererPane;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.canvas.mxImageCanvas;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxKeyboardHandler;
import com.mxgraph.swing.handler.mxRubberband;
import com.mxgraph.swing.view.mxInteractiveCanvas;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

public class CustomCanvas extends JPanel 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -844106998814982739L;

	public CustomCanvas()
	{
		super();
		setLayout(new BorderLayout()); 
		// Demonstrates the use of a Swing component for rendering vertices.
		// Note: Use the heavyweight feature to allow for event handling in
		// the Swing component that is used for rendering the vertex.
		mxConstants.DEFAULT_FONTSIZE=36;

		
		
		Font f = new Font("SimSun", Font.PLAIN, 36);
		JComponent j = new JLabel();
		FontMetrics fm = j.getFontMetrics(f);
		 
		double w=fm.stringWidth("Hello\nHello\nHello");//字符串宽度,to fix只取所有行中最长的一行
		double h=fm.getHeight();  //高度

		mxGraph graph = new mxGraph()
		{
			public void drawState(mxICanvas canvas, mxCellState state,
					boolean drawLabel)
			{
				String label = (drawLabel) ? state.getLabel() : "";

				// Indirection for wrapped swing canvas inside image canvas (used for creating
				// the preview image when cells are dragged)
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
				}
				else
				{
					super.drawState(canvas, state, drawLabel);
				}
			}
		};

		mxStylesheet stylesheet = graph.getStylesheet();
	     Map<String, Object> edgeStyle = stylesheet.getDefaultEdgeStyle();
	        //edgeStyle.put(mxConstants.STYLE_NOLABEL, "1");
	        //edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "000000");
	        Object styleEdge = mxConstants.EDGESTYLE_TOPTOBOTTOM;
	        edgeStyle.put(mxConstants.STYLE_EDGE, styleEdge);
	        //edgeStyle.put(mxConstants.STYLE_SHAPE, STYLE_SHAPE);
	        edgeStyle.put(mxConstants.STYLE_ROUNDED, "1");
	        edgeStyle.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ENTITY_RELATION);


	        // PROCESS STYLE
	        Map<String, Object> processStyle = stylesheet.getDefaultVertexStyle();
	        //Hashtable<String, Object> processStyle = new Hashtable<>();
	        processStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
	        processStyle.put(mxConstants.STYLE_OPACITY, 80);
	        processStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
	        processStyle.put(mxConstants.STYLE_FILLCOLOR, "#ffffff");
	        processStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000");
	        processStyle.put(mxConstants.STYLE_FONTSIZE, 45);
	        processStyle.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
	        processStyle.put(mxConstants.STYLE_GRADIENTCOLOR, "#f6f6f6");
	        processStyle.put(mxConstants.STYLE_SPACING_TOP, 4);
	        //stylesheet.putCellStyle(STYLENAME_PROCESS, processStyle);
	        //graph.setStylesheet(stylesheet);
        
		Object parent = graph.getDefaultParent();
		graph.getModel().beginUpdate();
		try
		{
			Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 180,
					40);
			Object v2 = graph.insertVertex(parent, null, "World!", 340, 250,
					180, 40);
			graph.insertEdge(parent, null, "Edge", v1, v2);
			graph.insertEdge(parent, null, "2", v1, v1);
			graph.insertEdge(parent, null, "Edge", v2, v1);
		}
		finally
		{
			graph.getModel().endUpdate();
		}

		mxGraphComponent graphComponent = new mxGraphComponent(graph)
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 4683716829748931448L;

			public mxInteractiveCanvas createCanvas()
			{
				return new SwingCanvas(this);
			}
		};

		add(graphComponent);

		// Adds rubberband selection
		new mxRubberband(graphComponent);
		new mxKeyboardHandler( graphComponent);
		//graphComponent.setConnectable(false);
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
		{		
			public void mouseReleased(MouseEvent e)
			{
				Object cell = graphComponent.getCellAt(e.getX(), e.getY());
				
				if (cell != null)
				{
					System.out.println("cell="+graph.getLabel(cell));
				}
				else
				{
					System.out.println("x="+e.getX()+", y="+e.getY());
				}
			}
		});
	}

	public class SwingCanvas extends mxInteractiveCanvas
	{
		protected CellRendererPane rendererPane = new CellRendererPane();

		protected JLabel vertexRenderer = new JLabel();

		protected mxGraphComponent graphComponent;

		public SwingCanvas(mxGraphComponent graphComponent)
		{
			this.graphComponent = graphComponent;

			vertexRenderer.setBorder(BorderFactory
					.createBevelBorder(BevelBorder.RAISED));
			vertexRenderer.setHorizontalAlignment(JLabel.CENTER);
			vertexRenderer.setBackground(graphComponent.getBackground().darker());
			vertexRenderer.setOpaque(true);
		}

		public void drawVertex(mxCellState state, String label)
		{
			vertexRenderer.setText(label);
			// TODO: Configure other properties...
			rendererPane.paintComponent(g, vertexRenderer, graphComponent,
					(int) state.getX() + translate.x, (int) state.getY()
							+ translate.y, (int) state.getWidth(),
					(int) state.getHeight(), true);
		}

	}

	public static void main(String[] args)
	{
		JFrame  frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		JPanel p=new JPanel(new BorderLayout());
		CustomCanvas c=new CustomCanvas();
		c.setSize(580, 800);
		p.add(c,BorderLayout.CENTER);
		p.add(new JButton("OK"),BorderLayout.SOUTH);
		//frame.getContentPane().add(c,BorderLayout.CENTER);
		//frame.getContentPane().add(new JButton("OK"),BorderLayout.SOUTH);
		frame.getContentPane().add(p,BorderLayout.CENTER);
		frame.pack();
		//c.setSize(1000, 720);
		frame.setSize(600, 820);
        frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
