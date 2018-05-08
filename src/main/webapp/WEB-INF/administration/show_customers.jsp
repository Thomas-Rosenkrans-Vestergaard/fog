<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Kunder</h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <table class="highlight administration-table">
            <thead>
            <th>ID</th>
            <th>Navn</th>
            <th>Adresse</th>
            <th>Email</th>
            <th>Telefon</th>
            <th>Aktiv</th>
            <th>Oprettet</th>
            </thead>
            <tbody>
            <c:forEach items="${customers}" var="customer">
                <tr onclick="location.href = '?action=update&id=${customer.getId()}'">
                    <td>${customer.getId()}</td>
                    <td><c:out value="${customer.getName()}"/></td>
                    <td><c:out value="${customer.getAddress()}"/></td>
                    <td><c:out value="${customer.getEmail()}"/></td>
                    <td><c:out value="${customer.getPhone()}"/></td>
                    <td><c:out value="${f:formatBoolean(customer.isActive())}"/></td>
                    <td><c:out value="${f:formatDatetime(customer.getCreatedAt())}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>