<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Beklædninger</h2>
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
            <c:forEach items="${claddings}" var="cladding">
                <tr>
                    <td>${cladding.getId()}</td>
                    <td><c:out value="${cladding.getName()}"/></td>
                    <td><c:out value="${cladding.getDescription()}"/></td>
                    <td><c:out value="${f:formatPrice(cladding.getPricePerSquareMeter())}"/></td>
                    <td><c:out value="${f:formatBoolean(cladding.isActive())}"/></td>
                    <th><a class="btn waves-effect waves-light" href="claddings?action=update&id=${cladding.getId()}">
                        VIS
                    </a></th>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="includes/bot.jspf" %>