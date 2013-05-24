require([
    'common/base',
    'cities/controller'
], function (BaseMain, Controller) {
    var baseMain = new BaseMain(Controller);
});
