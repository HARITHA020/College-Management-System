package service;

import dao.UserDAO;

public class AuthService {
	UserDAO user_dao=new UserDAO();
	
	public String checkrole(String email,String password) {
		 return user_dao.checkUser(email,password);
	}

}
