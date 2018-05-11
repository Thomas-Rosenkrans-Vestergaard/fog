<%@page contentType="text/html" pageEncoding="UTF-8" %>
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
            non enim in turpis pulvinar facilisis. Ut felis. Praesent dapibus, neque id cursus faucibus, tortor neque
            egestas augue, eu vulputate magna eros eu erat. Aliquam erat volutpat. Nam dui mi, tincidunt quis, accumsan
            porttitor, facilisis luctus, metus</p>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <table class="highlight">
            <thead>
            <th>Tilbud</th>
            <th>Medarbejder</th>
            <th>Købspris</th>
            <th>Oprettet</th>
            </thead>
            <tbody>
            <c:forEach items="${purchases}" var="purchase">
                <tr onclick="location.href = 'purchase?id=${purchase.getId()}'" style="cursor:pointer">
                    <c:set var="offer" value="${purchase.getOffer()}"/>
                    <c:set var="order" value="${offer.getOrder()}"/>
                    <c:set var="employee" value="${offer.getEmployee()}"/>
                    <td><a href="offer?id=${offer.getId()}">Tilbud</a></td>
                    <td><c:out value="${employee.getName()}"/></td>
                    <td>${f:formatPrice(offer.getPrice())}</td>
                    <td>${f:formatDatetime(offer.getCreatedAt())}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="includes/bottom.jspf" %>