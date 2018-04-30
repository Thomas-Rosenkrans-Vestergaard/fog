<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Design carport</h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum
            tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas
            semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo. Quisque sit amet est et sapien
            ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, commodo vitae, ornare sit amet, wisi. Aenean
            fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. Donec
            non enim in turpis pulvinar facilisis. Ut felis. Praesent dapibus, neque id cursus faucibus, tortor neque
            egestas augue, eu vulputate magna eros eu erat. Aliquam erat volutpat.</p>

    </div>
</div>
<div class="row">
    <form class="col s12 no-padding" method="post">
        <div class="row">
            <div class="col s12">
                <h5>Konstruktion</h5>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <select name="claddingId" required>
                    <c:forEach items="${claddings}" var="claddingId">
                        <option value="${claddingId.getId()}"><c:out value="${claddingId.getName()}"/></option>
                    </c:forEach>
                </select>
                <label>Beklædning</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <select id="width" name="width" required>
                    <option value="240">240 cm</option>
                    <option value="270">270 cm</option>
                    <option value="300">300 cm</option>
                    <option value="330">330 cm</option>
                    <option value="360">360 cm</option>
                    <option value="390">390 cm</option>
                    <option value="420">420 cm</option>
                    <option value="450">450 cm</option>
                    <option value="480">480 cm</option>
                    <option value="510">510 cm</option>
                    <option value="540">540 cm</option>
                    <option value="570">570 cm</option>
                    <option value="600">600 cm</option>
                    <option value="630">630 cm</option>
                    <option value="660">660 cm</option>
                    <option value="690">690 cm</option>
                    <option value="720">720 cm</option>
                    <option value="750">750 cm</option>
                </select>
                <label>Bredde</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <select id="length" name="length" required>
                    <option value="240">240 cm</option>
                    <option value="270">270 cm</option>
                    <option value="300">300 cm</option>
                    <option value="330">330 cm</option>
                    <option value="360">360 cm</option>
                    <option value="390">390 cm</option>
                    <option value="420">420 cm</option>
                    <option value="450">450 cm</option>
                    <option value="480">480 cm</option>
                    <option value="510">510 cm</option>
                    <option value="540">540 cm</option>
                    <option value="570">570 cm</option>
                    <option value="600">600 cm</option>
                    <option value="630">630 cm</option>
                    <option value="660">660 cm</option>
                    <option value="690">690 cm</option>
                    <option value="720">720 cm</option>
                    <option value="750">750 cm</option>
                    <option value="780">780 cm</option>
                </select>
                <label>Længde</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <select id="height" name="height" required>
                    <option value="180">180 cm</option>
                    <option value="210">210 cm</option>
                    <option value="240">240 cm</option>
                    <option value="270">270 cm</option>
                    <option value="300">300 cm</option>
                </select>
                <label>Height</label>
            </div>
        </div>
        <div class="row">
            <div class="col s12">
                <h5>Tag</h5>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <select name="roofing" required>
                    <c:forEach items="${roofings}" var="roofing">
                        <option value="${roofing.getId()}"><c:out value="${roofing.getName()}"/></option>
                    </c:forEach>
                </select>
                <label>Tagbeklædning</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input name="slope" id="slope" type="number" step="1" min="0" max="90" class="validate"
                       required>
                <label for="slope">Hældningsgraden</label>
            </div>
        </div>
        <div class="row">
            <div class="col s12">
                <h5>Spær</h5>
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
                <select name="shed-claddingId" class="shed-input" required>
                    <c:forEach items="${claddings}" var="claddingId">
                        <option value="${claddingId.getId()}"><c:out value="${claddingId.getName()}"/></option>
                    </c:forEach>
                </select>
                <label>Beklædning</label>
            </div>
        </div>
        <div class="row">
            <div class="col s12">
                <h5>Kundeoplysninger</h5>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input name="customer-name" id="customer-name" type="text" minlength="5" data-length="255"
                       class="validate" required>
                <label for="customer-name">Navn</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input name="customer-address" id="customer-address" type="text" minlength="10"
                       data-length="255"
                       class="validate" required>
                <label for="customer-address">Address</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input name="customer-email" id="customer-email" type="email" data-length="255" class="validate"
                       required>
                <label for="customer-email">Email</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input name="customer-phone" id="customer-phone" type="tel" data-length="30" class="validate"
                       required>
                <label for="customer-phone">Telefon</label>
            </div>
        </div>
        <div class="row">
            <p>
                <input name="contact-method" type="radio" id="contact-method-telephone" value="PHONE" required/>
                <label for="contact-method-telephone">Telefon</label>
            </p>
            <p>
                <input name="contact-method" type="radio" id="contact-method-email" value="EMAIL"
                       checked="checked"
                       required/>
                <label for="contact-method-email">Email</label>
            </p>
        </div>
        <div class="row">
            <div class="col s12">
                <button class="btn-large waves-effect waves-light" type="submit" name="action">Bestil
                    <i class="material-icons right">send</i>
                </button>
            </div>
        </div>
        <script>
            $(document).ready(function () {

                function updateShedInputs() {
                    if ($('input#shed').is(':checked')) {
                        $('input#shed').attr('value', 'true');
                        $('.shed-rows').show(500);
                        $('.shed-input').attr('required', 'required');
                    } else {
                        $('ïnput#shed').attr('value', 'false');
                        $('.shed-rows').hide(500);
                        $('.shed-input').removeAttr('required');
                    }
                }

                updateShedInputs();
                $('select').material_select();
                $('input#customer-name, input#customer-mail, input#customer-telephone').characterCounter();
                $('input#shed').on('change', function (input) {
                    updateShedInputs();
                });
            });
        </script>
    </form>
</div>
<%@ include file="includes/bottom.jspf" %>