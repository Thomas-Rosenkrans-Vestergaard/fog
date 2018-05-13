<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>OPRET TILBUD</h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla non lectus sed nisl molestie malesuada.
            Mauris dolor felis, sagittis at, luctus sed, aliquam non, tellus. Vivamus luctus egestas leo. Sed elit dui,
            pellentesque a, faucibus vel, interdum nec, diam.</p>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <form method="post">
            <input type="hidden" name="order" value="${order.getId()}">
            <div class="row">
                <div class="col s12 input-field">
                    <input type="number" name="price" id="price" step="1" min="1">
                    <label for="price">Tilbud pris</label>
                </div>
            </div>
            <div class="row">
                <div class="col s12">
                    <button class="btn-large waves-effect waves-light" type="submit" name="action">
                        Opret<i class="material-icons right">send</i>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <h3>ORDREINFORMATION</h3>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <table>
            <tbody>
            <tr>
                <th>ID</th>
                <td>${order.getId()}</td>
            </tr>
            <tr>
                <th>Bredde</th>
                <td>${order.getWidth()}</td>
            </tr>
            <tr>
                <th>Længde</th>
                <td>${order.getLength()}</td>
            </tr>
            <tr>
                <th>Højde</th>
                <td>${order.getHeight()}</td>
            </tr>
            <tr>
                <th>Tag</th>
                <td><c:out value="${order.getRoofing().getName()}"/></td>
            </tr>
            <tr>
                <th>Hældning</th>
                <td>${order.getSlope()}</td>
            </tr>
            <tr>
                <th>Spær</th>
                <td>${order.getRafterChoice()}</td>
            </tr>
            <tr>
                <th>Oprettet</th>
                <td>${f:formatDatetime(order.getCreatedAt())}</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="row">
    <div class="col s6">
        ${summary.getSkeletonConstructionSummary().getAerialView().getXML()}
    </div>
    <div class="col s6">
        ${summary.getSkeletonConstructionSummary().getSideView().getXML()}
    </div>
</div>
<div class="row">
    <div class="col s6">
        ${summary.getRoofingConstructionSummary().getSkeletonView().getXML()}
    </div>
    <div class="col s6">
        ${summary.getRoofingConstructionSummary().getTiledView().getXML()}
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>