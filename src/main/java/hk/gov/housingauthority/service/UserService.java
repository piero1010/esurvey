package hk.gov.housingauthority.service;

import java.util.ArrayList;
import java.util.List;

import hk.gov.housingauthority.model.SrvyRec;
import hk.gov.housingauthority.model.SrvyTmpl;
import hk.gov.housingauthority.model.User;

public interface UserService {
	public ArrayList<User> searchUser(User qryUser);
	public void insertUser(ArrayList<User> userList);
	public int getTotal();
	public ArrayList<String> getRankList();
	public ArrayList<User> getUserList(User user,String colOrder, int start, int length);
	public ArrayList<User> getCoorUserList(User user,String colOrder, int start, int length);
	public User getUserById(String userId);
	public String toJsonStr(ArrayList<User> user);
	public boolean isValidUsers(String users);
	public User getUserByDsgn(String designation);
	public String listToJsonStr(List<User> user);
	public String listToCoorJsonStr(List<User> user);
	
}
