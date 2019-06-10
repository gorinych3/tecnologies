<%--
  Created by IntelliJ IDEA.
  User: Егор
  Date: 03.06.2019
  Time: 0:48
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>Редактировать деталь</title>
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
        <li class="sel" id="selected" onclick="location.href='/updateElementPage'">Редактировать деталь</li>
        <li class="sel" onclick="location.href='/tools'">Инструмент</li>
        <li class="sel" onclick="location.href='/drills'">Сверла</li>
        <li class="sel" onclick="location.href='/plates'">Пластины</li>
        <li class="sel" onclick="location.href='/machines'">Станки</li>
        <li class="sel" onclick="location.href='/contacts'">Контакты</li>
    </ul>
</nav>


<div id="tableContainer">
    <div id="tableRow">
        <section class="main2">
            <form id="formElement" name="formElement" action="${pageContext.request.contextPath}/addElement">
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
                    <p>
                        <input id="prog" type="text" name="program" value="" placeholder="" style="width: 370px">
                    </p>
                </div>
                <div class="tableRow">
                    <p>Технология:</p>
                    <p>
                        <input id="tech" type="file" accept="image/jpeg" multiple name="technology" style="width: 370px">
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
                        <img src="../../resources/images/Help.png" width="20px" border="0" title="Красным цветом отмечен уже выбранный инструмент.
                        Если вы желаете изменить список инструмента,
                        вам придется выбрать все необходимые инструменты">
                    </p>
                    <p>
                        <select id="setTools" size="6" multiple name="myTools[]" style="width: 510px">
                            <option value="Ничего не менять">Ничего не менять</option>

                        </select>
                    </p>
                </div>
                <div class="tableRow">
                    <p>
                        Выберите пластины:
                        <img src="../../resources/images/Help.png" width="20px" border="0" title="Красным цветом отмечены уже выбранные пластины.
                        Если вы желаете изменить список пластин,
                        вам придется заново выбрать все необходимые пластины">
                    </p>
                    <p>
                        <select id="setPlates" size="6" multiple name="plates[]" style="width: 510px">
                            <option value="Ничего не менять">Ничего не менять</option>

                        </select>
                    </p>
                </div>
                <div class="tableRow">
                    <p>
                        Выберите станки:
                        <img src="../../resources/images/Help.png" width="20px" border="0" title="Красным цветом отмечены уже выбранные станки.
                        Если вы желаете изменить список станков,
                        вам придется заново выбрать все необходимые станки">
                    </p>
                    <p>
                        <select id="setMachines" multiple name="machines[]" style="width: 510px">
                            <option value="Ничего не менять">Ничего не менять</option>

                        </select>
                    </p>
                </div>
                <%--<div class="tableRow">--%>
                    <%--<p>--%>
                        <%--<input class="button5" type="button" value="Принять">--%>
                    <%--</p>--%>
                    <%--<p>--%>
                        <%--<input class="button4" type="button" value="Отменить">--%>
                    <%--</p>--%>
                <%--</div>--%>
                <span id="elid" hidden>${currentElement.elId}</span>


            </form>

            <div class="tableRow">
                <p>
                    <input class="button5" type="button" value="Принять">
                </p>
                <p>
                    <input class="button4" type="button" value="Отменить">
                </p>
            </div>

        </section>
        <section class="main2">
            <div id="right" style="width: auto; height: 100%">
                <p style="font-weight: bolder">Редактирование чертежей и фото</p>

                <div class="tableRow" style="font-weight: bolder">
                    <p>Добавить фото:</p>
                    <p>
                        <input id="photoAdd" type="file" accept="image/jpeg" name="photo">
                    </p>
                </div>
                <div class="tableRow" style="font-weight: bolder">
                    <p>Добавить технологию:</p>
                    <p>
                        <input id="techAdd" type="file" accept="image/jpeg" name="photo">
                    </p>
                </div>
                <span style="font-weight: bolder">Фото детали:</span>
                <div  id="updateElementPhoto">

                </div>
                <span style="font-weight: bolder">Технология:</span>
                <div id="updateElementTechnology">

                </div>
            </div>

        </section>



        <%--<aside>--%>
            <%--<div id="err">--%>
                <%--<p id="erMessage"></p>--%>
            <%--</div>--%>
            <%--<img id="prim" src="">--%>
        <%--</aside>--%>
    </div>
</div>

<footer>
    Copyright © 2019 gorinych3 <br>
    Все права защищены.
</footer>
<script src="../../resources/js/jquery.js"></script>
<script src="../../resources/bootstrap/js/bootstrap.min.js"></script>
<script src="../../resources/js/updateElement.js"></script>
<script>
    $(document).ready(function () {

        //$("#elid").html("${currentElement.elId}")
        for (var z = 0; z < ${countPathPhoto}; z++){
            $('#updateElementPhoto').append("<div class='image__wrapper tableRow photo' style='font-weight: bolder; color: black'></div>" +
                "<p><img id='id_photo"+z+"' class='bigImg minimized photos' alt='клик для увеличения' src='' height='100'>"+
                "<input id='change_photo"+z+"' type='radio' name='photo' value='change'>Заменить " +
                "<input id='delete_photo"+z+"' type='radio' name='photo' value='delete'>Удалить " +
                "<input id='ignore_photo"+z+"' type='radio' name='photo' value='ignore'>Не выполнять никаких действий</p>");
        }
        for (var j = 0; j < ${countPathPhoto}; j++){
            var photo_1 = "${currentElement.nameElement}";
            var photo_2 = "${currentElement.idNumb}";
            var photo = "photo-"+photo_1+"-"+photo_2;
            var path_photo = urlLit(photo,0);
            var full_path_photo = "${pageContext.request.contextPath}/downloadElementFilesPhoto/" + path_photo+"-"+j+"-"+${currentElement.elId};
            console.log("full_path_photo = " + full_path_photo);
            var ident_photo = '#id_photo'+j;
            var change_photo = '#change_photo'+j;
            var delete_photo = '#delete_photo'+j;
            var ignore_photo = '#ignore_photo'+j;
            $(ident_photo).attr('src', full_path_photo);
            $(change_photo).attr("name", full_path_photo);
            $(delete_photo).attr("name", full_path_photo);
            $(ignore_photo).attr("name", full_path_photo);

        }

        for (var x = 0; x < ${countPathTech}; x++){
            $('#updateElementTechnology').append("<div class='image__wrapper tableRow  tech' style='font-weight: bolder; color: black;'></div>" +
                "<p><img id='id_tech"+x+"' class='bigImg minimized techs' alt='клик для увеличения' src='' height='100'>"+
                "<input id='change_tech"+x+"' type='radio' name='tech' value='change'>Заменить  " +
                "<input id='delete_tech"+x+"' type='radio' name='tech' value='delete'>Удалить  " +
                "<input id='ignore_tech"+x+"'type='radio' name='tech' value='ignore'>Не выполнять никаких действий  </p>");

        }
        for (var j = 0; j < ${countPathTech}; j++){
            var tech_1 = "${currentElement.nameElement}";
            var tech_2 = "${currentElement.idNumb}";
            var tech = "tech-"+tech_1+"-"+tech_2;
            var path_tech = urlLit(tech,0);
            var full_path_tech = "${pageContext.request.contextPath}/downloadElementFilesPhoto/" + path_tech+"-"+j+"-"+${currentElement.elId};
            console.log("full_path_tech = " + full_path_tech);
            var ident_tech = '#id_tech'+j;
            var change_tech = '#change_tech'+j;
            var delete_tech = '#delete_tech'+j;
            var ignore_tech = '#ignore_tech'+j;
            $(ident_tech).attr('src', full_path_tech);
            $(change_tech).attr("name", full_path_tech);
            $(delete_tech).attr("name", full_path_tech);
            $(ignore_tech).attr("name", full_path_tech);



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
                window.location.href="/deleteElement/".concat(${currentElement.elId});
            }
        });



        $(function(){
            $('.minimized').click(function(event) {
                var i_path = $(this).attr('src');
                $('body').append('<div id="overlay"></div><div id="magnify"><img src="'+i_path+'"><div id="close-popup"><i></i></div></div>');
                $('#magnify').css({
                    left: ($(document).width() - $('#magnify').outerWidth())/2,
                    top: ($(window).height() - $('#magnify').outerHeight())/2
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
