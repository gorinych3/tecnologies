$(document).ready(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });


    $(".main").width($(window).width()-400);

    function Plate(plateId, name, model, type, photo) {
        this.plateId = plateId;
        this.name = name;
        this.model = model;
        this.type = type;
        this.photo = photo;
    }

    getJsonAddToArray();
// функция получает json, создает массив принимаемых объектов
//и передает массив в функцию построения таблицы
    function getJsonAddToArray() {
        var arrObj = [];
        var my_date = [];
        $.get("/getTxtDataPlate", function (data1, status) {
            console.log("Status: " + status);
            $.each(data1, function (key, val) {
                for (var key1 in val) {
                    my_date.push(val[key1]);
                }
                arrObj.push(new Plate(my_date[0], my_date[1], my_date[2], my_date[3], my_date[4]));
                my_date.length = 0;
            });
            for ( var i=0;i<arrObj.length;i++){
                addRow(arrObj[i]);
            }
        });
    }

//функция построения таблицы по одному объекту
    function addRow(lastElement){
        if(lastElement!==0) {
            var newFileName = urlLit(((lastElement.model + "-"+"0"+"_"+lastElement.plateId).trim()).split('.').join("-"),0);
            var insert = "<tr class='del' style='font-size: 1em'>" +
                "<td>" + lastElement.name + "</td>" +
                "<td class='model' style='cursor: pointer; text-decoration: underline'><span style='display: none'>" + newFileName + "</span>"+ lastElement.model+"</td>" +
                "<td>" + lastElement.type + "</td>" +
                "<td style='cursor: pointer; text-decoration: underline'>фото</td>" +
                "</tr>";
            $('.list> tbody').append(insert);
        }
    }
//-----------------------------------------------------------------


//обработка нажатия кнопок, функция добавляет форму на страницу и отправляет данные
//на сервер. Далее нужно отобразить полученный список на странице

    $(".button2").click(function () {
        $("#add_hide").css("display", "table-row");
        $(".button3").css("display", "block");
        $("#formTool").css("display", "block");
        $("#tableContainer").css("position", "static");
    });

    $(".button4").click(function () {
        $("#add_hide").css("display", "none");
        $("#formTool").css("display", "none");
        $(".button2").css("display", "block");
        $("#tableContainer").css("position", "relative");
    });

    $(".button3").click(function () {
        $("#add_hide").css("display", "none");
        $("#formTool").css("display", "none");
        $(".button2").css("display", "block");
        $("#tableContainer").css("position", "relative");



        var dataName = $('input[name="name"]').val();
        var dataModel = ($('input[name="model"]').val()).split('№').join('No.');
        var dataType = $('#type').val();
        var dataPhoto = $('input[name="photo"]').val();


        $('.model').each(function () {
            var new_text = $(this).html();
            new_text = new_text.substring(new_text.lastIndexOf('>')+1);
            if(new_text===dataModel){
                alert("Такая пластина уже существует. Измените модель или редактируйте существующую. Вы можете выбрать" +
                    "ее из списка");
                $('.button2').trigger('click', function() {
                    $(this).closest('form')[0].reset();
                    input.val('');
                });
                dataModel = '';
                return false;
            }
        });

        var empty = (dataName===''||dataModel===''||dataType===''||dataPhoto==='');

        var dataForm = {
            name: dataName,
            model: dataModel,
            type: dataType,
            photo: dataPhoto
        };

//отправка данных на сервер
        var url = $("#formTool").attr("action");

//отправка текстовой информации
        if(!empty) {
            $.ajax({
                url: url,
                type: "POST",
                data: JSON.stringify(dataForm),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function (res) {
                    console.log("My message "+ res.message+" my id "+res.id);

//если текст обработан и занесен в бд, сервер возвращает номер id для формирования уникального имени файла,
//по которому будет сформирован путь к данному файлу в файловой системе сервера
//в теории, можно было не передавать строкой id, а воспользоваться на сервере request.getSession().setAttribute();
//происходит загрузка файлов
//если загрузка прошла успешно, то последний элемент отображаем на странице
                    if(res.message === "success"){
                        var lastEl = new Plate(res.id, dataName, dataModel, dataType, dataPhoto);
                        dataModel = urlLit(dataModel,0);
                        uplaod(dataModel, res.id);
                        addRow(lastEl);
                    } else {
                        console.log("Error: " + res.message);
                        $("#err").css("display", "block");
                        $("#erMessage").html("Error: " + res.message);
                    }
                }
            });
        } else {
            alert("Заполните все поля");
        }
    });
    $("#err").click(function () {
        $(this).css("display", "none");
    });

//функция отправки файлов серверу
    uplaod = function(model, id){
        var data = new FormData();
        jQuery.each(jQuery('#files')[0].files, function(i, file) {
            var fileExtension = '.' + file.name.split('.').pop();
            var fileName = model+"-"+i+"-"+id;
            data.append('file-'+i, file, fileName.concat(fileExtension));
        });

        $.ajax({
            url:'/uploadFiles',
            data: data,
            cache: false,
            contentType: false,
            processData: false,
            type:'POST',
            success: function(response){
                if(response.Status === 200){
                    console.log(response.SucessfulList);
                }else{
                    console.log('Error');
                }
            }
        });
    };

//при переходе по ссылке получаем именя файла и отправляем запрос к серверу на подгрузку файла
    $('.list').on('click', 'tbody tr td:last-child', function(e) {
        var text = $(this).siblings('td').find('span').text();
        var down1 = "download1/".concat(text);
        $("#prim").attr('src', down1);
        $("#prim").css('display','block');
    });

//блок перехода по ссылке на страницу полной информации об объекте
    $('.list').on('click', 'tbody tr td:nth-child(2)', function(e) {
        var text = $(this).html();
        var span = text.substring(text.indexOf('>')+1, text.lastIndexOf('<'));
        var number_first = span.lastIndexOf('-');
        var id = span.substring(number_first+1);
        window.location.href="/getplate/".concat(id);
    });

//функция транслитерации для формирования имени файла и пути к нему
    function urlLit(w,v) {
        var tr='a b v g d e ["zh","j"] z i y k l m n o p r s t u f h c ch sh ["shh","shch"] ~ y ~ e yu ya ~ ["jo","e"]'.split(' ');
        var ww=''; w=w.toLowerCase();
        for(var i=0; i<w.length; ++i) {
            var cc=w.charCodeAt(i); var ch=(cc>=1072?tr[cc-1072]:w[i]);
            if(ch.length<3) ww+=ch; else ww+=eval(ch)[v];}
        return(ww.replace(/[^a-zA-Z0-9\-]/g,'-').replace(/[-]{2,}/gim, '-').replace( /^\-+/g, '').replace( /\-+$/g, ''));
    }
});