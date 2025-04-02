package com.dsgp.library;


import java.util.HashMap;
import java.security.SecureRandom;

public class User {
	private String username;
    private String password;
    private String role;
    
    private static HashMap<String, User> users = new HashMap<>();

    // Default constructor
    public User() {
    	username = "CHIN";
    	password = "Chin123";
    	role = "admin";
    }

    // Primary constructor
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;

        if (!users.containsKey(username)) {
            users.put(username, this);
            System.out.println("User registered: " + username);
        } else {
            System.out.println("User already exists.");
        }
    }

    // Copy constructor
    public User(User cp) {
        this.username = cp.username;
        this.password = cp.password;
        this.role = cp.role;
    }

    
    
    // Check password
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    // Change password
    public void changePassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password changed successfully.");
    }

    // Static method to change password
    public static void changePassword(String username, String oldPassword, String newPassword) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            if (user.checkPassword(oldPassword)) {
                user.changePassword(newPassword);
            } else {
                System.out.println("Incorrect old password.");
            }
        } else {
            System.out.println("User not found.");
        }
    }

    // Static method to log in
    public static void login(String username, String password) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            if (user.checkPassword(password)) {
                System.out.println("Login successful. Welcome, " + username + " (" + user.getRole() + ")");
            } else {
                System.out.println("Invalid password.");
            }
        } else {
            System.out.println("User not found.");
        }
    }
    
    public static void generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        String password = random.ints(length, 33, 126)
                .mapToObj(i -> String.valueOf((char) i))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        System.out.println("Generated Password: " + password);
    }

    private static boolean verifyLogin(String username, String password, User adminUser ) {
    	// Check against the admin user
    	if (adminUser .getUsername().equals(username) && adminUser .getPassword().equals(password)) {
    		return true;
    	 }
    		return false;
    	 }
    
    // Get a user by username
    public static User getUser(String username) {
        return users.get(username);
    }
  

    // Getters
    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public static boolean verifyLogin(String username, String password) {
	    // Check if the user exists
	    if (users.containsKey(username)) {
	        User user = users.get(username);
	        // Check if the password matches
	        return user.checkPassword(password);
	    }
	    return false; // User not found
	}
	

}
