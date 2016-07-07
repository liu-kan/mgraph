package org.liukan.mgraph.ui;

public interface IeditEdge {
	String getEdgeLabel();
	boolean isCanceled();
	boolean editLabel();
	boolean selectedNode1();
	void setSE(String s,String e);
}
