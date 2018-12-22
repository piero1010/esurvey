package hk.gov.housingauthority.dao;

import java.util.ArrayList;

import hk.gov.housingauthority.model.User;

public interface UserDao {

	public ArrayList<User> searchUser(User qryUser);
	
	public void insertUser(ArrayList<User> userList);
	
	public User getUserById(String UserId);
	
	public User getUserByDsgn(String designation);
	
	public User getUserByEmail(String email);

	public int getTotal();
	
	public ArrayList<String> getRankList();
	
	public ArrayList<User> getCoorUserList(User user,String colOrder, int start, int length);
	
	public ArrayList<User> getUserList(User user,String colOrder, int start, int length);
	
	public boolean isValidUsers(String users);
}
