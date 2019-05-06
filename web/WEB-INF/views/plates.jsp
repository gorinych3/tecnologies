<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>Пластины</title>
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
        <li class="sel" onclick="location.href='/contacts'">Инструмент</li>
        <li class="sel" id="selected" onclick="location.href='/plates'">Пластины</li>
        <li class="sel" onclick="location.href='/contacts'">Станки</li>
        <li class="sel" onclick="location.href='/contacts'">Контакты</li>
    </ul>
</nav>


<div id="tableContainer">
    <div id="tableRow">
        <section id="main">
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
            <table id="list">
                <thead>
                <tr id="zag">
                    <th>Наименование</th>
                    <th>Модель (по паспорту)</th>
                    <th>Тип</th>
                    <th>Фото</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
            <table id="formtable">
                <form id="formPlate" name="formPlate" action="${pageContext.request.contextPath}/addPlates">
                    <tr id="add_hide">
                        <td><input type="text" name="name" value=""></td>
                        <td><input id="inMod" type="text" name="model" value="" placeholder=""></td>
                        <td><input type="text" name="type" value=""></td>
                        <td><input id="files" type="file" accept="image/*" multiple name="photo"></td>
                    </tr>
                </form>
            </table>
            <div class="pagination-container">
                <nav>
                    <ul class="pagination justify-content-center"></ul>
                </nav>
            </div>
            <input class="button2" type="button" value="Добавить">
            <input class="button3" type="button" value="Принять">
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
<script src="../../resources/js/plates.js"></script>
<script>
    var table = '#list';
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

            $.each($("#list tbody tr"), function() {
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
