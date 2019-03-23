<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Детали</title>
    <link type="text/css" rel="stylesheet" href="../../resources/css/my_style_tmz.css">
</head>
<body>
<header class="top">
    <img src="../../resources/images/logoTEMZ.png">
</header>
<nav>
    <ul>
        <li class="sel" onclick="location.href='../..'">Главная</li>
        <li class="sel" id="selected" onclick="location.href='/elements'">Детали</li>
        <li class="sel" onclick="location.href='/addElements'">Добавить деталь</li>
        <li class="sel" onclick="location.href='/contacts'">Инструмент</li>
        <li class="sel" onclick="location.href='/plates'">Пластины</li>
        <li class="sel" onclick="location.href='/contacts'">Станки</li>
        <li class="sel" onclick="location.href='/contacts'">Контакты</li>
    </ul>
</nav>

<div id="tableContainer">
    <div id="tableRow">
        <section id="main">
            <table id="list">
                <tr id="zag">
                    <th>Наименование</th>
                    <th>Фото</th>
                    <th>Технология</th>
                    <th>Программа</th>
                    <th>Наладка</th>
                    <th>Примечание</th>
                </tr>
            <jsp:useBean id="elements" scope="request" type="java.util.List"/>
            <c:forEach items="${elements}" var="e">
                        <%--${e.nameElement}--%>
                <tr>
                    <td class="my_href">${e.nameElement}</td>
                    <td>${e.photo}</td>
                    <td>${e.technology}</td>
                    <td>${e.program}</td>
                    <td>${e.setup}</td>
                    <td>${e.notation}</td>
                </tr>
            </c:forEach>
            </table>

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
<script src="../../resources/js/jquery.js"></script>
<script src="../../resources/js/elements.js"></script>
</body>
</html>