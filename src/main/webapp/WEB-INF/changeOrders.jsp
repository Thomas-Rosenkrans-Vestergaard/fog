<%--
  Created by IntelliJ IDEA.
  User: mkl
  Date: 4/24/18
  Time: 11:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EmployeeOrderChange</title>
</head>
<body>
    gets in here with ID
    <div class="row">
        <form class="col s12">
            <div class="row">
                <div class="col s12">
                    <form id="formChangeOrder" action="ChangeOrderServlet" method="post">
                        <input type="hidden" value="changeOrder">
                        <input type="number" SQLname="height">changeHeight</label>


                    </form>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
