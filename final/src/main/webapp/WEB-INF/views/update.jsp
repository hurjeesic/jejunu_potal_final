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
    <title>${title}</title>
    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/update/main.css">

    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="<%= request.getContextPath() %>/js/update/main.js"></script>
    <script>
        <c:if test="${msg != null}">
            alert('${msg}');
        </c:if>
		window.onload = () => {
			const myDate = new Date(${todo.time.timeInMillis});
			const year = myDate.getFullYear();
			const month = myDate.getMonth() + 1;
			const day = myDate.getDate();
			document.getElementById('mainTitle').textContent =
				year + '-' + (month < 10 ? '0' : '') + month + '-' + (day < 10 ? '0' : '') + day;

			document.getElementById('imageFile').addEventListener('change', (event) => {
				changeImage(event);
			});

			// updateBtn;
		};
    </script>
</head>
<body>
<form action="<%= request.getContextPath() %>/todo/update/${no}" method="post">
    <div class="container">
        <header>
            <h1 id="mainTitle"></h1>
        </header>
        <div class="preview text-center">
            <c:choose>
                <c:when test="${todo.imageName eq null}">
                    <img src="<%= request.getContextPath() %>/image/no.jpg" class="preview-img" id="preview"/>
                </c:when>
                <c:otherwise>
                    <img src="<%= request.getContextPath() %>/image/${no}/${todo.imageName}" class="preview-img"
                         id="preview"/>
                </c:otherwise>
            </c:choose>
            <c:if test="${title eq 'Todo 수정'}">
                <div class="browse-button">
                    <i class="fa fa-pencil-alt"></i>
                    <input type="file" accept="image/*" name="image" class="browse-input" id="imageFile"/>
                </div>
                <span class="Error"></span>
            </c:if>
        </div>
        <div class="main-content"></div>
        <div id="updatedForm">
            <div class="form-group">
                <div class="col-md-12">
                    <c:choose>
                        <c:when test="${title eq 'Todo 확인'}">
                            <input type="text" class="form-control" value="${todo.title}" disabled>
                        </c:when>
                        <c:when test="${title eq 'Todo 수정'}">
                            <input type="text" name="title" class="form-control" id="title" placeholder="제목" value="${todo.title}">
                        </c:when>
                    </c:choose>
                </div>
            </div>

            <div class="form-group">
                <div class="col-md-12">
                    <c:choose>
                        <c:when test="${title eq 'Todo 확인'}">
                            <textarea class="form-control" rows="8" disabled>${todo.content}</textarea>
                        </c:when>
                        <c:when test="${title eq 'Todo 수정'}">
                            <textarea name="content" class="form-control" placeholder="내용" rows="8">${todo.content}</textarea>
                        </c:when>
                    </c:choose>
                </div>
            </div>

            <div class="form-group">
                <div class="col-md-12">
                    <c:choose>
                        <c:when test="${title eq 'Todo 확인'}">
                            <c:choose>
                                <c:when test="${todo.fileName eq null}">
                                    <a class="form-control">파일 없음</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="<%= request.getContextPath() %>/download/${no}/${todo.fileName}" class="form-control">다운로드</a>
                                </c:otherwise>
                            </c:choose>

                        </c:when>
                        <c:when test="${title eq 'Todo 수정'}">
                            <input type="file" name="file" class="form-control">
                        </c:when>
                    </c:choose>
                </div>
            </div>

            <div class="form-group">
                <div class="col-md-12">
                    <c:choose>
                        <c:when test="${title eq 'Todo 확인'}">
                            <input type="button" class="btn btn-success btn-block"
                                   onclick="window.location.href='<%= request.getContextPath() %>/todo/update/${no}'"
                                   value="수정하기">
                        </c:when>
                        <c:when test="${title eq 'Todo 수정'}">
                            <input type="submit" class="btn btn-success btn-block" value="수정">
                        </c:when>
                    </c:choose>

                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>
