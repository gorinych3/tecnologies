<%@ page import="java.util.List" %>
<%@ page import="ru.egor.entity.MyTool" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Егор
  Date: 04.02.2019
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TEST</title>
    <link rel="stylesheet" href="resources/css/w3.css">
    <%--<script type="text/javascript" src="resources/js/jquery.js"></script>--%>
    <%--<script type="text/javascript">--%>
    <%--// $('#myForm').on('submit', function(e) {--%>
    <%--//     e.preventDefault();--%>
    <%--//     var frm = $("#myForm");--%>
    <%--//     var dat = frm.serialize();--%>
    <%--//     $.ajax({--%>
    <%--//         type: 'POST',--%>
    <%--//         url: $('#myForm').attr('action'),--%>
    <%--//         data: dat,--%>
    <%--//         contentType: 'application/json'--%>
    <%--//         success: function(hxr) {--%>
    <%--//             alert("Success: " + hxr);--%>
    <%--//         }--%>
    <%--//     });--%>
    <%--// });--%>
    <%--$.ajax({--%>
    <%--method: "POST",--%>
    <%--url: "/test",--%>
    <%--data: {param1: "nameElement",--%>
    <%--param2: "photo",--%>
    <%--param3: "technology",--%>
    <%--param4: "program",--%>
    <%--param5: "setup",--%>
    <%--param6: "notation"--%>
    <%--}--%>
    <%--})--%>
    <%--</script>--%>
    <script type="text/javascript" src="resources/js/jquery.js"></script>
    <script type="text/javascript">
        function createList() {
            $.ajax({
                method: "POST",
                url: "/addElement",
                data: {param1: "instr"
                }
            })
        }
    </script>
</head>
<%--<body>--%>
<%--<h2>Test</h2>--%>
<%--<form id="myForm" action="${pageContext.request.contextPath}/test" method="POST" enctype="multipart/form-data">--%>
<%--<input type="text" name="name" value="myName">--%>
<%--<input type="text" name="profession" value="unknown">--%>
<%--<input type="submit" value="Submit">--%>
<%--</form>​--%>


<%--</div>--%>
<%--</div>--%>

<%--<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">--%>
<%--<button class="w3-btn w3-round-large" onclick="location.href='../..'">Back to main</button>--%>
<%--<button class="w3-btn w3-round-large" onclick="location.href='/add'">Back to add</button>--%>
<%--</div>--%>
<%--</body>--%>
<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>ТЕХНОЛОГИЯ</h1>
</div>

<div class="w3-container w3-padding">
    <div class="w3-card-4">
        <div class="w3-container w3-center w3-green">
            <h2>Новая деталь</h2>
        </div>
        <form id="one" method="post" action="${pageContext.request.contextPath}/addElement" class="w3-selection w3-light-grey w3-padding">

            <label>Наименование:
                <input type="text" name="nameElement" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br />
            </label>
            <br>
            <label>Эскиз:
                <input type="text" name="photo" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br />
            </label>
            <br>
            <label>Технология:
                <input type="text" name="technology" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br />
            </label>
            <br>
            <label>Программа:
                <input type="text" name="program" value="unknown" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br />
            </label>

            <label>Описание наладки:
                <input type="text" name="setup" value="unknown" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br />
            </label>

            <label>Проблемы в изготовлении и рекомендации к их устранению:
                <input type="text" name="notation" value="unknown" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br />
            </label>




            <%--<div>--%>
                <%--<label>Добавление инструмента:--%>
                    <%--<input type="text" name="instr" value="unknown" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br />--%>
                <%--</label>--%>
                <%--<input type="button" value="Добавить инструмент" name="instr" onclick="return createList();">--%>
            <%--</div>--%>

            <div>
                <label>Добавление инструмента:
                    <input type="text" name="instr" value="unknown" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br />

                </label>
                <input type="button" value="Добавить инструмент" name="instr" onclick="return createList();">
            </div>


            <button type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom">Принять</button>

        </form>



    </div>
</div>

<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='../..'">Back to main</button>
    <button class="w3-btn w3-round-large" onclick="location.href='/add'">Back to add</button>
</div>
</body>
</html>
