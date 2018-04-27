<%@ page import="tvestergaard.fog.data.customers.ContactMethod" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2><span class="focus">${customer.getName()}</span></h2>
    </div>
</div>
<div class="row">
    <form method="post" class="col s12">
        <input type="hidden" value="${customer.getId()}" name="id">
        <div class="row">
            <div class="col s12 input-field">
                <input type="text" name="name" id="name" data-length="255" class="validate"
                       value="${customer.getName()}" required>
                <label for="name">Navn</label>
            </div>
        </div>
        <div class="row">
            <div class="col s12 input-field">
                <input type="text" name="address" id="address" data-length="255" class="validate"
                       value="${customer.getAddress()}" required>
                <label for="address">Adresse</label>
            </div>
        </div>
        <div class="row">
            <div class="col s12 input-field">
                <input type="email" name="email" id="email" data-length="255" class="validate"
                       value="${customer.getEmail()}" required>
                <label for="email">Email</label>
            </div>
        </div>
        <div class="row">
            <div class="col s12 input-field">
                <input type="text" name="phone" id="phone" data-length="30" class="validate"
                       value="${customer.getPhone()}" required>
                <label for="phone">Telefon</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <select name="contact-method" id="contact-method" required>
                    <c:set var="methods" value="<%= ContactMethod.values() %>"/>
                    <c:forEach items="${methods}" var="method">
                        <option ${customer.getContactMethod() == method ? 'selected' : ''}
                                value="${method}">${method}</option>
                    </c:forEach>
                </select>
                <label>Kontaktmetode</label>
            </div>
        </div>
        <div class="class row">
            <div class="col s12">
                <p>
                    <input name="active" type="radio" id="active-true" value="true"
                    ${customer.isActive() ? 'checked' : ''}/>
                    <label for="active-true">Aktiv</label>
                </p>
                <p>
                    <input name="active" type="radio" id="active-false" value="false"
                    ${customer.isActive() ? '' : 'checked'}/>
                    <label for="active-false">Inaktiv</label>
                </p>
            </div>
        </div>
        <div class="row">
            <div class="col s12">
                <button class="btn-large waves-effect waves-light" type="submit" name="action">
                    Opdater<i class="material-icons right">send</i>
                </button>
            </div>
        </div>
        <script>
            $(document).ready(function () {
                $('select').material_select();
                $('input#name, input#address, input#email, input#phone').characterCounter();
            });
        </script>
    </form>
</div>
<%@ include file="includes/bot.jspf" %>