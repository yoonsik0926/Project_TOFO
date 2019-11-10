package com.teamList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.member.MemberDTO;
import com.sun.javafx.collections.MappingChange.Map;
import com.util.DBConn;

public class TeamListDAO {
	private Connection conn = DBConn.getConnection();
	
	public List<HashMap<String, Object>> listTeamMember(int num){
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select listNum, tl.num, tl.userId, userName, to_char(birth,'YYYY-MM-DD') birth, tel, rank " + 
					"from teamList tl " + 
					"left outer join member m on tl.userId=m.userId " + 
					"left outer join team t on tl.num=t.num where t.num=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("listNum", rs.getInt("listNum"));
				map.put("num", rs.getInt("num"));
				map.put("userId", rs.getString("userId"));
				map.put("userName", rs.getString("userName"));
				map.put("birth", rs.getString("birth"));
				map.put("tel", rs.getString("tel"));
				map.put("rank", rs.getString("rank"));
				
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}
	
//	
//	public TeamDTO readMember(String userId) {
//		TeamDTO dto = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		StringBuilder sb = new StringBuilder();
//		
//		try {
//			sb.append("select userId, userName, birth, created_Date from member where (instr(userId, ?)>=1) and enabled=1");
//			
//			pstmt=conn.prepareStatement(sb.toString());
//			pstmt.setString(1, userId);
//			rs = pstmt.executeQuery();
//			if(rs.next()) {
//				dto = new TeamDTO();
//				dto.setUserId(rs.getString("userId"));
//				dto.setUserId(rs.getString("userName"));
//				dto.setUserId(rs.getString("birth"));
//				dto.setUserId(rs.getString("created_Date"));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(rs!=null) {
//				try {
//					rs.close();
//				} catch (Exception e2) {
//				}
//			}
//			if(pstmt!=null) {
//				try {
//					pstmt.close();
//				} catch (Exception e2) {
//				}
//			}
//		}
//		return dto;
//	}
//	
//	public List<TeamDTO> readTeamMember(int num) {
//		List<TeamDTO> list = new ArrayList<TeamDTO>();
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		String sql;
//		
//		try {
//			sql = "select listNum, tl.userId, userName, birth, tel, rank " + 
//					"from teamList tl " + 
//					"left outer join member m on tl.userId=m.userId " + 
//					"left outer join team t on tl.num=t.num where t.num=?";
//			
//			pstmt=conn.prepareStatement(sql);
//			pstmt.setInt(1, num);
//			
//			rs = pstmt.executeQuery();
//			while(rs.next()) {
//				TeamDTO dto = new TeamDTO();
//				dto.setListNum(rs.getInt("listNum"));
//				dto.setUserId(rs.getString("userId"));
//				dto.setUserName(rs.getString("userName"));
//				dto.setBirth(rs.getString("birth"));
//				dto.setTel(rs.getString("tel"));
//				dto.setRank(rs.getString("rank"));
//				
//				list.add(dto);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(rs!=null) {
//				try {
//					rs.close();
//				} catch (Exception e2) {
//				}
//			}
//			if(pstmt!=null) {
//				try {
//					pstmt.close();
//				} catch (Exception e2) {
//				}
//			}
//		}
//		return list;		
//	}
//	
//	public int dataCount(int num) {
//		int result=0;
//		PreparedStatement pstmt=null;
//		ResultSet rs=null;
//		String sql;
//		
//		try {
//			sql="SELECT NVL(COUNT(*), 0) FROM teamList where num=?";
//			pstmt=conn.prepareStatement(sql);
//			pstmt.setInt(1, num);
//			rs=pstmt.executeQuery();
//			if(rs.next())
//				result=rs.getInt(1);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(rs!=null) {
//				try {
//					rs.close();
//				} catch (SQLException e) {
//				}
//			}
//			if(pstmt!=null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//				}
//			}
//		}
//		return result;
//	}
//	
//	public int updateRank(String leader, String userId, int num) {
//		int result=0;
//		PreparedStatement pstmt=null;
//		String sql;
//		
//		try {
//			sql="update teamList set rank='모임원' where userId=? and num=?";
//			
//			pstmt=conn.prepareStatement(sql);
//			pstmt.setString(1, leader);
//			pstmt.setInt(2, num);
//			
//			result = pstmt.executeUpdate();
//			pstmt.close();
//			pstmt=null;
//			
//			sql="update teamList set rank='모임장' where userId=? and num=?";
//			
//			pstmt=conn.prepareStatement(sql);
//			pstmt.setString(1, userId);
//			pstmt.setInt(2, num);
//			
//			result = pstmt.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(pstmt!=null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//				}
//			}
//		}
//		return result;
//	}
//	
//	public int deleteTeamList(String userId, String rank) {
//    	int result=0;
//    	PreparedStatement pstmt = null;
//    	String sql;
//    	
//    	try {
//    		if(rank.equals("모임장")) {
//    			sql="delete from teamList where userId=?";
//    			pstmt=conn.prepareStatement(sql);
//    			pstmt.setString(1, userId);
//    		}
//    		result = pstmt.executeUpdate();		
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(pstmt!=null) {
//				try {
//					pstmt.close();
//				} catch (Exception e2) {
//				}
//			}
//		}
//    	return result;
//    }
}
