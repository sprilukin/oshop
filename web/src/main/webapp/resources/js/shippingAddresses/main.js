require([
    'common/base',
    'shippingAddresses/controller'
], function (BaseMain, Controller) {
    var baseMain = new BaseMain(Controller);
});
