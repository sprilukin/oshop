require([
    'common/base',
    'addProductsToOrder/controller'
], function (BaseMain, Controller) {
    var baseMain = new BaseMain(Controller);
});
