package hk.gov.housingauthority.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hk.gov.housingauthority.controller.CoorController;
import hk.gov.housingauthority.model.Coordinator;
import hk.gov.housingauthority.model.SrvyGrp;
import hk.gov.housingauthority.model.User;
import hk.gov.housingauthority.util.CommonFunction;
import hk.gov.housingauthority.util.Constant;

public class CoordinatorDaoImpl implements CoordinatorDao {
	private static final Logger logger = LoggerFactory.getLogger(CoordinatorDaoImpl.class);
	private DataSource dataSource;
	
	public ArrayList<Coordinator> getCoordinatorList(String userId) {

		ArrayList<Coordinator> coorList = new ArrayList<Coordinator>();
		Connection conn;
		ResultSet rs = null;
		String userID="";
		Coordinator coordinator = new Coordinator();
		User user = new User();
		ArrayList<SrvyGrp> srvyGrpList = new ArrayList<SrvyGrp>();

		String sql = "SELECT c.SRVY_GRP_ID, g.SRVY_GRP_NAME"
				+ " ,c.[COOR_USER_ID], u.USER_DIV_CODE, u.USER_DSGN, u.USER_RANK_CODE, u.[USER_NAME]"
				+ " ,c.[LAST_REC_TXN_USER_ID], c.[LAST_REC_TXN_TYPE_CODE], c.[LAST_REC_TXN_DATE]"
				+ " FROM [dbo].[ESV_COOR] c"
				+ " LEFT JOIN [dbo].[ESV_USER] u on c.COOR_USER_ID = u.[USER_ID]"
				+ " LEFT JOIN [dbo].[ESV_SRVY_GRP] g on c.[SRVY_GRP_ID] = g.[SRVY_GRP_ID]"
				+ " WHERE c.[LAST_REC_TXN_TYPE_CODE] <> '" + Constant.LAST_REC_TXN_TYPE_CODE_DELETED
				+ " AND [USER_ID]=?";		
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, userId);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				if (!userID.equals(rs.getNString("COOR_USER_ID"))){
					if (!"".equals(userID)){
						coordinator.setSrvyGrpList(srvyGrpList);
						coorList.add(coordinator);
					}
					coordinator = new Coordinator();
					coordinator.setLastRecTxnUserId(rs.getNString("LAST_REC_TXN_USER_ID"));
					coordinator.setLastRecTxnTypeCode(rs.getNString("LAST_REC_TXN_TYPE_CODE"));
					coordinator.setLastRecTxnDate(rs.getTimestamp("LAST_REC_TXN_DATE"));

					user = new User();
//					UserDaoImpl.getUser(rs);
					user.setUserId(rs.getNString("COOR_USER_ID"));
					user.setUserName(rs.getNString("USER_NAME"));
					user.setUserDivCode(rs.getNString("USER_DIV_CODE"));
					user.setUserDsgn(rs.getNString("USER_DSGN"));
					user.setUserRankCode(rs.getNString("USER_RANK_CODE"));

					coordinator.setUser(user);

					srvyGrpList = new ArrayList<SrvyGrp>();
				}
				SrvyGrp srvyGrp = new SrvyGrp();
				srvyGrp.setSrvyGrpId(rs.getInt("SRVY_GRP_ID"));
				srvyGrp.setSrvyGrpName(rs.getNString("SRVY_GRP_NAME"));
				srvyGrpList.add(srvyGrp);
				
				userID = rs.getNString("COOR_USER_ID");
			}
			coordinator.setSrvyGrpList(srvyGrpList);
			coorList.add(coordinator);
			conn.close();

			return coorList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Coordinator> getCoordinatorList() {

		ArrayList<Coordinator> coorList = new ArrayList<Coordinator>();
		Connection conn;
		ResultSet rs = null;
		String userID="";
		Coordinator coordinator = new Coordinator();
		User user = new User();
		ArrayList<SrvyGrp> srvyGrpList = new ArrayList<SrvyGrp>();

		String sql = "SELECT c.SRVY_GRP_ID, g.SRVY_GRP_NAME"
				+ " ,c.[COOR_USER_ID], u.USER_DIV_CODE, u.USER_DSGN, u.USER_RANK_CODE, u.[USER_NAME]"
				+ " ,c.[LAST_REC_TXN_USER_ID], c.[LAST_REC_TXN_TYPE_CODE], c.[LAST_REC_TXN_DATE]"
				+ " FROM [dbo].[ESV_COOR] c"
				+ " LEFT JOIN [dbo].[ESV_USER] u on c.COOR_USER_ID = u.[USER_ID]"
				+ " LEFT JOIN [dbo].[ESV_SRVY_GRP] g on c.[SRVY_GRP_ID] = g.[SRVY_GRP_ID]"
				+ " WHERE c.[LAST_REC_TXN_TYPE_CODE] <> '" + Constant.LAST_REC_TXN_TYPE_CODE_DELETED
				+ "' order by u.[USER_ID]";
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				if (!userID.equals(rs.getNString("COOR_USER_ID"))){
					if (!"".equals(userID)){
						coordinator.setSrvyGrpList(srvyGrpList);
						coorList.add(coordinator);
					}
					coordinator = new Coordinator();
					coordinator.setLastRecTxnUserId(rs.getNString("LAST_REC_TXN_USER_ID"));
					coordinator.setLastRecTxnTypeCode(rs.getNString("LAST_REC_TXN_TYPE_CODE"));
					coordinator.setLastRecTxnDate(rs.getTimestamp("LAST_REC_TXN_DATE"));

					user = new User();
//					UserDaoImpl.getUser(rs);
					user.setUserId(rs.getNString("COOR_USER_ID"));
					user.setUserName(rs.getNString("USER_NAME"));
					user.setUserDivCode(rs.getNString("USER_DIV_CODE"));
					user.setUserDsgn(rs.getNString("USER_DSGN"));
					user.setUserRankCode(rs.getNString("USER_RANK_CODE"));

					coordinator.setUser(user);

					srvyGrpList = new ArrayList<SrvyGrp>();
				}
				SrvyGrp srvyGrp = new SrvyGrp();
				srvyGrp.setSrvyGrpId(rs.getInt("SRVY_GRP_ID"));
				srvyGrp.setSrvyGrpName(rs.getNString("SRVY_GRP_NAME"));
				srvyGrpList.add(srvyGrp);
				
				userID = rs.getNString("COOR_USER_ID");
			}
			coordinator.setSrvyGrpList(srvyGrpList);
			coorList.add(coordinator);
			conn.close();

			return coorList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void insertCoordinator(Coordinator coordinator) {
		PreparedStatement preparedStatement = null;
		Connection conn;
		String insertSql = "INSERT [dbo].[ESV_COOR] ([COOR_USER_ID], [SRVY_GRP_ID], [LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE])"
				+ " VALUES (?,?,?,?,?)";
		try {
			conn = dataSource.getConnection();

			preparedStatement = conn.prepareStatement(insertSql);
			try {
				
				for (SrvyGrp srvyGrp: coordinator.getSrvyGrpList()){
					preparedStatement.setNString(1, coordinator.getUser().getUserId());
					preparedStatement.setInt(2, srvyGrp.getSrvyGrpId());

					preparedStatement.setNString(3, CommonFunction.getSsnUserId());
					preparedStatement.setNString(4, Constant.LAST_REC_TXN_TYPE_CODE_ACTIVE);	
					preparedStatement.setObject(5, new Date());
					preparedStatement.execute();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int deleteCoordinator(Coordinator coordinator) {
		PreparedStatement preparedStatement = null;
		Connection conn;
		String updateSql = "DELETE FROM [dbo].[ESV_COOR] " 
				 + " WHERE [COOR_USER_ID] = ?";

		int intResults = 0;
		try {
			conn = dataSource.getConnection();

			preparedStatement = conn.prepareStatement(updateSql);
			try {
				preparedStatement.setNString(1, coordinator.getUser().getUserId());
				intResults = preparedStatement.executeUpdate();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			conn.close();
			 return intResults;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int inactiveCoordinator(Coordinator coordinator) {
		PreparedStatement preparedStatement = null;
		Connection conn;
		String updateSql = "UPDATE [dbo].[ESV_COOR] " 
				 + " SET [LAST_REC_TXN_USER_ID] = ?,"
				 + " [LAST_REC_TXN_TYPE_CODE] = ?,"
				 + " [LAST_REC_TXN_DATE] = ?"
				 + " WHERE [COOR_USER_ID] = ?";

		int intResults = 0;
		try {
			conn = dataSource.getConnection();

			preparedStatement = conn.prepareStatement(updateSql);
			try {
				preparedStatement.setNString(1, CommonFunction.getSsnUserId());
				preparedStatement.setNString(2, Constant.LAST_REC_TXN_TYPE_CODE_DELETED);
				preparedStatement.setObject(3, new Date());
				preparedStatement.setNString(4, coordinator.getUser().getUserId());					

				intResults = preparedStatement.executeUpdate();
				 
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			conn.close();
			 return intResults;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int updateCoorLastLoginDate(String userId) {
		PreparedStatement preparedStatement = null;
		Connection conn;
		String updateSql = "UPDATE [dbo].[ESV_COOR] " 
				 + " SET [LAST_LGN_DATE] = ? "
				 + " WHERE [COOR_USER_ID] = ? ";

		int intResults = 0;
		try {
			conn = dataSource.getConnection();

			preparedStatement = conn.prepareStatement(updateSql);
			try {
				preparedStatement.setObject(1, new Date());
				preparedStatement.setNString(2, userId);
				intResults = preparedStatement.executeUpdate();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			conn.close();
			 return intResults;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public Coordinator getCoordinatorByID(String userId) {
		Connection conn;
		ResultSet rs = null;
		Coordinator coordinator = new Coordinator();
		User user = null;
		ArrayList<SrvyGrp> srvyGrpList = new ArrayList<SrvyGrp>();

		String sql = "SELECT c.SRVY_GRP_ID, g.SRVY_GRP_NAME"
				+ " ,c.[COOR_USER_ID], u.USER_DIV_CODE, u.USER_DSGN, u.USER_RANK_CODE, u.[USER_NAME]"
				+ " ,c.[LAST_REC_TXN_USER_ID], c.[LAST_REC_TXN_TYPE_CODE], c.[LAST_REC_TXN_DATE]"
				+ " FROM [dbo].[ESV_COOR] c"
				+ " LEFT JOIN [dbo].[ESV_USER] u on c.COOR_USER_ID = u.[USER_ID]"
				+ " LEFT JOIN [dbo].[ESV_SRVY_GRP] g on c.[SRVY_GRP_ID] = g.[SRVY_GRP_ID]"
				+ "where [COOR_USER_ID] = ? AND c.[LAST_REC_TXN_TYPE_CODE] <> '" + Constant.LAST_REC_TXN_TYPE_CODE_DELETED+"'";

		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, userId);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				if (user == null){ 
					user = new User();
					coordinator.setLastRecTxnUserId(rs.getNString("LAST_REC_TXN_USER_ID"));
					coordinator.setLastRecTxnTypeCode(rs.getNString("LAST_REC_TXN_TYPE_CODE"));
					coordinator.setLastRecTxnDate(rs.getTimestamp("LAST_REC_TXN_DATE"));

					user.setUserId(rs.getNString("COOR_USER_ID"));
					user.setUserName(rs.getNString("USER_NAME"));
					user.setUserDivCode(rs.getNString("USER_DIV_CODE"));
					user.setUserDsgn(rs.getNString("USER_DSGN"));
					user.setUserRankCode(rs.getNString("USER_RANK_CODE"));
					coordinator.setUser(user);
				}
				SrvyGrp srvyGrp = new SrvyGrp();
				srvyGrp.setSrvyGrpId(rs.getInt("SRVY_GRP_ID"));
				srvyGrp.setSrvyGrpName(rs.getNString("SRVY_GRP_NAME"));
				srvyGrpList.add(srvyGrp);
			}
			coordinator.setSrvyGrpList(srvyGrpList);

			conn.close();

			return coordinator;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	public int getTotal() {
		int total = 0;
		Connection conn;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) FROM ESV_COOR";
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

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
