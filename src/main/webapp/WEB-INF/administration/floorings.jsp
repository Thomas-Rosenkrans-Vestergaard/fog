<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
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
            <th>VIS</th>
            </thead>
            <tbody>
            <c:forEach items="${floorings}" var="flooring">
                <tr>
                    <td>${flooring.getId()}</td>
                    <td><c:out value="${flooring.getName()}"/></td>
                    <td><c:out value="${flooring.getDescription()}"/></td>
                    <td><c:out value="${f:formatPrice(flooring.getPricePerSquareMeter())}"/></td>
                    <td><c:out value="${f:formatBoolean(flooring.isActive())}"/></td>
                    <th><a class="btn waves-effect waves-light" href="floorings?action=update&id=${flooring.getId()}">
                        VIS
                    </a></th>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="includes/bot.jspf" %>