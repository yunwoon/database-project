<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="sms.domain.*" import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FOR-MESG</title>
<link rel="stylesheet" href="resources/sms.css" type="text/css"></link>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
	<header>
		<a href="index.html" style="color: skyblue; text-decoration: none;">For
			Message</a>
			<% if(request.getAttribute("board") != null){ %>
		<a style="color: navy; text-decoration: none; font-size:15px; margin-left:1470px;"><%=request.getAttribute("board") %></a>
		<% } %>
	</header>
	<hr class="gray">

	<!-- 왼쪽 컨텐츠 -->
	<div id="left" class="verticalLine">
		<div style="margin-top: 30px;">
			<!-- 메시지창  바꿔야해 ★-->
			<div class="list-group" id="contact3"
				style="margin-top: 0px; margin-left: 60px; margin-right: 70px;">
				<%
					List<TalkVO> talkList = (List<TalkVO>) request.getAttribute("talkList");
					for (TalkVO vo : talkList) {
				%>
				<a
					href="http://localhost:8080/smsdb/SmsServlet?key=list&phone=<%=vo.getPhone()%>"
					class="list-group-item list-group-item-action ">
					<div class="d-flex w-100 justify-content-between">
						<h5 id="contact2" class="mb-1"><%=vo.getPhone()%></h5>
						<small><%=vo.getDate()%></small>
					</div>
					<p id="contact2"
						style="font-family: BBTreeGL !important; font-size: 16px;"
						class="mb-1"><%=vo.getLast()%></p>
				</a> <span style="color: salmon; margin-left: 0px;">&nbsp;</span>
				<%
					}
				%>
			</div>
		</div>
	</div>

	<!-- 오른쪽 컨텐츠 메시지 리스트 -->
	<div id="right">
		<div style="margin-top: 20px;">
			<!-- message search button -->
			<form action="http://localhost:8080/smsdb/SmsServlet"
				style="display: inline;">
				<div class="mx-auto input-group input-group-lg"
					style="justify-content: center; display: flex; width: 400px;">
					<div class="d-inline-flex input-group-prepend">
						<button class="btn btn-outline-secondary" name="key"
							value="search_msg" type="submit">#</button>
					</div>
					<input type="text" class="form-control"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-lg"
						placeholder="  search message" name="searchh"> &nbsp;
				</div>

				<!-- 메시지창 주우우우욱 -->
				<div class="list-group" id="contact4"
					style="margin-top: 30px; margin-left: 70px; margin-right: 70px;">
					<%
						List<MessageVO> messageList = (List<MessageVO>) request.getAttribute("messageList");
						for (MessageVO vo : messageList) {
							if (vo.getType().equals("r")) {
					%>
					<a id="contact2"
						class="list-group-item list-group-item-action list-group-item-primary"
						style="width: 30%; font-family: BBTreeGL !important; font-size: 16px;"><%=vo.getContent()%></a>
					<span
						style="color: salmon; opacity: 0.5; margin-left: 380px; text-align: left;">
						<a
						href="http://localhost:8080/smsdb/SmsServlet?key=delete_msg&content=<%=vo.getContent()%>&id=<%=vo.getPhone()%>">X&nbsp;</a>
						<a
						href="http://localhost:8080/smsdb/SmsServlet?key=board&content=<%=vo.getContent()%>&id=<%=vo.getPhone()%>">★</a>
					</span>
					<%
						} else {
					%>
					<a id="contact2" class="list-group-item list-group-item-action"
						style="width: 30%; margin-left: 800px; font-family: BBTreeGL !important; font-size: 16px;"><%=vo.getContent()%></a>
					<span style="color: salmon; opacity: 0.5; margin-left: 350px;">
						<a
						href="http://localhost:8080/smsdb/SmsServlet?key=delete_msg&content=<%=vo.getContent()%>&id=<%=vo.getPhone()%>">X&nbsp;</a>
						<a
						href="http://localhost:8080/smsdb/SmsServlet?key=board&content=<%=vo.getContent()%>&id=<%=vo.getPhone()%>">★</a>
					</span>
					<%
						} }
					%>
				</div>
			</form>
		</div>
	</div>
</body>
</html>