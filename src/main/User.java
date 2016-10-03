package main;

public class User {
private int Id=0;
private String userName="Dabom.com";
private String password="god";
private Boolean isAdmin=true;
int getId(){
	return Id;
}
String getPassword(){
	return password;
}
String getuserName(){
	return userName;
}
Boolean getAdmin(){
	return isAdmin;
}
public User(){
	
}
public User(int anId,String aUserName,String aPassword,Boolean isAAdmin){
	Id=anId;
	userName = aUserName;
	password=aPassword;
	isAdmin = isAAdmin;
}
}
