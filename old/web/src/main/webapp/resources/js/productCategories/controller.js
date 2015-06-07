/**
 * Product Categories controller
 */
define([
    'underscore',
    'productCategories/model',
    'productCategories/collection',
    'productCategories/listView',
    'productCategories/editView',
    'common/baseControllerWithListAndEdit'
], function (_, Model, Collection, ListView, EditView, BaseControllerWithListAndEdit) {

    var ProductCategoriesController = function() {
        this.initialize({
            EditView: EditView,
            Model: Model,
            collection: new Collection(),
            View: ListView,
            search: "name"
        });
    };

    _.extend(ProductCategoriesController.prototype, BaseControllerWithListAndEdit.prototype, {});

    return ProductCategoriesController;
});