/**
 * Shipping Types controller
 */
define([
    'underscore',
    'shippingTypes/model',
    'shippingTypes/collection',
    'shippingTypes/listView',
    'shippingTypes/editView',
    'common/baseControllerWithListAndEdit'
], function (_, Model, Collection, ListView, EditView, BaseControllerWithListAndEdit) {

    var ShippingTypesController = function() {
        this.initialize({
            EditView: EditView,
            Model: Model,
            collection: new Collection(),
            View: ListView,
            search: "name"
        });
    };

    _.extend(ShippingTypesController.prototype, BaseControllerWithListAndEdit.prototype, {});

    return ShippingTypesController;
});