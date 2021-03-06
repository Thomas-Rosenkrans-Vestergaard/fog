<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12 administration-header">
        <h2>Facadebeklædninger</h2>
        <a class="btn-floating btn-large waves-effect waves-light" href="?action=create">
            <i class="material-icons">add</i>
        </a>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla non lectus sed nisl molestie malesuada.
            Mauris dolor felis, sagittis at, luctus sed, aliquam non, tellus. Vivamus luctus egestas leo. Sed elit dui,
            pellentesque a, faucibus vel, interdum nec, diam.</p>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <table class="bordered highlight administration-table">
            <thead>
            <tr>
                <th>Navn</th>
                <th>Beskrivelse</th>
                <th>Aktiv</th>
            </tr>
            <%@ include file="../includes/table_filters.jspf" %>
            </thead>
            <tbody>
            <c:forEach items="${claddings}" var="cladding">
                <tr data-link="?action=update&id=${cladding.getId()}">
                    <td><c:out value="${cladding.getName()}"/></td>
                    <td><c:out value="${cladding.getDescription()}"/></td>
                    <td><c:out value="${f:formatBoolean(cladding.isActive())}"/></td>
                </tr>
            </c:forEach>
            <%@ include file="../includes/table_pagination.jspf" %>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>