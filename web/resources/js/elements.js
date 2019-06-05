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

    function Machine(machId, name, model, idNumber, photo){
        this.machId = machId;
        this.machName = name;
        this.machModel = model;
        this.machIdNumber = idNumber;
        this.machPhoto = photo;
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


    getJsonElements();
//----------------------------------------------------------------------------------------------------------------------
// блок получения списка инструмента

    function getJsonElements() {
        var my_dateElements = [];

        $.get("/getTxtDataElements", function (data2, status) {
            console.log("Status: " + status);
            $.each(data2, function (key, val) {   //берем строку json
                for (var key1 in val) {
                    my_dateElements.push(val[key1]);  //добавляем в массив значение каждого элемента по ключу
                }
                    console.log(my_dateElements[9].length);
                    addRow(new Element(my_dateElements[0], my_dateElements[1], my_dateElements[2], my_dateElements[3],
                        my_dateElements[4], my_dateElements[5], my_dateElements[6], my_dateElements[7], my_dateElements[8],
                        my_dateElements[9]));
                my_dateElements.length = 0;

            });
        });
    }


    //функция построения таблицы по одному объекту
    function addRow(lastElement){
        if(lastElement!==0) {
            asslist.push({id: lastElement.elementId, model: lastElement.elementIdNumb});
            var insert = "<tr class='del' style='font-size: 1em'>" +
                "<td>" + lastElement.elementName + "</td>" +
                "<td class='model ssylka'>"+ lastElement.elementIdNumb+"</td>" +
                "<td>" + lastElement.elementMachines[0].name + "</td>" +
                "<td class='ssylka' style='cursor: pointer'><span>фото</span></td>" +
                "</tr>";
            $('.listTools> tbody').append(insert);
        }
    }
//-----------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------


//---------------------------------------------------------------------------------------------------------------------
// блок добавления инструмента


    //кнопка добавить
    $(".button2").click(function () {
        window.location.href="addElementPage";
    });


    //при переходе по ссылке получаем именя файла и отправляем запрос к серверу на подгрузку файла
    $('.listTools').on('click', 'tbody tr td:nth-child(4)', function(e) {
        var text_name = $(this).siblings('td:nth-child(1)').text();
        var text_ident = $(this).siblings('td:nth-child(2)').text();
        var id;
        for(var n = 0; n < asslist.length; n++){
            if(asslist[n].model === text_ident){
                id = asslist[n].id;
            }
        }
        var newFileName = urlLit((("photo-"+text_name+"-"+text_ident + "-"+"0"+"_"+id).trim()).split('.').join("-"),0);
        var down1 = "downloadElementFilesPhoto/".concat(newFileName);
        console.log("addres for download = "+down1);
        $("#prim").attr('src', down1);
        $("#prim").css('display','block');
    });

//блок перехода по ссылке на страницу полной информации об объекте
    $('.listTools').on('click', 'tbody tr td:nth-child(2)', function(e) {
        console.log("листнер работает");
        var text = $(this).html();
        console.log("text = " + text);
        var id;
        for(var n = 0; n < asslist.length; n++){
            if(asslist[n].model === text){
                id = asslist[n].id;
            }
        }
        console.log("id = " + id);
        window.location.href="/getelement/".concat(id);
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