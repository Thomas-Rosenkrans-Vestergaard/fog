<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Køb</h2>
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
        <table class="bordered highlight">
            <thead>
            <tr>
                <th>Tilbud</th>
                <th>Kunde</th>
                <th>Medarbejder</th>
                <th>Købspris</th>
                <th>Oprettet</th>
            </tr>
            <%@ include file="../includes/table_filters.jspf" %>
            </thead>
            <tbody>
            <c:forEach items="${purchases}" var="purchase">
                <tr data-link="?action=show&id=${purchase.getId()}">
                    <c:set var="offer" value="${purchase.getOffer()}"/>
                    <c:set var="order" value="${offer.getOrder()}"/>
                    <c:set var="employee" value="${offer.getEmployee()}"/>
                    <c:set var="customer" value="${order.getCustomer()}"/>
                    <td><a href="offers?action=update&id=${offer.getId()}">${offer.getId()}</a></td>
                    <td><a
                            href="customers?action=update&id=${customer.getId()}"><c:out
                            value="${customer.getName()}"/></a></td>
                    <td><a href="employees?action=update&id=${employee.getId()}"><c:out
                            value="${employee.getName()}"/></a></td>
                    <td>${f:formatPrice(offer.getPrice())}</td>
                    <td>${f:formatDatetime(offer.getCreatedAt())}</td>
                </tr>
            </c:forEach>
            <%@ include file="../includes/table_pagination.jspf" %>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>