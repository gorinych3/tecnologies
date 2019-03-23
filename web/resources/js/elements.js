$(document).ready(function () {
    $("#main").width($(window).width()-400);

    $("#btnHide").click(function () {
        $("#div_hide").css("display", "none");
    });
    $("#btnShow").click(function () {
        $("#div_hide").css("display", "table-cell");
    });

    $("#span1").mouseenter(function () {
        $("#div_hide").css("visibility", "visible");
        $("#prim2").css("display", "none");
        $("#prim").css("display", "inline");
        $("#prim").attr("src", "resources/images/IMG_20180312_181928.jpg");
    });
    $("#span2").mouseenter(function () {
        $("#div_hide").css("visibility", "visible");
        $("#prim").css("display", "none");
        $("#prim2").css("display", "inline");
        $("#prim2").html("Полюс 018");
    });
    $(".u2").each(function () {
        $(this).mouseleave(function () {
            $("#div_hide").css("visibility", "hidden");
            $("#prim").css("display", "none");
            $("#prim2").css("display", "none");
        });
    });

    //-----------------------------------------------------------------------------------------------------------------
    // переход по имени детали в element.jsp
    $(".my_href").each(function (i) {
        $(this).click(function () {
            let $data = $(this).html();
            console.log($data);
            $.ajax
            ({
                url: '/getElement',
                type: 'POST',
                data: $data,
                success: function () {
                    console.log("Данные отправлены");
                    console.log($data);
                    //window.location.href = "/getElement";
                },
                'error' : function(request, status, error){
                    console.log('error:' + error);
                }
            });

            // window.location.href = "/getElement";
        });
    });

});