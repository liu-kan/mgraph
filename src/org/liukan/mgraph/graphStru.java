package org.liukan.mgraph;

import java.util.ArrayList;
import java.util.LinkedList;


/**
* @see mnode
* @see medge
*/
public class  graphStru {
	public int id;
	public String  name;    
	public int edgeFontSize;
	public int nodeFontSize;
	public LinkedList<mnode> nodes;
	public LinkedList<medge> edges;
	public void addNode(String id,String label,double x,double y,int gid){
		mnode node=new mnode(id, label,x, y, gid);
		nodes.add(node);
	}
	/**
	* @param label 显示在边上的标签
	* @param weight 边的权重
	* @param source 字符串型的开始节点id
	* @param target 字符串型的结束节点id
	* @param gid 所属图的id
	*/
	public void addEdge(String id,String label,double weight,String source,String target,int gid){
		medge edge=new medge(id, label,weight, source, target, gid);
		edges.add(edge);
	}
	public graphStru(){
		id=-1;//大于0才有效
		nodes=new LinkedList<mnode>();
		edges=new LinkedList<medge>();
	}
	
}
