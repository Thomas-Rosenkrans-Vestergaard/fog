<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2><span class="focus">${flooringId.getName()}</span></h2>
    </div>
</div>
<div class="row">
    <form method="post" class="col s12">
        <input type="hidden" value="${flooringId.getId()}" name="id">
        <div class="row">
            <div class="col s12 input-field">
                <input type="text" name="name" id="name" data-length="255" class="validate"
                       value="${flooringId.getName()}" required>
                <label for="name">Navn</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <textarea id="description" name="description" class="materialize-textarea validate"
                          required>${flooringId.getDescription()}</textarea>
                <label for="description">Beskrivelse</label>
            </div>
        </div>
        <div class="row">
            <div class="col s12 input-field">
                <input type="number" name="price" id="price" min="1" class="validate"
                       value="${flooringId.getPricePerSquareMeter()}" required>
                <label for="price">Pris (m<sup>2</sup>)</label>
            </div>
        </div>
        <div class="class row">
            <div class="col s12">
                <p>
                    <input name="active" type="radio" id="active-true" value="true"
                    ${flooringId.isActive() ? 'checked' : ''}/>
                    <label for="active-true">Aktiv</label>
                </p>
                <p>
                    <input name="active" type="radio" id="active-false" value="false"
                    ${flooringId.isActive() ? '' : 'checked'}/>
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