<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2><span class="focus">${employee.getName()}</span></h2>
    </div>
</div>
<div class="row">
    <form method="post" class="col s12">
        <input type="hidden" value="${employee.getId()}" name="id">
        <div class="row">
            <div class="col s12 input-field">
                <input type="text" name="name" id="name" data-length="255" class="validate"
                       value="${employee.getName()}" required>
                <label for="name">Navn</label>
            </div>
        </div>
        <div class="row">
            <div class="col s12 input-field">
                <input type="text" name="username" id="username" data-length="255" class="validate"
                       value="${employee.getUsername()}" required>
                <label for="username">Username</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <select multiple name="roles">
                    <c:forEach items="${roles}" var="role">
                        <option value="${role.name()}" ${employee.is(role) ? 'selected' : ''}><c:out
                                value="${role.name()}"/></option>
                    </c:forEach>
                </select>
                <label>Roller</label>
            </div>
        </div>
        <div class="row">
            <div class="col s8 input-field">
                <input type="password" name="password" id="password" class="validate" minlength="4">
                <label for="password">Adgangskode</label>
            </div>
            <div class="col s4">
                <p>
                <div class="switch">
                    <label>
                        Off
                        <input type="checkbox" id="update-password">
                        <span class="lever"></span>
                        On
                    </label>
                </div>
                </p>
                <script>

                    function updatePasswordInput() {
                        var input = $('input#password');
                        if ($(this).prop('checked'))
                            input.removeAttr('disabled');
                        else
                            input.attr('disabled', 'disabled');
                    }

                    $("input#update-password").on('change', updatePasswordInput);
                    updatePasswordInput();
                </script>
            </div>
        </div>
        <div class="class row">
            <div class="col s12">
                <p>
                    <input name="active" type="radio" id="active-true" value="true"
                    ${employee.isActive() ? 'checked' : ''}/>
                    <label for="active-true">Aktiv</label>
                </p>
                <p>
                    <input name="active" type="radio" id="active-false" value="false"
                    ${employee.isActive() ? '' : 'checked'}/>
                    <label for="active-false">Inaktiv</label>
                </p>
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
                $('select').material_select();
                $('input#name, input#address, input#email, input#phone').characterCounter();
            });
        </script>
    </form>
</div>
<%@ include file="../includes/bottom.jspf" %>