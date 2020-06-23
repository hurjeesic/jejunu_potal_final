<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jimen
  Date: 2020-06-23
  Time: 오후 1:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test='${sessionScope.get("user") == null}'>
    <c:redirect url="/login" context="/"/>
</c:if>
<html>
<head>
    <title></title>
    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/update/main.css">

    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script>
		const urlAry = window.location.href.split('/');
		document.title = urlAry[urlAry.length - 2];

		const myDate = new Date(${todo.time.timeInMillis});
		window.onload = () => {
			const myDate = new Date(${todo.time.timeInMillis});
			document.getElementById('mainTitle').textContent = myDate;
				<%--`${myDate.getFullYear()}-${myDate.getMonth + 1}-${myDate.getDate()}`;--%>

			document.getElementById('imageFile').addEventListener('change', (event) => {
				let image = document.getElementById('preview');
                let target = event.target;
				let reader = new FileReader();
				reader.onload = () => {
					image.src = reader.result;
				};

				reader.readAsDataURL(target.files[0]);
			});
		};
    </script>
</head>
<body>
<div class="container">
    <header>
        <h1 id="mainTitle"></h1>
    </header>
    <div class="preview text-center">
        <img src="<%= request.getContextPath() %>/image/no.jpg" class="preview-img" id="preview"/>
        <div class="browse-button">
            <i class="fa fa-pencil-alt"></i>
            <input type="file" accept="image/*" class="browse-input" id="imageFile"/>
        </div>
        <span class="Error"></span>
    </div>
    <div class="main-content"></div>
    <div id="updatedForm">
        <div class="form-group">
            <div class="col-md-12">
                <input type="text" name="title" class="form-control" id="title" placeholder="제목" value="${todo.title}">
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-12">
                <textarea name="content" class="form-control" id="content" placeholder="내용" rows="8">${todo.content}</textarea>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-12">
                <input type="file" name="file" class="form-control">
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-12">
                <input type="submit" class="btn btn-success btn-block" value="수정 완료">
            </div>
        </div>
    </div>
</div>
</body>
</html>
