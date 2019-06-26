<%--
  Created by IntelliJ IDEA.
  User: Егор
  Date: 03.06.2019
  Time: 0:48
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ include file="/WEB-INF/views/include.jsp" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
    <sec:csrfMetaTags/>
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
            <form:form id="formElement" name="formElement" action="${pageContext.request.contextPath}/updateElement">
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
                <span id="elid" hidden>${currentElement.elId}</span>


            </form:form>

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
                <span style="font-weight: bolder">Фото детали:</span>
                <div  id="updateElementPhoto">

                </div>
                <span style="font-weight: bolder">Технология:</span>
                <div id="updateElementTechnology">

                </div>
            </div>

        </section>
    </div>
</div>

        <div class="fixForm">
            <div id="formTool" class="form">
            <input id="photo_update" type="file" accept="image/jpeg" name="photo_update">
            <div class="tableRow">
                <p>
                    <input class="button3" type="button" value="Принять">
                </p>
                <p>
                    <input class="button2" type="button" value="Отменить">
                </p>
            </div>
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

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function(e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });

            for (var z = 0; z < ${countPathPhoto}; z++){
                create_one_div_photo(z);
            }

            for (var x = 0; x < ${countPathTech}; x++){
                create_one_div_tech(x);
            }

            function create_one_div_photo(id_number_photo) {
                $('#updateElementPhoto').append("<div class='image__wrapper tableRow photo' style='font-weight: bolder; color: black'></div>" +
                    "<p><img id='id_photo_"+id_number_photo+"' class='bigImg minimized photos clp"+id_number_photo+"' alt='клик для увеличения' src='' height='100'>"+
                    "<input id='change_photo_"+id_number_photo+"' type='button' class='button6 clp"+id_number_photo+"' value='Заменить' onclick='updateFiles(name, id)'>");

                var photo_1 = "${currentElement.nameElement}";
                var photo_2 = "${currentElement.idNumb}";
                var photo = "photo-"+photo_1+"-"+photo_2;
                var path_photo = urlLit(photo,0);
                var name_photo = path_photo+"-"+id_number_photo+"-"+${currentElement.elId};
                var full_path_photo = "${pageContext.request.contextPath}/downloadElementFilesPhoto/" + path_photo+"-"+id_number_photo+"-"+${currentElement.elId};
                console.log("full_path_photo = " + full_path_photo);
                var ident_photo = '#id_photo_'+id_number_photo;
                var change_photo = '#change_photo_'+id_number_photo;
                $(ident_photo).attr('src', full_path_photo);
                $(change_photo).attr("name", name_photo);
            }



            function create_one_div_tech(id_number_tech) {
                $('#updateElementTechnology').append("<div class='image__wrapper tableRow  tech' style='font-weight: bolder; color: black;'></div>" +
                    "<p><img id='id_tech_"+id_number_tech+"' class='bigImg minimized techs clt"+id_number_tech+"' alt='клик для увеличения' src='' height='100'>"+
                    "<input id='change_tech_"+id_number_tech+"' type='button' class='button6 clt"+id_number_tech+"' value='Заменить' onclick='updateFiles(name, id)'>");

                var tech_1 = "${currentElement.nameElement}";
                var tech_2 = "${currentElement.idNumb}";
                var tech = "tech-"+tech_1+"-"+tech_2;
                var path_tech = urlLit(tech,0);
                var name_tech = path_tech+"-"+id_number_tech+"-"+${currentElement.elId};
                var full_path_tech = "${pageContext.request.contextPath}/downloadElementFilesPhoto/" + path_tech+"-"+id_number_tech+"-"+${currentElement.elId};
                var ident_tech = '#id_tech_'+id_number_tech;
                var change_tech = '#change_tech_'+id_number_tech;
                $(ident_tech).attr('src', full_path_tech);
                $(change_tech).attr("name", name_tech);
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
<script>
    function updateFiles(name, id) {

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function(e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });

        $('.fixForm').css('display', 'block');
        $("#formTool").css("display", "block");
        $('.button3').css('display', 'block');
        $("#tableContainer").css("position", "static");

        //отменить
        $(".button2").click(function () {

            $(".fixForm").css("display", "none");
            $("#formTool").css("display", "none");
            $("#tableContainer").css("position", "relative");
        });

            //принять
        $('.button3').click(function () {
        $(".fixForm").css("display", "none");
        $("#formTool").css("display", "none");
        $('.button3').css('display', 'none');
        $("#tableContainer").css("position", "relative");

        var fileExtension = '.jpg';
        var data = {file: name.concat(fileExtension)};

        upload_new_files(name);

        function upload_new_files(new_file_name) {
            var data = new FormData();
            jQuery.each(jQuery('#photo_update')[0].files, function (i, file) {
                var fileExtension = '.' + file.name.split('.').pop();
                data.append('file-' + i, file, new_file_name.concat(fileExtension));

                $.ajax({
                    url: '/changeFilesElements',
                    data: data,
                    cache: false,
                    contentType: false,
                    processData: false,
                    type: 'POST',
                    async: true,
                    success: function (response) {
                        if (response.Status === 200) {
                            console.log(response.SucessfulList);
                            downloadNewFile();
                        } else {
                            console.log('Error');
                        }
                    }
                });

            });

        };


        function downloadNewFile() {
        var class_name = name.substring(0,1);
        var flag;
        if(class_name == 'p'){
            flag = '#id_photo_';
        }
        if(class_name == 't'){
            flag = '#id_tech_';
        }
        id = id.substring(id.lastIndexOf('_')+1);
        id = flag+id;
        $(id).attr('src', '');
        var new_src = "/downloadElementFilesPhoto/"+name;
        new_src = new_src + '?' + Math.random();
        $(id).attr('src', new_src);
        }
        })
    }
</script>
</body>
</html>
