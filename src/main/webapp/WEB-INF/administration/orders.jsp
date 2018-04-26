<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Ordre</h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <table class="highlight">
            <thead>
            <th>ID</th>
            <th>Kunde</th>
            <th>Beklædning</th>
            <th>Bredde</th>
            <th>Længde</th>
            <th>Højde</th>
            <th>Tag</th>
            <th>Hældning</th>
            <th>Spær</th>
            <th>Redskabsskur</th>
            <th>Oprettet</th>
            </thead>
            <tbody>
            <c:forEach items="${orders}" var="order">
                <tr>
                    <td>${order.getId()}</td>
                    <td><a href="${order.getCustomer().getId()}"><c:out value="${order.getCustomer().getName()}"/></a>
                    </td>
                    <td><a href="${order.getCladding().getId()}"><c:out value="${order.getCladding().getName()}"/></a>
                    <td>${order.getWidth()}</td>
                    <td>${order.getLength()}</td>
                    <td>${order.getHeight()}</td>
                    <td><a href="${order.getRoofing().getId()}"><c:out value="${order.getRoofing().getName()}"/></a>
                    </td>
                    <td>${order.getSlope()}</td>
                    <th>${order.getRafterChoice()}</th>
                    <td><a href="${order.getShed().getId()}"><c:out value="${order.getRoofing().getId()}"/></a></td>
                    <th>${f:formatDatetime(order.getCreatedAt())}</th>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="includes/bot.jspf" %>