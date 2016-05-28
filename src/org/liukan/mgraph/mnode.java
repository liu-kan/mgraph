package org.liukan.mgraph;

public class mnode {
	public String id;// = rs.getString("id");
	public String  label;// = rs.getString("label");
	public double x ;// = rs.getDouble("x");
	public double y ;// = rs.getDouble("y");
	public int gid ;
    public mnode(String _id,String _label,double _x,double _y,int _gid){
    	id=_id;label=_label;x=_x;y=_y;
    	gid=_gid;
    }
}
