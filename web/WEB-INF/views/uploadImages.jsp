<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>BORAJI.COM</title>
</head>
<body>
<h2>Spring MVC - File Download Example</h2>
<hr>
<ul>
    <%--<li><a id="d1" href="">Download File 1</a></li>--%>
    <li><img id="d2" src="">
</ul>
<script src="../../resources/js/jquery.js"></script>
<script>
    $(document).ready(function () {
        var down1 = "download1";
        // $("#d1").attr("href", down1);
        $("#d2").attr("src", down1);
    });
</script>
</body>
</html>
