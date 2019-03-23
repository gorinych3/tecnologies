<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
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
        <li class="sel" onclick="location.href='/elements'">Детали</li>
        <li class="sel" onclick="location.href='/contacts'">Инструмент</li>
        <li class="sel" id="selected" onclick="location.href='/plates'">Пластины</li>
        <li class="sel" onclick="location.href='/addPlates'">Добавить пластину</li>
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
                    <th>Модель (по паспорту)</th>
                    <th>Тип</th>
                    <th>Фото</th>
                </tr>
                <jsp:useBean id="plates" scope="request" type="java.util.List"/>
                <c:forEach items="${plates}" var="p">
                    <tr>
                        <td class="my_href">${p.name}</td>
                        <td>${p.model}</td>
                        <td>${p.type}</td>
                        <td>${p.photo}</td>
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
<script src="../../resources/js/plates.js"></script>
</body>
</html>
