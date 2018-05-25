<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2>Konstruer carport</h2>
    </div>
</div>
<div class="row">
    <form class="col s12 no-padding" method="get">
        ${csrf}
        <div class="row">
            <div class="col s12">
                <h5>Konstruktion</h5>
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
            <div class="input-field col s12">
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
                <button class="btn-large waves-effect waves-light" type="submit" name="submit">Konstruer
                    <i class="material-icons right">send</i>
                </button>
            </div>
        </div>
    </form>
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
        $('input#shed').on('change', function (input) {
            updateShedInputs();
        });
    });
</script>
<%@ include file="../includes/bottom.jspf" %>