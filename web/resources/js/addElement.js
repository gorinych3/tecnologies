$(document).ready(function () {
    $(".main").width($(window).width() - 400);

    function Plate(plateId, name, model, type, photo) {
        this.plateId = plateId;
        this.plate_name = name;
        this.plate_model = model;
        this.plate_type = type;
        this.plate_photo = photo;
    }

    function MyTool(toolId, name, model, type, photo, plates) {
        this.toolId = toolId;
        this.tool_name = name;
        this.tool_model = model;
        this.tool_type = type;
        this.tool_photo = photo;
        this.tool_plates = plates;
    }

    function Machine(machId, name, model, idNumber, photo){
        this.machId = machId;
        this.mach_name = name;
        this.mach_model = model;
        this.mach_idNumber = idNumber;
        this.mach_photo = photo;
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
//----------------------------------------------------------------------------------------------------------------------

// функция получает json, создает массив принимаемых объектов
//и передает массив в функцию построения таблицы для отображения выпадающего списка при добавлении инструмента
    function getJsonAddToArrayPlates() {
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
                $("#setPlates").append("<option  value='"+ arrPlates[i].plateId+"'>" + arrPlates[i].plate_name +
                    " "+ arrPlates[i].plate_model+"   "+ arrPlates[i].plate_type+"</option>");
            }
        });
    }

    //getJsonAddToArray();
// функция получает json, создает массив принимаемых объектов
//и передает массив в функцию построения таблицы для отображения выпадающего списка при добавлении инструмента
    function getJsonAddToArrayTools() {
        var arrObj = [];
        var my_date = [];
        var arrTools = [];
        $.get("/getTxtDataTools", function (data1, status) {
            console.log("Status: " + status);
            $.each(data1, function (key, val) {
                for (var key1 in val) {
                    my_date.push(val[key1]);
                }
                arrObj.push(my_date[1]);
                arrTools.push(new MyTool(my_date[0], my_date[1], my_date[2], my_date[3], my_date[4]));
                my_date.length = 0;
            });

            //формируем выпадающий список и ассоциативный массив ключ/значение для дальнейшей отображения инфы
            //после отправки данных на сервер
            $("#setTools").append("<optgroup id='sverlo' label='Сверла'>");
            $("#setTools").append("<optgroup id='freza' label='Фрезы'>");
            $("#setTools").append("<optgroup id='razvertka' label='Развертки'>");
            $("#setTools").append("<optgroup id='centrovka' label='Центровки'>");
            $("#setTools").append("<optgroup id='rezec' label='Резцы'>");


            for (var i = 0; i < arrTools.length; i++) {
                var el_of_list = "<option  value='" + arrTools[i].toolId + "'>" + arrTools[i].tool_name +
                    " " + arrTools[i].tool_model +"   "+arrTools[i].tool_type+"</option>";
                if(arrTools[i].tool_name === "Центровка") {
                    $("#centrovka").append(el_of_list);
                } else
                if(arrTools[i].tool_name === "Фреза") {
                    $("#freza").append(el_of_list);
                } else
                if(arrTools[i].tool_name === "Сверло") {
                    $("#sverlo").append(el_of_list);
                } else
                if(arrTools[i].tool_name === "Развертка") {
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
        var arrObj = [];
        var my_date = [];
        var arrMachines = [];
        $.get("/getTxtDataMachines", function (data1, status) {
            console.log("Status: " + status);
            $.each(data1, function (key, val) {
                for (var key1 in val) {
                    my_date.push(val[key1]);
                }
                arrObj.push(my_date[1]);
                arrMachines.push(new Machine(my_date[0], my_date[1], my_date[2], my_date[3], my_date[4]));
                my_date.length = 0;
            });

            //формируем выпадающий список и ассоциативный массив ключ/значение для дальнейшей отображения инфы
            //после отправки данных на сервер
            for (var i = 0; i < arrMachines.length; i++) {
                $("#setMachines").append("<option  value='"+ arrMachines[i].machId+"'>" + arrMachines[i].mach_name +
                    " "+ arrMachines[i].mach_model+"   "+ arrMachines[i].mach_idNumber+"</option>");
            }
        });
    }



//---------------------------------------------------------------------------------------------------------------------
// блок добавления инструмента


    $(".button4").click(function () {
        window.location.href="elements";
    });


//     //кнопка принять
//     $(".button3").click(function () {
//         $("#add_hide").css("display", "none");
//         $("#formTool").css("display", "none");
//         $(".button2").css("display", "block");
//         $("#tableContainer").css("position", "relative");
//
//         var dataPlatesId  = new Array();
//         var dataPlateName;
//         var dataName = $('input[name="name"]').val();
//         var dataModel = ($('input[name="model"]').val()).split('№').join('No.');
//         var dataType = $('#type').val();
//         $('#addOptions').each(function () {
//             dataPlatesId = $(this).val();
//         });
//         for (var k = 0; k < arr_plates_key_value.length; k++){
//             if(arr_plates_key_value[k].id == dataPlatesId[0]){
//                 dataPlateName = arr_plates_key_value[k].name;
//             }
//         }
//
//         var dataPhoto = $('input[name="photo"]').val();
//
//
//
//
//         $('.model').each(function () {
//             var new_text = $(this).html();
//             new_text = new_text.substring(new_text.lastIndexOf('>') + 1);
//             if (new_text === dataModel) {
//                 alert("Такой инструмент уже существует. Измените модель или редактируйте существующий. Вы можете выбрать" +
//                     "его из списка");
//                 $('.button2').trigger('click', function () {
//                     $(this).closest('form')[0].reset();
//                     input.val('');
//                 });
//                 dataModel = '';
//                 return false;
//             }
//         });
//
//         var empty = (dataName === '' || dataModel === '' || dataType === '' || dataPhoto === '' || dataPlatesId === '');
//
//
//         var dataForm = {
//             name: dataName,
//             model: dataModel,
//             type: dataType,
//             photo: dataPhoto,
//             platesId: dataPlatesId
//         };
//
//
//         //отправка данных на сервер
//         var url = $("#formTool").attr("action");
//
// //отправка текстовой информации
//         if (!empty) {
//             $.ajax({
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
//                         var lastEl = new MyTool(res.id, dataName, dataModel, dataType, dataPhoto, null);
//                         dataModel = urlLit(dataModel,0);
//                         uplaod(dataModel, res.id);
//                         addRow(lastEl, dataPlateName);
//                         console.log("Данные загрузились");
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
//
//         $("#err").click(function () {
//             $(this).css("display", "none");
//         });
//
// //функция отправки файлов серверу
//         uplaod = function(model, id){
//             var data = new FormData();
//             jQuery.each(jQuery('#files')[0].files, function(i, file) {
//                 var fileExtension = '.' + file.name.split('.').pop();
//                 var fileName = model+"-"+i+"-"+id;
//                 data.append('file-'+i, file, fileName.concat(fileExtension));
//             });
//
//             $.ajax({
//                 url:'/uploadFilesTools',
//                 data: data,
//                 cache: false,
//                 contentType: false,
//                 processData: false,
//                 type:'POST',
//                 success: function(response){
//                     if(response.Status === 200){
//                         console.log(response.SucessfulList);
//                     }else{
//                         console.log('Error');
//                     }
//                 }
//             });
//         };
//
//     });
//
//     //при переходе по ссылке получаем именя файла и отправляем запрос к серверу на подгрузку файла
//     $('.listTools').on('click', 'tbody tr td:nth-child(5)', function(e) {
//         var text = $(this).siblings('td:nth-child(2)').text();
//         var id;
//         for(var n = 0; n < asslist.length; n++){
//             if(asslist[n].model === text){
//                 id = asslist[n].id;
//             }
//         }
//         var newFileName = urlLit(((text + "-"+"0"+"_"+id).trim()).split('.').join("-"),0);
//         var down1 = "downloadToolsFiles/".concat(newFileName);
//         $("#prim").attr('src', down1);
//         $("#prim").css('display','block');
//     });
//
// //блок перехода по ссылке на страницу полной информации об объекте
//     $('.listTools').on('click', 'tbody tr td:nth-child(2)', function(e) {
//         var text = $(this).html();
//         var id;
//         for(var n = 0; n < asslist.length; n++){
//             if(asslist[n].model === text){
//                 id = asslist[n].id;
//             }
//         }
//         window.location.href="/gettool/".concat(id);
//     });
// //-----------------------------------------------------------------
//
//     //функция транслитерации для формирования имени файла и пути к нему
//     function urlLit(w, v) {
//         var tr = 'a b v g d e ["zh","j"] z i y k l m n o p r s t u f h c ch sh ["shh","shch"] ~ y ~ e yu ya ~ ["jo","e"]'.split(' ');
//         var ww = '';
//         w = w.toLowerCase();
//         for (var i = 0; i < w.length; ++i) {
//             var cc = w.charCodeAt(i);
//             var ch = (cc >= 1072 ? tr[cc - 1072] : w[i]);
//             if (ch.length < 3) ww += ch; else ww += eval(ch)[v];
//         }
//         return (ww.replace(/[^a-zA-Z0-9\-]/g, '-').replace(/[-]{2,}/gim, '-').replace(/^\-+/g, '').replace(/\-+$/g, ''));
//     }

});