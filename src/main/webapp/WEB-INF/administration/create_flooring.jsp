<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2><span class="focus">OPRET GULV</span></h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla non lectus sed nisl molestie malesuada.
            Mauris dolor felis, sagittis at, luctus sed, aliquam non, tellus. Vivamus luctus egestas leo. Sed elit dui,
            pellentesque a, faucibus vel, interdum nec, diam.</p>
    </div>
</div>
<div class="row">
    <form method="post" class="col s12">
        ${csrf}
        <div class="row">
            <div class="col s12 input-field">
                <input type="text" name="name" id="name" data-length="255" class="validate" required>
                <label for="name">Navn</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <textarea id="description" name="description" data-length="255" class="materialize-textarea validate"
                          required></textarea>
                <label for="description">Beskrivelse</label>
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
<%@ include file="../includes/bottom.jspf" %>