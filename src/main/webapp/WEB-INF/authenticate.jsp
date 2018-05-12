<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Autentificér</h2>
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
            <div class="row">
                <div class="col s12 input-field">
                    <input type="email" name="email" id="login-email" data-length="255" class="validate"
                           value="tvestergaard@hotmail.com" required>
                    <label for="login-email">Email</label>
                </div>
            </div>
            <div class="row">
                <div class="col s12 input-field">
                    <input type="password" name="password" id="login-password" class="validate" value="123456" required>
                    <label for="login-password">Adgangskode</label>
                </div>
            </div>
            <div class="row">
                <div class="col s12">
                    <button class="btn-large waves-effect waves-light" type="submit" name="action">LOG IND
                        <i class="material-icons right">send</i>
                    </button>
                    <a href="forgot-password" class="waves-effect waves-light btn-large"
                       style="margin-left: 20px">Glemt
                        password</a>
                    <a href="registration" class="waves-effect waves-light btn-large"
                       style="margin-left: 20px">Registrer</a>
                </div>
            </div>
        </form>
    </div>
</div>
<%@ include file="includes/bottom.jspf" %>