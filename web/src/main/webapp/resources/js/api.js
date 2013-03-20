(function (oshop, utils) {

    oshop.api = {};

    oshop.api.itemCategories = (function () {

        var URL_TEMPLATE = "/api/itemCategories/{{params}}";

        return {
            add: function (category, onSuccess, onFail) {
                utils.restCall(
                    utils.applyTemplate(URL_TEMPLATE, {params: "add"}),
                    {
                        method: "PUT",
                        data: category,
                        success: function (json, status) {
                            onSuccess.call(this, json, status);
                        },
                        fail: function (json, status) {
                            onFail.call(this, json, status);
                        }
                    });
            },

            list: function (onSuccess, onFail) {
                utils.restCall(
                    utils.applyTemplate(URL_TEMPLATE, {params: ""}),
                    {method: "GET",
                        success: function (json, status) {
                            onSuccess.call(this, json, status);
                        },
                        fail: function (json, status) {
                            onFail.call(this, json, status);
                        }});
            },

            get: {

            },

            delete: {

            }
        }
    })();

})(window.oshop, window.oshop.utils);
