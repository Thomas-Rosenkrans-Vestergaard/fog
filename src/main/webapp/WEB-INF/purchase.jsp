<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Mine køb</h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum
            tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas
            semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo. Quisque sit amet est et sapien
            ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, commodo vitae, ornare sit amet, wisi. Aenean
            fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. Donec
            non enim in turpis pulvinar facilisis. Ut felis. Praesent dapibus, neque id cursus faucibus, tortor neque
            egestas augue, eu vulputate magna eros eu erat. Aliquam erat volutpat. Nam dui mi, tincidunt quis, accumsan
            porttitor, facilisis luctus, metus</p>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <table>
            <tbody>
            <tr>
                <th>Tilbud</th>
                <td><a href="offer?id=${purchase.getOffer().getId()}">Tilbud</a></td>
            </tr>
            <tr>
                <th>Medarbejder</th>
                <td><c:out value="${purchase.getEmployee().getName()}"/></td>
            </tr>
            <tr>
                <th>Købspris</th>
                <td>${f:formatPrice(purchase.getOffer().getPrice())}</td>
            </tr>
            <tr>
                <th>Oprettet</th>
                <td>${f:formatDatetime(purchase.getCreatedAt())}</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <h3>Stykliste</h3>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum
            tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas
            semper. Aenean ultricies mi vitae est.</p>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <table class="highlight">
            <thead>
            <th>Materiale</th>
            <th>Mængde</th>
            <th>Noter</th>
            <th>Pris</th>
            </thead>
            <tbody>
            <c:forEach items="${constructionSummary.getSkeletonConstructionSummary().getMaterials().getLines()}"
                       var="line">
                <tr>
                    <td>
                        <c:out value="${line.getMaterial().getDescription()}"/>
                    </td>
                    <td>
                        <c:out value="${line.getAmount()}"/>
                    </td>
                    <td>
                        <c:out value="${line.getNotes()}"/>
                    </td>
                    <td>
                        <c:out value="${f:formatPrice(line.getTotal())}"/>
                    </td>
                </tr>
            </c:forEach>

            <c:forEach items="${constructionSummary.getRoofingConstructionSummary().getMaterials().getLines()}" var="line">
                <tr>
                    <td>
                        <c:out value="${line.getMaterial().getDescription()}"/>
                    </td>
                    <td>
                        <c:out value="${line.getAmount()}"/>
                    </td>
                    <td>
                        <c:out value="${line.getNotes()}"/>
                    </td>
                    <td>
                        <c:out value="${f:formatPrice(line.getTotal())}"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <h3>Tegninger</h3>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum
            tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas
            semper. Aenean ultricies mi vitae est.</p>
    </div>
</div>
<div class="row">
    <div class="col s12">
        ${constructionSummary.getSkeletonConstructionSummary().getAerialView().getXML()}
    </div>
</div>
<div class="row">
    <div class="col s12">
        ${constructionSummary.getSkeletonConstructionSummary().getSideView().getXML()}
    </div>
</div>
<div class="row">
    <div class="col s12">
        ${constructionSummary.getRoofingConstructionSummary().getAerialSkeletonView().getXML()}
    </div>
</div>
<div class="row">
    <div class="col s12">
        ${constructionSummary.getRoofingConstructionSummary().getAerialTiledView().getXML()}
    </div>
</div>
<%@ include file="includes/bottom.jspf" %>