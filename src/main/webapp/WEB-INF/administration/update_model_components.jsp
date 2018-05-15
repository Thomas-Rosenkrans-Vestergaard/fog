<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2><span class="focus">OPDATER MODEL COMPONENTER</span></h2>
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
        <c:forEach items="${definitions}" var="definition">
            <div class="row">
                <div class="col s12 input-field">

                    <input type="text" class="component_notes" data-length="255"
                           name="component_notes_${definition.getIdentifier()}"
                           id="component_${definition.getIdentifier()}"
                           value="${definition.getNotes()}">
                    </input>
                    <label for="component_${definition.getIdentifier()}">${definition.getIdentifier()}</label>
                </div>
            </div>
        </c:forEach>
        <div class="row">
            <div class="col s12">
                <button class="btn-large waves-effect waves-light" type="submit" name="action">
                    Opdater<i class="material-icons right">send</i>
                </button>
            </div>
        </div>
        <script>
            $(document).ready(function () {
                $('select').material_select();
            });
        </script>
    </form>
</div>