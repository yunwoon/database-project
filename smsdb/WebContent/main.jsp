<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="sms.domain.*"
	import = "java.util.List" %>
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
	<header><a href="index.html" style="color:skyblue; text-decoration:none;">For Message</a></header>
	<hr class="gray">

	<!-- search button -->
	<form action="http://localhost:8080/smsdb/SmsServlet" style="display:inline;">
	<div class="mx-auto input-group input-group-lg"
		style="justify-content: center; display: flex; width: 400px;">
			<div class="d-inline-flex input-group-prepend">
				<button class="btn btn-outline-secondary" name="key" value="search" type="submit">#</button>
			</div>
			<input type="text" class="form-control" aria-label="Sizing example input"
				aria-describedby="inputGroup-sizing-lg" placeholder="  search talk" name="searc"> &nbsp;
			<button type="submit" class="btn btn-secondary" name="key" value="to">&nbsp;To&nbsp;</button>
			&nbsp;
			<button type="submit" class="btn btn-secondary" name="key" value="from">From</button>
	</div>
	</form>
	<div style="margin-top:30px;" class="list-group">
		<%
			List<TalkVO> talkList = (List<TalkVO>)request.getAttribute("talkList");
			for (TalkVO vo : talkList) {
		%>
  			<a id="cont" href="http://localhost:8080/smsdb/SmsServlet?key=list&phone=<%=vo.getPhone() %>" 
  			class="list-group-item list-group-item-action">
    			<div class="d-flex w-100 justify-content-between">
      				<h5 id="contact2" class="mb-1"><%=vo.getPhone() %></h5>
      				<small><%=vo.getDate() %></small>
    			</div>
    		<p class="mb-1"><%=vo.getLast() %></p>
			</a>
    		<span style="color:salmon; margin-left:1220px;">
    		<a href="http://localhost:8080/smsdb/SmsServlet?key=delete&phone=<%=vo.getPhone() %>" style="display:inline; opacity:0.5; font-size:12px;">X</a></span>
  		<%} %>
  </div>
</body>
</html>