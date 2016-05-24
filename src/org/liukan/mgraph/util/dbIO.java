package org.liukan.mgraph.util;

import java.sql.*;

import org.liukan.mgraph.mgraphx;

import com.mxgraph.view.mxGraph;

public class dbIO {
	Connection c = null;
	Statement stmt = null;
	String driver,conn_url,username,password;
	public dbIO(String driver,String conn_url,String username,
			String password){
		this.driver=driver;
		this.conn_url=conn_url;this.username=username;
		this.password=password;
		conndb(driver, conn_url,username,
				 password);
	}
	public void conndb(){
		conndb(driver, conn_url,username,password);
	}
	public void conndb(String driver,String conn_url,String username,
			String password){		
	    try {
	      Class.forName(driver);
	      c = DriverManager.getConnection(conn_url);
	      //c.setAutoCommit(false);
	      System.out.println("Opened database successfully");
	    }
	    catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      //System.exit(0);
		    }
	}
	public void readGraph(int _gid,mgraphx _mgraphx){
		try {
			stmt=c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM graphs where id="+_gid+";" );
			if (!rs.isBeforeFirst() ) {    
				 System.out.println("No data"); 
				 return;
				} 
			mxGraph graph =_mgraphx.getGraphX();
			 graph.removeCells(graph.getChildVertices(graph.getDefaultParent()));
			 
			while ( rs.next() ) {
		         int id = rs.getInt("id");
		         String  name = rs.getString("name");
		         
		         int edgeFontSize  = rs.getInt("edgeFontSize");
		         int nodeFontSize  = rs.getInt("nodeFontSize");
		         _mgraphx.setupGraphStyle(edgeFontSize,nodeFontSize);
		      }
		      rs.close();
			 rs = stmt.executeQuery( "SELECT * FROM nodes where gid="+_gid+";" );
			while ( rs.next() ) {
				String id = rs.getString("id");
		         String  label = rs.getString("label");
		         double x  = rs.getDouble("x");
		         double y  = rs.getDouble("y");
		         int gid = rs.getInt("gid");
		         _mgraphx.addNode(id,label, x, y);
		      }
		      rs.close();
		      rs = stmt.executeQuery( "SELECT * FROM edges where gid="+_gid+";" );
				while ( rs.next() ) {
			         int id = rs.getInt("id");
			         String  label = rs.getString("label");
			         double weight  = rs.getDouble("weight");
			         int source  = rs.getInt("source");
			         int target  = rs.getInt("target");
			         int gid = rs.getInt("gid");
			         
			         System.out.println( "ID = " + id );
			         System.out.println( "label = " + label );
			         System.out.println( "weight = " + weight );
			         System.out.println( "source=" + source );
			         System.out.println( "target=" + target );
			         System.out.println( "gid = " + gid );
			         System.out.println();
			      }
			      rs.close();
			      
		}  catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		
	}
	public void close(){
		 try {
		    	stmt.close();
				c.close();
				System.out.println("DB closed");
			} 
		    catch (SQLException e) {				
				e.printStackTrace();
			}
		    finally {
		    	System.out.println("Have tried to close DB");
		    }
	}
	protected void finalize( )
	{
	// finalization code here
		 
	}
	 public static void main( String args[] )
	  {
	   
	    try {
	    	dbIO dbio=new dbIO("org.sqlite.JDBC","jdbc:sqlite:db.sqlite",null,null);	      
	    	//dbio.readGraph(1);
	    	dbio.close();
	    	
	    	//dbio=null;
	    	//System.gc(); 
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    
	  }
}
