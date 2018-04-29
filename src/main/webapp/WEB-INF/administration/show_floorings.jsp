<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Gulve</h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <a class="btn-floating btn-large waves-effect waves-light red" href="?action=create">
            <i class="material-icons">add</i>
        </a>
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
            <c:forEach items="${floorings}" var="flooring">
                <tr onclick="location.href = '?action=update&id=${cladding.getId()}'">
                    <td>${flooring.getId()}</td>
                    <td><c:out value="${flooring.getName()}"/></td>
                    <td><c:out value="${flooring.getDescription()}"/></td>
                    <td><c:out value="${f:formatPrice(flooring.getPricePerSquareMeter())}"/></td>
                    <td><c:out value="${f:formatBoolean(flooring.isActive())}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="bot.jspf" %>