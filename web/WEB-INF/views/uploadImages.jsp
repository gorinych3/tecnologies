<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>UploadFiles</title>
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
        <li class="sel" onclick="location.href='/plates'">Пластины</li>
        <li class="sel" onclick="location.href='/addPlates'">Добавить пластину</li>
        <li class="sel" onclick="location.href='/contacts'">Станки</li>
        <li class="sel" id="selected" onclick="location.href='/uploadImages'">TestUploadFiles</li>
    </ul>
</nav>

<form id='fileUpload' onsubmit="return false;">
    File <input type='file' id='file' multiple="multiple">
    <button onclick="uplaod();">Upload</button>
</form>

<script type="text/javascript">

    uplaod = function(){

        var data = new FormData();
        jQuery.each(jQuery('#file')[0].files, function(i, file) {
            data.append('file-'+i, file);
        });

        $.ajax({
            url:'/uploadImages',
            data: data,
            cache: false,
            contentType: false,
            processData: false,
            type:'POST',
            success: function(response){
                if(response.Status === 200){
                    alert(response.SucessfulList);
                }else{
                    alert('Error');
                }


            }
        });

    }
</script>



<footer>
    Copyright © 2019 gorinych3 <br>
    Все права защищены.
</footer>
<script src="../../resources/js/jquery.js"></script>

</body>
</html>
