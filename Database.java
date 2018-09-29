import java.sql.*;
public class Database{
	Connection Conn;//Connection(); 
	/*
	public void createTable(String name,String fields){
		try {
			try {
				Statement stmt = Conn.createStatement();
				stmt.executeQuery("CREATE TABLE "+name+fields+";");
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	*/

	public void createDatabase(String name){
		try {
			Statement stmt = Conn.createStatement();
			try {
				stmt.executeQuery("CREATE DATABASE "+name+";");
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	/*
	public addUser(int[] phone, char[] password,String fname,lname Bool gender){
		Statement stmt = conn.createStatement();
		String p;
		for (int i = 0; i<password.length,i++){
			p += password[i]
		}
		ResultSet rset = stmt.executeQuery("insert into Users ("+fname","+lname+","+ph,","") = "+username+";");
		rset.next()
		ch
		return

	}
	*/
	public Database(){
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/myDB");
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser("Tony");
			dataSource.setServerName("myDBHost.example.org");
			Connection conn = dataSource.getConnection(); 
			Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","Tony","");
			createDatabase("testDB");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	

	public static void main(String[] args) {
		Database d = new Database();
	}
}