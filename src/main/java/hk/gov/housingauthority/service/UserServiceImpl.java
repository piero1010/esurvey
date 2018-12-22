package hk.gov.housingauthority.service;

import java.util.ArrayList;
import java.util.List;

import hk.gov.housingauthority.dao.UserDao;
import hk.gov.housingauthority.model.SrvyEmailHist;
import hk.gov.housingauthority.model.SrvyRec;
import hk.gov.housingauthority.model.User;

public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public ArrayList<User> searchUser(User qryUser){
		return userDao.searchUser(qryUser);
	}
	public void insertUser(ArrayList<User> userList){
		userDao.insertUser(userList);
	}
	
	public User getUserByDsgn(String designation){
		return userDao.getUserByDsgn( designation);
	}
	
	@Override
	public int getTotal() {
		return userDao.getTotal();
	}

	@Override
	public ArrayList<String> getRankList() {
		return userDao.getRankList();
	}

	@Override
	public ArrayList<User> getCoorUserList(User user, String colOrder, int start, int length) {
		return userDao.getCoorUserList(user,colOrder, start, length);
	}
	
	@Override
	public ArrayList<User> getUserList(User user, String colOrder, int start, int length) {
		return userDao.getUserList(user,colOrder, start, length);
	}

	@Override
	public String toJsonStr(ArrayList<User> srvyEmailHistList) {
		String result = new String("[");
		for (int i = 0; i < srvyEmailHistList.size(); i++) {
			result += srvyEmailHistList.get(i).toJsonString();
			if (i + 1 != srvyEmailHistList.size()) {
				result += ",";
			}
		}
		result += "]";
		return result;
	}
	
	
	@Override
	public String listToCoorJsonStr(List<User> userList) {
		String result = new String("[");
		for (int i = 0; i < userList.size(); i++) {
			result += userList.get(i).toCoorJsonString();
			if (i + 1 != userList.size()) {
				result += ",";
			}
		}
		result += "]";
		return result;
	}
	@Override
	public String listToJsonStr(List<User> userList) {
		String result = new String("[");
		for (int i = 0; i < userList.size(); i++) {
			result += userList.get(i).toJsonString();
			if (i + 1 != userList.size()) {
				result += ",";
			}
		}
		result += "]";
		return result;
	}

	@Override
	public boolean isValidUsers(String users) {
		return userDao.isValidUsers(users);
	}

	@Override
	public User getUserById(String userId) {
		return userDao.getUserById(userId);
	}
}
