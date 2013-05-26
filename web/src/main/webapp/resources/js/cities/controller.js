/**
 * Cities controller
 */
define([
    'underscore',
    'cities/model',
    'cities/collection',
    'cities/listView',
    'cities/editView',
    'common/baseControllerWithListAndEdit'
], function (_, Model, Collection, ListView, EditView, BaseControllerWithListAndEdit) {

    var CitiesController = function() {
        this.initialize({
            EditView: EditView,
            Model: Model,
            collection: new Collection(),
            View: ListView,
            search: "name"
        });
    };

    _.extend(CitiesController.prototype, BaseControllerWithListAndEdit.prototype, {});

    return CitiesController;
});