package org.liukan.mgraph;

public class medge {
	public String id ;//= rs.getString("id");
	public String  label;// = rs.getString("label");
	public double weight ;// = rs.getDouble("weight");
	public String source  ;//= rs.getString("source");
	public String target;//  = rs.getString("target");
	public int gid;// = rs.getInt("gid");
    public medge(String _id,String _label,double _weight,String _source,String _target,int _gid){
    	id=_id;label=_label;weight=_weight;source=_source;
    	gid=_gid;target=_target;
    }
}