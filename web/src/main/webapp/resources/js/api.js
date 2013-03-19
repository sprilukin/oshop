window.oshop = window.oshop || {};

(function (context, Mustache) {

    var applyTemplate = function (template, model) {
        return Mustache.to_html(template, model);
    };

    context.itemCategories = (function() {

        var URL_TEMPLATE = context.addContextToUrl("/api/itemCategories/{{params}}");

        return {
            add: function (category, onSuccess, onFail) {
                $.ajax(applyTemplate(URL_TEMPLATE, {params: "add?_method=PUT"}), {
                    type: "POST",
                    contentType: "application/json",
                    dataType: "json",
                    data: category
                }).done(function () {
                    onSuccess.apply(this, arguments);
                }).fail(function (xhr, error, statusText) {
                    onFail.call(this, JSON.parse(xhr.responseText), parseInt(xhr.status));
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

})(window.oshop, Mustache);
