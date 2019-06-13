$(document).ready(function () {

    start_new_script();

    function start_new_script() {

        console.log("Новый скрипт заработал");

        var countPathPhoto = $('#countPathPhoto').val();
        var countPathTech = $('#countPathTech').val();
        var currentElement_elId = $('#currentElement_elId').val();
        var currentElement_nameElement = $('#currentElement_nameElement').val();
        var currentElement_idNumb = $('#currentElement_idNumb').val();

        console.log(countPathPhoto + "  " + countPathTech + "  " + currentElement_elId + "  " + currentElement_nameElement + "  " +
            currentElement_idNumb);

        for (var z = 0; z < countPathPhoto; z++) {
            $('#updateElementPhoto').append("<div class='image__wrapper tableRow photo' style='font-weight: bolder; color: black'></div>" +
                "<p><img id='id_photo_" + z + "' class='bigImg minimized photos clp" + z + "' alt='клик для увеличения' src='' height='100'>" +
                "<input id='change_photo_" + z + "' type='button' class='button6 clp" + z + "' value='Заменить'>" +
                "<input id='delete_photo_" + z + "' type='button' class='button7 clp" + z + "' value='Удалить' onclick='delFiles(name, id)'></p>");
        }


        for (var j = 0; j < countPathPhoto; j++) {
            var photo_1 = currentElement_nameElement;
            var photo_2 = currentElement_idNumb;
            var photo = "photo-" + photo_1 + "-" + photo_2;
            var path_photo = urlLit(photo, 0);
            var name_photo = path_photo + "-" + j + "-" + currentElement_elId;
            var full_path_photo = "${pageContext.request.contextPath}/downloadElementFilesPhoto/" + path_photo + "-" + j + "-" + currentElement_elId;
            console.log("full_path_photo = " + full_path_photo);
            var ident_photo = '#id_photo_' + j;
            var change_photo = '#change_photo_' + j;
            var delete_photo = '#delete_photo_' + j;
            var ignore_photo = '#ignore_photo_' + j;
            $(ident_photo).attr('src', full_path_photo);
            $(change_photo).attr("name", name_photo);
            $(delete_photo).attr("name", name_photo);
            $(ignore_photo).attr("name", name_photo);

        }

        for (var x = 0; x < countPathTech; x++) {
            $('#updateElementTechnology').append("<div class='image__wrapper tableRow  tech' style='font-weight: bolder; color: black;'></div>" +
                "<p><img id='id_tech_" + x + "' class='bigImg minimized techs clt" + x + "' alt='клик для увеличения' src='' height='100'>" +
                "<input id='change_tech_" + x + "' type='button' class='button6 clt" + x + "' value='Заменить'>" +
                "<input id='delete_tech_" + x + "' type='button' class='button7 clt" + x + "' value='Удалить' onclick='delFiles(name, id)'></p>");
        }


        for (var j = 0; j < countPathTech; j++) {
            var tech_1 = currentElement_nameElement;
            var tech_2 = currentElement_idNumb;
            var tech = "tech-" + tech_1 + "-" + tech_2;
            var path_tech = urlLit(tech, 0);
            var name_tech = path_tech + "-" + j + "-" + currentElement_elId;
            var full_path_tech = "${pageContext.request.contextPath}/downloadElementFilesPhoto/" + path_tech + "-" + j + "-" + currentElement_elId;
            console.log("full_path_tech = " + full_path_tech);
            var ident_tech = '#id_tech_' + j;
            var change_tech = '#change_tech_' + j;
            var delete_tech = '#delete_tech_' + j;
            var ignore_tech = '#ignore_tech_' + j;
            $(ident_tech).attr('src', full_path_tech);
            $(change_tech).attr("name", name_tech);
            $(delete_tech).attr("name", name_tech);
            $(ignore_tech).attr("name", name_tech);


        }


        function urlLit(w, v) {
            var tr = 'a b v g d e ["zh","j"] z i y k l m n o p r s t u f h c ch sh ["shh","shch"] ~ y ~ e yu ya ~ ["jo","e"]'.split(' ');
            var ww = '';
            w = w.toLowerCase();
            for (i = 0; i < w.length; ++i) {
                cc = w.charCodeAt(i);
                ch = (cc >= 1072 ? tr[cc - 1072] : w[i]);
                if (ch.length < 3) ww += ch; else ww += eval(ch)[v];
            }
            return (ww.replace(/[^a-zA-Z0-9\-]/g, '-').replace(/[-]{2,}/gim, '-').replace(/^\-+/g, '').replace(/\-+$/g, ''));
        }

        $("#del").click(function () {
            if (confirm("Вы точно хотите удалить данную деталь? Вся информация о ней будет удалена!!")) {
                window.location.href = "/deleteElement/".concat(currentElement_elId);
            }
        });


        $(function () {
            $('.minimized').click(function (event) {
                var i_path = $(this).attr('src');
                $('body').append('<div id="overlay"></div><div id="magnify"><img src="' + i_path + '"><div id="close-popup"><i></i></div></div>');
                $('#magnify').css({
                    left: ($(document).width() - $('#magnify').outerWidth()) / 2,
                    top: ($(window).height() - $('#magnify').outerHeight()) / 2
                });
                $('#overlay, #magnify').fadeIn('fast');
            });

            $('body').on('click', '#close-popup, #overlay', function (event) {
                event.preventDefault();

                $('#overlay, #magnify').fadeOut('fast', function () {
                    $('#close-popup, #magnify, #overlay').remove();
                });
            });
        });

        function delFiles(name, id) {
            var fileExtension = '.jpg';
            console.log(name.concat(fileExtension));
            var data = {file: name.concat(fileExtension)};
            var class_name = name.substring(0, 1);
            var flag;
            if (class_name == 't') {
                flag = '.clt';
            }
            if (class_name == 'p') {
                flag = '.clp';
            }


            $.ajax({
                url: '/deleteFilesElements',
                data: JSON.stringify(data),
                cache: false,
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                processData: false,
                type: 'POST',
                async: true,
                success: function (response) {
                    console.log(response);
                }
            });
            console.log(id);
            id = id.substring(id.lastIndexOf('_') + 1);
            console.log(id);
            id = flag + id;
            console.log(id);
            $(id).css('display', 'none');
        }
    }

    console.log("Новый скрипт заработал - 2");
});

