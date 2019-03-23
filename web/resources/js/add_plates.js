$(document).ready(function () {
    $("#main").width($(window).width()-400);


    $("#formPlate").submit(function(){
        return false;
    });



    $("#btnSave").click(function () {

        var dataName = $('input[name="name"]').val();
        var dataModel = $('input[name="model"]').val();
        var dataType = $('input[name="type"]').val();
        var dataPhoto = $('input[name="photo"]').val();

        var empty = (dataName===''||dataModel===''||dataType===''||dataPhoto==='');

        var data = {
            name: dataName,
            model: dataModel,
            type: dataType,
            photo: dataPhoto
        };

        if(!empty) {
            $.ajax({
                url: $("#formPlate").attr("action"),
                type: "POST",
                data: JSON.stringify(data),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function (json) {
                    if (json.status === "fail") {
                        alert(json.message);
                    }
                    if (json.status === "success") {
                        alert(json.message);
                    }

                }
            });

            setTimeout(function () {
                location.href = '/plates';
            }, 1000);

        } else {
            alert("Заполните все поля");
        }
    });

});