<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2><span class="focus">UPDATE ORDER</span></h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <a href="offers?action=create&order=${order.getId()}" class="waves-effect waves-light btn-large">
            <i class="material-icons right">add</i>OPRET TILBUD
        </a>
    </div>
</div>
<div class="row">
    <form method="post" class="col s12">
        <input type="hidden" value="${order.getId()}" name="id">
        <div class="row">
            <div class="input-field col s12">
                <select multiple name="cladding">
                    <c:forEach items="${claddings}" var="cladding">
                        <option value="${cladding.getId()}"
                            ${cladding.getId() == order.getCladding().getId() ? 'selected' : ''}>
                            <c:out value="${cladding.getName()}"/></option>
                    </c:forEach>
                </select>
                <label>Beklædninger</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input type="number" name="width" id="width" min="1" class="validate" value="${order.getWidth()}"
                       required>
                <label for="width">Bredde</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input type="number" name="length" id="length" min="1" class="validate" value="${order.getLength()}"
                       required>
                <label for="length">Længde</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input type="number" name="height" id="height" min="1" class="validate" value="${order.getHeight()}"
                       required>
                <label for="height">Højde</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <select multiple name="roofing">
                    <c:forEach items="${roofings}" var="roofing">
                        <option value="${roofing.getId()}"
                            ${roofing.getId() == order.getRoofing().getId() ? 'selected' : ''}>
                            <c:out value="${roofing.getName()}"/></option>
                    </c:forEach>
                </select>
                <label>Tag</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input type="number" name="slope" id="slope" min="1" class="validate" value="${order.getSlope()}"
                       required>
                <label for="slope">Taghældning</label>
            </div>
        </div>
        <div class="row">
            <p>
                <input name="rafters" type="radio" id="rafters-premade" value="PREBUILT" checked="checked" required/>
                <label for="rafters-premade">Færdiglavet spær</label>
            </p>
            <p>
                <input name="rafters" type="radio" id="rafters-self" value="BUILD_SELF" required/>
                <label for="rafters-self">Byg selv spær</label>
            </p>
        </div>
        <div class="class row">
            <div class="col s12">
                <p>
                    <input name="active" type="radio" id="active-true" value="true"
                    ${order.isActive() ? 'checked' : ''}/>
                    <label for="active-true">Aktiv</label>
                </p>
                <p>
                    <input name="active" type="radio" id="active-false" value="false"
                    ${order.isActive() ? '' : 'checked'}/>
                    <label for="active-false">Inaktiv</label>
                </p>
            </div>
        </div>
        <div class="row">
            <div class="col s12">
                <h5>Redskabsskur</h5>
            </div>
        </div>
        <div class="row">
            <div class="col s12">
                <div class="switch">
                    <label>
                        <input type="checkbox" name="shed" id="shed" value="true">
                        <span class="lever"></span>
                    </label>
                </div>
            </div>
        </div>
        <div class="row shed-rows">
            <div class="input-field col s12 m6">
                <input name="shed-width" id="shed-width" type="number" step="1" min="0"
                       class="validate shed-input"
                       required>
                <label for="shed-width">Bredde</label>
            </div>
            <div class="input-field col s12 m6">
                <input name="shed-depth" id="shed-depth" type="number" step="1" min="0"
                       class="validate shed-input"
                       required>
                <label for="shed-depth">Dybde</label>
            </div>
        </div>
        <div class="row shed-rows">
            <div class="input-field col s12 m6">
                <select name="shed-flooring" class="shed-input" required>
                    <c:forEach items="${floorings}" var="flooring">
                        <option value="${flooring.getId()}"><c:out value="${flooring.getName()}"/></option>
                    </c:forEach>
                </select>
                <label>Gulv</label>
            </div>
            <div class="input-field col s12 m6">
                <select name="shed-cladding" class="shed-input" required>
                    <c:forEach items="${claddings}" var="cladding">
                        <option value="${cladding.getId()}"><c:out value="${cladding.getName()}"/></option>
                    </c:forEach>
                </select>
                <label>Beklædning</label>
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