<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Mine tilbud</h2>
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
            <th>Beklædning</th>
            <th>Bredde</th>
            <th>Længde</th>
            <th>Højde</th>
            <th>Tag</th>
            <th>Hældning</th>
            <th>Spær</th>
            <th>Redskabsskur</th>
            <th>Oprettet</th>
            <th>Medarbejder</th>
            <th>Tilbud pris</th>
            <th>Acceptér</th>
            <th>Afvis</th>
            </thead>
            <tbody>
            <c:forEach items="${offers}" var="offer">
                <tr>
                    <c:set var="order" value="${offer.getOrder()}"/>
                    <c:set var="employee" value="${offer.getEmployee()}"/>
                    <td><c:out value="${order.getCladding().getName()}"/></td>
                    <td>${order.getWidth()}</td>
                    <td>${order.getLength()}</td>
                    <td>${order.getHeight()}</td>
                    <td><c:out value="${order.getRoofing().getName()}"/></td>
                    <td>${order.getSlope()}</td>
                    <td>${order.getRafterChoice()}</td>
                    <td><c:out value="${order.getRoofing().getName()}"/></td>
                    <td>${f:formatDatetime(order.getCreatedAt())}</td>
                    <td><c:out value="${employee.getName()}"/></td>
                    <td>${f:formatPrice(offer.getPrice())}</td>
                    <td><a href="?action=accept&offer=${offer.getId()}">Acceptér</a></td>
                    <td><a href="?action=reject&offer=${offer.getId()}">Afvis</a></td>
                    <td></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="includes/bottom.jspf" %>