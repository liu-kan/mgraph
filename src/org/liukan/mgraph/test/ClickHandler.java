package org.liukan.mgraph.test;


import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxKeyboardHandler;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

public class ClickHandler extends JPanel
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2764911804288120883L;
	public void setupGraph(mxStylesheet stylesheet) {
		// graph setup
		
		Map<String, Object> edgeStyle = stylesheet.getDefaultEdgeStyle();
		// edgeStyle.put(mxConstants.STYLE_NOLABEL, "1");
		// edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "000000");
		Object styleEdge = mxConstants.EDGESTYLE_TOPTOBOTTOM;
		//edgeStyle.put(mxConstants.STYLE_EDGE, styleEdge);
		// edgeStyle.put(mxConstants.STYLE_SHAPE, STYLE_SHAPE);
		edgeStyle.put(mxConstants.STYLE_ROUNDED, "1");
		//edgeStyle.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ENTITY_RELATION);

		// PROCESS STYLE
		Map<String, Object> processStyle = stylesheet.getDefaultVertexStyle();
		// Hashtable<String, Object> processStyle = new Hashtable<>();
		processStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
		processStyle.put(mxConstants.STYLE_OPACITY, 80);
		processStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
		processStyle.put(mxConstants.STYLE_FILLCOLOR, "#ffffff");
		processStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000");
		processStyle.put(mxConstants.STYLE_FONTSIZE, 45);
		processStyle.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
		processStyle.put(mxConstants.STYLE_GRADIENTCOLOR, "#f6f6f6");
		processStyle.put(mxConstants.STYLE_SPACING_TOP, 3);
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

	public ClickHandler()
	{
		super();
		mxStylesheet stylesheet = new mxStylesheet();
		setupGraph(stylesheet);
		final mxGraph graph = new mxGraph(stylesheet);
		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();
		try
		{
		   Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,
		         30,"ROUNDED");
		   Object v2 = graph.insertVertex(parent, null, "World!",
		         240, 150, 80, 30,"ROUNDED");
		   graph.insertEdge(parent, null, "Edge", v1, v2);
		}
		finally
		{
		   graph.getModel().endUpdate();
		}
		
		final mxGraphComponent graphComponent = new mxGraphComponent(graph);
		//getContentPane().
		new mxKeyboardHandler( graphComponent);
		add(graphComponent);
		
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
		{
		
			public void mouseReleased(MouseEvent e)
			{
				Object cell = graphComponent.getCellAt(e.getX(), e.getY());
				
				if (cell != null)
				{
					System.out.println("cell="+graph.getLabel(cell));
				}
			}
		});
	}

	public static void main(String[] args)
	{
		
		
		JFrame  frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		ClickHandler c=new ClickHandler();

		frame.add(c);	
		frame.pack();
		c.setSize(1000, 720);
		frame.setSize(1000, 720);
        frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
