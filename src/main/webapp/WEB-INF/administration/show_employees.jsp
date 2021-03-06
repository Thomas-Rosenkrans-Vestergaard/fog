<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12 administration-header">
        <h2>Medarbejdere</h2>
        <a class="btn-floating btn-large waves-effect waves-light" href="?action=create">
            <i class="material-icons">add</i>
        </a>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla non lectus sed nisl molestie malesuada.
            Mauris dolor felis, sagittis at, luctus sed, aliquam non, tellus. Vivamus luctus egestas leo. Sed elit dui,
            pellentesque a, faucibus vel, interdum nec, diam. Itaque earum rerum hic tenetur a sapiente delectus, ut aut
            reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.</p>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <table class="bordered highlight administration-table">
            <thead>
            <tr>
                <th>Navn</th>
                <th>Brugernavn</th>
                <th>Aktiv</th>
                <th>Oprettet</th>
                <th>Roller</th>
            </tr>
            <%@ include file="../includes/table_filters.jspf" %>
            </thead>
            <tbody>
            <c:forEach items="${employees}" var="employee">
                <tr data-link="?action=update&id=${employee.getId()}">
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
            <%@ include file="../includes/table_pagination.jspf" %>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>