<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.CsrfAuthenticationStrategy.SaveOnAccessCsrfToken"--%>
<%--
  Created by IntelliJ IDEA.
  User: Егор
  Date: 06.05.2019
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>Инструмент</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/my_style_tmz.css">

</head>
<body>

<header class="top">
    <img src="${pageContext.request.contextPath}/resources/images/logoTEMZ.png">
    <sec:csrfMetaTags />
</header>
<nav>
    <ul>
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/'">Главная</li>
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/elements'">Детали</li>
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/tools'">Инструменты</li>
        <li class="sel" id="selected" onclick="location.href='${pageContext.request.contextPath}/drills'">Сверла</li>
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
            <table class="listTools">
                <thead>
                <tr class="zag">
                    <th>Наименование</th>
                    <th>Модель (диаметр)</th>
                    <th>Тип</th>
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
    <form:form id="formTool" name="formTool" action="${pageContext.request.contextPath}/addDrill">
        <div class="tableRow">
            <p>Наименование:</p>
            <p>
                <select id="drill_name">
                    <option disabled>Выберите инструмент</option>
                    <option value="Центровка">Центровка</option>
                    <option value="Сверло">Сверло</option>
                    <option value="Развертка">Развертка</option>
                    <option value="Фреза">Фреза</option>
                </select>
            </p>
        </div>
        <div class="tableRow">
            <p>Модель (диаметр):</p>
            <p>
                <input id="inMod" type="text" name="model" value="" placeholder="">
            </p>
        </div>
        <div class="tableRow">
            <p>
                Выберите тип:
            </p>
            <p>
                <select id="type">
                    <option disabled>Выберите тип</option>
                    <option value="Правый">Правый</option>
                    <option value="Левый">Левый</option>
                    <option value="Перовое">Перовое</option>
                </select>
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
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/my_drill.js"></script>
<script>
    function formSubmit() {
        document.getElementById("logoutForm").submit();
    }
</script>
<script>
    var table = '.listTools';
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

            $.each($(".listTools tbody tr"), function() {
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
