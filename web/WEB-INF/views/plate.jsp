<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>Детали</title>
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
        <li class="sel" onclick="location.href='/plates'">Пластины</li>
        <li class="sel" id="selected" onclick="location.href='/plate'">Пластина</li>
        <li class="sel" onclick="location.href='/contacts'">Станки</li>
        <li class="sel" onclick="location.href='/contacts'">Контакты</li>

    </ul>
</nav>

<div id="plateInfo">
<div class="tableRow">
    <p>Наименование:</p>
    <p class="info">                     ${currentPlate.name}</p>
</div>
<div class="tableRow">
    <p>Модель:</p>
    <p id="model" class="info">          ${currentPlate.model}</p>
</div>
<div class="tableRow">
    <p>Тип:</p>
    <p class="info">                     ${currentPlate.type}</p>
</div>

    <div id="fotos">
    </div>
</div>



<footer>
    Copyright © 2019 gorinych3 <br>
    Все права защищены.
</footer>
<script src="../../resources/js/jquery.js"></script>
<script src="../../resources/bootstrap/js/bootstrap.min.js"></script>
<script>
    $(document).ready(function () {
        for (var z = 0; z < ${countPath}; z++){
            $('#fotos').append("<img id='id"+z+"' class='bigImg' src='' width='300'>");
        }
    for (var j = 0; j < ${countPath}; j++){
        var model = $('#model').html();
        var path = urlLit(model,0);
        var full_path = "${pageContext.request.contextPath}/download1/" + path+"-"+j+"-"+${currentPlate.plateId};
        console.log("Генерация пути   "+full_path);
        var ident = '#id'+j;
        $(ident).attr('src', full_path);

    }

    function urlLit(w,v) {
        var tr='a b v g d e ["zh","j"] z i y k l m n o p r s t u f h c ch sh ["shh","shch"] ~ y ~ e yu ya ~ ["jo","e"]'.split(' ');
        var ww=''; w=w.toLowerCase();
        for(i=0; i<w.length; ++i) {
            cc=w.charCodeAt(i); ch=(cc>=1072?tr[cc-1072]:w[i]);
            if(ch.length<3) ww+=ch; else ww+=eval(ch)[v];}
        return(ww.replace(/[^a-zA-Z0-9\-]/g,'-').replace(/[-]{2,}/gim, '-').replace( /^\-+/g, '').replace( /\-+$/g, ''));
    }
    });
</script>

</body>
</html>
