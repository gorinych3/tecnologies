<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.CsrfAuthenticationStrategy.SaveOnAccessCsrfToken"--%>
<%--
  Created by IntelliJ IDEA.
  User: Егор
  Date: 21.05.2019
  Time: 20:31
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>Станок</title>
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
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/drills'">Сверла</li>
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/plates'">Пластины</li>
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/machines'">Станки</li>
        <li class="sel" id="selected" onclick="location.href='${pageContext.request.contextPath}/machine'">Станок</li>
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/contacts'">Контакты</li>
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
        <p class="info">                     ${currentMachine.name}</p>
    </div>
    <div class="tableRow">
        <p>Модель:</p>
        <p id="model" class="info">          ${currentMachine.model}</p>
    </div>
    <div class="tableRow">
        <p>Тип:</p>
        <p id="number" class="info">          ${currentMachine.idNumber}</p>
    </div>

    <div id="fotos">
    </div>

    <sec:authorize access="hasRole('ROLE_ADMIN')">
    <button id="del" class="button2" type="button">Удалить</button>
    </sec:authorize>
    <div id="buttons" align="right">
        <button class="back" onclick="location.href='${pageContext.request.contextPath}/machines';" type="button" >Назад</button>
        <button class="back" onclick="location.href='${pageContext.request.contextPath}/';" type="button" >На главную</button>
    </div>
</div>





<footer>
    Copyright © 2019 gorinych3 <br>
    Все права защищены.
</footer>
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
<script>
    function formSubmit() {
        document.getElementById("logoutForm").submit();
    }
</script>
<script>
    $(document).ready(function () {

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function(e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });

        for (var z = 0; z < ${countPath}; z++){
            $('#fotos').append("<img id='id"+z+"' class='bigImg' src='' width='300'>");
        }
        for (var j = 0; j < ${countPath}; j++){
            var model = $('#number').html();
            var path = urlLit(model,0);
            var full_path = "${pageContext.request.contextPath}/downloadMachineFiles/" + path+"-"+j+"-"+${currentMachine.machId};
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
            if(confirm("Вы точно хотите удалить данный станок? Вся информация о нем будет удалена!!")){
                window.location.href="${pageContext.request.contextPath}/deleteMachine/".concat(${currentMachine.machId});
            }
        })
    });
</script>

</body>
</html>
