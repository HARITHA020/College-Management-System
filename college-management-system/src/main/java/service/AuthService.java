package service;

import dao.UserDAO;

public class AuthService {

    private UserDAO userDAO = new UserDAO();

    public String checkRole(String email, String password) {
        return userDAO.checkUser(email, password);
    }
}