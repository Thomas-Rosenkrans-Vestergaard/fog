<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Mine køb</h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum
            tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas
            semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo. Quisque sit amet est et sapien
            ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, commodo vitae, ornare sit amet, wisi. Aenean
            fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. Donec
            non enim in turpis pulvinar facilisis.</p>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <table class="bordered highlight">
            <thead>
            <tr>
                <th>Tilbud</th>
                <th>Medarbejder</th>
                <th>Købspris</th>
                <th>Oprettet</th>
            </tr>
            <%@ include file="includes/table_filters.jspf" %>
            </thead>
            <tbody>
            <c:forEach items="${purchases}" var="purchase">
                <tr data-link="purchase?id=${purchase.getId()}">
                    <c:set var="offer" value="${purchase.getOffer()}"/>
                    <c:set var="order" value="${offer.getOrder()}"/>
                    <c:set var="employee" value="${offer.getEmployee()}"/>
                    <td><a href="offer?id=${offer.getId()}">${offer.getId()}</a></td>
                    <td><c:out value="${employee.getName()}"/></td>
                    <td>${f:formatPrice(offer.getPrice())}</td>
                    <td>${f:formatDatetime(offer.getCreatedAt())}</td>
                </tr>
            </c:forEach>
            <%@ include file="includes/table_pagination.jspf" %>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="includes/bottom.jspf" %>