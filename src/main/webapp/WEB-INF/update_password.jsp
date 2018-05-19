<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Opdatér adgangskode</h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum
            tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas
            semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo. Quisque sit amet est et sapien
            ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, commodo vitae, ornare sit amet, wisi. Aenean
            fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. Donec
            non enim in turpis pulvinar facilisis. Ut felis. Praesent dapibus, neque id cursus faucibus, tortor neque
            egestas augue, eu vulputate magna eros eu erat. Aliquam erat volutpat. Nam dui mi, tincidunt quis, accumsan
            porttitor, facilisis luctus, metus</p>
    </div>
</div>
<div class="row">
    <div class="col s12 no-padding">
        <form method="post">
            ${csrf}
            <div class="row">
                <input type="hidden" name="id" value="${customer.getId()}">
                <div class="input-field col s12 l10">
                    <input type="password" name="old-password" id="old-password" required>
                    <label for="old-password">Nuværende adgangskode</label>
                </div>
                <div class="input-field col s12 l10">
                    <input type="password" name="new-password" id="new-password" required>
                    <label for="new-password">Nye adgangskode</label>
                </div>
                <div class="col s12 l2">
                    <button class="btn-large waves-effect waves-light" type="submit" name="action">ANMOD
                        <i class="material-icons right">send</i>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<%@ include file="includes/bottom.jspf" %>