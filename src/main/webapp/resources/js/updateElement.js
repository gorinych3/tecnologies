$(document).ready(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });


    $(".main").width($(window).width() - 400);

    var arrPlates = [];
    var arrTools = [];
    var arrMachines = [];
    var arrElements = [];
    var elem = new Element();

    function Plate(plateId, name, model, type, photo) {
        this.plateId = plateId;
        this.name = name;
        this.model = model;
        this.type = type;
        this.photo = photo;
    }

    function MyTool(toolId, name, model, type, photo, plates) {
        this.toolId = toolId;
        this.name = name;
        this.model = model;
        this.type = type;
        this.photo = photo;
        this.plates = plates;
    }

    function Machine(machId, name, model, idNumber, photo){
        this.machId = machId;
        this.name = name;
        this.model = model;
        this.idNumber = idNumber;
        this.photo = photo;
    }

    function Element(elId, nameElement, idNumb, technology, program, setup, notation, tools, plates, machines){
        this.elId = elId;
        this.nameElement = nameElement;
        this.idNumb = idNumb;
        this.technology = technology;
        this.program = program;
        this.setup = setup;
        this.notation = notation;
        this.tools = tools;
        this.plates = plates;
        this.machines = machines;
    }


    getJsonAddToArrayTools();
    getJsonAddToArrayPlates();
    getJsonAddToArrayMachines();
    setTimeout(getJsonElement(), 100);
//----------------------------------------------------------------------------------------------------------------------

    // функция получает json, создает массив принимаемых объектов
    function getJsonElement() {
        var my_date = [];
        var id = $("#elid").html();
        var url_get_el = "/tecnologies-1.0-SNAPSHOT/getTxtDataOneElement/"+id;

        $.getJSON(url_get_el, function(data, status) {
            console.log("Status get element: " + status);
            $.each(data, function (key, val) {
                my_date.push(val);
            });

                elem.elId = my_date[0];
                elem.nameElement = my_date[1];
                elem.idNumb = my_date[2];
                elem.technology = my_date[3];
                elem.program = my_date[4];
                elem.setup = my_date[5];
                elem.notation = my_date[6];
                elem.tools = my_date[7];
                elem.plates = my_date[8];
                elem.machines = my_date[9];
                $("#prog").val(elem.program);
                $("#setup").val(elem.setup);
                $("#notation").val(elem.notation);

                for(var i = 0; i < elem.tools.length; i++) {
                    var idi = "#" + elem.tools[i].toolId;
                    $(idi).css("background-color", "#FFA07A");
                }

                for(var j = 0; j < elem.plates.length; j++) {
                    var idj = "#" + elem.plates[j].plateId;
                    $(idj).css("background-color", "#FFA07A");
                }

                for(var k = 0; k < elem.machines.length; k++) {
                    var idk = "#" + elem.machines[k].machId;
                    $(idk).css("background-color", "#FFA07A");
                }
        });
    };

// функция получает json, создает массив принимаемых объектов
//и передает массив в функцию построения таблицы для отображения выпадающего списка при добавлении инструмента
    function getJsonAddToArrayPlates() {
        var my_date = [];
        $.get("/tecnologies-1.0-SNAPSHOT/getTxtDataPlate", function (data1, status) {
            console.log("Status get plates: " + status);
            $.each(data1, function (key, val) {
                for (var key1 in val) {
                    my_date.push(val[key1]);
                }
                arrPlates.push(new Plate(my_date[0], my_date[1], my_date[2], my_date[3], my_date[4]));
                my_date.length = 0;
            });

            //формируем выпадающий список и ассоциативный массив ключ/значение для дальнейшей отображения инфы
            //после отправки данных на сервер
            for (var i = 0; i < arrPlates.length; i++) {
                $("#setPlates").append("<option id='"+arrPlates[i].plateId+"' value='"+ arrPlates[i].plateId+"'>" + arrPlates[i].name +
                    " "+ arrPlates[i].model+"   "+ arrPlates[i].type+"</option>");
            }
        });
    }

    //getJsonAddToArray();
// функция получает json, создает массив принимаемых объектов
//и передает массив в функцию построения таблицы для отображения выпадающего списка при добавлении инструмента
    function getJsonAddToArrayTools() {
        var my_date = [];
        $.get("/tecnologies-1.0-SNAPSHOT/getTxtDataTools", function (data1, status) {
            console.log("Status get tools: " + status);
            $.each(data1, function (key, val) {
                for (var key1 in val) {
                    my_date.push(val[key1]);
                }
                arrTools.push(new MyTool(my_date[0], my_date[1], my_date[2], my_date[3], my_date[4]));
                my_date.length = 0;
            });

            //формируем выпадающий список
            $("#setTools").append("<optgroup id='sverlo' label='Сверла'>");
            $("#setTools").append("<optgroup id='freza' label='Фрезы'>");
            $("#setTools").append("<optgroup id='razvertka' label='Развертки'>");
            $("#setTools").append("<optgroup id='centrovka' label='Центровки'>");
            $("#setTools").append("<optgroup id='rezec' label='Резцы'>");


            for (var i = 0; i < arrTools.length; i++) {
                var el_of_list = "<option  id='"+arrTools[i].toolId+"' value='" + arrTools[i].toolId + "'>" + arrTools[i].name +
                    " " + arrTools[i].model +"   "+arrTools[i].type+"</option>";
                if(arrTools[i].name === "Центровка") {
                    $("#centrovka").append(el_of_list);
                } else
                if(arrTools[i].name === "Фреза") {
                    $("#freza").append(el_of_list);
                } else
                if(arrTools[i].name === "Сверло") {
                    $("#sverlo").append(el_of_list);
                } else
                if(arrTools[i].name === "Развертка") {
                    $("#razvertka").append(el_of_list);
                } else {
                    $("#rezec").append(el_of_list);
                }
            }
        });
    }

    // функция получает json, создает массив принимаемых объектов
//и передает массив в функцию построения таблицы для отображения выпадающего списка при добавлении инструмента
    function getJsonAddToArrayMachines() {
        var my_date = [];
        $.get("/tecnologies-1.0-SNAPSHOT/getTxtDataMachines", function (data1, status) {
            console.log("Status get machines: " + status);
            $.each(data1, function (key, val) {
                for (var key1 in val) {
                    my_date.push(val[key1]);
                }
                arrMachines.push(new Machine(my_date[0], my_date[1], my_date[2], my_date[3], my_date[4]));
                my_date.length = 0;
            });

            //формируем выпадающий список и ассоциативный массив ключ/значение для дальнейшей отображения инфы
            //после отправки данных на сервер
            for (var i = 0; i < arrMachines.length; i++) {
                $("#setMachines").append("<option id='"+arrMachines[i].machId+"' value='"+ arrMachines[i].machId+"'>" + arrMachines[i].name +
                    " "+ arrMachines[i].model+"   "+ arrMachines[i].idNumber+"</option>");
            }
        });
    }

//---------------------------------------------------------------------------------------------------------------------
// блок добавления инструмента

    //кнопка отменить
    $(".button4").click(function () {
        window.history.back();
    });


    //кнопка принять
    $(".button5").click(function () {

        var dataPlatesId = [];
        var dataToolsId = [];
        var dataMachineId = [];
        var dataPlate;
        var dataTool;
        var dataMachine;

        var element_programm = $('input[name="program"]').val();
        var element_setup = $("#setup").val();
        var element_notation = $("#notation").val();

        var flag = "Ничего не менять";
        if ($('#setPlates').val() == flag || $('#setPlates').val() == '') {
            dataPlate = elem.plates;
        } else {
            $('#setPlates').each(function () {
                dataPlatesId = $(this).val();
            });
        }

        if ($('#setTools').val() == flag || $('#setTools').val() == '') {
            dataTool = elem.tools;
        } else {
            $('#setTools').each(function () {
                dataToolsId = $(this).val();
            });
        }

        if ($('#setMachines').val() == flag || $('#setMachines').val() == '') {
            dataMachine = elem.machines;
        } else {
            $('#setMachines').each(function () {
                dataMachineId = $(this).val();
            });
        }


        if (dataPlate === undefined) {
            dataPlate = createArrsForSendToServer(dataPlatesId, arrPlates, 'plateId');
        }
        if (dataTool === undefined) {
            dataTool = createArrsForSendToServer(dataToolsId, arrTools, 'toolId');
        }
        if (dataMachine === undefined) {
            dataMachine = createArrsForSendToServer(dataMachineId, arrMachines, 'machId');
        }

        function createArrsForSendToServer(arrId, arrObj, id) {
            var resArr = [];
            for (var i = 0; i < arrId.length; i++) {
                for (var j = 0; j < arrObj.length; j++) {
                    if (arrObj[j][id] == arrId[i]) {
                        resArr.push(arrObj[j]);
                    }
                }
            }
            return resArr;
        }

        var dataForm = {
            elId: elem.elId,
            nameElement: elem.nameElement,
            idNumb: elem.idNumb,
            technology: elem.technology,
            program: element_programm,
            setup: element_setup,
            notation: element_notation,
            tools: dataTool,
            plates: dataPlate,
            machines: dataMachine
        };

        //отправка данных на сервер
        var url = $("#formElement").attr("action");

//отправка текстовой информации

        jQuery.ajax({
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
                    console.log("Данные загрузились");
                    setTimeout(window.location.href = "elements", 1000);
                } else {
                    console.log("Error: " + res.message);
                    $("#err").css("display", "block");
                    $("#erMessage").html("Error: " + res.message);
                }
            }
        });

    });

});