<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.CsrfAuthenticationStrategy.SaveOnAccessCsrfToken"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Главная</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/my_style_tmz.css">
</head>
<body>
<header class="top">
    <img src="${pageContext.request.contextPath}/resources/images/logoTEMZ.png">
    
</header>
<nav>
    <ul>
        <li class="sel" id="selected" onclick="location.href='${pageContext.request.contextPath}/'">Главная</li>
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/elements'">Детали</li>
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/tools'">Инструменты</li>
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/drills'">Сверла</li>
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/plates'">Пластины</li>
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/machines'">Станки</li>
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/contacts'">Контакты</li>
        <li class="sel" style="position: absolute; right: 10px; top: 155px"><c:url var="logoutUrl" value="/logout" />
            <a href="javascript:formSubmit()"> Logout</a>
            <form style="display: none" action="${logoutUrl}" method="post" id="logoutForm">
                <input type="hidden" name="${_csrf.parameterName}"     value="${_csrf.token}" />
            </form>
        </li>

    </ul>
</nav>

<div id="tableContainer">
    <div id="tableRow">
        <section class="main">
            <div>
                <p>Данная программа предназначена для работы с технологиями всех деталей, которые изготавливаются на
                    участках ЧПУ-2 и ЧПУ-3. Программа позволяет добавлять технологию детали, редактировать (изменять),
                    удалять как саму технологию целиком, так и отдельные ее компоненты (инструмент, пластины и т.д).
                </p>
                <ul>Уровни доступа:
                    <li>Администратор - все операции (добавление, редактирование, чтение, удаление</li>
                    <li>Пользователь - все вышеперечисленные операции, кроме удаления</li>
                    <li>Оператор - только чтение</li>
                </ul>
                <br>

                <ul class="u2">На каждой странице будут отображен список деталей, инструмента и т.д. Каждый элемент списка
                    содержит:
                    <li>Наименование <span id="span2">Пример</span> </li>
                    <li>Маркировку по паспорту</li>
                    <li>Фотографию или чертеж. <span id="span1">Пример</span> </li>
                    <li>Список сопутствующих элементов</li>
                    <li>Дополнительные параметры</li>
                </ul>

            </div>
        </section>

        <aside>
            <div id="div_hide">
                <img id="prim" src="">
                <p id="prim2"></p>
            </div>
        </aside>
    </div>
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
