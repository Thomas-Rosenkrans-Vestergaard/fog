<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Tagbeklædninger</h2>
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
                    <select name="type" id="type">
                        <c:forEach items="${types}" var="type">
                            <option value="${type.name()}"><c:out value="${type.name()}"/></option>
                        </c:forEach>
                    </select>
                    <label for="type">Tagtype at oprette.</label>
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
        <table class="bordered highlight administration-table">
            <thead>
            <tr>
                <th>Navn</th>
                <th>Beskrivelse</th>
                <th>Aktiv</th>
                <th>Type</th>
            </tr>
            <%@ include file="../includes/table_filters.jspf" %>
            </thead>
            <tbody>
            <c:forEach items="${roofings}" var="roofing">
                <tr data-link="?action=update&id=${roofing.getId()}">
                    <td><c:out value="${roofing.getName()}"/></td>
                    <td><c:out value="${roofing.getDescription()}"/></td>
                    <td><c:out value="${f:formatBoolean(roofing.isActive())}"/></td>
                    <td><c:out value="${roofing.getType()}"/></td>
                </tr>
            </c:forEach>
            <%@ include file="../includes/table_pagination.jspf" %>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>