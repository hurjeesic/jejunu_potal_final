<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jimen
  Date: 2020-06-26
  Time: 오전 12:53
  reference: https://www.bootdey.com/snippets/view/edit-profile-form#css
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test='${sessionScope.get("user") == null}'>
    <c:redirect url="/login" context="/"/>
</c:if>
<html>
<head>
    <title>User Information</title>
    <jsp:include page="module.jsp" />
    <style>
        .container {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
        }

        input[name="nickname"] {
            display: inline-block;
            width: 80%;
        }

        input[type="button"] {
            width: 100%;
        }

        #confirmNicknameBtn {
            display: inline-block;
            float: right;
            width: 18%;
        }
    </style>
</head>
<body>
<jsp:include page="nav.jsp" />
<div class="container bootstrap snippet">
    <h1 class="text-primary"><span class="glyphicon glyphicon-user"></span>${user.nickname}님의 회원 정보</h1>
    <hr>
    <div class="row">
        <!-- left column -->
<%--        <div class="col-md-3">--%>
<%--            <div class="text-center">--%>
<%--                <img src="http//placehold.it/100" class="avatar img-circle" alt="avatar">--%>
<%--                <h6>Upload a different photo...</h6>--%>

<%--                <input type="file" class="form-control">--%>
<%--            </div>--%>
<%--        </div>--%>

        <!-- edit form column -->
        <div class="col-md-9 personal-info">
            <form class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="col-lg-3 control-label">아이디</label>
                    <div class="col-lg-8">
                        <input type="text" name="id" class="form-control" value="${user.id}" disabled>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-3 control-label">비밀번호</label>
                    <div class="col-lg-8">
                        <input type="password" name="password" class="form-control" value="">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-3 control-label">별명</label>
                    <div class="col-lg-8">
                        <input type="text" name="nickname" class="form-control" value="${user.nickname}">
                        <input type="button" class="btn btn-dark" id="confirmNicknameBtn" value="중복확인">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-lg-8">
                        <input type="button" class="btn btn-primary" id="updateBtn" value="수정하기">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-lg-8">
                        <input type="button" class="btn btn-danger" id="withdrawalBtn" value="회원탈퇴">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
