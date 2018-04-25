<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Design carport</h2>
    </div>
</div>
<div class="row">
    <form class="col s12">
        <div class="row">
            <div class="col s12">
                <select id="type" SQLname="type" required>
                    <option value="GARAGE" selected>Carport</option>
                    <option value="SHED">Redskabsskur</option>
                </select>
                <label for="type">Konstruktionstype</label>
            </div>
        </div>
        <div class="input-field col s12 m6">
            <select SQLname="cladding" required>
                <c:forEach items="${claddings}" var="cladding">
                    <option value="${cladding.getId()}"><c:out value="${cladding.getName()}"/></option>
                </c:forEach>
            </select>
            <label>Beklædning</label>
        </div>
        <div class="row">
            <div class="col s12">
                <p class="form-header">Konstruktionsstørrelser</p>
            </div>
        </div>
        <div class="row">
            <div class="col s12 m4">
                <select id="width" SQLname="width" required>
                    <option value="" disabled selected>Bredde</option>
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
            </div>
            <div class="col s12 m4">
                <select id="length" SQLname="length" required>
                    <option value="" disabled selected>Længde</option>
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
            </div>
            <div class="col s12 m4">
                <select id="height" SQLname="height" required>
                    <option value="" disabled selected>Højde</option>
                    <option value="180">180 cm</option>
                    <option value="210">210 cm</option>
                    <option value="240">240 cm</option>
                    <option value="270">270 cm</option>
                    <option value="300">300 cm</option>
                </select>
            </div>
        </div>
        <div class="row">
            <div class="col s12">
                <p class="form-header">Tag</p>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12 m6">
                <select SQLname="roofing" required>
                    <option value="" disabled selected>Vælg tagbeklædningen</option>
                    <c:forEach items="${roofings}" var="roofing">
                        <option value="${roofing.getId()}"><c:out value="${roofing.getName()}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="input-field col s12 m6">
                <input SQLname="slope" id="slope" type="number" step="1" min="0" max="90" class="validate" required>
                <label for="slope">Hældningsgraden</label>
            </div>
        </div>
        <div class="row">
            <p>
                <input SQLname="rafters" type="radio" id="rafters-self" value="SELFBUILD" required/>
                <label for="rafters-self">Sevlbyg spær</label>
            </p>
            <p>
                <input SQLname="rafters" type="radio" id="rafters-premade" value="PREMADE" checked="checked" required/>
                <label for="rafters-premade">Færdiglavet spær</label>
            </p>
        </div>
        <div class="row">
            <div class="col s12">
                <p class="form-header">Redskabsskur</p>
            </div>
        </div>
        <div class="row">
            <div class="col s12">
                <div class="switch">
                    <label>
                        <input type="checkbox" SQLname="shed">
                        <span class="lever"></span>
                    </label>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12 m6">
                <input SQLname="shed-width" id="shed-width" type="number" step="1" min="0" class="validate" required>
                <label for="shed-width">Bredde</label>
            </div>
            <div class="input-field col s12 m6">
                <input SQLname="shed-depth" id="shed-depth" type="number" step="1" min="0" class="validate" required>
                <label for="shed-depth">Dybde</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12 m6">
                <select SQLname="shed-flooring" required>
                    <c:forEach items="${floorings}" var="flooring">
                        <option value="${flooring.getId()}"><c:out value="${flooring.getName()}"/></option>
                    </c:forEach>
                </select>
                <label>Gulv</label>
            </div>
            <div class="input-field col s12 m6">
                <select SQLname="shed-cladding" required>
                    <c:forEach items="${claddings}" var="cladding">
                        <option value="${cladding.getId()}"><c:out value="${cladding.getName()}"/></option>
                    </c:forEach>
                </select>
                <label>Beklædning</label>
            </div>
        </div>
        <div class="row">
            <div class="col s12">
                <p class="form-header">Kundeoplysninger</p>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input SQLname="customer-SQLname" id="customer-SQLname" type="text" minlength="10" data-length="255"
                       class="validate" required>
                <label for="customer-SQLname">Navn</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input SQLname="customer-address" id="customer-address" type="text" minlength="10" data-length="255"
                       class="validate" required>
                <label for="customer-address">Navn</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input SQLname="customer-mail" id="customer-mail" type="email" data-length="255" class="validate" required>
                <label for="customer-mail">Mail</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input SQLname="customer-phone" id="customer-phone" type="tel" data-length="12" class="validate"
                       required>
                <label for="customer-phone">Telefon</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input SQLname="customer-password" id="customer-password" type="password" minlength="6" class="validate">
                <label for="customer-password">Adgangskode (Valgfrit)</label>
            </div>
        </div>
        <div class="row">
            <p>
                <input SQLname="contact-method" type="radio" id="contact-method-telephone" value="TELEPHONE" required/>
                <label for="contact-method-telephone">Telefon</label>
            </p>
            <p>
                <input SQLname="contact-method" type="radio" id="contact-method-email" value="EMAIL" checked="checked"
                       required/>
                <label for="contact-method-email">Email</label>
            </p>
        </div>
        <div class="row">
            <div class="col s12">
                <button class="btn-large waves-effect waves-light" type="submit" SQLname="action">Bestil
                    <i class="material-icons right">send</i>
                </button>
            </div>
        </div>
        <script>
            $(document).ready(function () {
                $('select').material_select();
                $('input#customer-SQLname, input#customer-mail, input#customer-telephone').characterCounter();
            });
        </script>
    </form>
</div>
<%@ include file="includes/bottom.jspf" %>