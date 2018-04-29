<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Kundekonto</h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum
            tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas
            semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo.</p>
    </div>
</div>
<div class="row">
    <div class="col s12 l6 no-padding">
        <div class="row">
            <div class="col 12">
                <h2 class="account-form-headers">Opret konto</h2>
                <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.
                    Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante.</p>
            </div>
        </div>
        <form method="post">
            <input type="hidden" name="action" value="registration">
            <div class="row">
                <div class="row">
                    <div class="col s12 input-field">
                        <input type="text" name="name" id="name" data-length="255" class="validate" required>
                        <label for="name">Navn</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col s12 input-field">
                        <input type="text" name="address" id="address" data-length="255" class="validate" required>
                        <label for="address">Adresse</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col s12 input-field">
                        <input type="email" name="email" id="signup-email" data-length="255" class="validate" required>
                        <label for="signup-email">Email</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col s12 input-field">
                        <input type="text" name="phone" id="phone" data-length="30" class="validate" required>
                        <label for="phone">Telefon</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col s12 input-field">
                        <input type="password" name="password" id="signup-password" class="validate" required>
                        <label for="signup-password">Adgangskode</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col s12">
                        <button class="btn-large waves-effect waves-light" type="submit" name="action">OPRET KONTO
                            <i class="material-icons right">send</i>
                        </button>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div class="col s12 l6 no-padding">
        <div class="row">
            <div class="col 12">
                <h2 class="account-form-headers">Log ind</h2>
                <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.
                    Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante.
                    <a href="forgot-password">Glemt password</a>.</p>
            </div>
        </div>
        <form method="post">
            <input type="hidden" name="action" value="login">
            <div class="row">
                <div class="col s12 input-field">
                    <input type="email" name="email" id="login-email" data-length="255" class="validate" required>
                    <label for="login-email">Email</label>
                </div>
            </div>
            <div class="row">
                <div class="col s12 input-field">
                    <input type="password" name="password" id="login-password" class="validate" required>
                    <label for="login-password">Adgangskode</label>
                </div>
            </div>
            <div class="row">
                <div class="col s12">
                    <button class="btn-large waves-effect waves-light" type="submit" name="action">LOG IND
                        <i class="material-icons right">send</i>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<%@ include file="includes/bottom.jspf" %>