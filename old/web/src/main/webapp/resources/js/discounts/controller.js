/**
 * Discounts controller
 */
define([
    'underscore',
    'discounts/model',
    'discounts/collection',
    'discounts/listView',
    'discounts/editView',
    'common/baseControllerWithListAndEdit'
], function (_, Model, Collection, ListView, EditView, BaseControllerWithListAndEdit) {

    var DiscountsController = function() {
        this.initialize({
            EditView: EditView,
            Model: Model,
            collection: new Collection(),
            View: ListView,
            search: "description"
        });
    };

    _.extend(DiscountsController.prototype, BaseControllerWithListAndEdit.prototype, {});

    return DiscountsController;
});