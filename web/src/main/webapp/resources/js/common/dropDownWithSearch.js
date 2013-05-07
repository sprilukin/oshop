/**
 * Bootstrap alert wrapped in backbone view
 */
define([
    'jquery',
    'underscore',
    'mustache',
    'select2'
], function ($, _, Mustache) {

    var DropDownWithSearch = function(options) {
        options = _.extend({
            placeholder: "Select item",
            allowClear: false,
            urlTemplate: undefined,
            initialValue: undefined,
            resultParser: undefined
        }, options);

        this.el = options.element.select2({
            placeholder: options.placeholder,
            allowClear: options.allowClear,
            ajax: {
                url: function (term) {
                    return Mustache.render(options.urlTemplate, {term: term});
                },
                dataType: 'json',
                results: function (data, page) {
                    return options.resultParser ? {results: options.resultParser(data, page)} : data;
                }
            },
            initSelection: function(element, callback) {
                if (options.initialValue) {
                    $(element).val(options.initialValue.id);
                    callback(options.initialValue)
                }
            }
        });
    };

    _.extend(DropDownWithSearch.prototype, {
        destroy: function() {
            this.el.select2("destroy");
        }
    });

    return DropDownWithSearch;
});