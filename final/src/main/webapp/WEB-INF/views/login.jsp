<%--
  Created by IntelliJ IDEA.
  User: jimen
  Date: 2020-06-21
  Time: 오전 1:13
  reference: https://bootsnipp.com/snippets/92gmX
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link rel="stylesheet" href="css/login/main.css">

    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
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
        <form action="/login" method="post">
            <div class="login-show">
                <h2>로그인</h2>
                <input type="text" name="id" placeholder="아이디">
                <input type="password" name="password" placeholder="비밀번호">
                <input type="submit" value="로그인">
                <a href="">비밀번호를 잊으셨나요?</a>
            </div>
        </form>
        <div class="register-show">
            <h2>회원가입</h2>
            <input type="text" placeholder="아이디">
            <div id="emailForm">
                <input type="email" placeholder="이메일">
                <input type="button" id="confirm" value="확인">
            </div>
            <input type="password" placeholder="비밀번호">
            <input type="password" placeholder="비밀번호 확인">
            <input type="text" placeholder="별명">
            <input type="button" value="가입">
        </div>
    </div>
</div>
</body>
</html>
