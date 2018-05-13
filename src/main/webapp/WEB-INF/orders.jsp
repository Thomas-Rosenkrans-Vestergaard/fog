<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Mine ordre</h2>
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
        <table class="highlight">
            <thead>
            <tr>
                <th>Bredde</th>
                <th>Længde</th>
                <th>Højde</th>
                <th>Tag</th>
                <th>Hældning</th>
                <th>Spær</th>
                <th>Oprettet</th>
                <th>Redskabsskur</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${orders}" var="order">
                <tr style="cursor:pointer;" onclick="location.href = 'order?id=${order.getId()}'">
                    <td>${order.getWidth()}</td>
                    <td>${order.getLength()}</td>
                    <td>${order.getHeight()}</td>
                    <td><c:out value="${order.getRoofing().getName()}"/></td>
                    <td>${order.getSlope()}</td>
                    <td>${order.getRafterChoice()}</td>
                    <td>${f:formatDatetime(order.getCreatedAt())}</td>
                    <c:if test="${order.getShed() == null}">
                        <td>Intet skur</td>
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
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="includes/bottom.jspf" %>