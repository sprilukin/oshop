require([
    'invoicePrint/controller',
    'invoicePrint/salesReceiptView'
], function (Controller, View) {
    new Controller({View: View}).list();
});
