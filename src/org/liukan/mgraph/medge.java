/*
 * 
 */
package org.liukan.mgraph;
// TODO: Auto-generated Javadoc

/**
 * 边的数据类型，内部使用的数据类型，库使用者无需理会.
 */
public class medge {
	
	/** The id. */
	public String id ;//= rs.getString("id");
	
	/** The label. */
	public String  label;// = rs.getString("label");
	
	/** The weight. */
	public double weight ;// = rs.getDouble("weight");
	
	/** The source. */
	public String source  ;//= rs.getString("source");
	
	/** The target. */
	public String target;//  = rs.getString("target");
	
	/** The gid. */
	public int gid;// = rs.getInt("gid");
	
	/**
	 * 边的数据类型构造函数.
	 *
	 * @param _id  边id
	 * @param _label 边标签
	 * @param _weight 边权重
	 * @param _source 边的起始节点id
	 * @param _target 边的终止节点id
	 * @param _gid 边所属图的id，作为新图保存时设为0即可
	 */
    public medge(String _id,String _label,double _weight,String _source,String _target,int _gid){
    	id=_id;label=_label;weight=_weight;source=_source;
    	gid=_gid;target=_target;
    }
}