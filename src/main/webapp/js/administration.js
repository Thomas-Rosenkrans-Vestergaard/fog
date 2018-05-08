function updateNewOrdersCounter() {

    $.get({
        method: 'GET',
        url: '../api?action=getNumberOfNewOrders',
        success: function (data) {
            $('#new-orders-counter').text = data['orders'];
            if (data['orders'] == 0)
                $('#new-orders-counter').hide();
            else
                $('#new-orders-counter').show();
        }
    });
}

$(document).ready(function () {
    updateNewOrdersCounter();
    setInterval(updateNewOrdersCounter, 60 * 1000);
});