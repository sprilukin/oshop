/**
 * Product Categories controller
 */
define([
    'underscore',
    'customers/model',
    'customers/collection',
    'customers/listView',
    'customers/editView',
    'common/baseControllerWithListAndEdit',
    'common/sorter'
], function (_, Model, Collection, ListView, EditView, BaseControllerWithListAndEdit, Sorter) {

    var CustomersController = function() {
        var sorter = new Sorter({
            sorters: [{
                name: "id",
                type: "desc"
            }]
        });

        this.initialize({
            sorter: sorter,
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