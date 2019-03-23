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
        <li class="sel" onclick="location.href='/contacts'">Инструмент</li>
        <li class="sel" onclick="location.href='/contacts'">Пластины</li>
        <li class="sel" onclick="location.href='/contacts'">Станки</li>
        <li class="sel" onclick="location.href='/contacts'">Контакты</li>
    </ul>
</nav>

<div id="tableContainer">
    <div id="tableRow">
        <section id="main">

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
<script src="../../resources/js/my_script_tmz.js"></script>
</body>
</html>
