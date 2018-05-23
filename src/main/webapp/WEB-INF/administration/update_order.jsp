<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s12">
        <h2><span class="focus">Opdatér ordre</span></h2>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <form method="post" action="orders">
            ${csrf}
            <input type="hidden" name="id" value="${order.getId()}">
            <button class="btn-large" type="submit" name="action" value="cancel"
            ${order.isActive() ? '' : 'disabled'}><i class="material-icons right">clear</i>Aflys
            </button>
        </form>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla non lectus sed nisl molestie malesuada.
            Mauris dolor felis, sagittis at, luctus sed, aliquam non, tellus. Vivamus luctus egestas leo. Sed elit dui,
            pellentesque a, faucibus vel, interdum nec, diam. Itaque earum rerum hic tenetur a sapiente delectus, ut aut
            reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.</p>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <h2>Tilbud</h2>
    </div>
</div>
<div class="row">
    <div class="col 12">
        <a href="offers?action=create&order=${order.getId()}" ${order.isActive() ? '' : 'disabled'} class="btn-large">
            <i class="material-icons right">add</i>OPRET TILBUD
        </a>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <table class="bordered highlight">
            <thead>
            <tr>
                <th>Tilbudspris</th>
                <th>Medarbejder</th>
                <th>Status</th>
                <th>Oprettet</th>
            </tr>
            <%@ include file="../includes/table_filters.jspf" %>
            </thead>
            <tbody>
            <c:forEach items="${offers}" var="offer">
                <tr data-link="offer?id=${offer.getId()}">
                    <td>${f:formatPrice(offer.getPrice())}</td>
                    <td>${offer.getEmployee().getName()}</td>
                    <td>${offer.getStatus()}</td>
                    <td>${f:formatDatetime(offer.getCreatedAt())}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty offers}">
                <tr>
                    <td colspan="4">Ingen tilbud for denne ordre.</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>
<div class="row">
    <div class="col s12">
        <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla non lectus sed nisl molestie malesuada.
            Mauris dolor felis, sagittis at, luctus sed, aliquam non, tellus. Vivamus luctus egestas leo.</p>
    </div>
</div>
<div class="row">
    <form method="post" class="col s12 no-padding">
        ${csrf}
        <input type="hidden" value="${order.getId()}" name="id">
        <div class="row">
            <div class="input-field col s12">
                <select id="width" name="width" required>
                    <option ${order.getWidth() == 240 ? 'selected' : ''} value="240">240 cm</option>
                    <option ${order.getWidth() == 270 ? 'selected' : ''} value="270">270 cm</option>
                    <option ${order.getWidth() == 300 ? 'selected' : ''} value="300">300 cm</option>
                    <option ${order.getWidth() == 330 ? 'selected' : ''} value="330">330 cm</option>
                    <option ${order.getWidth() == 360 ? 'selected' : ''} value="360">360 cm</option>
                    <option ${order.getWidth() == 390 ? 'selected' : ''} value="390">390 cm</option>
                    <option ${order.getWidth() == 420 ? 'selected' : ''} value="420">420 cm</option>
                    <option ${order.getWidth() == 450 ? 'selected' : ''} value="450">450 cm</option>
                    <option ${order.getWidth() == 480 ? 'selected' : ''} value="480">480 cm</option>
                    <option ${order.getWidth() == 510 ? 'selected' : ''} value="510">510 cm</option>
                    <option ${order.getWidth() == 540 ? 'selected' : ''} value="540">540 cm</option>
                    <option ${order.getWidth() == 570 ? 'selected' : ''} value="570">570 cm</option>
                    <option ${order.getWidth() == 600 ? 'selected' : ''} value="600">600 cm</option>
                    <option ${order.getWidth() == 630 ? 'selected' : ''} value="630">630 cm</option>
                    <option ${order.getWidth() == 660 ? 'selected' : ''} value="660">660 cm</option>
                    <option ${order.getWidth() == 690 ? 'selected' : ''} value="690">690 cm</option>
                    <option ${order.getWidth() == 720 ? 'selected' : ''} value="720">720 cm</option>
                    <option ${order.getWidth() == 750 ? 'selected' : ''} value="750">750 cm</option>
                </select>
                <label>Bredde</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <select id="length" name="length" required>
                    <option ${order.getLength() == 240 ? 'selected' : ''} value="240">240 cm</option>
                    <option ${order.getLength() == 270 ? 'selected' : ''} value="270">270 cm</option>
                    <option ${order.getLength() == 300 ? 'selected' : ''} value="300">300 cm</option>
                    <option ${order.getLength() == 330 ? 'selected' : ''} value="330">330 cm</option>
                    <option ${order.getLength() == 360 ? 'selected' : ''} value="360">360 cm</option>
                    <option ${order.getLength() == 390 ? 'selected' : ''} value="390">390 cm</option>
                    <option ${order.getLength() == 420 ? 'selected' : ''} value="420">420 cm</option>
                    <option ${order.getLength() == 450 ? 'selected' : ''} value="450">450 cm</option>
                    <option ${order.getLength() == 480 ? 'selected' : ''} value="480">480 cm</option>
                    <option ${order.getLength() == 510 ? 'selected' : ''} value="510">510 cm</option>
                    <option ${order.getLength() == 540 ? 'selected' : ''} value="540">540 cm</option>
                    <option ${order.getLength() == 570 ? 'selected' : ''} value="570">570 cm</option>
                    <option ${order.getLength() == 600 ? 'selected' : ''} value="600">600 cm</option>
                    <option ${order.getLength() == 630 ? 'selected' : ''} value="630">630 cm</option>
                    <option ${order.getLength() == 660 ? 'selected' : ''} value="660">660 cm</option>
                    <option ${order.getLength() == 690 ? 'selected' : ''} value="690">690 cm</option>
                    <option ${order.getLength() == 720 ? 'selected' : ''} value="720">720 cm</option>
                    <option ${order.getLength() == 750 ? 'selected' : ''} value="750">750 cm</option>
                    <option ${order.getLength() == 780 ? 'selected' : ''} value="780">780 cm</option>
                </select>
                <label>Længde</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <select id="height" name="height" required>
                    <option ${order.getHeight() == 180 ? 'selected' : ''} value="180">180 cm</option>
                    <option ${order.getHeight() == 210 ? 'selected' : ''} value="210">210 cm</option>
                    <option ${order.getHeight() == 240 ? 'selected' : ''} value="240">240 cm</option>
                    <option ${order.getHeight() == 270 ? 'selected' : ''} value="270">270 cm</option>
                    <option ${order.getHeight() == 300 ? 'selected' : ''} value="300">300 cm</option>
                </select>
                <label>Height</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <select name="roofing">
                    <c:forEach items="${roofings}" var="roofing">
                        <option value="${roofing.getId()}" ${roofing.getId() == order.getRoofing().getId() ?
                                'selected' : ''}><c:out value="${roofing.getName()}"/></option>
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
        <div class="class row">
            <div class="col s12">
                <input name="active" type="radio" id="active-true" value="true"
                ${order.isActive() ? 'checked' : ''}/>
                <label for="active-true">Aktiv</label>
            </div>
            <div class="col 12">
                <input name="active" type="radio" id="active-false" value="false"
                ${order.isActive() ? '' : 'checked'}/>
                <label for="active-false">Inaktiv</label>
            </div>
        </div>
        <div class="row">
            <div class="col s12">
                <h5>Redskabsskur</h5>
            </div>
        </div>
        <c:set var="shed" value="${order.getShed()}"/>
        <input name="shed-action" type="hidden" value="${shed == null ? 'create' : 'update'}">
        <input name="shed-id" type="hidden" value="${shed.getId()}">
        <div class="row">
            <div class="col s12">
                <div class="switch">
                    <label>
                        <input type="checkbox" name="shed" id="shed" value="true" ${shed != null ? 'checked' : ''}>
                        <span class="lever"></span>
                    </label>
                </div>
            </div>
        </div>
        <div class="row shed-rows">
            <div class="input-field col s12">
                <input name="shed-depth" id="shed-depth" type="number" step="1" min="0"
                       class="validate shed-input"
                       value="${shed.getDepth()}"
                       required>
                <label for="shed-depth">Dybde</label>
            </div>
        </div>
        <div class="row shed-rows">
            <div class="input-field col s12 m6">
                <select name="shed-flooring" class="shed-input" required>
                    <c:forEach items="${floorings}" var="flooring">
                        <option ${shed.getFlooring().getId() == flooring.getId() ? 'selected' : ''}
                                value="${flooring.getId()}"><c:out value="${flooring.getName()}"/></option>
                    </c:forEach>
                </select>
                <label>Gulv</label>
            </div>
            <div class="input-field col s12 m6">
                <select name="shed-cladding" class="shed-input" required>
                    <c:forEach items="${claddings}" var="cladding">
                        <option ${shed.getCladding().getId() == cladding.getId() ? 'selected' : ''}
                                value="${cladding.getId()}"><c:out value="${cladding.getName()}"/></option>
                    </c:forEach>
                </select>
                <label>Beklædning</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <textarea id="comment" name="comment" class="materialize-textarea">${order.getComment()}</textarea>
                <label for="comment">Kommentar</label>
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
                $('input#shed').on('change', updateShedInputs);
            });
        </script>
    </form>
</div>
<%@ include file="../includes/bottom.jspf" %>