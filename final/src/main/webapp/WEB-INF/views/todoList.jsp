<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jimen
  Date: 2020-06-22
  Time: 오전 6:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test='${sessionScope.get("user") == null}'>
    <c:redirect url="/login" context="/"/>
</c:if>
<html>
<head>
    <title>${user.nickname}의 Todo List</title>
    <jsp:include page="module.jsp" />
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/todo/main.css">

    <script>
		const root = "<%= request.getContextPath() %>";
		const today = '${today}';
		const todoList = [];
		<c:if test="${todoList != null}">
		<c:forEach var="todo" items="${todoList}">
		todoList.push({
			no: ${todo.no},
			title: '${todo.title}',
			time: new Date(${todo.time.timeInMillis}),
			complete: ${todo.complete}
		});
		</c:forEach>
		</c:if>

        window.onload = () => {
	        document.getElementById('title').textContent =
		        window.location.href.substring(window.location.href.lastIndexOf('/') + 1);
        }
    </script>
    <script type="module" src="<%= request.getContextPath() %>/js/myLibrary.js"></script>
    <script type="module" src="<%= request.getContextPath() %>/js/todo/index.js"></script>
</head>
<body>
<jsp:include page="nav.jsp" />
<div class="container">
    <header>
        <h1 id="title">Todo List</h1>

        <form class="new-task" id="input-form">
            <input type="text" name="text" id="input" placeholder="할 일을 입력하세요"/>
        </form>
    </header>
    <div id="result"></div>
</div>
</body>
</html>
