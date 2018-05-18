<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Ordre</h2>
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
<%@ include file="../includes/table_controls.jspf" %>
<div class="row">
    <div class="col s12">
        <table class="bordered highlight">
            <thead>
            <th>ID</th>
            <th>Kunde</th>
            <th>Bredde</th>
            <th>Længde</th>
            <th>Højde</th>
            <th>Tag</th>
            <th>Hældning</th>
            <th>Spær</th>
            <th>Oprettet</th>
            <th>Redskabsskur</th>
            </thead>
            <tbody>
            <c:forEach items="${orders}" var="order">
                <tr>
                    <td><a href="?action=update&id=${order.getId()}">${order.getId()}</a></td>
                    <c:set value="${order.getCustomer()}" var="customer"/>
                    <td>
                        <a href="customers?action=update&id=${customer.getId()}"><c:out
                                value="${customer.getName()}"/></a>
                    </td>
                    <td>${order.getWidth()}</td>
                    <td>${order.getLength()}</td>
                    <td>${order.getHeight()}</td>
                    <c:set var="roofing" value="${order.getRoofing()}"/>
                    <td>
                        <a href="roofings?action=update&id=${roofing.getId()}"><c:out value="${roofing.getName()}"/>
                        </a></td>
                    <td>${order.getSlope()}</td>
                    <td>${order.getRafterChoice()}</td>
                    <td>${f:formatDatetime(order.getCreatedAt())}</td>
                    <c:if test="${order.getShed() == null}">
                        <td>Intet skur</td>
                    </c:if>
                    <c:if test="${order.getShed() != null}">
                        <td>
                            <table class="inner-table">
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
                    <c:if test="${order.getNumberOfOpenOffers() == 0 && order.isActive()}">
                        <td><i style="color:#0D47A1" class="material-icons small">new_releases</i></td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>