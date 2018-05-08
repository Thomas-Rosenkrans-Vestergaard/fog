<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Modeller</h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <table class="highlight">
            <thead>
            <th>Id</th>
            <th>Navn</th>
            </thead>
            <tbody>
            <c:forEach items="${models}" var="model">
                <tr onclick="location.href = '?action=update&id=${model.getId()}'">
                    <td><c:out value="${model.getId()}"/></td>
                    <td><c:out value="${model.getName()}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="bot.jspf" %>