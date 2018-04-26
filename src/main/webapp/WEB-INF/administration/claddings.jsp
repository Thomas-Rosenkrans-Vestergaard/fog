<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Beklædninger</h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <table class="highlight">
            <thead>
            <th>ID</th>
            <th>Navn</th>
            <th>Beskrivelse</th>
            <th>Pris (m<sup>2</sup>)</th>
            <th>Aktiv</th>
            </thead>
            <tbody>
            <c:forEach items="${claddings}" var="cladding">
                <tr>
                    <td>${cladding.getId()}</td>
                    <td><c:out value="${cladding.getName()}"/></td>
                    <td><c:out value="${cladding.getDescription()}"/></td>
                    <td><c:out value="${f:formatPrice(cladding.getPricePerSquareMeter())}"/></td>
                    <td><c:out value="${f:formatBoolean(cladding.isActive())}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="includes/bot.jspf" %>