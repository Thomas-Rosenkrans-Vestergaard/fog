<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Kunder</h2>
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
<div class="row">
    <div class="col s12">
        <table class="bordered highlight administration-table">
            <thead>
            <tr>
                <th>Navn</th>
                <th>Adresse</th>
                <th>Email</th>
                <th>Telefon</th>
                <th>Aktiv</th>
                <th>Bekræftet</th>
                <th>Oprettet</th>
            </tr>
            <%@ include file="../includes/table_filters.jspf" %>
            </thead>
            <tbody>
            <c:forEach items="${customers}" var="customer">
                <tr>
                    <td><c:out value="${customer.getName()}"/></td>
                    <td><c:out value="${customer.getAddress()}"/></td>
                    <td><c:out value="${customer.getEmail()}"/></td>
                    <td><c:out value="${customer.getPhone()}"/></td>
                    <td>
                        <form method="POST">
                                ${csrf}
                            <input type="hidden" name="id" value="${customer.getId()}">
                            <input type="hidden" name="action"
                                   value="${customer.isActive() ? 'deactivate' : 'activate'}">
                            <input class="btn" type="submit" value="${customer.isActive() ? 'Inaktiver' : 'Aktiver'}">
                        </form>
                        </form>
                    </td>
                    <td>${f:formatBoolean(customer.isVerified())}</td>
                    <td><c:out value="${f:formatDatetime(customer.getCreatedAt())}"/></td>
                </tr>
            </c:forEach>
            <%@ include file="../includes/table_pagination.jspf" %>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>