<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Tilbud</h2>
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
                <th>Kunde</th>
                <th>Ordre</th>
                <th>Status</th>
                <th>Oprettet</th>
                <th>Medarbejder</th>
                <th>Tilbud pris</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${offers}" var="offer">
                <c:set var="order" value="${offer.getOrder()}"/>
                <c:set var="employee" value="${offer.getEmployee()}"/>
                <tr data-link="?action=update&id=${offer.getId()}">
                    <td>
                        <a href="customers?action=update&id=${order.getCustomer().getId()}">
                            <c:out value="${order.getCustomer().getName()}"/>
                        </a>
                    </td>
                    <td>
                        <a href="orders?action=update&id=${order.getId()}">${order.getId()}</a>
                    </td>
                    <td><c:out value="${offer.getStatus().name()}"/></td>
                    <td>${f:formatDatetime(order.getCreatedAt())}</td>
                    <td>
                        <a href="employees?action=update&id=${employee.getId()}">
                            <c:out value="${employee.getName()}"/>
                        </a>
                    </td>
                    <td>${f:formatPrice(offer.getPrice())}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>