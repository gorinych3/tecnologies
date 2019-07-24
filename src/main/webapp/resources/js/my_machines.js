$(document).ready(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });


    $(".main").width($(window).width()-400);

    var asslist = [];

    function Machine(machineId, name, model, idNum, photo) {
        this.machineId = machineId;
        this.name = name;
        this.model = model;
        this.idNum = idNum;
        this.photo = photo;
    }

    getJsonAddToArray();
// функция получает json, создает массив принимаемых объектов
//и передает массив в функцию построения таблицы
    function getJsonAddToArray() {
        var arrObj = [];
        var my_date = [];
        $.get("/tecnologies-1.0-SNAPSHOT/getTxtDataMachines", function (data1, status) {
            console.log("Status: " + status);
            $.each(data1, function (key, val) {
                for (var key1 in val) {
                    my_date.push(val[key1]);
                }
                arrObj.push(new Machine(my_date[0], my_date[1], my_date[2], my_date[3], my_date[4]));
                my_date.length = 0;
            });
            for ( var i=0;i<arrObj.length;i++){
                addRow(arrObj[i]);
            }
        });
    }

//функция построения таблицы по одному объекту
    function addRow(lastElement){
        asslist.push({id: lastElement.machineId, idNum: lastElement.idNum});
        if(lastElement!==0) {
            var insert = "<tr class='del' style='font-size: 1em'>" +
                "<td>" + lastElement.name + "</td>" +
                "<td class='model ssylka'>"+ lastElement.model+"</td>" +
                "<td>" + lastElement.idNum + "</td>" +
                "<td class='ssylka' style='cursor: pointer'><span>фото</span></td>" +
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



        var dataName = $('#machine_name').val();
        var dataModel = $('#inMod').val();
        var dataIdNumb = $('#type').val();
        var dataPhoto = $('input[name="photo"]').val();


        $('.model').each(function () {
            var new_text = $(this).html();
            var machine_name = $(this).prev().html();
            var machine_type = $(this).next().html();
            if (new_text === dataModel && machine_name === dataName && machine_type === dataIdNumb) {
                alert("Такой станок уже существует. Измените модель или редактируйте существующую. Вы можете выбрать" +
                    "ее из списка");
                $('.button2').trigger('click', function() {
                    $(this).closest('form')[0].reset();
                    input.val('');
                });
                dataModel = '';
                return false;
            }
        });

        var empty = (dataName===''||dataModel===''||dataIdNumb===''||dataPhoto==='');

        var dataForm = {
            name: dataName,
            model: dataModel,
            idNumber: dataIdNumb,
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
                        var lastEl = new Machine(res.id, dataName, dataModel, dataIdNumb, dataPhoto);
                        dataModel = urlLit(dataIdNumb,0);
                        uplaod(dataIdNumb, res.id);
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
    uplaod = function(idNumb, id){
        var data = new FormData();
        jQuery.each(jQuery('#files')[0].files, function(i, file) {
            var fileExtension = '.' + file.name.split('.').pop();
            var fileName = idNumb+"-"+i+"-"+id;
            data.append('file-'+i, file, fileName.concat(fileExtension));
        });

        $.ajax({
            url:'/tecnologies-1.0-SNAPSHOT/uploadFilesMachine',
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
    $('.list').on('click', 'tbody tr td:nth-child(4)', function(e) {
        var text = $(this).siblings('td:nth-child(3)').text();
        var id;
        for(var n = 0; n < asslist.length; n++){
            if(asslist[n].idNum === text){
                id = asslist[n].id;
            }
        }
        var newFileName = urlLit(((text + "-"+"0"+"_"+id).trim()).split('.').join("-"),0);
        var down1 = "/tecnologies-1.0-SNAPSHOT/downloadMachineFiles/".concat(newFileName);
        $("#prim").attr('src', down1);
        $("#prim").css('display','block');
    });

    //блок перехода по ссылке на страницу полной информации об объекте
    $('.list').on('click', 'tbody tr td:nth-child(2)', function(e) {
        var text = $(this).siblings('td:nth-child(3)').text();
        var id;
        for(var n = 0; n < asslist.length; n++){
            if(asslist[n].idNum === text){
                id = asslist[n].id;
            }
        }
        window.location.href="/tecnologies-1.0-SNAPSHOT/getMachine/".concat(id);
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