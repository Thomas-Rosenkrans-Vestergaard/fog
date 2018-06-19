<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Opret tag</h2>
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
    <form method="post" class="col s12 no-padding">
        ${csrf}
        <div class="row">
            <div class="col s12 input-field">
                <input type="text" name="name" id="name" data-length="255" class="input-length validate" required>
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
                <input name="active" type="radio" id="active-true" value="true"/>
                <label for="active-true">Aktiv</label>
            </div>
            <div class="col s12">
                <input name="active" type="radio" id="active-false" value="false"/>
                <label for="active-false">Inaktiv</label>
            </div>
        </div>
        <input type="hidden" name="type" id="type" value="${type}" readonly>
        <div class="row">
            <div class="col s12">
                <h3>Komponenter</h3>
            </div>
        </div>
        <div class="row">
            <div class="col s12">
                <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla non lectus sed nisl molestie
                    malesuada.Mauris dolor felis, sagittis at, luctus sed, aliquam non, tellus. Vivamus luctus egestas
                    leo. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam.</p>
            </div>
        </div>
        <c:forEach items="${components}" var="definition">
            <div class="row">
                <div class="col s12 input-field">
                    <select ${definition.isMultiple() ? 'multiple' : ''} name="component_${definition.getIdentifier()}"
                                                                         id="component_${definition.getIdentifier()}">
                        <c:forEach items="${materials.get(definition.getCategory().getId())}" var="material">
                            <option value="${material.getId()}">${material.getDescription()}</option>
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