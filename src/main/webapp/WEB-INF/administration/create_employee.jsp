<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2><span class="focus">${flooring.getName()}</span></h2>
    </div>
</div>
<div class="row">
    <form method="post" class="col s12">
        <div class="row">
            <div class="col s12 input-field">
                <input type="text" name="name" id="name" data-length="255" class="validate" required>
                <label for="name">Navn</label>
            </div>
        </div>
        <div class="row">
            <div class="col s12 input-field">
                <input type="text" name="username" id="username" data-length="255" class="validate" required>
                <label for="username">Brugernavn</label>
            </div>
        </div>
        <div class="row">
            <div class="col s12 input-field">
                <input type="password" name="password" id="password" class="validate" minlength="4" required>
                <label for="password">Adgangskode</label>
            </div>
        </div>
        <div class="row">
            <div class="col s12 input-field">
                <input type="password" name="password-repeat" id="password-repeat" class="validate" minlength="4"
                       required>
                <label for="password-repeat">Gentag adgangskode</label>
            </div>
        </div>
        <div class="class row">
            <div class="col s12">
                <p>
                    <input name="active" type="radio" id="active-true" value="true"/>
                    <label for="active-true">Aktiv</label>
                </p>
                <p>
                    <input name="active" type="radio" id="active-false" value="false"/>
                    <label for="active-false">Inaktiv</label>
                </p>
            </div>
        </div>
        <div class="row">
            <div class="col s12">
                <button class="btn-large waves-effect waves-light" type="submit" name="action">
                    Opret<i class="material-icons right">send</i>
                </button>
            </div>
        </div>
        <script>
            $(document).ready(function () {
                $('input#name').characterCounter();
            });
        </script>
    </form>
</div>
<%@ include file="includes/bot.jspf" %>