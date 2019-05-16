$(document).ready(function () {
    $(".main").width($(window).width() - 400);

    var asslist = [];


    function MyDrill(toolId, name, model, type) {
        this.toolId = toolId;
        this.tool_name = name;
        this.tool_model = model;
        this.tool_type = type;
    }

     getJsonDrills();
//----------------------------------------------------------------------------------------------------------------------
// блок получения списка инструмента

    function getJsonDrills() {
        var my_dateTools = [];
        $.get("/getTxtDataTools", function (data2, status) {
            console.log("Status: " + status);
            $.each(data2, function (key, val) {   //берем строку json
                for (var key1 in val) {
                    my_dateTools.push(val[key1]);  //добавляем в массив значение каждого элемента по ключу
                }
                if(my_dateTools[1] === "Центровка" || my_dateTools[1] === "Сверло" || my_dateTools[1] === "Развертка"
                    || my_dateTools[1] === "Фреза") {
                    addRow(new MyDrill(my_dateTools[0], my_dateTools[1], my_dateTools[2], my_dateTools[3]));
                }
                    my_dateTools.length = 0;

            });
        });
    }




    //функция построения таблицы по одному объекту
    function addRow(lastElement){
        if(lastElement!==0) {
            asslist.push({id: lastElement.toolId, model: lastElement.tool_model});
            var insert = "<tr class='del' style='font-size: 1em'>" +
                "<td>" + lastElement.tool_name + "</td>" +
                "<td class='model'>"+ lastElement.tool_model.replace(',', '.')+"</td>" +
                "<td>" + lastElement.tool_type + "</td>" +
                "</tr>";
            $('.listTools> tbody').append(insert);
        }
    }

//---------------------------------------------------------------------------------------------------------------------
// блок добавления инструмента


    //кнопка добавить
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


    //кнопка принять
    $(".button3").click(function () {
        $("#add_hide").css("display", "none");
        $("#formTool").css("display", "none");
        $(".button2").css("display", "block");
        $("#tableContainer").css("position", "relative");


        var dataName = $('#drill_name').val();
        var dataModel = ($('input[name="model"]').val()).split('№').join('No.');
        var dataType = $('#type').val();

        dataModel = dataModel.replace(",", ".");

        if(!($.isNumeric(dataModel))){
            alert("Введите корректный диаметр");
                return false;
        }

        $('.model').each(function () {
            var new_text = $(this).html();
            new_text = new_text.substring(new_text.lastIndexOf('>') + 1);
            var drill_name = $(this).prev().html();
            var drill_type = $(this).next().html();
            if (new_text === dataModel && drill_name === dataName && drill_type === dataType) {
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



        var empty = (dataName === '' || dataModel === '' || dataType === '');

        var dataForm = {
            name: dataName,
            model: dataModel,
            type: dataType,
            photo: "not have",
            platesId: "not have"
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

//если загрузка прошла успешно, то последний элемент отображаем на странице
                    if (res.message === "success") {
                        var lastEl = new MyDrill(res.id, dataName, dataModel, dataType);
                        addRow(lastEl);
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

    });

});