<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Tag</h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <a href="?action=create" class="waves-effect waves-light btn-large">
            <i class="material-icons right">add</i>OPRET
        </a>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <table class="highlight">
            <thead>
            <th>ID</th>
            <th>Navn</th>
            <th>Beskrivelse</th>
            <th>Aktiv</th>
            <th>Tagtype</th>
            </thead>
            <tbody>
            <c:forEach items="${roofings}" var="roofing">
                <tr onclick="location.href = '?action=update&id=${roofing.getId()}'">
                    <td>${roofing.getId()}</td>
                    <td><c:out value="${roofing.getName()}"/></td>
                    <td><c:out value="${roofing.getDescription()}"/></td>
                    <td><c:out value="${f:formatBoolean(roofing.isActive())}"/></td>
                    <td><c:out value="${roofing.getType()}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="bot.jspf" %>