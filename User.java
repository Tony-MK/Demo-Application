class User {
	public String FirstName;
	public String LastName;
	public String Telephone;
	public boolean Gender;


	public User(String first_name,String last_name,String telephone,boolean gender)throws Exception{
		this.Telephone = telephone;
		this.FirstName = first_name;
		this.LastName = last_name;
		this.Gender = gender;
	}
}