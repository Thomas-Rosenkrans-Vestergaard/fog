<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2><span class="focus">OPDATER MATERIALE</span></h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <form method="post">
            <input type="hidden" name="category" value="${material.getCategoryId()}">
            <div class="row">
                <div class="col s12 input-field">
                    <input type="text" name="number" id="number" data-length="12" class="validate"
                           value="${material.getNumber()}" required>
                    <label for="number">Nummer</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                <textarea id="description" name="description" data-length="255" class="materialize-textarea validate"
                          required>${material.getDescription()}</textarea>
                    <label for="description">Beskrivelse</label>
                </div>
            </div>
            <div class="row">
                <div class="col s12 input-field">
                    <input type="number" name="price" id="price" min="0" class="validate"
                           value="${material.getPrice()}" required>
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
            <c:if test="${not attributes.isEmpty()}">
                <div class="row">
                    <div class="col s12">
                        <h2>Attributter</h2>
                    </div>
                </div>
                <c:forEach items="${material.getAttributes()}" var="attribute">
                    <div class="row">
                        <div class="col s12 input-field">
                            <c:set var="definition" value="${attribute.getDefinition()}"/>
                            <input type="text" name="attribute_${definition.getName()}"
                                   id="attribute_${definition.getName()}" value="${attribute.getValue()}"
                                   required>
                            <label for="attribute_${definition.getName()}">${definition.getName()}</label>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
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
</div>
<%@ include file="bot.jspf" %>