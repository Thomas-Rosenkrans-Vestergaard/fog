<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2><span class="focus">${roofing.getName()}</span></h2>
    </div>
</div>
<div class="row">
    <form method="post" class="col s12">
        <div class="row">
            <div class="col s12 input-field">
                <input type="text" name="name" id="name" data-length="255" class="validate" required>
                <label for="name">Navn</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <textarea id="description" name="description" class="materialize-textarea validate" required></textarea>
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
            <div class="col s12 input-field">
                <input type="text" name="type" id="type" value="${type}" readonly>
                <label for="type">Tagtype</label>
            </div>
        </div>
        <div class="row">
            <div class="col s12">
                <h3>Components</h3>
            </div>
        </div>
        <c:forEach items="${components}" var="component">
            <div class="row">
                <div class="col s12 input-field">
                    <select name="component_${component.getIdentifier()}" id="component_${component.getIdentifier()}">
                        <c:forEach items="${materials.get(component.getId())}" var="material">
                            <option value="${material.getId()}">${material.getDescription()}</option>
                        </c:forEach>
                    </select>
                    <label for="component_${component.getIdentifier()}">${component.getIdentifier()}</label>
                </div>
            </div>
        </c:forEach>
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
                $('select').material_select();
            });
        </script>
    </form>
</div>
<%@ include file="../includes/bottom.jspf" %>