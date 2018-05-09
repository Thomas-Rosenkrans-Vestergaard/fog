<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Ordre</h2>
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
            <th>Kunde</th>
            <th>Beklædning</th>
            <th class="short">Bredde</th>
            <th class="short">Længde</th>
            <th class="short">Højde</th>
            <th>Tag</th>
            <th>Hældning</th>
            <th>Spær</th>
            <th>Redskabsskur</th>
            <th>Oprettet</th>
            </thead>
            <tbody>
            <c:forEach items="${orders}" var="order">
                <tr onclick="location.href = '?action=update&id=${order.getId()}'">
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
                    <td>${order.getRafterChoice()}</td>
                    <td><a href="${order.getShed().getId()}"><c:out value="${order.getRoofing().getId()}"/></a></td>
                    <td>${f:formatDatetime(order.getCreatedAt())}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>