package dbc;

import java.sql.*;
import java.util.Properties;


public final class JdbcUtil {
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/youzhan?useUnicode=true&characterEncoding=utf-8";
	private static String user = "root";
	private static String password = "";
	private static Properties pr = new Properties();
	
	static {
		try{
			
//			pr.load(JdbcUtil.class.getClassLoader().getResourceAsStream("db.properties"));
//			driver = pr.getProperty("driver");
//			url = pr.getProperty("url");
//			user = pr.getProperty("username");
//			password = pr.getProperty("password");
			//System.out.println(driver); 
			Class.forName(driver);
			//System.out.println("�ɹ�����MySQL��������");
		}catch(Exception e){
			throw new ExceptionInInitializerError(e);
		}
		
	}
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url,user,password);
	}
	
	public static void free(ResultSet rs,Statement st,Connection conn){
		try{
			if(rs != null) rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
					
				}
			}
		}
	}

}
