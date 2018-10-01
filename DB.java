import java.sql.*;
class DB{
	protected String table_name;
	protected String database_name;
	public DB(String dbName,String userstable){
		this.database_name = dbName;
		this.table_name = userstable;
	}
	private String nameColo = "firstname,lastname,telephone,gender";
	public void CreateUser(String fname,String lname,String telephone,String gender){
		try{
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/"+database_name+"?useSSL=false","root", "Amakau@123"
			);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(
				"INSERT INTO "+table_name+" ("+nameColo+") VALUES('"+fname+"','"+lname+"','"+telephone+"','"+gender+"');"
			);
		}catch(SQLException ex){
			ex.printStackTrace();
		}	
	}
	public boolean CheckUser(String fname,String lname,String telephone,String gender){
		try{
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/"+database_name+"?useSSL=false","root", "Amakau@123"
			);
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery("SELECT "+nameColo+" FROM "+table_name+" where firstname="+fname+"lastname="+lname+"telephone="+telephone+"gender="+gender+" limit=1;"
				);
			return res.next();
		}catch(SQLException ex){
			ex.printStackTrace();
		}	
		return false;
	}
	public String[][] GetUsers(){
		String[][] users = new String[10][4];
		try{
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/"+database_name+"?useSSL=false","root", "Amakau@123"
			);
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM "+table_name+";");
			int n = 0;
			while(res.next()){
				String[] user = {
				res.getString("firstname"),res.getString("lastname"),
				res.getString("telephone"),Integer.toString(res.getInt("gender"))
				};
				users[n] = user;
				n++;
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}	
		return users;
	}
}