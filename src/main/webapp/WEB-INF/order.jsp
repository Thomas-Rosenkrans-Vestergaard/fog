<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>ORDRE #${order.getId()}</h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <form method="post">
            <input type="hidden" name="id" value="${order.getId()}">
            <button class="btn-large" type="submit" name="action" value="cancel"
            ${order.isActive() ? '' : 'disabled'}>Aflys
            </button>
        </form>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum
            tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas
            semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo. Quisque sit amet est et sapien
            ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, commodo vitae, ornare sit amet, wisi. Aenean
            fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. Donec
            non enim in turpis pulvinar facilisis.</p>
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
                <th>Kommentar</th>
                <td><c:out value="${order.getComment()}"/></td>
            </tr>
            <tr>
                <th>Aktiv</th>
                <td>${f:formatBoolean(order.isActive())}</td>
            </tr>
            <tr>
                <th>Redskabsskur</th>
                <c:if test="${order.getShed() == null}">
                    <td>Intet</td>
                </c:if>
                <c:if test="${order.getShed() != null}">
                    <td>
                        <table>
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
        <h3>Tilbud</h3>
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
        <table class="bordered highlight">
            <thead>
            <tr>
                <th>Tilbudspris</th>
                <th>Status</th>
                <th>Oprettet</th>
                <th style="width: 120px">Aktioner</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${offers.isEmpty()}">
                <tr>
                    <td colspan="4">Ingen tilbud for denne ordre</td>
                </tr>
            </c:if>
            <c:forEach items="${offers}" var="offer">
                <tr>
                    <td>${f:formatPrice(offer.getPrice())}</td>
                    <th>${offer.getStatus()}</th>
                    <td>${f:formatDatetime(offer.getCreatedAt())}</td>
                    <c:if test="${offer.isOpen()}">
                        <td>
                            <form action="orders" method="post" style="display:inline-block">
                                    ${csrf}
                                <input type="hidden" name="offer" value="${offer.getId()}">
                                <button class="btn-floating waves-effect waves-light" type="submit" name="action"
                                        value="accept"><i class="material-icons right">check</i>
                                </button>
                            </form>
                            <form action="orders" method="post" style="display:inline-block;margin-left: 20px">
                                    ${csrf}
                                <input type="hidden" name="offer" value="${offer.getId()}">
                                <button class="btn-floating waves-effect waves-light" type="submit" name="action"
                                        value="reject"><i class="material-icons right">clear</i>
                                </button>
                            </form>
                        </td>
                    </c:if>
                    <c:if test="${!offer.isOpen()}">
                        <td></td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="includes/bottom.jspf" %>