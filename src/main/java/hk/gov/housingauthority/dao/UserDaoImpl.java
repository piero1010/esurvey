package hk.gov.housingauthority.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.util.StringUtils;

import hk.gov.housingauthority.model.Record;
import hk.gov.housingauthority.model.SrvyEmailHist;
import hk.gov.housingauthority.model.SrvyPpt;
import hk.gov.housingauthority.model.SrvyTmpl;
import hk.gov.housingauthority.model.User;
import hk.gov.housingauthority.util.Constant;

public class UserDaoImpl  implements UserDao {

	private DataSource dataSource;

	public ArrayList<User> searchUser(User qryUser){
		Connection conn;
		ResultSet rs = null;
		ArrayList<User> userList = new ArrayList<User>();

		String SelectSql = "select [USER_ID], [USER_DSGN], [USER_NAME], [USER_EMAIL], [USER_DIV_CODE], [USER_RANK_CODE], [USER_STS_IND], [LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE] from [dbo].[ESV_USER] where 1=1 ";

		if (qryUser.getUserId() != null){
			SelectSql += " and [USER_ID] like ?";
		}
		if (qryUser.getUserDsgn() != null){
			SelectSql += " and [USER_DSGN] like ?";
		}
		if (qryUser.getUserName() != null){
			SelectSql += " and [USER_NAME] like ?";
		}
		if (qryUser.getUserEmail() != null){
			SelectSql += " and [USER_EMAIL] like ?";
		}
		if (qryUser.getUserDivCode() != null){
			SelectSql += " and [USER_DIV_CODE] like ?";
		}
		if (qryUser.getUserRankCode() != null){
			SelectSql += " and [USER_RANK_CODE] like ?";
		}
		if (qryUser.getUserStsInd() != null){
			SelectSql += " and [USER_STS_IND] like ?";
		}

		try {
			conn = dataSource.getConnection();
			int i = 0;
			PreparedStatement preparedStatement = conn.prepareStatement(SelectSql);
			if (qryUser.getUserId() != null){
				i++;
				preparedStatement.setString(i, qryUser.getUserId() );
			}
			if (qryUser.getUserDsgn() != null){
				i++;
				preparedStatement.setString(i, qryUser.getUserDsgn());
			}
			if (qryUser.getUserName() != null){
				i++;
				preparedStatement.setString(i, qryUser.getUserName());
			}
			if (qryUser.getUserEmail() != null){
				i++;
				preparedStatement.setString(i, qryUser.getUserEmail());
			}
			if (qryUser.getUserDivCode() != null){
				i++;
				preparedStatement.setString(i, qryUser.getUserDivCode());
			}
			if (qryUser.getUserRankCode() != null){
				i++;
				preparedStatement.setString(i, qryUser.getUserRankCode());
			}
			if (qryUser.getUserStsInd() != null){
				i++;
				preparedStatement.setString(i, qryUser.getUserStsInd());
			}
			
			rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				userList.add(this.getUser(rs));
			}
			conn.close();

			return userList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public void insertUser(ArrayList<User> userArrayList) {
		PreparedStatement preparedStatement = null;
		Connection conn;
		String insertSql = "INSERT [dbo].[ESV_USER] ([USER_ID], [USER_DSGN], [USER_NAME], [USER_EMAIL], [USER_DIV_CODE], [USER_RANK_CODE], [USER_STS_IND], [LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE]) VALUES (?,?,?,?,?,?,?,?,?,?)";
		try {
			conn = dataSource.getConnection();

			preparedStatement = conn.prepareStatement(insertSql);
			for (int i = 0; i < userArrayList.size(); i++) {
				if (userArrayList.get(i).getUserId() != null) {
					try {
						preparedStatement.setNString(1, userArrayList.get(i).getUserId());
						preparedStatement.setNString(2, userArrayList.get(i).getUserDsgn());
						preparedStatement.setNString(3, userArrayList.get(i).getUserName());
						preparedStatement.setNString(4, userArrayList.get(i).getUserEmail());
						preparedStatement.setNString(5, userArrayList.get(i).getUserDivCode());
						preparedStatement.setNString(6, userArrayList.get(i).getUserRankCode());
						preparedStatement.setNString(7, userArrayList.get(i).getUserStsInd());
						preparedStatement.setNString(8, userArrayList.get(i).getLastRecTxnUserId());
						preparedStatement.setNString(9, userArrayList.get(i).getLastRecTxnTypeCode());
						preparedStatement.setObject(10, userArrayList.get(i).getLastRecTxnDate());

						preparedStatement.execute();
					} catch (Exception ex) {
						System.out.print(userArrayList.get(i).getUserId());
						System.out.print(userArrayList.get(i).getUserDsgn());
						System.out.print(userArrayList.get(i).getUserName());
						System.out.print(userArrayList.get(i).getUserEmail());
						System.out.print(userArrayList.get(i).getUserDivCode());
						System.out.print(userArrayList.get(i).getUserRankCode());
						System.out.print(userArrayList.get(i).getUserStsInd());
						System.out.print(userArrayList.get(i).getLastRecTxnUserId());
						System.out.print(userArrayList.get(i).getLastRecTxnTypeCode());
						System.out.println(userArrayList.get(i).getLastRecTxnDate());

					}
				}
			}
			// preparedStatement.executeBatch();
			// conn.commit();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public User getUserById(String UserId){
		Connection conn;
		ResultSet rs = null;
		User user = null;

		String sql = "select [USER_ID], [USER_DSGN], [USER_NAME], [USER_EMAIL], [USER_DIV_CODE], "
				+ "[USER_RANK_CODE], [USER_STS_IND], [LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], "
				+ "[LAST_REC_TXN_DATE] from [dbo].[ESV_USER] where [USER_ID] = ?";
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setNString(1, UserId);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				user = this.getUser(rs);
			}
			conn.close();

			return user;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[UserDao] Cannot get : " + UserId);
			return null;
		}
	}
	
	public User getUserByDsgn(String designation){
		Connection conn;
		ResultSet rs = null;
		User user = null;

		String sql = "select [USER_ID], [USER_DSGN], [USER_NAME], [USER_EMAIL], [USER_DIV_CODE], "
				+ "[USER_RANK_CODE], [USER_STS_IND], [LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], "
				+ "[LAST_REC_TXN_DATE] from [dbo].[ESV_USER] where [USER_DSGN] = ?";
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setNString(1, designation);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				user = this.getUser(rs);
			}
			conn.close();

			return user;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[UserDao] Cannot get : " + designation);
			return null;
		}
	}
	
	public User getUserByEmail(String email){
		Connection conn;
		ResultSet rs = null;
		User user = null;

		String sql = "select [USER_ID], [USER_DSGN], [USER_NAME], [USER_EMAIL], [USER_DIV_CODE], "
				+ "[USER_RANK_CODE], [USER_STS_IND], [LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], "
				+ "[LAST_REC_TXN_DATE] from [dbo].[ESV_USER] where [USER_EMAIL] = ?";
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setNString(1, email);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				user = this.getUser(rs);
			}
			conn.close();

			return user;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[UserDao] Cannot get : " + email);
			return null;
		}
	}


	public int getTotal() {
		int total = 0;
		Connection conn;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) FROM ESV_USER";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				total = rs.getInt(1);
			}
			conn.close();
		} catch (Exception e) {
			return total;
		}
		return total;
	}

	public ArrayList<String> getRankList() {		
		Connection conn = null;
		ResultSet rs = null;
		ArrayList<String> rankList= new ArrayList<String>();
		String sql = "SELECT DISTINCT USER_RANK_CODE "
				+ "FROM [DBO].[ESV_USER] "
				+ "WHERE USER_RANK_CODE IS NOT NULL "
				+ "ORDER BY USER_RANK_CODE";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				rankList.add(rs.getNString("USER_RANK_CODE"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return rankList;
	}
	
	public static User getUser(ResultSet rs) throws SQLException{
		User user = new User();
		try{user.setTotRec(rs.getInt("_TOTAL_"));}catch(Exception ex){}
		try{user.setUserId(rs.getString("USER_ID"));}catch(Exception ex){}
		try{user.setUserDsgn(rs.getString("USER_DSGN"));}catch(Exception ex){}
		try{user.setUserName(rs.getString("USER_NAME"));}catch(Exception ex){}
		try{user.setUserEmail(rs.getString("USER_EMAIL"));}catch(Exception ex){}
		try{user.setUserDivCode(rs.getString("USER_DIV_CODE"));}catch(Exception ex){}
		try{user.setUserRankCode(rs.getString("USER_RANK_CODE"));}catch(Exception ex){}
		try{user.setUserStsInd(rs.getString("USER_STS_IND"));}catch(Exception ex){}
		try{user.setLastRecTxnUserId(rs.getString("LAST_REC_TXN_USER_ID"));}catch(Exception ex){}
		try{user.setLastRecTxnTypeCode(rs.getString("LAST_REC_TXN_TYPE_CODE"));}catch(Exception ex){}
		try{user.setLastRecTxnDate(rs.getTimestamp("LAST_REC_TXN_DATE"));}catch(Exception ex){}
		return user;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public ArrayList<User> getCoorUserList(User user, String colOrder, int start, int length) {
		Connection conn = null;
		ResultSet rs = null;
		String filter=" EU.LAST_REC_TXN_TYPE_CODE!='"+Constant.LAST_REC_TXN_TYPE_CODE_DELETED+"' ";
		
		if(user.getUserDivCode()!=null){
			filter+=" AND USER_DIV_CODE like '"+ user.getUserDivCode()+"'";
		}
		if(user.getUserRankCode()!=null){
			filter+=" AND USER_RANK_CODE like '"+ user.getUserRankCode()+"'";
		}
		if(user.getUserDsgn()!=null){
			filter+=" AND USER_DSGN like '"+ user.getUserDsgn()+"'";
		}
		if(user.getUserName()!=null){
			filter+=" AND USER_NAME like '"+ user.getUserName()+"'";
		}
		if("".equals(colOrder)){
			colOrder = " ORDER BY USER_DIV_CODE,USER_RANK_CODE,USER_DSGN ";
		}else{
			colOrder = " ORDER BY "+colOrder;
		}
		
		ArrayList<User> userList = new ArrayList<User>();
		String condition = "SELECT ROW_NUMBER() OVER(" + colOrder + ") AS _ROW_, COUNT(*) OVER() AS _TOTAL_, * "
				+ "FROM [dbo].[ESV_USER] AS EU "				
				+ "WHERE "+filter;
		String sql = "SELECT * FROM ("+condition+") AS PAGE WHERE _ROW_ BETWEEN ? AND ? "+colOrder;
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, start + 1);
			preparedStatement.setInt(2, start + length);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				userList.add(this.getUser(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return userList;
	}
	
	@Override
	public ArrayList<User> getUserList(User user, String colOrder, int start, int length) {
		Connection conn = null;
		ResultSet rs = null;
		String filter=" PPT_USER_ID IS NULL ";
		
		if(user.getUserDivCode()!=null){
			filter+=" AND USER_DIV_CODE = '"+ user.getUserDivCode()+"'";
		}
		if(user.getUserRankCode()!=null){
			filter+=" AND USER_RANK_CODE = '"+ user.getUserRankCode()+"'";
		}
		if(user.getUserDsgn()!=null){
			filter+=" AND USER_DSGN like '"+ user.getUserDsgn()+"'";
		}
		if(user.getUserName()!=null){
			filter+=" AND USER_NAME like '"+ user.getUserName()+"'";
		}
		if("".equals(colOrder)){
			colOrder = " ORDER BY USER_DIV_CODE,USER_RANK_CODE,USER_DSGN ";
		}else{
			colOrder = " ORDER BY "+colOrder;
		}
		
		ArrayList<User> userList = new ArrayList<User>();
		String condition = "SELECT ROW_NUMBER() OVER(" + colOrder + ") AS _ROW_, COUNT(*) OVER() AS _TOTAL_, EU.* "
				+ "FROM [dbo].[ESV_USER] AS EU "
				+ "LEFT JOIN [dbo].[ESV_SRVY_PPT] AS ESP ON ESP.PPT_USER_ID=EU.USER_ID AND "
				+ "ESP.LAST_REC_TXN_TYPE_CODE!='"+Constant.LAST_REC_TXN_TYPE_CODE_DELETED+"' AND "
				+ "SRVY_REC_ID=? "
				+ "WHERE "+filter;
		String sql = "SELECT * FROM ("+condition+") AS PAGE WHERE _ROW_ BETWEEN ? AND ? "+colOrder;
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(user.getFilterList().get("srvyRecId")));
			preparedStatement.setInt(2, start + 1);
			preparedStatement.setInt(3, start + length);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				userList.add(this.getUser(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return userList;
	}


	@Override
	public boolean isValidUsers(String users) {
		Connection conn = null;
		ResultSet rs = null;
		int result = 0;
		boolean isValid = false;
		String sql = "SELECT COUNT(*) AS TOT "
				+ "FROM [DBO].[ESV_USER] "
				+ "WHERE USER_ID IN ("+users+") ";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				if(StringUtils.countOccurrencesOf(users,",")+1==rs.getInt("TOT")){
					isValid = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return isValid;
	}
}
