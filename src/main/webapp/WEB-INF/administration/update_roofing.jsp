<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2><span class="focus">${roofing.getName()}</span></h2>
    </div>
</div>
<div class="row">
    <form method="post" class="col s12">
        <input type="hidden" value="${roofing.getId()}" name="id">
        <div class="row">
            <div class="col s12 input-field">
                <input type="text" name="name" id="name" data-length="255" class="validate"
                       value="${roofing.getName()}" required>
                <label for="name">Navn</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <textarea id="description" name="description" class="materialize-textarea validate"
                          required>${roofing.getDescription()}</textarea>
                <label for="description">Beskrivelse</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input type="number" step="1" name="minimum-slope" min="1" max="89" id="minimum-slope"
                       value="${roofing.getMinimumSlope()}">
                <label for="minimum-slope">Mindste hældning</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input type="number" step="1" name="maximum-slope" min="1" max="89" id="maximum-slope"
                       value="${roofing.getMaximumSlope()}">
                <label for="maximum-slope">Største hældning</label>
            </div>
        </div>
        <div class="class row">
            <div class="col s12">
                <p>
                    <input name="active" type="radio" id="active-true" value="true"
                    ${roofing.isActive() ? 'checked' : ''}/>
                    <label for="active-true">Aktiv</label>
                </p>
                <p>
                    <input name="active" type="radio" id="active-false" value="false"
                    ${roofing.isActive() ? '' : 'checked'}/>
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
                $('input#name').characterCounter();
            });
        </script>
    </form>
</div>
<%@ include file="bot.jspf" %>