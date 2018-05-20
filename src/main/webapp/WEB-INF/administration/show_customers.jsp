<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Kunder</h2>
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
        <table class="bordered highlight administration-table">
            <thead>
            <tr>
                <th>Navn</th>
                <th>Adresse</th>
                <th>Email</th>
                <th>Telefon</th>
                <th class="short">Aktiv</th>
            </tr>
            <th>Oprettet</th>
            </thead>
            <tbody>
            <c:forEach items="${customers}" var="customer">
                <tr data-link="?action=update&id=${customer.getId()}">
                    <td><c:out value="${customer.getName()}"/></td>
                    <td><c:out value="${customer.getAddress()}"/></td>
                    <td><c:out value="${customer.getEmail()}"/></td>
                    <td><c:out value="${customer.getPhone()}"/></td>
                    <td><c:out value="${f:formatBoolean(customer.isActive())}"/></td>
                    <td><c:out value="${f:formatDatetime(customer.getCreatedAt())}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>