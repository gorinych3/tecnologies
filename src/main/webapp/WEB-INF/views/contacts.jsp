<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.CsrfAuthenticationStrategy.SaveOnAccessCsrfToken"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Контакты</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/my_style_tmz.css">
</head>
<body>
<header class="top">
    <img src="${pageContext.request.contextPath}/resources/images/logoTEMZ.png">
</header>
<nav>
    <ul>
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/'">Главная</li>
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/elements'">Детали</li>
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/tools'">Инструмент</li>
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/drills'">Сверла</li>
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/plates'">Пластины</li>
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/machines'">Станки</li>
        <li class="sel" id="selected" onclick="location.href='${pageContext.request.contextPath}/contacts'">Контакты</li>
        <li class="sel" style="position: absolute; right: 10px; top: 155px"><c:url var="logoutUrl" value="/logout" />
            <a href="javascript:formSubmit()"> Logout</a>
            <form style="display: none" action="${logoutUrl}" method="post" id="logoutForm">
                <input type="hidden" name="${_csrf.parameterName}"     value="${_csrf.token}" />
            </form>
        </li>
    </ul>
</nav>

    <div id="contact">
        <ul style="list-style: none;"> Контакты
            <li>mail: gorinych33@yandex.ru</li>
            <li>тел.: +7-982-981-46-78</li>
            <li>адрес: ЧПУ-3 </li>
        </ul>
    </div>


<footer>
    Copyright © 2019 gorinych3 <br>
    Все права защищены.
</footer>
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/my_script_tmz.js"></script>
<script>
    function formSubmit() {
        document.getElementById("logoutForm").submit();
    }
</script>
</body>
</html>