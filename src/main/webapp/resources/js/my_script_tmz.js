$(document).ready(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });


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

});