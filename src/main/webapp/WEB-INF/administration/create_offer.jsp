<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
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
            ${csrf}
            <input type="hidden" name="order" value="${order.getId()}">
            <div class="row">
                <div class="col s10 input-field">
                    <input type="number" name="price" id="price" step="1" min="1">
                    <label for="price">Tilbud pris</label>
                </div>
                <div class="col s2">
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
        <table class="bordered highlight">
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
            <tr>
                <th>Redskabsskur</th>
                <c:if test="${order.getShed() == null}">
                    <td>Intet</td>
                </c:if>
                <c:if test="${order.getShed() != null}">
                    <td>
                        <table>
                            <thead>
                            <tbody>
                            <tr>
                                <th>Dybde</th>
                                <td>${order.getShed().getDepth()}</td>
                            </tr>
                            <tr>
                                <th>Beklædning</th>
                                <td>${order.getShed().getCladding().getName()}</td>
                            </tr>
                            <tr>
                                <th>Gulv</th>
                                <td>${order.getShed().getFlooring().getName()}</td>
                            </tr>
                            </tbody>
                        </table>
                    </td>
                </c:if>
            </tr>
            </tbody>
        </table>
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
    <div class="col s12 l6">
        <h4 class="drawing-title">Skelet ovenfra</h4>
        ${summary.getSkeletonConstructionSummary().getAerialView().getXML()}
    </div>
    <div class="col s12 l6">
        <h4 class="drawing-title">Skelet fra siden</h4>
        ${summary.getSkeletonConstructionSummary().getSideView().getXML()}
    </div>
</div>
<div class="row">
    <div class="col s12 l6">
        <h4 class="drawing-title">Taget uden tegl</h4>
        ${summary.getRoofingConstructionSummary().getAerialSkeletonView().getXML()}
    </div>
    <div class="col s12 l6">
        <h4 class="drawing-title">Teget med halvt tegl</h4>
        ${summary.getRoofingConstructionSummary().getAerialTiledView().getXML()}
    </div>
</div>
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
                <th>Materiale</th>
                <th>Mængde</th>
                <th>Noter</th>
                <th>Pris</th>
                </thead>
                <tbody>
                <tr>
                    <td class="bold">Skelet materialer</td>
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
                    <td class="bold">Tag materialer</td>
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