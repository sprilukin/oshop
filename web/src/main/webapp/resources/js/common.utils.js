(function (oshop, $, Mustache, _) {

    oshop.utils = (function() {

        return {
            applyTemplate: function (template, model) {
                if (template.indexOf("#") === 0) {
                    template = $(template).html();
                }

                return Mustache.to_html(template, model);
            },

            restCall: function(url, params) {
                params = _.extend(
                    {
                        method: "GET",
                        data: {},
                        success: function() {},
                        fail: function() {}
                    }, params);

                //Adding _method to url
                if (_.indexOf(["GET", "POST"], params.method.toUpperCase()) == -1) {
                    url += ((url.indexOf("?") > 0) ? "&" : "?") + "_method=" + params.method
                }

                $.ajax(oshop.addContextToUrl(url), {
                    type: params.method,
                    contentType: "application/json",
                    dataType: "json",
                    data: params.data
                }).done(function (json, statusAsText, xhr) {
                    params.success && params.success.call(this, json, xhr.status);
                }).fail(function (xhr, error, statusText) {
                    params.fail && params.fail.call(this, JSON.parse(xhr.responseText), xhr.status);
                });
            }
        }
    })();

})(window.oshop, jQuery, Mustache, _);
