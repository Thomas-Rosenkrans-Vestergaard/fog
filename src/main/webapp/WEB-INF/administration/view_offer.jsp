<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s8">
        <h2>Tilbud #${offer.getId()}</h2>
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
        <table class="bordered highlight">
            <thead>
            <tr>
                <th>Ordre</th>
                <th>Tilbudspris</th>
                <th>Medarbejder</th>
                <th>Status</th>
                <th>Oprettet</th>
            </tr>
            <%@ include file="../includes/table_filters.jspf" %>
            </thead>
            <tbody>
            <tr>
                <td><a href="orders?action=update&id=${offer.getOrder().getId()}">#${offer.getOrder().getId()}</a></td>
                <td>${f:formatPrice(offer.getPrice())}</td>
                <td>${offer.getEmployee().getName()}</td>
                <td>${offer.getStatus()}</td>
                <td>${f:formatDatetime(offer.getCreatedAt())}</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>