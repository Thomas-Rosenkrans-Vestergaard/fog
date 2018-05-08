<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Medarbejdere</h2>
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
            <th>ID</th>
            <th>Navn</th>
            <th>Brugernavn</th>
            <th>Aktiv</th>
            <th>Oprettet</th>
            <th>Roller</th>
            </thead>
            <tbody>
            <c:forEach items="${employees}" var="employee">
                <tr onclick="location.href = '?action=update&id=${employee.getId()}'">
                    <td>${employee.getId()}</td>
                    <td><c:out value="${employee.getName()}"/></td>
                    <td><c:out value="${employee.getUsername()}"/></td>
                    <td><c:out value="${f:formatBoolean(employee.isActive())}"/></td>
                    <td><c:out value="${f:formatDatetime(employee.getCreatedAt())}"/></td>
                    <td>
                        <ul>
                            <c:forEach items="${employee.getRoles()}" var="role">
                                <li><c:out value="${role}"/></li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>