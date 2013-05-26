/**
 * Product Categories controller
 */
define([
    'underscore',
    'customers/model',
    'customers/collection',
    'customers/listView',
    'customers/editView',
    'common/baseControllerWithListAndEdit'
], function (_, Model, Collection, ListView, EditView, BaseControllerWithListAndEdit) {

    var CustomersController = function() {
        this.initialize({
            EditView: EditView,
            Model: Model,
            collection: new Collection(),
            View: ListView,
            search: "name"
        });
    };

    _.extend(CustomersController.prototype, BaseControllerWithListAndEdit.prototype, {});

    return CustomersController;
});