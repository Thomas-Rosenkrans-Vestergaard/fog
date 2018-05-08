<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Tag</h2>
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
        <table class="highlight">
            <thead>
            <th>ID</th>
            <th>Navn</th>
            <th>Beskrivelse</th>
            <th>Aktiv</th>
            <th>Tagtype</th>
            </thead>
            <tbody>
            <c:forEach items="${roofings}" var="roofing">
                <tr onclick="location.href = '?action=update&id=${roofing.getId()}'">
                    <td>${roofing.getId()}</td>
                    <td><c:out value="${roofing.getName()}"/></td>
                    <td><c:out value="${roofing.getDescription()}"/></td>
                    <td><c:out value="${f:formatBoolean(roofing.isActive())}"/></td>
                    <td><c:out value="${roofing.getType()}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script>
    $('select').material_select();
</script>
<%@ include file="../includes/bottom.jspf" %>