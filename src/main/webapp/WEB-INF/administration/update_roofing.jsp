<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Opdater tag</h2>
    </div>
</div>
<div class="row">
    <div class="col 12">
        <a class="btn-large btn-large waves-effect waves-light"
           href="?action=update_components&roofing=${roofing.getType().name()}">
            Opdater component definitioner
        </a>
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
                <input type="text" name="name" id="name" data-length="255" class="input-length validate"
                       value="${roofing.getName()}"
                       required>
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
        <input type="hidden" name="type" value="${roofing.getType().name()}">
        <div class="class row">
            <div class="col s12">
                <p>
                    <input name="active" type="radio" id="active-true" value="true"
                    ${roofing.isActive() ? 'checked' : ''}/>
                    <label for="active-true">Aktiv</label>
                </p>
                <p>
                    <input name="active" type="radio" id="active-false" value="false"
                    ${roofing.isActive() ? '' : 'checked'} />
                    <label for="active-false">Inaktiv</label>
                </p>
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
                    <c:set var="definition" value="${component.getDefinition()}"/>
                    <select name="component_${definition.getIdentifier()}" id="component_${definition.getIdentifier()}">
                        <c:forEach items="${materials.get(definition.getCategory().getId())}" var="material">
                            <option ${component.getMaterial().getId() == material.getId() ? 'selected' : ''}
                                    value="${material.getId()}">${material.getDescription()}</option>
                        </c:forEach>
                    </select>
                    <label for="component_${definition.getIdentifier()}">${definition.getIdentifier()}</label>
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
    </form>
</div>
<%@ include file="../includes/bottom.jspf" %>