<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jimen
  Date: 2020-06-21
  Time: 오전 1:13
  reference: https://bootsnipp.com/snippets/92gmX
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test='${sessionScope.get("user") != null}'>
    <c:redirect url="index"/>
</c:if>
<html>
<head>
    <title>Login</title>
    <jsp:include page="module.jsp" />
    <link rel="stylesheet" href="css/login/main.css">

    <script>
		const root = "<%= request.getContextPath() %>";
		<c:if test='${msg != null}'>
		alert('${msg}');
		</c:if>
    </script>
    <script src="js/login/main.js"></script>
</head>
<body>
<div class="login-reg-panel">
    <div class="login-info-box">
        <h2>계정이 있으신가요?</h2>
        <p>가입하신 아이디로 로그인을 해주세요.</p>
        <label id="label-register" for="log-reg-show">로그인</label>
        <input type="radio" name="active-log-panel" id="log-reg-show" checked="checked">
    </div>

    <div class="register-info-box">
        <h2>계정이 없으신가요?</h2>
        <p>로그인을 위해서 회원가입을 해주세요.</p>
        <label id="label-login" for="log-login-show">회원가입</label>
        <input type="radio" name="active-log-panel" id="log-login-show">
    </div>

    <div class="white-panel">
        <form name="login" action="/login" method="post">
            <div class="login-show">
                <h2>로그인</h2>
                <input type="text" name="id" placeholder="아이디" value="${id}">
                <input type="password" name="password" placeholder="비밀번호">
                <input type="button" id="loginBtn" value="로그인">
                <a href="">비밀번호를 잊으셨나요?</a>
            </div>
        </form>
        <form name="register" action="/user/register" method="post">
            <div class="register-show">
                <h2>회원가입</h2>
                <div class="checkLine">
                    <input type="text" name="id" class="checkLine" id="idTxt" placeholder="아이디">
                    <input type="button" class="checkLine" id="confirmIdBtn" value="중복확인">
                </div>
                <input type="password" name="password" id="pwdTxt" placeholder="비밀번호">
                <input type="password" id="confirmPwdTxt" placeholder="비밀번호 확인">
                <div class="checkLine">
                    <input type="text" name="nickname" id="nicknameTxt" placeholder="별명">
                    <input type="button" class="checkLine" id="confirmNicknameBtn" value="중복확인">
                </div>
                <input type="button" id="registerBtn" value="가입">
            </div>
        </form>
    </div>
</div>
</body>
</html>
