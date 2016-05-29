/*
 * 
 */
package org.liukan.mgraph;
// TODO: Auto-generated Javadoc

/**
 * 节点数据类型，内部使用的数据类型，库使用者无需理会.
 */
public class mnode {
	
	/** The id. */
	public String id;// = rs.getString("id");
	
	/** The label. */
	public String  label;// = rs.getString("label");
	
	/** The x. */
	public double x ;// = rs.getDouble("x");
	
	/** The y. */
	public double y ;// = rs.getDouble("y");
	
	/** The gid. */
	public int gid ;
	
	/**
	 * 构造函数.
	 *
	 * @param _id 节点id
	 * @param _label 节点标签
	 * @param _x 节点坐标x
	 * @param _y 节点坐标y
	 * @param _gid 节点所属图的id，作为新图保存时设为0即可
	 */
    public mnode(String _id,String _label,double _x,double _y,int _gid){
    	id=_id;label=_label;x=_x;y=_y;
    	gid=_gid;
    }
}
