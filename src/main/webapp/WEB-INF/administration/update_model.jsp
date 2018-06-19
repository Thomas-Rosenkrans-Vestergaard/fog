<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/top.jspf" %>
<div class="row">
    <div class="col s9">
        <h2>OPDATER MODEL</h2>
    </div>
    <div class="col s3" style="margin-top: 20px">
        <a class="btn-large btn-large waves-effect waves-light" href="?action=update_components&model=${model.getId()}">
            Opdater komponent definitioner
        </a>
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
    <form method="post" class="col s12 no-padding">
        ${csrf}
        <div class="row">
            <div class="col s12 input-field">
                <input type="text" name="name" id="name" data-length="255" class="input-length validate"
                       value="${model.getName()}"
                       required>
                <label for="name">Navn</label>
            </div>
        </div>
        <div class="row">
            <div class="col s12">
                <h3>Komponenter</h3>
            </div>
        </div>
        <c:forEach items="${components}" var="component">
            <div class="row">
                <div class="col s12 input-field">
                    <c:set var="definition" value="${component.getDefinition()}"/>
                    <select ${definition.isMultiple() ? 'multiple' : ''} name="component_${definition.getIdentifier()}"
                                                                         id="component_${definition.getIdentifier()}">
                        <c:forEach items="${materials.get(definition.getCategory().getId())}" var="material">
                            <option ${component.contains(material) ? 'selected' : ''}
                                    value="${material.getId()}">${material.getDescription()}</option>
                        </c:forEach>
                    </select>
                    <label for="component_${definition.getIdentifier()}">${definition.getIdentifier()}</label>
                </div>
            </div>
        </c:forEach>
        <div class="row">
            <div class="col s12">
                <button class="btn-large waves-effect waves-light" type="submit" name="action">
                    Opdater<i class="material-icons right">send</i>
                </button>
            </div>
        </div>
    </form>
</div>