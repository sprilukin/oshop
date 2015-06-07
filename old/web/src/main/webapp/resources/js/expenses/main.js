require([
    'common/base',
    'expenses/controller'
], function (BaseMain, Controller) {
    var baseMain = new BaseMain(Controller);
});
