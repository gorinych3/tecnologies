<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.CsrfAuthenticationStrategy.SaveOnAccessCsrfToken"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>Пластина</title>
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
        <li class="sel" onclick="location.href='../../../../../../../'">Главная</li>
        <li class="sel" onclick="location.href='/elements'">Детали</li>
        <li class="sel" onclick="location.href='/tools'">Инструменты</li>
        <li class="sel" onclick="location.href='/drills'">Сверла</li>
        <li class="sel" onclick="location.href='/plates'">Пластины</li>
        <li class="sel" id="selected" onclick="location.href='/plate'">Пластина</li>
        <li class="sel" onclick="location.href='/machines'">Станки</li>
        <li class="sel" onclick="location.href='/contacts'">Контакты</li>
        <li class="sel" style="position: absolute; right: 10px; top: 155px"><c:url var="logoutUrl" value="/logout" />
            <a href="javascript:formSubmit()"> Logout</a>
            <form style="display: none" action="${logoutUrl}" method="post" id="logoutForm">
                <input type="hidden" name="${_csrf.parameterName}"     value="${_csrf.token}" />
            </form>
        </li>

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


    <sec:authorize access="hasRole('ROLE_ADMIN')">
    <button id="del" class="button2" type="button">Удалить</button>
    </sec:authorize>
    <div id="buttons" align="right">
    <button class="back" onclick="location.href='/plates';" type="button" >Назад</button>
    <button class="back" onclick="location.href='../../../../../../../';" type="button" >На главную</button>
    </div>
</div>





<footer>
    Copyright © 2019 gorinych3 <br>
    Все права защищены.
</footer>
<script src="../../resources/js/jquery.js"></script>
<script src="../../resources/bootstrap/js/bootstrap.min.js"></script>
<script>
    function formSubmit() {
        document.getElementById("logoutForm").submit();
    }
</script>
<script>
    $(document).ready(function () {
        for (var z = 0; z < ${countPath}; z++){
            $('#fotos').append("<img id='id"+z+"' class='bigImg' src='' width='300'>");
        }
    for (var j = 0; j < ${countPath}; j++){
        var model = $('#model').html();
        var path = urlLit(model,0);
        var full_path = "${pageContext.request.contextPath}/download1/" + path+"-"+j+"-"+${currentPlate.plateId};
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

    $("#del").click(function () {
        if(confirm("Вы точно хотите удалить данную пластину? Вся информация о ней будет удалена!!")){
        window.location.href="/deletePlate/".concat(${currentPlate.plateId});
        }
    })
    });
</script>

</body>
</html>
