<%--
  Created by IntelliJ IDEA.
  User: Егор
  Date: 17.05.2019
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ include file="/WEB-INF/views/include.jsp" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>Станки</title>
    <link rel="stylesheet" href="../../resources/bootstrap/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="../../resources/css/my_style_tmz.css">

</head>
<body>
<header class="top">
    <img src="../../resources/images/logoTEMZ.png">
    <sec:csrfMetaTags />
</header>
<nav>
    <ul>
        <li class="sel" onclick="location.href='../..'">Главная</li>
        <li class="sel" onclick="location.href='/elements'">Детали</li>
        <li class="sel" onclick="location.href='/tools'">Инструмент</li>
        <li class="sel" onclick="location.href='/drills'">Сверла</li>
        <li class="sel" onclick="location.href='/plates'">Пластины</li>
        <li class="sel" id="selected" onclick="location.href='/machines'">Станки</li>
        <li class="sel" onclick="location.href='/contacts'">Контакты</li>
    </ul>
</nav>


<div id="tableContainer">
    <div id="tableRow">
        <section class="main">
            <div style="display: inline">
                <div class="form-group mySearch">
                    <input type="text" class="form-control pull-right" id="search" placeholder="Поиск по таблице">
                </div>
                <h4>Отображать по</h4>
                <div class="form-group">
                    <select name="state" id="maxRows" class="form-control" style="width:150px;">
                        <option value="5000">Показать все</option>
                        <option value="5">5</option>
                        <option value="10">10</option>
                        <option value="15">15</option>
                        <option value="20">20</option>
                        <option value="50">50</option>
                        <option value="75">75</option>
                        <option value="100">100</option>
                    </select>
                </div>
            </div>
            <table class="list">
                <thead>
                <tr class="zag">
                    <th>Наименование</th>
                    <th>Модель (по паспорту)</th>
                    <th>Идентификатор</th>
                    <th>Фото</th>
                </tr>
                </thead>
                <tbody>

                </tbody>

            </table>
            <div class="pagination-container">
                <nav>
                    <ul class="pagination justify-content-center"></ul>
                </nav>
            </div>
            <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_USER')">
            <input class="button2" type="button" value="Добавить">
            </sec:authorize>
        </section>

        <aside>
            <div id="err">
                <p id="erMessage"></p>
            </div>
            <img id="prim" src="">
        </aside>
    </div>
</div>

<div class="fixForm">
    <form:form id="formTool" name="formTool" action="${pageContext.request.contextPath}/addMachine">
        <div class="tableRow">
            <p>Наименование:</p>
            <p>
                <select id="machine_name">
                    <option disabled>Выберите станок</option>
                    <option value="Многофункциональный токарный">Многофункциональный токарный</option>
                    <option value="Автомат продольного точения">Автомат продольного точения</option>
                </select>
            </p>
        </div>
        <div class="tableRow">
            <p>Модель:</p>
            <p>
                <select id="inMod">
                    <option disabled>Выберите модель</option>
                    <option value="Spinner TC65-MC">Spinner TC65-MC</option>
                    <option value="Spinner PD-C">Spinner PD-C</option>
                    <option value="Unitec SL1693">Unitec SL1693</option>
                    <option value="AccuWay UT-200">AccuWay UT-200</option>
                </select>
            </p>
        </div>
        <div class="tableRow">
            <p>
                Выберите тип:
            </p>
            <p>
                <select id="type">
                    <option disabled>Выберите номер</option>
                    <option value="470069">470069</option>
                    <option value="470068">470068</option>
                    <option value="470110">470110</option>
                    <option value="470018">470018</option>
                    <option value="470017">470017</option>
                    <option value="470022">470022</option>
                    <option value="470124">470124</option>
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


    </form:form>
</div>

<footer>
    Copyright © 2019 gorinych3 <br>
    Все права защищены.
</footer>
<script src="../../resources/js/jquery.js"></script>
<script src="../../resources/bootstrap/js/bootstrap.min.js"></script>
<script src="../../resources/js/my_machines.js"></script>
<script>
    var table = '.list';
    $('#maxRows').on('change', function () {
        $('.pagination').html('');
        var trnum = 0;
        var maxRows = parseInt($(this).val());
        var totalRows = $(table+' tbody tr').length;
        $(table+' tr:gt(0)').each(function () {
            trnum++;
            if(trnum > maxRows){
                $(this).hide();
            }
            if(trnum <= maxRows){
                $(this).show();
            }
        });
        if(totalRows > maxRows){
            var pagenum = Math.ceil(totalRows/maxRows);
            for(var i = 1; i <= pagenum;){
                $('.pagination').append('<li data-page="'+i+'">\<span>'+ i++ +'<span class="sr-only">(current)</span></span>\</li>').show();
            }
        }
        $('.pagination li:first-child').addClass('active');
        $('.pagination li').on('click', function () {
            var pageNum = $(this).attr('data-page');
            var trIndex = 0;
            $('.pagination li').removeClass('active');
            $(this).addClass('active');
            $(table+' tr:gt(0)').each(function () {
                trIndex++;
                if(trIndex > (maxRows*pageNum) || trIndex <= ((maxRows*pageNum)-maxRows)){
                    $(this).hide();
                } else {
                    $(this).show();
                }
            })
        })
    });
</script>
<script>
    $(document).ready(function(){
        $("#search").keyup(function(){
            _this = this;

            $.each($(".list tbody tr"), function() {
                if($(this).text().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1) {
                    $(this).hide();
                } else {
                    $(this).show();
                }
            });
        });
    });
</script>
</body>
</html>
