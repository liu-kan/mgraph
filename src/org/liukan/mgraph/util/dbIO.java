/*
 * 
 */
package org.liukan.mgraph.util;

import java.sql.*;

import org.liukan.mgraph.graphStru;
import org.liukan.mgraph.medge;
import org.liukan.mgraph.mnode;

// TODO: Auto-generated Javadoc
/**
 * 负责从jdbc数据库读写图数据.
 *
 * @author liukan
 * <a href="mailto:liukan@126.com">liukan@126.com</a>
 * @version 0.1
 */
public class dbIO {
	
	/** The c. */
	Connection c = null;
	
	/** The stmt. */
	Statement stmt = null;
	
	/** The password. */
	String driver,conn_url,username,password;

	private String whole_conn_url;
	
	/**
	 * 构造函数，下面各参数以mysql为例.
	 *
	 * @param driver com.mysql.jdbc.Driver
	 * @param conn_url jdbc:mysql://192.168.0.2:3336/db_zlpj
	 * @param username root
	 * @param password wipm
	 */
	public dbIO(String driver,String conn_url,String username,
			String password){
		this.driver=driver;
		this.conn_url=conn_url;this.username=username;
		this.password=password;
		conndb(driver, conn_url,username,
				 password);
	}
	public dbIO(String driver,String whole_conn_url){
		this.driver=driver;
		this.whole_conn_url=conn_url;
		conndb(driver, whole_conn_url);
	}
	private void conndb(String driver2, String whole_conn_url2) {
		try {
		      Class.forName(driver2);		      
		      c = DriverManager.getConnection(whole_conn_url2);
		      //c.setAutoCommit(false);
		      System.out.println("Opened database successfully");
		    }
		    catch ( Exception e ) {
			      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			      //System.exit(0);
			    }
		
	}
	/**
	 * 已构造函数中设置的参数连接数据库.
	 */
	public void conndb(){
		conndb(driver, conn_url,username,password);
	}
	
	/**
	 * 连接数据库，参数同构造函数.
	 *
	 * @param driver the driver
	 * @param conn_url the conn_url
	 * @param username the username
	 * @param password the password
	 */
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
	
	/**
	 * 保存图像到数据库.
	 *
	 * @param gs 图像数据结构
	 * @return 最终保存的图像编号
	 * @throws Exception the exception
	 */
	public int saveG2DB(graphStru gs)throws Exception{
		if(gs.id<0)
			return gs.id;		
		try {
			c.setAutoCommit(false);
			
			stmt=c.createStatement();
			PreparedStatement statement=null;
			if(gs.id==0){
				String sql = "INSERT INTO graphs ( name, edgeFontSize, nodeFontSize)" +
				        "VALUES ( ?, ?,?)";	
				statement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, gs.name);
				statement.setInt(2, gs.edgeFontSize);
				statement.setInt(3, gs.nodeFontSize);
				statement.executeUpdate();
				ResultSet rs = statement.getGeneratedKeys();
				if (rs.next()) {
					gs.id = rs.getInt(1);
				}
				
			}else{
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
			e.printStackTrace();
		   c.rollback();
		}
		finally{
			stmt.close();
		}
		return gs.id;
	}
	
	/**
	 * 从数据库中读取图像到图像数据结构.
	 *
	 * @param _gid 图像在数据库中的id
	 * @return 返回图像对应的数据结构
	 * @throws SQLException the SQL exception
	 */
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
				         //if(label.length()<1)
				        	 //label=String.valueOf(weight);
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
	
	/**
	 * 关闭数据库连接在数据库不在使用时调用，应和conndb成对出现.
	 */
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize( )
	{
	// finalization code here
		 
	}
	
	/**
	 * Prints the sql exception.
	 *
	 * @param ex the ex
	 */
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
	
	/**
	 * 测试用途，库使用者无需理会.
	 *
	 * @param args the arguments
	 */
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
