$(document).ready(function () {
    $(".main").width($(window).width() - 400);

    var arrPlates = [];
    var arrTools = [];
    var arrMachines = [];
    var arrElements = [];

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
        this.elementId = elId;
        this.elementName = nameElement;
        this.elementIdNumb = idNumb;
        this.elementTechnology = technology;
        this.elementProgramm = program;
        this.elementSetup = setup;
        this.elementNotation = notation;
        this.elementTools = tools;
        this.elementPlates = plates;
        this.elementMachines = machines;
    }


    getJsonAddToArrayTools();
    getJsonAddToArrayPlates();
    getJsonAddToArrayMachines();
    getJsonAddToArrayElement();
//----------------------------------------------------------------------------------------------------------------------

    // функция получает json, создает массив принимаемых объектов
    function getJsonAddToArrayElement() {
        var my_date = [];
        $.get("/getTxtDataElements", function (data1, status) {
            console.log("Status get elements: " + status);
            $.each(data1, function (key, val) {
                for (var key1 in val) {
                    my_date.push(val[key1]);
                }
                arrElements.push(new Element(my_date[0], my_date[1], my_date[2]));
                my_date.length = 0;
            });
        });
    }

// функция получает json, создает массив принимаемых объектов
//и передает массив в функцию построения таблицы для отображения выпадающего списка при добавлении инструмента
    function getJsonAddToArrayPlates() {
        var my_date = [];
        $.get("/getTxtDataPlate", function (data1, status) {
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
                $("#setPlates").append("<option  value='"+ arrPlates[i].plateId+"'>" + arrPlates[i].name +
                    " "+ arrPlates[i].model+"   "+ arrPlates[i].type+"</option>");
            }
        });
    }

    //getJsonAddToArray();
// функция получает json, создает массив принимаемых объектов
//и передает массив в функцию построения таблицы для отображения выпадающего списка при добавлении инструмента
    function getJsonAddToArrayTools() {
        var my_date = [];
        $.get("/getTxtDataTools", function (data1, status) {
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
                var el_of_list = "<option  value='" + arrTools[i].toolId + "'>" + arrTools[i].name +
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
        $.get("/getTxtDataMachines", function (data1, status) {
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
                $("#setMachines").append("<option  value='"+ arrMachines[i].machId+"'>" + arrMachines[i].name +
                    " "+ arrMachines[i].model+"   "+ arrMachines[i].idNumber+"</option>");
            }
        });
    }

//---------------------------------------------------------------------------------------------------------------------
// блок добавления инструмента

    //кнопка отменить
    $(".button4").click(function () {
        window.location.href="elements";
    });


    //кнопка принять
    $(".button5").click(function () {

        var dataPlatesId = [];
        var dataToolsId = [];
        var dataMachineId = [];

        var element_name = $('input[name="name"]').val();
        var element_idNumb = $('input[name="idNumb"]').val();
        var element_setup = $("#setup").val();
        var element_notation = $("#notation").val();
        $('#setPlates').each(function () {
            dataPlatesId = $(this).val();
        });
        $('#setTools').each(function () {
            dataToolsId = $(this).val();
        });
        $('#setMachines').each(function () {
            dataMachineId = $(this).val();
        });


         var dataPlate = createArrsForSendToServer(dataPlatesId, arrPlates, 'plateId');
         var dataTool = createArrsForSendToServer(dataToolsId, arrTools, 'toolId');
         var dataMachine = createArrsForSendToServer(dataMachineId, arrMachines, 'machId');

        function createArrsForSendToServer(arrId, arrObj, id) {
            var resArr = [];
            for(var i = 0; i < arrId.length; i++){
                for(var j = 0; j < arrObj.length; j++){
                    if(arrObj[j][id] == arrId[i]){
                        resArr.push(arrObj[j]);
                    }
                }
            }
            return resArr;
        }


        //проверка на совпадение имен
        for(var i = 0; i < arrElements.length; i++){
            if(element_idNumb === arrElements[i].elementIdNumb || element_name === arrElements[i].elementName){
                alert("Такая деталь уже существует. Измените идентификатор или редактируйте существующий. Вы можете выбрать" +
                    "его из списка");
                    $(".input").val('');
                element_idNumb = '';
                element_name = '';
                return false;
            }
        }

        var empty = (element_name === '' || element_idNumb === '' || dataToolsId.length === 0 ||
            dataPlatesId.length === 0 || dataMachineId.length === 0);

        var dataForm = {
            elId: 0,
            nameElement: element_name,
            idNumb: element_idNumb,
            technology: "file",
            program: "file",
            setup: element_setup,
            notation: element_notation,
            tools: dataTool,
            plates: dataPlate,
            machines: dataMachine
        };


        //отправка данных на сервер
        var url = $("#formElement").attr("action");

//отправка текстовой информации
         if (!empty) {
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
                        // dataModel = urlLit(dataModel,0);
                        var file_name = urlLit(element_name + " " + element_idNumb, 0);
                        console.log(file_name);
                        var flagPhoto = "#photo";
                        var flagTechnology = "#tech";
                        var flagProgramm = "#prog";
                        uplaod(file_name, res.id, flagPhoto);
                        uplaod(file_name, res.id, flagTechnology);
                        uplaod(file_name, res.id, flagProgramm);
                        console.log("Данные загрузились");
                        window.location.href = "elements";
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
        uplaod = function(name_file, id, flag){
            var data = new FormData();
            jQuery.each(jQuery(flag)[0].files, function(i, file) {
                var fileExtension = '.' + file.name.split('.').pop();
                var suffix_file = flag.substring(1);
                console.log(suffix_file);
                var fileName = suffix_file+"-"+name_file+"-"+i+"-"+id;
                data.append('file-'+i, file, fileName.concat(fileExtension));
                console.log(fileName.concat(fileExtension));
            });

            $.ajax({
                url:'/uploadFilesElements',
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