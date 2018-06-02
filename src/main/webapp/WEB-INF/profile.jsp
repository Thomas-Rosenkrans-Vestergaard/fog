<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Profil</h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum
            tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas
            semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo. Quisque sit amet est et sapien
            ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, commodo vitae, ornare sit amet, wisi. Aenean
            fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui.</p>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <table class="bordered highlight">
            <tr>
                <th>Navn</th>
                <td><c:out value="${customer.getName()}"/></td>
            </tr>
            <tr>
                <th>Adresse</th>
                <td><c:out value="${customer.getAddress()}"/></td>
            </tr>
            <tr>
                <th>Email</th>
                <td><c:out value="${customer.getEmail()}"/></td>
            </tr>
            <tr>
                <th>Telefon</th>
                <td><c:out value="${customer.getPhone()}"/></td>
            </tr>
            <tr>
                <th>Aktiv</th>
                <td>${f:formatBoolean(customer.isActive())}</td>
            </tr>
            <tr>
                <th>Bekræftet</th>
                <td>
                    <p style="float:left">${f:formatBoolean(customer.isVerified())}</p>
                    <form method="post" style="float:right">
                        <input type="hidden" name="action" value="resend-confirmation">
                        <input class="btn" type="submit" value="Gensend" ${customer.isVerified() ? 'disabled' :
                                ''}>
                    </form>
                </td>
            </tr>
        </table>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <h3>Opdater informationer</h3>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <form method="POST">
            ${csrf}
                <input type="hidden" name="action" value="update-information">
            <div class="row">
                <div class="col s12 input-field">
                    <input type="text" name="name" id="name" class="validate" value="${customer.getName()}"
                           data-length="255" required>
                    <label for="name">Navn</label>
                </div>
            </div>
            <div class="row">
                <div class="col s12 input-field">
                    <input type="text" name="address" id="address" class="validate" value="${customer.getAddress()}"
                           data-length="255" required>
                    <label for="address">Adresse</label>
                </div>
            </div>
            <div class="row">
                <div class="col s12 input-field">
                    <input type="text" name="email" id="email" class="validate" value="${customer.getEmail()}"
                           data-length="255" required>
                    <label for="email">Email</label>
                </div>
            </div>
                <div class="row">
                    <div class="col s12 input-field">
                        <input type="text" name="phone" id="phone" class="validate" value="${customer.getPhone()}"
                               data-length="30" required>
                        <label for="phone">Telefon</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col s12">
                        <input type="submit" class="btn-large" value="Opdater">
                    </div>
                </div>
        </form>
    </div>
</div>
<%@ include file="includes/bottom.jspf" %>