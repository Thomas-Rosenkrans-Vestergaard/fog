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
            <div class="col s4">
                <select id="type" name="type">
                    <option value="GARAGE" selected>Carport</option>
                    <option value="SHED">Redskabsskur</option>
                </select>
                <label for="type">Konstruktionstype</label>
            </div>
        </div>
        <div class="row">
            <div class="col s12">
                <h3>Konstruktionsstørrelser</h3>
            </div>
        </div>
        <div class="row">
            <div class="col s4">
                <select id="width" name="width">
                    <option value="" disabled selected>Choose your option</option>
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
                <label for="width">Bredde</label>
            </div>
            <div class="col s4">
                <select id="length" name="length">
                    <option value="" disabled selected>Choose your option</option>
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
                <label for="length">Længde</label>
            </div>
            <div class="col s4">
                <select id="height" name="height">
                    <option value="" disabled selected>Choose your option</option>
                    <option value="180">180 cm</option>
                    <option value="210">210 cm</option>
                    <option value="240">240 cm</option>
                    <option value="270">270 cm</option>
                    <option value="300">300 cm</option>
                </select>
                <label for="length">Højde</label>
            </div>
        </div>

    </form>
</div>
<%@ include file="includes/bottom.jspf" %>