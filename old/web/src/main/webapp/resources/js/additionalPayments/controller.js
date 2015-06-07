/**
 * Additional Payments controller
 */
define([
    'underscore',
    'additionalPayments/model',
    'additionalPayments/collection',
    'additionalPayments/listView',
    'additionalPayments/editView',
    'common/baseControllerWithListAndEdit'
], function (_, Model, Collection, ListView, EditView, BaseControllerWithListAndEdit) {

    var AdditionalPaymentsController = function() {
        this.initialize({
            EditView: EditView,
            Model: Model,
            collection: new Collection(),
            View: ListView,
            search: "description"
        });
    };

    _.extend(AdditionalPaymentsController.prototype, BaseControllerWithListAndEdit.prototype, {});

    return AdditionalPaymentsController;
});