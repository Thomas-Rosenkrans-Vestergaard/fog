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
            <div class="input-field col s12">
                <input id="height" type="text" class="validate">
                <label for="height">Height</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input disabled value="I am not editable" id="disabled" type="text" class="validate">
                <label for="disabled">Disabled</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input id="password" type="password" class="validate">
                <label for="password">Password</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input id="email" type="email" class="validate">
                <label for="email">Email</label>
            </div>
        </div>
        <div class="row">
            <div class="col s12">
                This is an inline input field:
                <div class="input-field inline">
                    <input id="email" type="email" class="validate">
                    <label for="email" data-error="wrong" data-success="right">Email</label>
                </div>
            </div>
        </div>
    </form>
</div>
<%@ include file="includes/bottom.jspf" %>