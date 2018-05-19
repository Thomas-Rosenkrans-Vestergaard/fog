<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2><span class="focus">OPRET MATERIALE</span></h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <form method="post">
            ${csrf}
            <input type="hidden" name="category" value="${category}">
            <div class="row">
                <div class="col s12 input-field">
                    <input type="text" name="number" id="number" data-length="12" class="input-length validate"
                           required>
                    <label for="number">Nummer</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                <textarea id="description" name="description" data-length="255"
                          class="materialize-textarea input-length validate"
                          required></textarea>
                    <label for="description">Beskrivelse</label>
                </div>
            </div>
            <div class="row">
                <div class="col s12 input-field">
                    <input type="number" name="price" id="price" min="0" step=".01" class="validate" required>
                    <label for="price">Pris</label>
                </div>
            </div>
            <div class="row">
                <div class="col s12 input-field">
                    <input type="number" name="unit" id="unit" min="0" class="validate" required>
                    <label for="unit">Enhed</label>
                </div>
            </div>
            <c:if test="${not attributes.isEmpty()}">
                <div class="row">
                    <div class="col s12">
                        <h2>Attributter</h2>
                    </div>
                </div>
                <c:forEach items="${attributes}" var="attribute">
                    <div class="row">
                        <div class="col s12 input-field">
                            <input type="text" name="attribute_${attribute.getName()}"
                                   id="attribute_${attribute.getName()}" required>
                            <label for="attribute_${attribute.getName()}">${attribute.getName()}</label>
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
        </form>
    </div>
</div>
<%@ include file="../includes/bottom.jspf" %>