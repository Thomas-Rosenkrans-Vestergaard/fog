<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2><span class="focus">OPRET TILBUD</span></h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <form method="post">
            <input type="hidden" name="order" value="${order.getId()}">
            <div class="row">
                <div class="col s12 input-field">
                    <input type="number" name="price" id="price" step="1" min="1">
                    <label for="price">Tilbud pris</label>
                </div>
            </div>
            <div class="row">
                <div class="col s12">
                    <button class="btn-large waves-effect waves-light" type="submit" name="action">
                        Opret<i class="material-icons right">send</i>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>