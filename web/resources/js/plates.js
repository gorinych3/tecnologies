$(document).ready(function () {
    $("#main").width($(window).width()-400);

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
        $.get("/ajaxtest", function (data1, status) {
            console.log("Status: " + status);
            $.each(data1, function (key, val) {
                for (var key1 in val) {
                    my_date.push(val[key1]);
                }
                arrObj.push(new Plate(my_date[0], my_date[1], my_date[2], my_date[3], my_date[4]));
                //arrObj.reverse();
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
            var insert = "<tr class='my_href' class='del' style='font-size: 1em'>" +
                "<td>" + lastElement.name + "</td>" +
                "<td>" + lastElement.model + "</td>" +
                "<td>" + lastElement.type + "</td>" +
                "<td><a href='"+lastElement.photo+"'>фото</a></td>" +
                "</tr>";
            $(insert).insertAfter($("tr:first"));
        }
        lastElement = 0;
    }
//-----------------------------------------------------------------


//обработка нажатия кнопок, функция добавляет форму на страницу и отправляет данные
//на сервер. Далее нужно отобразить полученный список на странице


    $(".button2").click(function () {
        $("#add_hide").css("display", "table-row");
        $(".button2").css("display", "none");
        $(".button3").css("display", "block");
    });

    $(".button3").click(function () {
        $("#add_hide").css("display", "none");
        $(".button3").css("display", "none");
        $(".button2").css("display", "block");

        var dataName = $('input[name="name"]').val();
        var dataModel = $('input[name="model"]').val();
        var dataType = $('input[name="type"]').val();
        var dataPhoto = $('input[name="photo"]').val();

        var empty = (dataName===''||dataModel===''||dataType===''||dataPhoto==='');

        var dataForm = {
            name: dataName,
            model: dataModel,
            type: dataType,
            photo: dataPhoto
        };

        //dataPhoto = "фото "+dataPhoto;

        var url = $("#formPlate").attr("action");

        if(!empty) {
            $.ajax({
                url: url,
                type: "POST",
                data: JSON.stringify(dataForm),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function (res) {
                    if(res === "success"){
                        console.log(res);
                        var lastEl = new Plate(null, dataName, dataModel, dataType, dataPhoto);
                        addRow(lastEl);
                    } else {
                        console.log("Error: " + res);
                        $("#err").css("display", "block");
                        $("#erMessage").html("Error: " + res);
                    }
                }
            });

        } else {
            alert("Заполните все поля");
        }
    });

    $("#err").click(function () {
        $(this).css("display", "none");
    })
});