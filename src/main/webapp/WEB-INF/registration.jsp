<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Registrer</h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Pellentesque habitant morbi
            tristique senectus et netus et malesuada fames ac turpis egestas. Et harum quidem rerum facilis est et
            expedita distinctio. Phasellus et lorem id felis nonummy placerat. Mauris metus. Praesent id justo in neque
            elementum ultrices. Aenean placerat. In dapibus augue non sapien. Etiam neque. Fusce consectetuer risus a
            nunc. Vivamus luctus egestas leo.</p>
    </div>
</div>
<div class="row">
    <div class="col s12 no-padding">
        <form method="post">
            ${csrf}
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
</div>
<%@ include file="includes/bottom.jspf" %>