<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Tag</h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <table class="highlight">
            <thead>
            <th>ID</th>
            <th>Navn</th>
            <th>Beskrivelse</th>
            <th>Minimum hældning</th>
            <th>Maximum hældning</th>
            <th>Pris (m<sup>2</sup>)</th>
            <th>Aktiv</th>
            </thead>
            <tbody>
            <c:forEach items="${roofings}" var="roofing">
                <tr>
                    <td>${roofing.getId()}</td>
                    <td><c:out value="${roofing.getName()}"/></td>
                    <td><c:out value="${roofing.getDescription()}"/></td>
                    <td><c:out value="${roofing.getMinimumSlope()}"/></td>
                    <td><c:out value="${roofing.getMaximumSlope()}"/></td>
                    <td><c:out value="${f:formatPrice(roofing.getPricePerSquareMeter())}"/></td>
                    <td><c:out value="${f:formatBoolean(roofing.isActive())}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="includes/bot.jspf" %>