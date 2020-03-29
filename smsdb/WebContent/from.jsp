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
	<header><a href="index.html" style="color:skyblue; text-decoration:none;">For Message</a></header>
	<hr class="gray">
	<div>
	
		<!-- 왼쪽 컨텐츠 -->
		<div id="left" class="verticalLine">
			<div style="margin-top:30px;">
				<!-- 검색 창 -->
				<form action="http://localhost:8080/smsdb/SmsServlet"
					style="display: inline;">
					<div class="mx-auto input-group input-group-lg"
						style="justify-content: center; display: flex; width: 320px; ">
						<div class="d-inline-flex input-group-prepend">
							<button class="btn btn-outline-secondary" name="key"
								value="search_contact2" type="submit">#</button>
						</div>
						<input type="text" class="form-control"
							aria-label="Sizing example input"
							aria-describedby="inputGroup-sizing-lg"
							placeholder="  search people" name="searchh"> &nbsp;
					</div>
				</form>
				<!-- 주소록  -->
				<div class="list-group" id="contact" style="margin-top:30px; margin-left:70px; margin-right:70px;">
					<%
						List<ContactVO> contactList = (List<ContactVO>)request.getAttribute("contactList");
						for (ContactVO vo : contactList) {
					%>
					<a href="#" class="list-group-item list-group-item-action" style="color:#5f6769;">
						<div class = "d-flex w-50 justify-content-between">
							<h5 class="mb-1" style="font-size:17px;"><%=vo.getName() %></h5>
						</div>
						<p class = "mb-1"><%=vo.getPhone() %></p>
					</a>
					<%} %>
				</div>
			</div>
		</div>
		
			<!-- 오른쪽 컨텐츠 -->
			<div id="right">
				<div style="margin-top:20px;">
				<form action="http://localhost:8080/smsdb/SmsServlet?key=receive" method="post">
					<div style="margin-bottom:40px;">
						<button type="button" class="btn btn-secondary btn-lg">FROM
						<input style="display:inline-block;" type="text" class="form-control" name="phone"></button>
						<input type="hidden" name="type" value="r" />
					</div>
					<textarea name="content" placeholder="&nbsp;&nbsp;80자 이내로 메시지를 작성해주세요."></textarea><br><br>
					<div style="float:right; margin-right:120px;">
						<button type="reset" class="btn btn-light btn-lg">RE-WRITE</button>&nbsp;&nbsp;
						<button type="submit" class="btn btn-secondary btn-lg">GET</button>
					</div>
				</form>
				</div>
			</div>
		</div>
</body>
</html>