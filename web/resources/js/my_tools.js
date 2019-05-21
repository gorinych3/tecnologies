$(document).ready(function () {
    $(".main").width($(window).width() - 400);

    var asslist = [];

    function Plate(plateId, name, model, type, photo) {
        this.plateId = plateId;
        this.name = name;
        this.model = model;
        this.type = type;
        this.photo = photo;
    }

    function MyTool(toolId, name, model, type, photo, plates) {
        this.toolId = toolId;
        this.tool_name = name;
        this.tool_model = model;
        this.tool_type = type;
        this.tool_photo = photo;
        this.tool_plates = plates;
    }

    getJsonTools();
//----------------------------------------------------------------------------------------------------------------------
// блок получения списка инструмента

    function getJsonTools() {
        var my_dateTools = [];
        var arrPlate = [];
        $.get("/getTxtDataTools", function (data2, status) {
            console.log("Status: " + status);
            $.each(data2, function (key, val) {   //берем строку json
                for (var key1 in val) {
                    my_dateTools.push(val[key1]);  //добавляем в массив значение каждого элемента по ключу
                }
                var name_plate;
                for(var j = my_dateTools[5].length-1; j >= 0; j--){
                    name_plate = my_dateTools[5][j].name;
                }
                if(!(my_dateTools[1] === "Центровка" || my_dateTools[1] === "Сверло" || my_dateTools[1] === "Развертка"
                    || my_dateTools[1] === "Фреза")) {
                    addRow(new MyTool(my_dateTools[0], my_dateTools[1], my_dateTools[2], my_dateTools[3], my_dateTools[4], arrPlate), name_plate);
                }
                my_dateTools.length = 0;
                arrPlate.length = 0;
            });
         });
    }


    //функция построения таблицы по одному объекту
    function addRow(lastElement, name_plate){
        if(lastElement!==0) {
            asslist.push({id: lastElement.toolId, model: lastElement.tool_model});
            var insert = "<tr class='del' style='font-size: 1em'>" +
                "<td>" + lastElement.tool_name + "</td>" +
                "<td class='model ssylka'>"+ lastElement.tool_model+"</td>" +
                "<td>" + lastElement.tool_type + "</td>" +
                "<td>" + name_plate + "</td>" +
                "<td class='ssylka' style='cursor: pointer'><span>фото</span></td>" +
                "</tr>";
            $('.listTools> tbody').append(insert);
        }
    }
//-----------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    //getJsonAddToArray();
// функция получает json, создает массив принимаемых объектов
//и передает массив в функцию построения таблицы для отображения выпадающего списка при добавлении инструмента
    var arr_plates_key_value = [];
    function getJsonAddToArray() {
        var arrObj = [];
        var my_date = [];
        var arrPlates = [];
        $.get("/getTxtDataPlate", function (data1, status) {
            console.log("Status: " + status);
            $.each(data1, function (key, val) {
                for (var key1 in val) {
                    my_date.push(val[key1]);
                }
                arrObj.push(my_date[1]);
                arrPlates.push(new Plate(my_date[0], my_date[1], my_date[2], my_date[3], my_date[4]));
                my_date.length = 0;
            });

            //формируем выпадающий список и ассоциативный массив ключ/значение для дальнейшей отображения инфы
            //после отправки данных на сервер
            for (var i = 0; i < arrPlates.length; i++) {
                $("#addOptions").append("<option  value='"+ arrPlates[i].plateId+"'>" + arrPlates[i].name + "</option>");
                arr_plates_key_value[i] = {id: arrPlates[i].plateId, name: arrPlates[i].name};
            }
        });
    }



//---------------------------------------------------------------------------------------------------------------------
// блок добавления инструмента


    //кнопка добавить
    $(".button2").click(function () {
        $("#add_hide").css("display", "table-row");
        $(".button3").css("display", "block");
        $("#formTool").css("display", "block");
        $("#tableContainer").css("position", "static");
        getJsonAddToArray();
    });


    $(".button4").click(function () {
        $("#add_hide").css("display", "none");
        $("#formTool").css("display", "none");
        $(".button2").css("display", "block");
        $("#tableContainer").css("position", "relative");
    });


    //кнопка принять
    $(".button3").click(function () {
        $("#add_hide").css("display", "none");
        $("#formTool").css("display", "none");
        $(".button2").css("display", "block");
        $("#tableContainer").css("position", "relative");

        var dataPlatesId  = new Array();
        var dataPlateName;
    var dataName = $('input[name="name"]').val();
    var dataModel = ($('input[name="model"]').val()).split('№').join('No.');
    var dataType = $('#type').val();
    $('#addOptions').each(function () {
        dataPlatesId = $(this).val();
    });
        for (var k = 0; k < arr_plates_key_value.length; k++){
            if(arr_plates_key_value[k].id == dataPlatesId[0]){
                dataPlateName = arr_plates_key_value[k].name;
            }
        }

    var dataPhoto = $('input[name="photo"]').val();


    $('.model').each(function () {
        var new_text = $(this).html();
        new_text = new_text.substring(new_text.lastIndexOf('>') + 1);
        if (new_text === dataModel) {
            alert("Такой инструмент уже существует. Измените модель или редактируйте существующий. Вы можете выбрать" +
                "его из списка");
            $('.button2').trigger('click', function () {
                $(this).closest('form')[0].reset();
                input.val('');
            });
            dataModel = '';
            return false;
        }
    });

    var empty = (dataName === '' || dataModel === '' || dataType === '' || dataPhoto === '' || dataPlatesId === '');

    var dataForm = {
        name: dataName,
        model: dataModel,
        type: dataType,
        photo: dataPhoto,
        platesId: dataPlatesId
    };


    //отправка данных на сервер
    var url = $("#formTool").attr("action");

//отправка текстовой информации
    if (!empty) {
        $.ajax({
            url: url,
            type: "POST",
            data: JSON.stringify(dataForm),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function (res) {
                console.log("My message " + res.message + " my id " + res.id);

//если текст обработан и занесен в бд, сервер возвращает номер id для формирования уникального имени файла,
//по которому будет сформирован путь к данному файлу в файловой системе сервера
//в теории, можно было не передавать строкой id, а воспользоваться на сервере request.getSession().setAttribute();
//происходит загрузка файлов
//если загрузка прошла успешно, то последний элемент отображаем на странице
                if (res.message === "success") {
                    var lastEl = new MyTool(res.id, dataName, dataModel, dataType, dataPhoto, null);
                    dataModel = urlLit(dataModel,0);
                    uplaod(dataModel, res.id);
                     addRow(lastEl, dataPlateName);
                    console.log("Данные загрузились");
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
        url:'/uploadFilesTools',
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

    });

    //при переходе по ссылке получаем именя файла и отправляем запрос к серверу на подгрузку файла
    $('.listTools').on('click', 'tbody tr td:nth-child(5)', function(e) {
        var text = $(this).siblings('td:nth-child(2)').text();
        var id;
        for(var n = 0; n < asslist.length; n++){
            if(asslist[n].model === text){
                id = asslist[n].id;
            }
        }
        var newFileName = urlLit(((text + "-"+"0"+"_"+id).trim()).split('.').join("-"),0);
         var down1 = "downloadToolsFiles/".concat(newFileName);
         $("#prim").attr('src', down1);
         $("#prim").css('display','block');
    });

//блок перехода по ссылке на страницу полной информации об объекте
    $('.listTools').on('click', 'tbody tr td:nth-child(2)', function(e) {
        var text = $(this).html();
        var id;
        for(var n = 0; n < asslist.length; n++){
            if(asslist[n].model === text){
                id = asslist[n].id;
            }
        }
        window.location.href="/gettool/".concat(id);
    });
//-----------------------------------------------------------------

        //функция транслитерации для формирования имени файла и пути к нему
        function urlLit(w, v) {
            var tr = 'a b v g d e ["zh","j"] z i y k l m n o p r s t u f h c ch sh ["shh","shch"] ~ y ~ e yu ya ~ ["jo","e"]'.split(' ');
            var ww = '';
            w = w.toLowerCase();
            for (var i = 0; i < w.length; ++i) {
                var cc = w.charCodeAt(i);
                var ch = (cc >= 1072 ? tr[cc - 1072] : w[i]);
                if (ch.length < 3) ww += ch; else ww += eval(ch)[v];
            }
            return (ww.replace(/[^a-zA-Z0-9\-]/g, '-').replace(/[-]{2,}/gim, '-').replace(/^\-+/g, '').replace(/\-+$/g, ''));
        }

});