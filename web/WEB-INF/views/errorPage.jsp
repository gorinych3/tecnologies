<%--
  Created by IntelliJ IDEA.
  User: Егор
  Date: 26.12.2018
  Time: 1:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ErrorPage</title>
    <link rel="stylesheet" href="resources/css/w3.css">
</head>

<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>ТЕХНОЛОГИЯ</h1>
</div>

<div class="w3-container w3-center w3-margin-bottom w3-padding">
    <div class="w3-card-4">
        <div class="w3-container w3-light-blue">
            <h2>ERROR</h2>
        </div>

        <div class="w3-panel w3-red w3-display-container w3-card-4 w3-round">
            <h5>Result - operation "${operationName}"  do not complete<br>
                Input current "${errorMessage}"</h5>
        </div>
    </div>
</div>

<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick='history.back()'>Back</button>
    <button class="w3-btn w3-round-large" onclick="location.href='../..'">Back to main</button>
    <%--<button class="w3-btn w3-round-large" onclick="location.href='/list'">Back to search</button>--%>
    <button class="w3-btn w3-round-large" onclick="location.href='/addElement'">Back to add</button>
    <%--<button class="w3-btn w3-round-large" onclick="location.href='/update'">Back to update</button>--%>
    <%--<button class="w3-btn w3-round-large" onclick="location.href='/delete'">Back to delete</button>--%>
</div>
</body>
</html>
