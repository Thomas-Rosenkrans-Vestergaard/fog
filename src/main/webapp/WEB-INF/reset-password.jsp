<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Gensæt adgangskode</h2>
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
    <div class="col s12 no-padding">
        ${csrf}
        <form method="post">
            <div class="row">
                ${csrf}
                <input type="hidden" name="tokenId" value="${id}">
                <input type="hidden" name="tokenSecret" value="${token}">
                <div class="input-field col s12 l10">
                    <input type="password" name="password" id="password" required>
                    <label for="password">Nye adgangskode</label>
                </div>
                <div class="col s12 l2">
                    <button class="btn-large waves-effect waves-light" type="submit" name="action">OPDATER
                        <i class="material-icons right">send</i>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<%@ include file="includes/bottom.jspf" %>