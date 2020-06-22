<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jimen
  Date: 2020-06-10
  Time: 오후 12:57
  reference: https://www.jqueryscript.net/time-clock/dynamic-event-calendar-bootstrap.html
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test='${sessionScope.get("user") == null}'>
    <c:redirect url="login"/>
</c:if>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Main</title>

    <link href="https://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" href="css/calendar/calendar.css">
    <link rel="stylesheet" href="css/calendar/main.css">
    <link rel="stylesheet" href="css/calendar/spinner.css">
    <link rel="stylesheet" href="css/calendar/style.css">
    <style>
        .pull-left {
            float: left;
        }

        .pull-right {
            float: right;
        }
    </style>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/js/all.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
    <c:if test="${todoNumberList != null}">
        <script>
	        const root = '<%= request.getContextPath() %>';
	        const datas = [];
			// cls: 'bg-orange-alt', 'bg-green-alt', 'bg-red-alt', 'bg-cyan-alt', 'bg-purple-alt', 'bg-sky-blue-alt'
			<c:forEach var="todoNumber" items="${todoNumberList}">
			datas.push({
				title: 'Total',
				number: ${todoNumber.value.total},
				time: `${todoNumber.key} 00:00:00`,
				cls: 'bg-orange-alt'
			});
			datas.push({
				title: 'Progress',
				number: ${todoNumber.value.process},
				time: `${todoNumber.key} 00:00:00`,
				cls: 'bg-red-alt'
			});
			datas.push({
				title: 'complete',
				number: ${todoNumber.value.complete},
				time: `${todoNumber.key} 00:00:00`,
				cls: 'bg-green-alt'
			});
			</c:forEach>
        </script>
    </c:if>
    <script type="module" src="js/calendar/main.js"></script>
    <script src="js/calendar/etc.js"></script>
</head>
<body>
<div class="container" style="margin:30px auto">
    <header class="text-center text-white mb-2">
        <h1 class="display-4">${user.nickname}님의 todo list</h1>
        <%-- <p class="lead">본인의 todo 리스트를 만들어 보세요!</p> --%>
    </header>

    <div id="calendar" style="background-color:#fafafa"></div>
</div>
</body>
</html>