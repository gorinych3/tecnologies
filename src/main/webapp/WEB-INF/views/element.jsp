<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.CsrfAuthenticationStrategy.SaveOnAccessCsrfToken"--%>
<%--
  Created by IntelliJ IDEA.
  User: Егор
  Date: 15.05.2019
  Time: 0:49
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>Деталь</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/my_style_tmz.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>

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
        <li class="sel" id="selected" onclick="location.href='${pageContext.request.contextPath}/element'">Деталь</li>
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/tools'">Инструменты</li>
        <li class="sel" onclick="location.href='${pageContext.request.contextPath}/drills'">Сверла</li>
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

<div id="plateInfo">
    <p style="font-weight: bolder; color: black">Деталь:</p>
    <div class="tableRow">
        <p>Наименование:</p>
        <p class="info">                     ${currentElement.nameElement}</p>
    </div>
    <div class="tableRow">
        <p>Идентификатор:</p>
        <p id="model" class="info">          ${currentElement.idNumb}</p>
    </div>
    <div class="tableRow">
        <p>Программа:</p>
        <p id="programm" class="info">          ${currentElement.program}</p>
    </div>
    <div class="tableRow">
        <p>Наладка:</p>
        <p class="info">                     ${currentElement.setup} </p>
    </div>
    <div class="tableRow">
        <p>Примечание:</p>
        <p class="info">                     ${currentElement.notation} </p>
    </div>
    <br>

    <div id="photo" class="image__wrapper" style='font-weight: bolder; color: black'>Фото детали:<br></div>
    <div id="tech" class="image__wrapper" style="font-weight: bolder; color: black">Технология:<br></div>
    <br>
    <jsp:useBean id="currentTool" scope="request" type="java.util.List"/>
    <c:forEach items="${currentTool}" var="t">
        <p style="font-weight: bolder; color: black">${t.name}:</p>
        <div class="tableRow">
            <p>Модель:</p>
            <p class="info model1">                     ${t.model}</p>
        </div>
        <div class="tableRow">
            <p>Тип:</p>
            <p class="info">                     ${t.type}</p>
        </div>

        <div id="${t.toolId}"></div>
        <div><script src="${pageContext.request.contextPath}/resources/js/getFilesPlates.js"></script>
            <script language="JavaScript">
                var name = "${t.name}";
                console.log("name = " + name);
                if ((name === "Сверло" || name === "Фреза" || name === "Центровка" || name === "Развертка")) {
                } else {
                    console.log("Вызов функции получения файлов инструмента");
                    getPhotoPlates(${t.toolId}, "${t.model}", "${pageContext.request.contextPath}/downloadToolsFiles/")
                }
            </script>
        </div>
        <br>
    </c:forEach>
    <div id="toolPhoto">
    </div>

    <br>

    <jsp:useBean id="currentPlates" scope="request" type="java.util.Set"/>
    <c:forEach items="${currentPlates}" var="p">
        <p style="font-weight: bolder; color: black">Пластина ${p.name}:</p>
        <div class="tableRow">
            <p>Модель:</p>
            <p class="info model1">                     ${p.model}</p>
        </div>
        <div class="tableRow">
            <p>Тип:</p>
            <p class="info">                     ${p.type}</p>
        </div>

        <div id="${p.plateId}"></div>
        <div><script src="${pageContext.request.contextPath}/resources/js/getFilesPlates.js"></script>
            <script language="JavaScript">
                getPhotoPlates(${p.plateId}, "${p.model}", "${pageContext.request.contextPath}/download1/")</script>

        </div>

        <br>
    </c:forEach>
    <div id="platePhoto">
    </div>

    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_USER')">
    <button id="updateS" class="button2" type="button">Редактировать</button>
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
    <button id="del" class="button2" type="button">Удалить</button>
    </sec:authorize>
    <div id="buttons" align="right">
        <button class="back" onclick="location.href='${pageContext.request.contextPath}/elements';" type="button" >Назад</button>
        <button class="back" onclick="location.href='${pageContext.request.contextPath}/';" type="button" >На главную</button>
    </div>
</div>

<footer>
    Copyright © 2019 gorinych3 <br>
    Все права защищены.
</footer>

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

        for (var z = 0; z < ${countPathPhoto}; z++){
            $('#photo').append("<img id='id_photo"+z+"' class='bigImg minimized' alt='клик для увеличения' src='' height='150'>");
        }
        for (var j = 0; j < ${countPathPhoto}; j++){
            var photo_1 = "${currentElement.nameElement}";
            var photo_2 = "${currentElement.idNumb}";
            var photo = "photo-"+photo_1+"-"+photo_2;
            var path_photo = urlLit(photo,0);
            var full_path_photo = "${pageContext.request.contextPath}/downloadElementFilesPhoto/" + path_photo+"-"+j+"-"+${currentElement.elId};
            console.log("full_path_photo = " + full_path_photo);
            var ident_photo = '#id_photo'+j;
            $(ident_photo).attr('src', full_path_photo);

        }

        for (var x = 0; x < ${countPathTech}; x++){
            $('#tech').append("<img id='id_tech"+x+"' class='bigImg minimized' alt='клик для увеличения' src='' height='150'>");
        }
        for (var j = 0; j < ${countPathTech}; j++){
            var tech_1 = "${currentElement.nameElement}";
            var tech_2 = "${currentElement.idNumb}";
            var tech = "tech-"+tech_1+"-"+tech_2;
            var path_tech = urlLit(tech,0);
            var full_path_tech = "${pageContext.request.contextPath}/downloadElementFilesPhoto/" + path_tech+"-"+j+"-"+${currentElement.elId};
            console.log("full_path_tech = " + full_path_tech);
            var ident_tech = '#id_tech'+j;
            $(ident_tech).attr('src', full_path_tech);

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
            if(confirm("Вы точно хотите удалить данную деталь? Вся информация о ней будет удалена!!")){
                window.location.href="${pageContext.request.contextPath}/deleteElement/".concat(${currentElement.elId});
            }
        });

        // переход на страницу редактирования

        $("#updateS").click(function () {
            window.location.href = "${pageContext.request.contextPath}/updateElementPage";
        });

        $(function(){
            $('.minimized').click(function(event) {
                var i_path = $(this).attr('src');
                $('body').append('<div id="overlay"></div><div id="magnify"><img src="'+i_path+'"><div id="close-popup"><i></i></div></div>');
                $('#magnify').css({
                    left: 250,
                    top: 10
                });
                $('#overlay, #magnify').fadeIn('fast');
            });

            $('body').on('click', '#close-popup, #overlay', function(event) {
                event.preventDefault();

                $('#overlay, #magnify').fadeOut('fast', function() {
                    $('#close-popup, #magnify, #overlay').remove();
                });
            });
        });
    });
</script>

</body>
</html>
