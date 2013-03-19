window.oshop = window.oshop || {};

(function (oshop, utils) {

    oshop.itemCategories = (function () {

        var URL_TEMPLATE = "/api/itemCategories/{{params}}";

        return {
            add: function (category, onSuccess, onFail) {
                utils.restCall(
                    utils.applyTemplate(URL_TEMPLATE, {params: "add"}),
                    {
                        method: "PUT",
                        data: category,
                        success: function () {
                            onSuccess.apply(this, arguments);
                        },
                        fail: function (json, status) {
                            onFail.call(this, json, status);
                        }
                    });
            },

            list: {

            },

            get: {

            },

            delete: {

            }
        }
    })();

})(window.oshop, window.oshop.utils);
