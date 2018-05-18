<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Mine tilbud</h2>
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
        <table class="bordered highlight">
            <thead>
            <th>Ordre</th>
            <th>Tilbudspris</th>
            <th>Status</th>
            <th>Oprettet</th>
            <th style="width: 120px">Aktioner</th>
            </thead>
            <tbody>
            <c:forEach items="${offers}" var="offer">
                <tr>
                    <c:set var="order" value="${offer.getOrder()}"/>
                    <td><a href="order?id=${order.getId()}">Ordre</a></td>
                    <td>${f:formatPrice(offer.getPrice())}</td>
                    <th>${offer.getStatus()}</th>
                    <td>${f:formatDatetime(offer.getCreatedAt())}</td>
                    <c:if test="${offer.isOpen()}">
                        <td>
                            <form method="post" style="display:inline-block">
                                <input type="hidden" name="offer" value="${offer.getId()}">
                                <button class="btn-floating waves-effect waves-light" type="submit" name="action"
                                        value="accept"><i class="material-icons right">check</i>
                                </button>
                            </form>
                            <form method="post" style="display:inline-block;margin-left: 20px">
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