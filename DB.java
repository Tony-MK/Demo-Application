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
			ResultSet res = stmt.executeQuery("SELECT "+nameColo+" FROM "+table_name+" WHERE `firstname`='"+fname+"' AND `lastname`='"+lname+"' AND `telephone`='"+telephone+"' AND `gender`='"+gender+"' limit 1;"
				);
			return res.next();
		}catch(SQLException ex){
			ex.printStackTrace();
		}	
		return false;
	}	
	public int CountRows(String... condition){
		try{
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/"+database_name+"?useSSL=false","root", "Amakau@123"
			);
			String str = "SELECT COUNT(*) FROM Users " ;
			try{
				Statement stmt = conn.createStatement();
				if (condition.length != 0){
					str += "WHERE ";
					for (int i=0;i<condition.length;i++){
						str += condition[i];
					}
				}
				str+=";";
				ResultSet res = stmt.executeQuery(str);
				res.next();
				return res.getInt(1);
			}catch(SQLException ex){
				ex.printStackTrace();
			}
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return -1;
	}
	public String[][] GetUsers() throws Exception{
		int nRows = CountRows();
		if (nRows > 0) {
			String[][] users = new String[nRows][4];
			try{
				Connection conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3307/"+database_name+"?useSSL=false","root", "Amakau@123"
				);
				Statement stmt = conn.createStatement();
				ResultSet res = stmt.executeQuery("SELECT * FROM "+table_name+" LIMIT "+Integer.toString(nRows)+";");
				while(res.next()){
					nRows -= 1;
					String[] user = {
					res.getString("firstname"),res.getString("lastname"),
					res.getString("telephone"),Integer.toString(res.getInt("gender"))
					};
					users[nRows] = user;
				}
			}catch(SQLException ex){
				ex.printStackTrace();
			}	
			return users;
		}else{
			throw new Exception("No Users found");
		}
	}
}