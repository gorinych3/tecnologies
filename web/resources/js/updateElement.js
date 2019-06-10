$(document).ready(function () {
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
        var url_get_el = "/getTxtDataOneElement/"+id;

        $.getJSON(url_get_el, function(data, status) {
            console.log("Status get element: " + status);
            $.each(data, function (key, val) {
                my_date.push(val);
            });
                //elem = new Element(my_date[0], my_date[1], my_date[2], my_date[3], my_date[4], my_date[5], my_date[6], my_date[7],
                //    my_date[8], my_date[9]);
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
                $("#setMachines").append("<option id='"+arrMachines[i].machId+"' value='"+ arrMachines[i].machId+"'>" + arrMachines[i].name +
                    " "+ arrMachines[i].model+"   "+ arrMachines[i].idNumber+"</option>");
            }
        });
    }


        console.log(elem);
//---------------------------------------------------------------------------------------------------------------------
// блок добавления инструмента

    //кнопка отменить
    $(".button4").click(function () {
        //window.location.href="elements";
        window.history.back();
    });


    //кнопка принять
    $(".button5").click(function () {

        console.log("element = " + elem.nameElement);

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
        if($('#setPlates').val() == flag){
            dataPlate = elem.plates;
            console.log("plates without change = "+ elem.plates);
        } else {
            $('#setPlates').each(function () {
                    dataPlatesId = $(this).val();
            });
        }

        if($('#setTools').val() == flag){
            dataTool = elem.tools;
        } else {
            $('#setTools').each(function () {
                dataToolsId = $(this).val();
            });
        }

        if($('#setMachines').val() == flag){
            dataMachine = elem.machines;
        } else {
            $('#setMachines').each(function () {
                dataMachineId = $(this).val();
            });
        }




        // $('#setPlates').each(function () {
        //     if((this).val() == "Ничего не менять"){
        //         dataPlate = elem.plates;
        //     }else {
        //         dataPlatesId = $(this).val();
        //     }
        // });
        // $('#setTools').each(function () {
        //     if((this).val() == "Ничего не менять"){
        //         dataTool = elem.tools;
        //     } else {
        //         dataToolsId = $(this).val();
        //     }
        // });
        // $('#setMachines').each(function () {
        //     if((this).val() == "Ничего не менять") {
        //         dataMachine = elem.machines;
        //     } else {
        //         dataMachineId = $(this).val();
        //     }
        // });

        if(dataPlate === undefined) {
            dataPlate = createArrsForSendToServer(dataPlatesId, arrPlates, 'plateId');
        }
        if(dataTool === undefined) {
            dataTool = createArrsForSendToServer(dataToolsId, arrTools, 'toolId');
        }
        if(dataMachine === undefined) {
            dataMachine = createArrsForSendToServer(dataMachineId, arrMachines, 'machId');
        }

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


        // console.log(new Element(elem.elId, elem.nameElement, elem.idNumb, elem.technology, element_programm, element_setup, element_notation,
        //     dataTool, dataPlate, dataMachine));


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

        //проверяем, есть ли файлы на удаление и удаляем их

        change_files('.photo', '#change_photo', '#delete_photo');

        change_files('.tech', '#change_tech', '#delete_tech');

        function change_files(ident, change_f, delete_f) {
            console.log(ident);
            var file_name_radio;
            var checked_radio;
            $(ident).each(function (i) {
                var c_p = change_f + i;
                var d_p = delete_f + i;
                checked_radio = $(c_p).prop("checked");
                file_name_radio = $(c_p).attr('name');
                if ($(c_p).prop("checked")) {
                    //заменяем файл под тем же именем
                    console.log("замена файла" + "  " + file_name_radio);
                    //нужно вывести диалоговое окно с фоткой и инпутом файла
                }
                if ($(d_p).prop("checked")) {
                    //удаляем файл
                    console.log("удаление файла" + "  " + file_name_radio.substring(file_name_radio.lastIndexOf('/')+1));
                    delete_files(file_name_radio.substring(file_name_radio.lastIndexOf('/')+1));

                }
            });
        };


        function delete_files(name_file) {
            var fileExtension = '.jpg';
            alert(name_file.concat(fileExtension));
            var data = {file: name_file.concat(fileExtension)};


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
        };




        // $('.photo').each(function (i) {
        //     file_name_radio = $(this).attr('src');
        //    var checked_radio = $('input[name="file_name_radio"]').val();
        //    alert("checked_radio = " + checked_radio);
        // });

        //проверяем, были ли добавлены файлы, если да - то добавляем их в конец списка фоток
        //для этого нужно получить индекс файла из его имени и добавить файл с новым именем
        //в котором будет указан последний +1 индекс
        var new_file_name = 0;
        add_files('.photos', '#photoAdd');
        add_files('.techs', '#techAdd');
        function add_files(param1, param2) {
            var last_photo;
            $(param1).each(function () {
                last_photo = $(this).attr('src');
                if(last_photo === undefined){}
                else {
                    var id_photo = last_photo.substring(last_photo.lastIndexOf('-') + 1);
                    last_photo = last_photo.substring(0, last_photo.lastIndexOf('-'));
                    var photo_index = last_photo.substring(last_photo.lastIndexOf('-') + 1);
                    photo_index++;
                    new_file_name = last_photo.substring(last_photo.lastIndexOf('/') + 1, last_photo.lastIndexOf('-')) + "-" + photo_index + "-" + id_photo;
                }
            });
            if(last_photo !== undefined) {
                upload_new_files(new_file_name, param2);
                last_photo = undefined;
            }
            function upload_new_files(new_file_name, flag) {
                var data = new FormData();
                jQuery.each(jQuery(flag)[0].files, function (i, file) {
                    var fileExtension = '.' + file.name.split('.').pop();
                    alert("полное имя файла перед отправкой" + new_file_name.concat(fileExtension));
                    data.append('file-' + i, file, new_file_name.concat(fileExtension));


                    $.ajax({
                        url: '/uploadFilesElements',
                        data: data,
                        cache: false,
                        contentType: false,
                        processData: false,
                        type: 'POST',
                        async: true,
                        success: function (response) {
                            if (response.Status === 200) {
                                console.log(response.SucessfulList);
                            } else {
                                console.log('Error');
                            }
                        }
                    });

                });

            };
        }

        //отправка данных на сервер
        //var url = $("#formElement").attr("action");

//отправка текстовой информации
//         if (!empty) {
//             jQuery.ajax({
//                 url: url,
//                 type: "POST",
//                 data: JSON.stringify(dataForm),
//                 contentType: 'application/json; charset=utf-8',
//                 dataType: 'json',
//                 success: function (res) {
//                     console.log("My message " + res.message + " my id " + res.id);
//
// //если текст обработан и занесен в бд, сервер возвращает номер id для формирования уникального имени файла,
// //по которому будет сформирован путь к данному файлу в файловой системе сервера
// //в теории, можно было не передавать строкой id, а воспользоваться на сервере request.getSession().setAttribute();
// //происходит загрузка файлов
// //если загрузка прошла успешно, то последний элемент отображаем на странице
//                     if (res.message === "success") {
//                         // dataModel = urlLit(dataModel,0);
//                         var file_name = urlLit(element_name + " " + element_idNumb, 0);
//                         console.log("filename for download = "+file_name);
//                         var flagPhoto = "#photo";
//                         var flagTechnology = "#tech";
//
//                         //проверяем, были ли добавлены файлы, если да - то добавляем их в конец списка фоток
//                         var last_photo;
//                         $('.photo').each(function () {
//                             last_photo = $('.src').get();
//                             alert(last_photo);
//                         });
//
//                         //setTimeout(uplaod(file_name, res.id, flagTechnology),1000);
//                         //setTimeout(uplaod(file_name, res.id, flagPhoto),1000);
//
//                         console.log("Данные загрузились");
//                         setTimeout(window.location.href = "elements", 1000);
//                         //window.location.href = "elements";
//                     } else {
//                         console.log("Error: " + res.message);
//                         $("#err").css("display", "block");
//                         $("#erMessage").html("Error: " + res.message);
//                     }
//                 }
//             });
//         } else {
//             alert("Заполните все поля");
//         }

        //
        // $("#err").click(function () {
        //     $(this).css("display", "none");
        // });

//функция отправки файлов серверу
        uplaod = function(name_file, flag){
            var data = new FormData();
            jQuery.each(jQuery(flag)[0].files, function(i, file) {
                var fileExtension = '.' + file.name.split('.').pop();
                //var suffix_file = flag.substring(1);
                //console.log("suffix file = "+suffix_file);
                //var fileName = suffix_file+"-"+name_file+"-"+i+"-"+id;
                alert(name_file.concat(fileExtension));
                data.append('file-'+i, file, name_file.concat(fileExtension));
            });

            $.ajax({
                url:'/uploadFilesElements',
                data: data,
                cache: false,
                contentType: false,
                processData: false,
                type:'POST',
                async: true,
                success: function(response){
                    if(response.Status === 200){
                        console.log(response.SucessfulList);
                        return;
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