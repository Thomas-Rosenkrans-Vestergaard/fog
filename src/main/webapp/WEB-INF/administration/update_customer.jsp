<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Opdater kunde</h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla non lectus sed nisl molestie malesuada.
            Mauris dolor felis, sagittis at, luctus sed, aliquam non, tellus. Vivamus luctus egestas leo. Sed elit dui,
            pellentesque a, faucibus vel, interdum nec, diam.</p>
    </div>
</div>
<div class="row">
    <form method="post" class="col s12 no-padding">
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
        <div class="class row">
            <div class="col s12">
                <input name="active" type="radio" id="active-true" value="true"
                ${customer.isActive() ? 'checked' : ''}/>
                <label for="active-true">Aktiv</label>
            </div>
            <div class="col s12">
                <input name="active" type="radio" id="active-false" value="false"
                ${customer.isActive() ? '' : 'checked'}/>
                <label for="active-false">Inaktiv</label>
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
<%@ include file="../includes/bottom.jspf" %>