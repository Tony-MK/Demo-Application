import java.sql.*;
class DB{
	//Male and female representing
	public final String MALE = "Male";
	protected final String FEMALE = "Female";

	//Database Variables
	protected String TABLE_NAME;
	protected String DB_NAME;
	protected final String DB_USERNAME = "root";
	protected final String DB_PASSWORD = "password";
	public DB(String dbName,String userstable){
		this.DB_NAME = dbName;
		this.TABLE_NAME = userstable;
	}
	private String nameColo = "firstname,lastname,telephone,gender";
	public String booltoGender(boolean gender){
		if(gender)return FEMALE;
		else return MALE;
	}
	public void CreateUser(String fname,String lname,String telephone,String dob,Boolean gender){
		try{
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/"+DB_NAME+"?useSSL=false",DB_USERNAME, DB_PASSWORD
			);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(
				"INSERT INTO "+TABLE_NAME+" ("+nameColo+") VALUES('"+fname+"','"+lname+"','"+telephone+"','"+","+dob+","+booltoGender(gender)+"');"
			);
		}catch(SQLException ex){
			ex.printStackTrace();
		}	
	}
	public double GetRatio(){return (double) (CountRows("`gender`='"+FEMALE+"'")/CountRows("`gender`='"+MALE+"'"));}

	public boolean CheckUser(String fname,String lname,String telephone,String dob,boolean gender){
		try{
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/"+DB_NAME+"?useSSL=false","root", "Amakau@123"
			);
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery("SELECT "+nameColo+" FROM "+TABLE_NAME+" WHERE `firstname`='"+fname+"' AND `lastname`='"+lname+"' AND `telephone`='"+telephone+"' AND `date_of_birth`='"+dob+"' AND `gender`='"+booltoGender(gender)+"' limit 1;"
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
					"jdbc:mysql://localhost:3307/"+DB_NAME+"?useSSL=false","root", "Amakau@123"
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
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/"+DB_NAME+"?useSSL=false","root", "Amakau@123"
			);
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM "+TABLE_NAME+" LIMIT "+Integer.toString(nRows)+";");
			while(res.next()){
				nRows -= 1;
				String[] user = {
				res.getString("firstname"),res.getString("lastname"),
				res.getString("telephone"),res.getString("date_of_birth"),
				res.getString("gender"),
				};
				users[nRows] = user;
			}
			return users;
		}else{
			throw new Exception("No Users found");
		}
	}
}