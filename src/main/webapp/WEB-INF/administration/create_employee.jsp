<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Opret medarbejder</h2>
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
            <div class="col s12 input-field">
                <input type="text" name="username" id="username" data-length="255" class="validate" required>
                <label for="username">Brugernavn</label>
            </div>
        </div>
        <div class="row">
            <div class="col s12 input-field">
                <input type="password" name="password" id="password" class="validate" minlength="4" required>
                <label for="password">Adgangskode</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <select multiple name="roles">
                    <c:forEach items="${roles}" var="role">
                        <option value="${role.getId()}"><c:out value="${role.name()}"/></option>
                    </c:forEach>
                </select>
                <label>Roller</label>
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
                $('input#name, input#username').characterCounter();
                $('select').material_select();
            });
        </script>
    </form>
</div>
<%@ include file="bot.jspf" %>