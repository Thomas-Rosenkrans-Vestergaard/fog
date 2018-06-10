function constructInnerTable(table) {
    table = $(table);
    table.wrap("<div class='inner-table-wrap'></div>");
    table.before("<button class='btn inner-table-show'>VIS</button>");
}

function showTable(button) {
    button.siblings('table.inner-table').show();
    button.hide();
}

$(document).ready(function () {
    $('select').material_select();
    $('.input-length').characterCounter();
    $('table.inner-table').each(function (index, element) {
        constructInnerTable(element);
    });
    $('table.inner-table').hide();
    $('button.inner-table-show').on('click', function (e) {
        e.preventDefault();
        showTable($(this));
    });

    $('[data-link]').on('click', function (e) {
        var target = $(e.target)[0].tagName;
        if (target !== 'TD') {
            return;
        }

        location.href = $(this).attr("data-link");
    });

    $('.header-select').on('change', function () {
        console.log($(this).parents('form'));
        $(this).parents('form').submit();
    });
});