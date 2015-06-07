require([
    'common/base',
    'customers/controller'
], function (BaseMain, Controller) {
    var baseMain = new BaseMain(Controller);
});
