<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/functions.tld" prefix="f" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="../css/materialize.min.css"/>
    <link rel="stylesheet" href="../css/administration.css"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="../js/materialize.min.js"></script>
    <script src="../js/administration.js"></script>
    <%@ include file="../includes/notifications.jspf" %>
    <title>${title}</title>
</head>
<body>
<form method="post">
    <input type="text" name="username" placeholder="brugernavn">
    <input type="password" name="password" placeholder="adgangskode">
    <input type="submit" value="log ind">
</form>
</body>
</html>