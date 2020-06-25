<%--
  Created by IntelliJ IDEA.
  User: jimen
  Date: 2020-06-26
  Time: 오전 12:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar bg-light">
    <ul class="navbar-nav">
        <li class="nav-item">
            <a href="<%= request.getContextPath() %>/logout" class="nav-link">로그아웃</a>
        </li>
        <li class="nav-item">
            <a href="<%= request.getContextPath() %>/user/update" class="nav-link">회원수정</a>
        </li>
    </ul>
</nav>