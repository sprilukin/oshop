(function (oshop, $, Mustache) {

    oshop.utils = (function() {

        return {
            applyTemplate: function (template, model, destination) {
                if (template.indexOf("#") === 0) {
                    template = $(template).html();
                }

                var html = Mustache.to_html(template, model);

                if (destination) {
                    $(destination).html(html);
                }

                return html;
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
                }).done(function () {
                    params.success && params.success.apply(this, arguments);
                }).fail(function (xhr, error, statusText) {
                    params.fail && params.fail.call(this, JSON.parse(xhr.responseText), parseInt(xhr.status));
                });
            }
        }
    })();

})(window.oshop, jQuery, Mustache);
