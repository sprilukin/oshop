/**
 * Expenses controller
 */
define([
    'underscore',
    'incomes/model',
    'incomes/collection',
    'incomes/listView',
    'incomes/editView',
    'common/baseControllerWithListAndEdit',
    'common/sorter'
], function (_, Model, Collection, ListView, EditView, BaseControllerWithListAndEdit, Sorter) {

    var IncomesController = function() {
        this.sorter = new Sorter({
            sorters: [{
                name: "date",
                type: "desc"
            }]
        });

        this.initialize({
            EditView: EditView,
            Model: Model,
            collection: new Collection({sorter: this.sorter.format()}),
            sorter: this.sorter,
            View: ListView,
            search: "description"
        });
    };

    _.extend(IncomesController.prototype, BaseControllerWithListAndEdit.prototype);

    return IncomesController;
});