<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2><span class="focus">OPRET MATERIALE</span></h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <form method="post">
            <div class="row">
                <div class="col s12 input-field">
                    <input type="text" name="number" id="number" data-length="12" class="validate" required>
                    <label for="number">Nummer</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                <textarea id="description" name="description" data-length="255" class="materialize-textarea validate"
                          required></textarea>
                    <label for="description">Beskrivelse</label>
                </div>
            </div>
            <div class="row">
                <div class="row">
                    <div class="input-field col s12">
                        <textarea id="notes" name="notes" class="materialize-textarea"></textarea>
                        <label for="notes">Noter</label>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col s12 input-field">
                    <input type="number" name="width" id="width" min="1" class="validate" required>
                    <label for="width">Bredde</label>
                </div>
            </div>
            <div class="row">
                <div class="col s12 input-field">
                    <input type="number" name="height" id="height" min="1" class="validate" required>
                    <label for="height">Højde</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <select name="usage">
                        <c:forEach items="${usages}" var="usage">
                            <option value="${usage.getId()}"><c:out value="${usage.name()}"/></option>
                        </c:forEach>
                    </select>
                    <label>Benyttelse</label>
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
                    $('select').material_select();
                });
            </script>
        </form>
    </div>
</div>
<%@ include file="bot.jspf" %>