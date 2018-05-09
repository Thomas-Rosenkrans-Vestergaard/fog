<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Beklædninger</h2>
        <a class="waves-effect waves-light btn-large"><i class="material-icons left">add</i>OPRET BEKLÆDNING</a>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla non lectus sed nisl molestie malesuada.
            Mauris dolor felis, sagittis at, luctus sed, aliquam non, tellus. Vivamus luctus egestas leo. Sed elit dui,
            pellentesque a, faucibus vel, interdum nec, diam. Itaque earum rerum hic tenetur a sapiente delectus, ut aut
            reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.</p>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <table class="highlight administration-table">
            <thead>
            <th class="short">ID</th>
            <th>Navn</th>
            <th class="long">Beskrivelse</th>
            <th class="short">Aktiv</th>
            </thead>
            <tbody>
            <c:forEach items="${claddings}" var="claddingId">
                <tr onclick="location.href = '?action=update&id=${claddingId.getId()}'">
                    <td>${claddingId.getId()}</td>
                    <td><c:out value="${claddingId.getName()}"/></td>
                    <td><c:out value="${claddingId.getDescription()}"/></td>
                    <td><c:out value="${f:formatBoolean(claddingId.isActive())}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>