<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<div class="row">
    <div class="col s12">
        <h2><span class="focus">${cladding.getName()}</span></h2>
    </div>
</div>
<div class="row">
    <form method="post" class="col s12">
        ${csrf}
        <input type="hidden" value="${cladding.getId()}" name="id">
        <div class="row">
            <div class="col s12 input-field">
                <input type="text" name="name" id="name" data-length="255" class="validate"
                       value="${cladding.getName()}" required>
                <label for="name">Navn</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <textarea id="description" name="description" class="materialize-textarea validate"
                          required>${cladding.getDescription()}</textarea>
                <label for="description">Beskrivelse</label>
            </div>
        </div>
        <div class="class row">
            <div class="col s12">
                <p>
                    <input name="active" type="radio" id="active-true" value="true"
                    ${cladding.isActive() ? 'checked' : ''}/>
                    <label for="active-true">Aktiv</label>
                </p>
                <p>
                    <input name="active" type="radio" id="active-false" value="false"
                    ${cladding.isActive() ? '' : 'checked'}/>
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
<%@ include file="../includes/bottom.jspf" %>