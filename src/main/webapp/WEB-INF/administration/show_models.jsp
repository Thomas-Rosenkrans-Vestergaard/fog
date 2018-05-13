<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Modeller</h2>
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
<%@ include file="../includes/administration_controls.jspf" %>
<div class="row">
    <div class="col s12">
        <table class="highlight administration-table">
            <thead>
            <th class="short">Id</th>
            <th>Navn</th>
            </thead>
            <tbody>
            <c:forEach items="${models}" var="model">
                <tr onclick="location.href = '?action=update&id=${model.getId()}'">
                    <td><c:out value="${model.getId()}"/></td>
                    <td><c:out value="${model.getName()}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>