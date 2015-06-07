/**
 * Product Categories controller
 */
define([
    'underscore',
    'orderStates/model',
    'orderStates/collection',
    'orderStates/listView',
    'orderStates/editView',
    'common/baseControllerWithListAndEdit'
], function (_, Model, Collection, ListView, EditView, BaseControllerWithListAndEdit) {

    var OrderStatesController = function() {
        this.initialize({
            EditView: EditView,
            Model: Model,
            collection: new Collection(),
            View: ListView,
            search: "name"
        });
    };

    _.extend(OrderStatesController.prototype, BaseControllerWithListAndEdit.prototype, {});

    return OrderStatesController;
});