/**
 * REST API
 */
define([
    'router/ajaxUtils',
    'mustache'
], function (ajaxUtils, Mustache) {

    /**
     * Item Categories
     */
    var itemCategories = (function () {

        var URL_TEMPLATE = "api/itemCategories/{{params}}";

        return {

            /**
             * Add item category
             *
             * @param category item category object. id should be not set
             * @param onSuccess onSuccess handler
             * @param onFail onFail handler
             */
            add: function (category, onSuccess, onFail) {
                ajaxUtils.restCall(
                    Mustache.to_html(URL_TEMPLATE, {params: ""}), {
                        method: "PUT",
                        data: category,
                        success: function (json, status) {
                            onSuccess && onSuccess(json, status);
                        },
                        fail: function (json, status) {
                            onFail && onFail(json, status);
                        }
                    });
            },

            /**
             * List itemCategories
             *
             * @param onSuccess onSuccess handler
             * @param onFail onFail handler
             */
            list: function (onSuccess, onFail) {
                ajaxUtils.restCall(
                    Mustache.to_html(URL_TEMPLATE, {params: ""}), {
                        method: "GET",
                        success: function (json, status) {
                            onSuccess && onSuccess(json, status);
                        },
                        fail: function (json, status) {
                            onFail && onFail(json, status);
                        }
                    });
            },

            /**
             * Get itemCategory with given Id
             *
             * @param id identifier of the itemCategory to get
             * @param onSuccess onSuccess handler
             * @param onFail onFail handler
             */
            get: function (id, onSuccess, onFail) {
                ajaxUtils.restCall(
                    Mustache.to_html(URL_TEMPLATE, {params: id}), {
                        method: "GET",
                        success: function (json, status) {
                            onSuccess && onSuccess(json, status);
                        },
                        fail: function (json, status) {
                            onFail && onFail(json, status);
                        }
                    });
            },

            /**
             * Update itemCategory
             *
             * @param category
             * @param onSuccess onSuccess handler
             * @param onFail onFail handler
             */
            update: function (category, onSuccess, onFail) {
                ajaxUtils.restCall(
                    Mustache.to_html(URL_TEMPLATE, {params: category.id}), {
                        method: "PUT",
                        success: function (json, status) {
                            onSuccess && onSuccess(json, status);
                        },
                        fail: function (json, status) {
                            onFail && onFail(json, status);
                        }
                    });
            },

            /**
             * Delete itemCategory by given id
             *
             * @param id identifier of the itemCategory to remove
             * @param onSuccess onSuccess handler
             * @param onFail onFail handler
             */
            delete: function (id, onSuccess, onFail) {
                ajaxUtils.restCall(
                    Mustache.to_html(URL_TEMPLATE, {params: id}), {
                        method: "DELETE",
                        success: function (json, status) {
                            onSuccess && onSuccess(json, status);
                        },
                        fail: function (json, status) {
                            onFail && onFail(json, status);
                        }
                    });
            }
        }
    })(); // itemCategories end

    return {
        itemCategories: itemCategories
    }
});
