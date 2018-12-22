package hk.gov.housingauthority.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

import hk.gov.housingauthority.model.SrvyAdtnInfo;
import hk.gov.housingauthority.util.Constant;

public class SrvyAdtnInfoDaoImpl implements SrvyAdtnInfoDao {

	private DataSource dataSource;

	public int insertSrvyAdtnInfo(SrvyAdtnInfo srvyAdtnInfo) {
		int last_inserted_id = 0;
		PreparedStatement preparedStatement = null;
		Connection conn = null;

		String insertSql = "INSERT [dbo].[ESV_SRVY_ADTN_INFO] ([SRVY_REC_ID], [ADTN_INFO_NAME], [ADTN_INFO_DESC], [LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE]) VALUES (?,?,?,?,?,?)";
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, srvyAdtnInfo.getSrvyRecId());
			preparedStatement.setNString(2, srvyAdtnInfo.getAdtnInfoName());
			preparedStatement.setNString(3, srvyAdtnInfo.getAdtnInfoDesc());
			preparedStatement.setNString(4, srvyAdtnInfo.getLastRecTxnUserId());
			preparedStatement.setNString(5, Constant.LAST_REC_TXN_TYPE_CODE_ACTIVE);
			preparedStatement.setObject(6, new Date());
			preparedStatement.execute();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				last_inserted_id = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return last_inserted_id;
	}

	public int updateSrvyAdtnInfo(SrvyAdtnInfo srvyAdtnInfo) {
		Connection conn = null;
		ResultSet rs = null;

		String sql = "UPDATE [dbo].[ESV_SRVY_ADTN_INFO] SET [ADTN_INFO_DESC] = ? , "
				+ "[LAST_REC_TXN_USER_ID] = ?, [LAST_REC_TXN_TYPE_CODE] = ? , [LAST_REC_TXN_DATE] = ? "
				+ "WHERE [SRVY_ADTN_INFO_ID] = ? ";

		int intResults = 0;
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			try {
				preparedStatement.setNString(1, srvyAdtnInfo.getAdtnInfoDesc());
				preparedStatement.setNString(2, srvyAdtnInfo.getLastRecTxnUserId());
				preparedStatement.setNString(3, Constant.LAST_REC_TXN_TYPE_CODE_UPDATE);
				preparedStatement.setObject(4, new Date());
				preparedStatement.setInt(5, srvyAdtnInfo.getSrvyAdtnInfoId());
				intResults = preparedStatement.executeUpdate();
			} catch (Exception ex) {
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return intResults;
	}

	public void deleteSrvyAdtnInfo(SrvyAdtnInfo srvyAdtnInfo) {
		Connection conn = null;
		String sql = "UPDATE [dbo].[ESV_SRVY_ADTN_INFO] SET "
				+ "[LAST_REC_TXN_USER_ID] = ?, [LAST_REC_TXN_TYPE_CODE] = ? , [LAST_REC_TXN_DATE] = ? "
				+ "WHERE [SRVY_ADTN_INFO_ID] = ? ";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setNString(1, srvyAdtnInfo.getLastRecTxnUserId());
			preparedStatement.setNString(2, Constant.LAST_REC_TXN_TYPE_CODE_DELETED);
			preparedStatement.setObject(3, new Date());
			preparedStatement.setInt(4, srvyAdtnInfo.getSrvyAdtnInfoId());
			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	public ArrayList<SrvyAdtnInfo> getSrvyAdtnInfoList(SrvyAdtnInfo srvyAdtnInfo, String colOrder, int start, int length) {
		Connection conn = null;
		ResultSet rs = null;
		String filter = " LAST_REC_TXN_TYPE_CODE!='"+Constant.LAST_REC_TXN_TYPE_CODE_DELETED+"' ";
		if (srvyAdtnInfo.getSrvyRecId() != null) {
			filter += " AND SRVY_REC_ID = " + srvyAdtnInfo.getSrvyRecId();
		}
		if ("".equals(colOrder)) {
			colOrder = " ORDER BY SRVY_ADTN_INFO_ID DESC ";
		} else {
			colOrder = " ORDER BY " + colOrder;
		}

		ArrayList<SrvyAdtnInfo> srvyAdtnInfoList = new ArrayList<SrvyAdtnInfo>();
		String condition = "SELECT ROW_NUMBER() OVER(" + colOrder + ") AS _ROW_, COUNT(*) OVER() AS _TOTAL_, ESC.* "
				+ "FROM [dbo].[ESV_SRVY_ADTN_INFO] AS ESC " + "WHERE " + filter;
		String sql = "SELECT * FROM (" + condition + ") AS PAGE WHERE _ROW_ BETWEEN ? AND ? " + colOrder;
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, start + 1);
			preparedStatement.setInt(2, start + length);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				srvyAdtnInfoList.add(this.getSrvyAdtnInfo(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return srvyAdtnInfoList;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public SrvyAdtnInfo getSrvyAdtnInfo(ResultSet rs) throws SQLException {
		SrvyAdtnInfo srvyAdtnInfo = new SrvyAdtnInfo();
		try {
			srvyAdtnInfo.setTotRec(rs.getInt("_TOTAL_"));
		} catch (Exception ex) {
		}
		try {
			srvyAdtnInfo.setSrvyAdtnInfoId(rs.getInt("SRVY_ADTN_INFO_ID"));
		} catch (Exception ex) {
		}
		try {
			srvyAdtnInfo.setSrvyRecId(rs.getInt("SRVY_REC_ID"));
		} catch (Exception ex) {
		}
		try {
			srvyAdtnInfo.setAdtnInfoName(rs.getNString("ADTN_INFO_NAME"));
		} catch (Exception ex) {
		}
		try {
			srvyAdtnInfo.setAdtnInfoDesc(rs.getNString("ADTN_INFO_DESC"));
		} catch (Exception ex) {
		}
		try {
			srvyAdtnInfo.setLastRecTxnUserId(rs.getNString("LAST_REC_TXN_USER_ID"));
		} catch (Exception ex) {
		}
		try {
			srvyAdtnInfo.setLastRecTxnTypeCode(rs.getNString("LAST_REC_TXN_TYPE_CODE"));
		} catch (Exception ex) {
		}
		try {
			srvyAdtnInfo.setLastRecTxnDate(rs.getTimestamp("LAST_REC_TXN_DATE"));
		} catch (Exception ex) {
		}
		return srvyAdtnInfo;
	}

	@Override
	public ArrayList<SrvyAdtnInfo> getSrvyAdtnInfoList(Integer srvyRecId) {
		Connection conn = null;
		ResultSet rs = null;
		ArrayList<SrvyAdtnInfo> srvyAdtnInfoList = new ArrayList<SrvyAdtnInfo>();
		String sql = "SELECT * "
				+ "FROM [dbo].[ESV_SRVY_ADTN_INFO] "
				+ "WHERE [LAST_REC_TXN_TYPE_CODE]!=? AND [SRVY_REC_ID] =?";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setNString(1, Constant.LAST_REC_TXN_TYPE_CODE_DELETED);
			preparedStatement.setInt(2, srvyRecId);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				srvyAdtnInfoList.add(this.getSrvyAdtnInfo(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return srvyAdtnInfoList;
	}

	@Override
	public boolean isSrvyAdtnInfoExists(SrvyAdtnInfo srvyAdtnInfo) {
		boolean result = false;
		Connection conn = null;
		ResultSet rs = null;
		String sql = "SELECT * "
				+ "FROM [dbo].[ESV_SRVY_ADTN_INFO] "
				+ "WHERE [LAST_REC_TXN_TYPE_CODE]!=? AND [SRVY_REC_ID] =?  AND [ADTN_INFO_NAME] =? ";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setNString(1, Constant.LAST_REC_TXN_TYPE_CODE_DELETED);
			preparedStatement.setInt(2, srvyAdtnInfo.getSrvyRecId());
			preparedStatement.setString(3, srvyAdtnInfo.getAdtnInfoName());
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return result;
	}

}
