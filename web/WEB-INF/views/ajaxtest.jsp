<%--
  Created by IntelliJ IDEA.
  User: Егор
  Date: 25.03.2019
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="../../resources/js/jquery.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $("#test").click(function(){
            $.get("/ajaxtest",function(data,status){
                alert("Data: " + data + "\nStatus: " + status);
            });
        });
    });
</script>
<body>
<button id="test">Load</button>
</body>
</html>
