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
        <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum
            tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas
            semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo. Quisque sit amet est et sapien
            ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, commodo vitae, ornare sit amet, wisi. Aenean
            fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. Donec
            non enim in turpis pulvinar facilisis. Ut felis. Praesent dapibus, neque id cursus faucibus, tortor neque
            egestas augue, eu vulputate magna eros eu erat. Aliquam erat volutpat. Nam dui mi, tincidunt quis, accumsan
            porttitor, facilisis luctus, metus</p>
    </div>
</div>
<%@ include file="../includes/table_controls.jspf" %>
<div class="row">
    <div class="col s12">
        <table class="highlight">
            <thead>
            <th>ID</th>
            <th>Tilbud</th>
            <th>Kunde</th>
            <th>Medarbejder</th>
            <th>Købspris</th>
            <th>Oprettet</th>
            </thead>
            <tbody>
            <c:forEach items="${purchases}" var="purchase">
                <tr>
                    <c:set var="offer" value="${purchase.getOffer()}"/>
                    <c:set var="order" value="${offer.getOrder()}"/>
                    <c:set var="employee" value="${offer.getEmployee()}"/>
                    <c:set var="customer" value="${order.getCustomer()}"/>
                    <td><a href="?action=update&id=${purchase.getId()}">${purchase.getId()}</a></td>
                    <td><a href="offers?action=update&id=${offer.getId()}">Tilbud</a></td>
                    <td><a
                            href="customers?action=update&id=${customer.getId()}"><c:out
                            value="${customer.getName()}"/></a></td>
                    <td><a href="employees?action=update&id=${employee.getId()}"><c:out
                            value="${employee.getName()}"/></a></td>
                    <td>${f:formatPrice(offer.getPrice())}</td>
                    <td>${f:formatDatetime(offer.getCreatedAt())}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>