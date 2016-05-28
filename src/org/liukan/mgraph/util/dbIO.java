package org.liukan.mgraph.util;

import java.sql.*;

import org.liukan.mgraph.graphStru;
import org.liukan.mgraph.medge;
import org.liukan.mgraph.mnode;


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
		String connCMD = String.format("%s?user=%s&password=%s&useUnicode=true&characterEncoding=UTF8",conn_url,username, password);
	    try {
	      Class.forName(driver);
	      if(driver.contains("sqlite"))
	    	  connCMD=conn_url;
	      c = DriverManager.getConnection(connCMD);
	      //c.setAutoCommit(false);
	      System.out.println("Opened database successfully");
	    }
	    catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      //System.exit(0);
		    }
	}
	public boolean saveG2DB(graphStru gs)throws Exception{
		if(gs.id<0)
			return false;
		boolean rv=false;
		try {
			c.setAutoCommit(false);
			
			stmt=c.createStatement();
			PreparedStatement statement=null;
			ResultSet rs = stmt.executeQuery( "select * FROM graphs where id="+gs.id+";" );
			
			if (!rs.isBeforeFirst() ) {  
				System.out.println("add G!");
				String sql = "INSERT INTO graphs (id, name, edgeFontSize, nodeFontSize)" +
				        "VALUES (?, ?, ?,?)";				
				statement = c.prepareStatement(sql);
				statement.setInt(1, gs.id);
				statement.setString(2, gs.name);
				statement.setInt(3, gs.edgeFontSize);
				statement.setInt(4, gs.nodeFontSize);
				statement.executeUpdate(); 
				
			} else{
				System.out.println("update G!");
				String sql = "UPDATE graphs SET edgeFontSize=?, nodeFontSize=?, name=? WHERE id=?";
				 
				statement = c.prepareStatement(sql);
				statement.setInt(1, gs.edgeFontSize);
				statement.setInt(2, gs.nodeFontSize);
				statement.setString(3, gs.name);
				statement.setInt(4, gs.id);
				 
				statement.executeUpdate();
			
			}
			String sql = "DELETE FROM edges WHERE gid=?";			 
			statement = c.prepareStatement(sql);
			statement.setInt(1, gs.id);			 
			statement.executeUpdate();
			
			sql = "INSERT INTO edges (id ,source,target,label,weight,gid)" +
			        "VALUES (?, ?, ?,?,?,?)";	
			statement = c.prepareStatement(sql);
			//System.out.println("updating edges");
			int size = gs.edges.size();
			for (int j = 0; j < size; j++) {
				medge e=gs.edges.get(j);
				if(e.source.length()<1)
					continue;
				//System.out.println(e.id+","+e.source+","+e.target+","+e.label+","+e.weight+","+gs.id);
					statement.setInt(1,Integer.parseInt(e.id));
					statement.setString(2, e.source);
					statement.setString(3, e.target);
					statement.setString(4, e.label);
					statement.setDouble(5, e.weight);
					statement.setInt(6,gs.id);
				int rown=statement.executeUpdate();
				//System.out.println(rown);
				
			}
			System.out.println("update nodes");
			sql = "DELETE FROM nodes WHERE gid=?";			 
			statement = c.prepareStatement(sql);
			statement.setInt(1, gs.id);			 
			statement.executeUpdate();
			sql = "INSERT INTO nodes (id ,label,x,y,gid)" +
			        "VALUES (?, ?, ?,?,?)";	
			statement = c.prepareStatement(sql);
			size = gs.nodes.size();
			for (int j = 0; j < size; j++) {
				mnode e=gs.nodes.get(j);
				if(e.id.length()<1)
					continue;
					statement.setInt(1,Integer.parseInt(e.id));
					statement.setString(2, e.label);
					statement.setDouble(3, e.x);
					statement.setDouble(4, e.y);
				
					statement.setInt(5,gs.id);
					statement.executeUpdate(); 
				
			}
			c.commit();
		}  
		catch(Exception e)
		{
		   c.rollback();
		}
		finally{
			stmt.close();
		}
		return rv;
	}
	public graphStru readGraph(int _gid/*,mgraphx _mgraphx*/) throws SQLException{
		graphStru rv=new graphStru();
		try {
			stmt=c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM graphs where id="+_gid+";" );
			if (!rs.isBeforeFirst() ) {    
				 System.out.println("No data"); 
				 rv= null;
				} 
			//mxGraph graph =_mgraphx.getGraphX();
			//graph.removeCells(graph.getChildVertices(graph.getDefaultParent()));
			else{ 
				while ( rs.next() ) {
			         int id = rs.getInt("id");
			         String  name = rs.getString("name");
			         
			         int edgeFontSize  = rs.getInt("edgeFontSize");
			         int nodeFontSize  = rs.getInt("nodeFontSize");
			         //_mgraphx.setupGraphStyle(edgeFontSize,nodeFontSize);
			         rv.id=id;rv.name=name;rv.nodeFontSize=nodeFontSize;
			         rv.edgeFontSize=edgeFontSize;
			      }
			      rs.close();
				 rs = stmt.executeQuery( "SELECT * FROM nodes where gid="+_gid+";" );
				 
				while ( rs.next() ) {
					
					String id = Integer.toString(rs.getInt("id"));
					//System.out.println("Reading nodes data:" +id); 
			         String  label = rs.getString("label");
			         double x  = rs.getDouble("x");
			         double y  = rs.getDouble("y");
			         int gid = rs.getInt("gid");
			         //_mgraphx.addNode(id,label, x, y);
			         rv.addNode(id, label, x, y, gid);
			      }
				
			      rs.close();
			      rs = stmt.executeQuery( "SELECT * FROM edges where gid="+_gid+";" );
					while ( rs.next() ) {
						String id = Integer.toString(rs.getInt("id"));
				         String  label = rs.getString("label");
				         double weight  = rs.getDouble("weight");
				         String source  = rs.getString("source");
				         String target  = rs.getString("target");
				         int gid = rs.getInt("gid");
				         //mxCell sc=(mxCell)((mxGraphModel) _mgraphx.getGraphX().getModel()).getCell(source);
				         //mxCell ec=(mxCell)((mxGraphModel) _mgraphx.getGraphX().getModel()).getCell(target);
				         if(label.length()<1)
				        	 label=String.valueOf(weight);
				         //_mgraphx.addEdge(label, sc, ec);
				         rv.addEdge(id, label, weight, source, target, gid);			         
				      }
				      rs.close();
			}
			      
		}  
		finally{
			stmt.close();
		}
		//_mgraphx.hLayout();
		return rv;
	}
	public void close(){
		 try {
		    	
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
	public static void printSQLException(SQLException ex) {

	    for (Throwable e : ex) {
	        if (e instanceof SQLException) {
	            /*if (ignoreSQLException(
	                ((SQLException)e).
	                getSQLState()) == false) 
	            */{

	                e.printStackTrace(System.err);
	                System.err.println("SQLState: " +
	                    ((SQLException)e).getSQLState());

	                System.err.println("Error Code: " +
	                    ((SQLException)e).getErrorCode());

	                System.err.println("Message: " + e.getMessage());

	                Throwable t = ex.getCause();
	                while(t != null) {
	                    System.out.println("Cause: " + t);
	                    t = t.getCause();
	                }
	            }
	        }
	    }
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
