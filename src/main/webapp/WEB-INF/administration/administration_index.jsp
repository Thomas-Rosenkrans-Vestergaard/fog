<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Administration</h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Logget ind som <span class="focus"><c:out value="${employee.getName()}"/></span></p>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <table class="highlight administration-table">
            <tbody>
            <tr>
                <th>Navn</th>
                <td><c:out value="${employee.getName()}"/></td>
            </tr>
            <tr>
                <th>Brugernavn</th>
                <td><c:out value="${employee.getUsername()}"/></td>
            </tr>
            <tr>
                <th>Oprettet</th>
                <td><c:out value="${f:formatDatetime(employee.getCreatedAt())}"/></td>
            </tr>
            <tr>
                <th>Roller</th>
                <td>
                    <ul>
                        <c:forEach items="${employee.getRoles()}" var="role">
                            <li><c:out value="${role}"/></li>
                        </c:forEach>
                    </ul>
                </td>
            </tr>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>