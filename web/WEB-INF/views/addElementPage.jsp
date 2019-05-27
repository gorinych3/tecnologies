<%--
  Created by IntelliJ IDEA.
  User: Егор
  Date: 27.05.2019
  Time: 1:56
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>Добавить деталь</title>
    <link rel="stylesheet" href="../../resources/bootstrap/css/bootstrap.min.css">
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
        <li class="sel" id="selected" onclick="location.href='/addElementPage'">Добавить деталь</li>
        <li class="sel" onclick="location.href='/tools'">Инструмент</li>
        <li class="sel" onclick="location.href='/drills'">Сверла</li>
        <li class="sel" onclick="location.href='/plates'">Пластины</li>
        <li class="sel" onclick="location.href='/machines'">Станки</li>
        <li class="sel" onclick="location.href='/contacts'">Контакты</li>
    </ul>
</nav>


<div id="tableContainer">
    <div id="tableRow">
        <section class="main">
            <form id="formElement" name="formElement" action="${pageContext.request.contextPath}//addElement">
                <div class="tableRow">
                    <p>Наименование:</p>
                    <p>
                        <input type="text" name="name" value="" style="width: 370px">
                    </p>
                </div>
                <div class="tableRow">
                    <p>Идентификатор:</p>
                    <p>
                        <input id="idNumb" type="text" name="idNumb" value="" placeholder="" style="width: 370px">
                    </p>
                </div>
                <div class="tableRow">
                    <p>Технология:</p>
                    <p>
                        <input id="technology" type="file" accept="image/*" multiple name="technology" style="width: 370px">
                    </p>
                </div>
                <div class="tableRow">
                    <p>Программа:</p>
                    <p>
                        <input id="programm" type="file" accept="application/msword" multiple name="programm" style="width: 370px">
                    </p>
                </div>
                <div class="tableRow">
                    <p>Наладка:</p>
                    <p>
                        <textarea name="setup_text" id="setup" cols="70" rows="5"></textarea>
                    </p>
                </div>
                <div class="tableRow">
                    <p>Примечания к изготовлению:</p>
                    <p>
                        <textarea name="notation_text" id="notation" cols="70" rows="5"></textarea>
                    </p>
                </div>
                <div class="tableRow">
                    <p>
                        Выберите инструмент:
                    </p>
                    <p>
                        <select id="setTools" size="6" multiple name="myTools[]" style="width: 510px">
                            <option disabled>Выберите инструмент</option>

                        </select>
                    </p>
                </div>
                <div class="tableRow">
                    <p>
                        Выберите пластины:
                    </p>
                    <p>
                        <select id="setPlates" size="6" multiple name="plates[]" style="width: 510px">
                            <option disabled>Выберите пластины</option>

                        </select>
                    </p>
                </div>
                <div class="tableRow">
                    <p>
                        Выберите станки:
                    </p>
                    <p>
                        <select id="setMachines" multiple name="machines[]" style="width: 510px">
                            <option disabled>Выберите станки</option>

                        </select>
                    </p>
                </div>

                <div class="tableRow">
                    <p>Фото:</p>
                    <p>
                        <input id="files" type="file" accept="image/*" multiple name="photo">
                    </p>
                </div>

                <div class="tableRow">
                    <p>
                        <input class="button3" type="button" value="Принять">
                    </p>
                    <p>
                        <input class="button4" type="button" value="Отменить">
                    </p>
                </div>


            </form>

        </section>

        <aside>
            <div id="err">
                <p id="erMessage"></p>
            </div>
            <img id="prim" src="">
        </aside>
    </div>
</div>

<footer>
    Copyright © 2019 gorinych3 <br>
    Все права защищены.
</footer>
<script src="../../resources/js/jquery.js"></script>
<script src="../../resources/bootstrap/js/bootstrap.min.js"></script>
<script src="../../resources/js/addElement.js"></script>
</body>
</html>
