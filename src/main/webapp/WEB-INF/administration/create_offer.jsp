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
        <h3>ORDREINFORMATION</h3>
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
                <th>Oprettet</th>
                <td>${f:formatDatetime(order.getCreatedAt())}</td>
            </tr>
            <tr>
                <th>Kommentar</th>
                <td><c:out value="${order.getComment()}"/></td>
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
<div class="row">
    <div class="col s12">
        <p>Opret tilbuddet herunder.</p>
    </div>
</div>
<div class="row">
    <div class="col s12 no-padding">
        <form method="post">
            ${csrf}
            <input type="hidden" name="order" value="${order.getId()}">
            <div class="row">
                <input type="hidden" id="total" value="${summary.getTotal()}">
                <div class="col s2 input-field">
                    <input type="number" class="validate" min="0" value="40" id="coverage">
                    <label for="coverage">Dækningsgrad</label>
                </div>
                <div class="col s8 input-field">
                    ${csrf}
                    <input type="text" class="validate" pattern="^\d+(\.\d{1,2})?$" name="price"
                           id="price" required>
                    <label for="price">Tilbud pris</label>
                </div>
                <script>
                    $('input#coverage').on('input', updatePrice);
                    updatePrice();

                    function updatePrice() {
                        var multiplier = 100 / parseFloat($('input#coverage').val());
                        var total = parseInt($('input#total').val());
                        $('input#price').attr("value", multiplier * total / 100);
                    }

                </script>
                <div class="col s2">
                    ${csrf}
                    <button class="btn-large waves-effect waves-light" type="submit" name="action"
                            style="display:block;width: 100%">
                        Opret<i class="material-icons right">send</i>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>