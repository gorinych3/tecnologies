$(document).ready(function () {
    $("#main").width($(window).width()-400);

    // $('.mytranslit').bind('change keyup input click', function(){
    //     $('.mytranslitto').val(urlLit($('.mytranslit').val(),0))
    // });



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

                var newFileName = (lastElement.model + "-"+"0"+"_"+lastElement.plateId).trim();
               // var newFileName2 = newFileName.split(' ').join('-');
                var newFileName2 = newFileName.split('.').join("-");
                newFileName2 = urlLit(newFileName2,0);
                //console.log("добавление в таблицу спан "+newFileName2);
                var location = '/plate/'+lastElement.model;

            var insert = "<tr class='del' style='font-size: 1em'>" +
                "<td>" + lastElement.name + "</td>" +
                "<td style='cursor: pointer; text-decoration: underline'><span style='display: none'>" + newFileName2 + "</span>"+ lastElement.model+"</td>" +
                "<td>" + lastElement.type + "</td>" +
                "<td style='cursor: pointer; text-decoration: underline'>фото</td>" +
                "</tr>";
            //$(insert).insertAfter($("tr:first"));
            $('#list> tbody').append(insert);
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
        $("#list").css("border-bottom", "none");
    });

    $(".button3").click(function () {
        $("#add_hide").css("display", "none");
        $(".button3").css("display", "none");
        $(".button2").css("display", "block");
        $("#list").css("border-bottom", "thin solid black");

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

        var url = $("#formPlate").attr("action");

        if(!empty) {
            $.ajax({
                url: url,
                type: "POST",
                data: JSON.stringify(dataForm),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function (res) {
                    console.log("My message "+ res.message+" my id "+res.id);
                    if(res.message === "success"){
                        var lastEl = new Plate(res.id, dataName, dataModel, dataType, dataPhoto);
                        //addRow(lastEl);
                        dataModel = urlLit(dataModel,0);
                        uplaod(dataModel, res.id);
                        addRow(lastEl);
                        console.log(lastEl.plateId + " "+ lastEl.name + " "+ lastEl.model);
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

    uplaod = function(model, id){
        var data = new FormData();
        jQuery.each(jQuery('#files')[0].files, function(i, file) {
            //console.log("file name "+file.name);
            var fileExtension = '.' + file.name.split('.').pop();
            var fileName = model+"-"+i+"-"+id;
            data.append('file-'+i, file, fileName.concat(fileExtension));
        });

        $.ajax({
            url:'/uploadImages',
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

    $('#list').on('click', 'tbody tr td:last-child', function(e) {
        var text = $(this).siblings('td').find('span').text();
        console.log("Передаем в контроллер имя файла  "+text);
        var down1 = "download1/".concat(text);
        console.log("Передаем в контроллер address  "+down1);
         $("#prim").attr('src', down1);
         $("#prim").css('display','block');
    });

    $('#list').on('click', 'tbody tr td:nth-child(2)', function(e) {
        var text = $(this).html();
        console.log("text second child  "+text);
        var number_first = text.lastIndexOf('-');
        var number_last = text.lastIndexOf('<');
        var id = text.substring(number_first+1, number_last);
        console.log(id);
        window.location.href="/getplate/".concat(id);
        //window.location.href="/plate";
    });





    function urlLit(w,v) {
        var tr='a b v g d e ["zh","j"] z i y k l m n o p r s t u f h c ch sh ["shh","shch"] ~ y ~ e yu ya ~ ["jo","e"]'.split(' ');
        var ww=''; w=w.toLowerCase();
        for(i=0; i<w.length; ++i) {
            cc=w.charCodeAt(i); ch=(cc>=1072?tr[cc-1072]:w[i]);
            if(ch.length<3) ww+=ch; else ww+=eval(ch)[v];}
        return(ww.replace(/[^a-zA-Z0-9\-]/g,'-').replace(/[-]{2,}/gim, '-').replace( /^\-+/g, '').replace( /\-+$/g, ''));
    }

});