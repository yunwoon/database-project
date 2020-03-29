package sms.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sms.domain.ContactVO;
import sms.domain.MessageVO;
import sms.domain.TalkVO;
import sms.persistence.SmsDAO;

@WebServlet("/SmsServlet")
public class SmsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SmsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String cmd = "";
		cmd = request.getParameter("key");
		
		if(cmd.equals("to")) {
			SmsDAO dao = new SmsDAO();
			ArrayList<ContactVO> contactList = dao.getContactList();
			request.setAttribute("contactList", contactList);
			RequestDispatcher view = request.getRequestDispatcher("to.jsp");
			view.forward(request, response);
		}else if(cmd.equals("from")) {
			SmsDAO dao = new SmsDAO();
			ArrayList<ContactVO> contactList = dao.getContactList();
			request.setAttribute("contactList", contactList);
			RequestDispatcher view = request.getRequestDispatcher("from.jsp");
			view.forward(request, response);
		}else if(cmd.equals("main")) {
			SmsDAO dao = new SmsDAO();
			ArrayList<TalkVO> talkList = dao.getTalkList();
			request.setAttribute("talkList", talkList);
			RequestDispatcher view = request.getRequestDispatcher("main.jsp");
			view.forward(request, response);
		}else if(cmd.equals("delete")) {
			TalkVO talkVO = new TalkVO();
			talkVO.setPhone(request.getParameter("phone"));
			
			SmsDAO dao = new SmsDAO();
			if(dao.delete(talkVO)) {
				ArrayList<TalkVO> talkList = dao.getTalkList();
				request.setAttribute("talkList", talkList);
				RequestDispatcher view = request.getRequestDispatcher("main.jsp");
				view.forward(request, response);
			}
		}else if(cmd.equals("search")) {
			SmsDAO dao = new SmsDAO();
			String word = request.getParameter("searc");
			ArrayList<TalkVO> talkList = dao.searchTalkList(word);
			request.setAttribute("talkList", talkList);
			RequestDispatcher view = request.getRequestDispatcher("main.jsp");
			view.forward(request, response);
		}else if(cmd.equals("search_contact1")) {
			SmsDAO dao = new SmsDAO();
			String word = request.getParameter("searchh");
			ArrayList<ContactVO> contactList = dao.searchContactList(word);
			request.setAttribute("contactList", contactList);
			RequestDispatcher view = request.getRequestDispatcher("to.jsp");
			view.forward(request, response);
		}else if(cmd.equals("search_contact2")) {
			SmsDAO dao = new SmsDAO();
			String word = request.getParameter("searchh");
			ArrayList<ContactVO> contactList = dao.searchContactList(word);
			request.setAttribute("contactList", contactList);
			RequestDispatcher view = request.getRequestDispatcher("from.jsp");
			view.forward(request, response);
		}else if(cmd.equals("list")) {
			SmsDAO dao = new SmsDAO();
			String name = request.getParameter("phone");
			//공지 띄우기
			String board = dao.getBoard(name);
			request.setAttribute("board", board);
			//왼쪽에 대화창 그냥 쭈우우욱 띄워주기 위함
			ArrayList<TalkVO> talkList = dao.getTalkList();
			request.setAttribute("talkList", talkList);
			//오른쪽 메시지창 쭈우욱 띄우자
			ArrayList<MessageVO> messageList = dao.getMessageList(name);
			request.setAttribute("messageList", messageList);
			RequestDispatcher view = request.getRequestDispatcher("msglist.jsp");
			view.forward(request, response);
		}else if(cmd.equals("search_msg")) {
			SmsDAO dao = new SmsDAO();
			String word = request.getParameter("searchh");
			String word2 = request.getParameter("id");
			//왼쪽에 대화창 그냥 쭈우우욱 띄워주기 위함
			ArrayList<TalkVO> talkList = dao.getTalkList();
			request.setAttribute("talkList", talkList);
			//오른쪽 메시지창 쭈우욱 띄우자
			ArrayList<MessageVO> messageList = dao.searchMessageList(word, word2);
			request.setAttribute("messageList", messageList);
			RequestDispatcher view = request.getRequestDispatcher("msglist.jsp");
			view.forward(request, response);
		}else if(cmd.equals("delete_msg")) {
			SmsDAO dao = new SmsDAO();
			String word = request.getParameter("content");
			String word2 = request.getParameter("id");
			
			if(dao.deleteMessage(word)) {
				//왼쪽에 대화창 그냥 쭈우우욱 띄워주기 위함
				ArrayList<TalkVO> talkList = dao.getTalkList();
				request.setAttribute("talkList", talkList);
				//오른쪽 메시지창 쭈우욱 띄우자
				ArrayList<MessageVO> messageList = dao.getMessageList2(word2);
				request.setAttribute("messageList", messageList);
				RequestDispatcher view = request.getRequestDispatcher("msglist.jsp");
				view.forward(request, response);
			}
		}else if(cmd.equals("board")) { /*공지 설정하고 어차피 다시 그 페이지에 있을 거니깐 !! 위에 공간도 만들고, 해당 아이디에 맞는 공지 불러오도록 select 도 추가해*/
			SmsDAO dao = new SmsDAO();
			String word = request.getParameter("content");
			String word2 = request.getParameter("id");
			
			if(dao.setBoard(word,word2)) {
				//공지 띄우기
				String board = dao.getBoard2(word2);
				request.setAttribute("board", board);
				//왼쪽에 대화창 그냥 쭈우우욱 띄워주기 위함
				ArrayList<TalkVO> talkList = dao.getTalkList();
				request.setAttribute("talkList", talkList);
				//오른쪽 메시지창 쭈우욱 띄우자
				ArrayList<MessageVO> messageList = dao.getMessageList2(word2);
				request.setAttribute("messageList", messageList);
				RequestDispatcher view = request.getRequestDispatcher("msglist.jsp");
				view.forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String cmd = "";
		cmd = request.getParameter("key");
		
		if(cmd.equals("send")) {
			MessageVO messageVO = new MessageVO();

			messageVO.setPhone(request.getParameter("phone"));
			messageVO.setType(request.getParameter("type"));
			messageVO.setContent(request.getParameter("content"));
			
			SmsDAO smsDAO = new SmsDAO();
			if (smsDAO.add(messageVO)) {
				ArrayList<TalkVO> talkList = smsDAO.getTalkList();
				request.setAttribute("talkList", talkList);
				RequestDispatcher view = request.getRequestDispatcher("main.jsp");
				view.forward(request, response);
			} else {
				SmsDAO dao = new SmsDAO();
				ArrayList<ContactVO> contactList = dao.getContactList();
				request.setAttribute("contactList", contactList);
				RequestDispatcher view = request.getRequestDispatcher("to.jsp");
				view.forward(request, response);
			}
		}else if(cmd.equals("receive")) {
			MessageVO messageVO = new MessageVO();
			
			messageVO.setPhone(request.getParameter("phone"));
			messageVO.setType(request.getParameter("type"));
			messageVO.setContent(request.getParameter("content"));
			
			SmsDAO smsDAO = new SmsDAO();
			if (smsDAO.add(messageVO)) {
				ArrayList<TalkVO> talkList = smsDAO.getTalkList();
				request.setAttribute("talkList", talkList);
				RequestDispatcher view = request.getRequestDispatcher("main.jsp");
				view.forward(request, response);
			} else {
				SmsDAO dao = new SmsDAO();
				ArrayList<ContactVO> contactList = dao.getContactList();
				request.setAttribute("contactList", contactList);
				RequestDispatcher view = request.getRequestDispatcher("from.jsp");
				view.forward(request, response);
			}
		}
	}
}