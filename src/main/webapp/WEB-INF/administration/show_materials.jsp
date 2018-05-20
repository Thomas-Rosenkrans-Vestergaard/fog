<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Materialer</h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla non lectus sed nisl molestie malesuada.
            Mauris dolor felis, sagittis at, luctus sed, aliquam non, tellus.</p>
    </div>
</div>
<div class="row">
    <div class="col s12 no-padding">
        <form method="get">
            <input type="hidden" name="action" value="create">
            <div class="row">
                <div class="col s6 input-field">
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
<div class="row">
    <div class="col s12">
        <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla non lectus sed nisl molestie malesuada.
            Mauris dolor felis, sagittis at, luctus sed, aliquam non, tellus. Vivamus luctus egestas leo. Sed elit dui,
            pellentesque a, faucibus vel, interdum nec, diam. Itaque earum rerum hic tenetur a sapiente delectus, ut aut
            reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.</p>
    </div>
</div>
<%@ include file="../includes/table_controls.jspf" %>
<div class="row">
    <div class="col s12">
        <table class="bordered highlight administration-table">
            <thead>
            <tr>
                <th>Nummer</th>
                <th>Beskrivelse</th>
                <th>Pris</th>
                <th>Enhed</th>
                <th>Kategori</th>
                <th>Aktiv</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${materials}" var="material">
                <tr data-link="?action=update&id=${material.getId()}">
                    <td><c:out value="${material.getNumber()}"/></td>
                    <td><c:out value="${material.getDescription()}"/></td>
                    <td><c:out value="${f:formatPrice(material.getPrice())}"/></td>
                    <td><c:out value="${material.getUnit()}"/></td>
                    <td><c:out value="${material.getCategory().getName()}"/></td>
                    <td>${f:formatBoolean(material.isActive())}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>