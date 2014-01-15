/**
 * Expenses controller
 */
define([
    'underscore',
    'incomes/model',
    'incomes/collection',
    'incomes/listView',
    'incomes/editView',
    'common/baseControllerWithListAndEdit'
], function (_, Model, Collection, ListView, EditView, BaseControllerWithListAndEdit) {

    var ExpensesController = function() {
        this.initialize({
            EditView: EditView,
            Model: Model,
            collection: new Collection(),
            View: ListView,
            search: "description"
        });
    };

    _.extend(ExpensesController.prototype, BaseControllerWithListAndEdit.prototype, {});

    return ExpensesController;
});