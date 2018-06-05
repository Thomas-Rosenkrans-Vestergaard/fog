<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>OPDATER MATERIALE</h2>
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
    <div class="col s12 no-padding">
        <form method="post">
            ${csrf}
            <input type="hidden" name="category" value="${material.getCategoryId()}">
            <div class="row">
                <div class="col s12 input-field">
                    <input type="text" name="number" id="number" data-length="12" class="input-length"
                           value="${material.getNumber()}" readonly>
                    <label for="number">Nummer</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                <textarea id="description" name="description" data-length="255"
                          class="materialize-textarea input-length validate"
                          required>${material.getDescription()}</textarea>
                    <label for="description">Beskrivelse</label>
                </div>
            </div>
            <div class="row">
                <div class="col s12 input-field">
                    <input type="number" name="price" id="price" min="0" pattern="^[0-9]+([\,][0-9]{0,2})?$"
                           class="validate price-input"
                           value="${f:toDouble(material.getPrice())}" required>
                    <label for="price">Pris</label>
                </div>
            </div>
            <div class="row">
                <div class="col s12 input-field">
                    <input type="number" name="unit" id="unit" min="0" class="validate"
                           value="${material.getUnit()}" required>
                    <label for="unit">Enhed</label>
                </div>
            </div>
                <c:set var="attributes" value="${material.getAttributes()}"/>
                <c:if test="${!attributes.isEmpty()}">
                <div class="row">
                    <div class="col s12">
                        <h2>Attributter</h2>
                    </div>
                </div>
                <c:forEach items="${attributes}" var="attribute">
                    <div class="row">
                        <div class="col s12 input-field">
                            <c:set var="definition" value="${attribute.getDefinition()}"/>
                            <input type="text" name="attribute_${definition.getName()}"
                                   id="attribute_${definition.getName()}"
                                   value="${attribute.isNull() ? '' : attribute.getValue()}"
                                   required>
                            <label for="attribute_${definition.getName()}">${definition.getName()}</label>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <div class="row">
                <div class="col s12">
                    <button class="btn-large waves-effect waves-light" type="submit" name="action">
                        Opdater<i class="material-icons right">send</i>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>