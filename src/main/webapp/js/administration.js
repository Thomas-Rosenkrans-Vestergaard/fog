function updateNewOrdersCounter() {

    $.get({
        method: 'GET',
        url: '../api?action=getNumberOfNewOrders',
        success: function (data) {
            $('#new-orders-counter').text = data['orders'];
        }
    });
}

$(document).ready(function () {
    updateNewOrdersCounter();
    setInterval(updateNewOrdersCounter, 60 * 1000);
});