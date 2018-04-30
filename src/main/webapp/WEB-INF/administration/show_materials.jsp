<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Materialer</h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <a class="btn-floating btn-large waves-effect waves-light red" href="?action=create">
            <i class="material-icons">add</i>
        </a>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <table class="highlight">
            <thead>
            <th>Nummer</th>
            <th>Beskrivelse</th>
            <th>Noter</th>
            <th>Bredde</th>
            <th>Højde</th>
            <th>Brug</th>
            </thead>
            <tbody>
            <c:forEach items="${materials}" var="material">
                <tr onclick="location.href = '?action=update&id=${material.getId()}'">
                    <td><c:out value="${material.getNumber()}"/></td>
                    <td><c:out value="${material.getDescription()}"/></td>
                    <td><c:out value="${material.getNotes()}"/></td>
                    <td><c:out value="${material.getHeight()}"/></td>
                    <td><c:out value="${material.getWidth()}"/></td>
                    <td><c:out value="${material.getUsage()}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="bot.jspf" %>