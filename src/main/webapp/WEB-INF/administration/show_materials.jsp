<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Materialer</h2>
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
        <form method="get">
            <input type="hidden" name="action" value="create">
            <div class="row">
                <div class="col s6">
                    <select name="category" id="category">
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.getId()}"><c:out value="${category.getName()}"/></option>
                        </c:forEach>
                    </select>
                    <label for="category">Materiale at oprette.</label>
                </div>
                <div class="col s6">
                    <input type="submit" name="submit" value="OPRET" class="btn-large waves-effect waves-light">
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    $('select').material_select();
</script>
<div class="row">
    <div class="col s12">
        <table class="highlight administration-table">
            <thead>
            <th>Nummer</th>
            <th>Beskrivelse</th>
            <th class="short">Pris</th>
            <th class="short">Enhed</th>
            <th class="short">Kategori</th>
            </thead>
            <tbody>
            <c:forEach items="${materials}" var="material">
                <tr onclick="location.href = '?action=update&id=${material.getId()}'">
                    <td><c:out value="${material.getNumber()}"/></td>
                    <td><c:out value="${material.getDescription()}"/></td>
                    <td><c:out value="${material.getPrice()}"/></td>
                    <td><c:out value="${material.getUnit()}"/></td>
                    <td><c:out value="${material.getCategory().getName()}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>