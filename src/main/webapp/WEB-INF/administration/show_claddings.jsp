<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Beklædninger</h2>
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
        <table class="highlight administration-table">
            <thead>
            <th>ID</th>
            <th>Navn</th>
            <th>Beskrivelse</th>
            <th>Aktiv</th>
            </thead>
            <tbody>
            <c:forEach items="${claddings}" var="claddingId">
                <tr onclick="location.href = '?action=update&id=${claddingId.getId()}'">
                    <td>${claddingId.getId()}</td>
                    <td><c:out value="${claddingId.getName()}"/></td>
                    <td><c:out value="${claddingId.getDescription()}"/></td>
                    <td><c:out value="${f:formatBoolean(claddingId.isActive())}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>