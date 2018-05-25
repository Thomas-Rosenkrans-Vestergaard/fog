<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>RESULTAT</h2>
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
        <h3>TEGNINGER</h3>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum
            tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas
            semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo.</p>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <ul class="tabs">
            <li class="tab"><a href="#drawing_1">Skelet ovenfra</a></li>
            <li class="tab"><a href="#drawing_2">Skelet fra siden</a></li>
            <li class="tab"><a href="#drawing_3">Skelet ovenfra</a></li>
            <li class="tab"><a href="#drawing_4">Skelet ovenfra m. tag</a></li>
        </ul>
    </div>
    <div id="drawing_1" class="col s12">
        ${summary.getSkeletonConstructionSummary().getAerialView().getXML()}
    </div>
    <div id="drawing_2" class="col s12">
        ${summary.getSkeletonConstructionSummary().getSideView().getXML()}
    </div>
    <div id="drawing_3" class="col s12">
        ${summary.getRoofingConstructionSummary().getAerialSkeletonView().getXML()}
    </div>
    <div id="drawing_4" class="col s12">
        ${summary.getRoofingConstructionSummary().getAerialTiledView().getXML()}
    </div>
</div>
</div>
<script>$('.tabs').tabs();</script>
<div class="row">
    <div class="col s12">
        <h3>MATERIALER</h3>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum
            tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas
            semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo.</p>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <table>
            <table class="bordered highlight">
                <thead>
                <tr>
                    <th>Materiale</th>
                    <th>Mængde</th>
                    <th>Noter</th>
                    <th>Pris</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td class="bold" colspan="4">Skelet materialer</td>
                </tr>
                <c:forEach items="${summary.getSkeletonConstructionSummary().getMaterials().getLines()}"
                           var="line">
                    <tr>
                        <td><c:out value="${line.getMaterial().getDescription()}"/></td>
                        <td><c:out value="${line.getAmount()}"/></td>
                        <td><c:out value="${line.getNotes()}"/></td>
                        <td><c:out value="${f:formatPrice(line.getTotal())}"/></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td class="bold">${f:formatPrice(summary.getSkeletonConstructionSummary().getTotal())}</td>
                </tr>
                <tr>
                    <td class="bold" colspan="4">Tag materialer</td>
                </tr>
                <c:forEach items="${summary.getRoofingConstructionSummary().getMaterials().getLines()}" var="line">
                    <tr>
                        <td><c:out value="${line.getMaterial().getDescription()}"/></td>
                        <td><c:out value="${line.getAmount()}"/></td>
                        <td><c:out value="${line.getNotes()}"/></td>
                        <td><c:out value="${f:formatPrice(line.getTotal())}"/></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td class="bold">${f:formatPrice(summary.getRoofingConstructionSummary().getTotal())}</td>
                </tr>
                <tr>
                    <td class="bold">Total</td>
                    <td></td>
                    <td></td>
                    <td class="bold">${f:formatPrice(summary.getTotal())}</td>
                </tr>
                </tbody>
            </table>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>