/*
 * 
 */
package org.liukan.mgraph;

import java.util.ArrayList;
import java.util.LinkedList;


// TODO: Auto-generated Javadoc
/**
 * 图的数据结构.
 */
public class  graphStru {
	
	/** The id. */
	public int id;
	
	/** The name. */
	public String  name;    
	
	/** The edge font size. */
	public int edgeFontSize;
	
	/** The node font size. */
	public int nodeFontSize;
	
	/** The nodes. */
	public LinkedList<mnode> nodes;
	
	/** The edges. */
	public LinkedList<medge> edges;
	public mnode getNode(String id){
		int size = nodes.size();
		for (int j = 0; j < size; j++) {
			mnode n=nodes.get(j);
			if(n.id.equals(id))
				return n;
		}
		return null;
	}
	/**
	 * 向图数据结构中添加节点.
	 *
	 * @param id 节点id
	 * @param label 节点标签
	 * @param x 节点坐标x
	 * @param y 节点坐标y
	 * @param gid 节点所属图的id，作为新图保存时设为0即可
	 */
	public void addNode(String id,String label,double x,double y,int gid){
		mnode node=new mnode(id, label,x, y, gid);
		nodes.add(node);
	}
	
	/**
	 * 向图数据结构中添加边.
	 *
	 * @param id 边id
	 * @param label 显示在边上的标签
	 * @param weight 边的权重
	 * @param source 字符串型的开始节点id
	 * @param target 字符串型的结束节点id
	 * @param gid 所属图的id，作为新图保存时设为0即可
	 */
	public void addEdge(String id,String label,double weight,String source,String target,int gid){
		medge edge=new medge(id, label,weight, source, target, gid);
		edges.add(edge);
	}
	
	/**
	 * 图数据结构构造函数，其中的成员变量id;name;edgeFontSize;nodeFontSize只在需要时直接赋值<br>
	 * 成员变量nodes;edges分别通过多次调用addNode和addEdge设置<br>
	 * 该数据结构不提供删除和编辑nodes;edges操作，<br>
	 * 请使用者自行编写你们实际程序中使用的图数据结构到本数据结构或数据库结构的转换<br>
	 * 如需删除和编辑nodes;edges操作，请在使用者自身的图数据结构中完成后再转换.
	 */
	public graphStru(){
		id=-1;//大于0才有效
		nodes=new LinkedList<mnode>();
		edges=new LinkedList<medge>();
	}
	
}
