require([
    'invoicePrint/controller',
    'invoicePrint/ukrPostView'
], function (Controller, View) {
    new Controller({View: View}).list();
});
