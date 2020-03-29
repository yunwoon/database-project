package sms.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sms.domain.ContactVO;
import sms.domain.MessageVO;
import sms.domain.TalkVO;

public class SmsDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost/smsdb?useSSL=false&serverTimezone=UTC";

	void connect() {
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(jdbc_url, "mysql_id", "mysql_pwd");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void disconnect() {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean add(MessageVO vo) { /* 메시지 송수신 */
		connect();
		int res = 0;
		String sql = "insert into message(phone,type,content) values (?,?,?)";
		String sql2 = "update contact c, message m set c.`last` = ? where c.phone = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getPhone());
			pstmt.setString(2, vo.getType());
			pstmt.setString(3, vo.getContent());
			res = pstmt.executeUpdate();

			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, vo.getContent());
			pstmt.setString(2, vo.getPhone());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;
	}

	public ArrayList<ContactVO> getContactList() { /* 주소록 받아오기 */
		connect();
		ArrayList<ContactVO> contactList = new ArrayList<ContactVO>();
		String sql = "select * from contact";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ContactVO vo = new ContactVO();
				vo.setName(rs.getString("name"));
				vo.setPhone(rs.getString("phone"));
				vo.setLast(rs.getString("last"));
				contactList.add(vo);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return contactList;
	}

	public ArrayList<ContactVO> searchContactList(String str) { /* 주소록 검색기능 */
		connect();
		ArrayList<ContactVO> contactList = new ArrayList<ContactVO>();
		String sql = "select `name`, phone from contact where `name` like '%" + str + "%'";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ContactVO vo = new ContactVO();
				vo.setName(rs.getString("name"));
				vo.setPhone(rs.getString("phone"));
				contactList.add(vo);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return contactList;
	}

	public ArrayList<TalkVO> getTalkList() { /* 대화창 띄우기 (메인) */
		connect();
		ArrayList<TalkVO> talkList = new ArrayList<TalkVO>();
		String sql = "select c.name, m.date, c.last from contact c, message m where m.content = c.last order by m.date desc";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				TalkVO vo = new TalkVO();
				vo.setPhone(rs.getString("name"));
				vo.setDate(rs.getString("date"));
				vo.setLast(rs.getString("last"));
				talkList.add(vo);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return talkList;
	}

	public ArrayList<TalkVO> searchTalkList(String str) { /* 대화창 검색 기능 */
		connect();
		ArrayList<TalkVO> talkList = new ArrayList<TalkVO>();
		String sql = "select c.name, m.date, c.last from contact c, message m where c.last like '%" + str
				+ "%' group by c.name";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				TalkVO vo = new TalkVO();
				vo.setPhone(rs.getString("name"));
				vo.setDate(rs.getString("date"));
				vo.setLast(rs.getString("last"));
				talkList.add(vo);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return talkList;
	}

	public boolean delete(TalkVO vo) { /* 대화창 삭제 */
		connect();
		String sql = "delete from message m where m.phone = (select c.phone from contact c where c.name = ?)";
		int res = 0;
		// String sql = "delete from message m where m.phone = (select c.phone from
		// contact c where c.name = '엄마')";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getPhone());
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;
	}

	public ArrayList<MessageVO> getMessageList(String str) { /* 메시지창 띄우기 (개인별) */
		connect();
		ArrayList<MessageVO> messageList = new ArrayList<MessageVO>();
		String sql = "select phone, `date`, content, `type` from message m where m.phone = (select c.phone from contact c where c.name=?) order by m.date";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, str);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MessageVO vo = new MessageVO();
				vo.setPhone(rs.getString("phone"));
				vo.setDate(rs.getString("date"));
				vo.setContent(rs.getString("content"));
				vo.setType(rs.getString("type"));
				messageList.add(vo);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return messageList;
	}

	public ArrayList<MessageVO> getMessageList2(String str) { /* 메시지창 띄우기 (개인별) */
		connect();
		ArrayList<MessageVO> messageList = new ArrayList<MessageVO>();
		String sql = "select phone, `date`, content, `type` from message m where m.phone = '" + str
				+ "' order by m.date";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MessageVO vo = new MessageVO();
				vo.setPhone(rs.getString("phone"));
				vo.setDate(rs.getString("date"));
				vo.setContent(rs.getString("content"));
				vo.setType(rs.getString("type"));
				messageList.add(vo);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return messageList;
	}

	public ArrayList<MessageVO> searchMessageList(String str, String str2) { /* 메시지 검색기능 */
		connect();
		ArrayList<MessageVO> messageList = new ArrayList<MessageVO>();
		String sql = "select m.phone, m.`date`, m.content, m.`type` from message m where content like '%" + str
				+ "%' group by m.phone = (select c.phone from contact c where c.phone='" + str2 + "')";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MessageVO vo = new MessageVO();
				vo.setPhone(rs.getString("phone"));
				vo.setDate(rs.getString("date"));
				vo.setContent(rs.getString("content"));
				vo.setType(rs.getString("type"));
				messageList.add(vo);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return messageList;
	}

	public boolean deleteMessage(String str) { /* 메시지 삭제 */
		connect();
		String sql = "delete from message where content = '" + str + "'";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;
	}

	public boolean setBoard(String str, String str2) { /* 공지 생성 */
		connect();

		String sql = "insert into board(msgnum,content) values (?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, str2);
			pstmt.setString(2, str);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;
	}

	public String getBoard(String str) { /* 공지 띄우기 */
		connect();

		String sql = "select b.content from board b where b.msgnum in (select m.phone from message m where m.phone = (select c.phone from contact c where c.name = ?)) order by brdnum desc";
		String result = null;
		int num = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, str);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next() && num == 0) {
				result = rs.getString("content");
				num++;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return result;
		} finally {
			disconnect();
		}
		return result;
	}

	public String getBoard2(String str) { /* 공지 띄우기 */
		connect();

		String sql = "select b.content from board b where b.msgnum in (select m.phone from message m where m.phone=?) order by brdnum desc";
		String result = null;
		int num = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, str);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next() && num == 0) {
				result = rs.getString("content");
				num++;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return result;
		} finally {
			disconnect();
		}
		return result;
	}
}
