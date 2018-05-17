<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12 administration-header">
        <h2>Gulve</h2>
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
<%@ include file="../includes/table_controls.jspf" %>
<div class="row">
    <div class="col s12">
        <table class="highlight administration-table">
            <thead>
            <th class="short">ID</th>
            <th>Navn</th>
            <th class="long">Beskrivelse</th>
            <th class="short">Aktiv</th>
            </thead>
            <tbody>
            <c:forEach items="${floorings}" var="flooring">
                <tr>
                    <td><a href="?action=update&id=${flooring.getId()}">${flooring.getId()}</a></td>
                    <td><c:out value="${flooring.getName()}"/></td>
                    <td><c:out value="${flooring.getDescription()}"/></td>
                    <td><c:out value="${f:formatBoolean(flooring.isActive())}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>