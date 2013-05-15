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
        var that = this;
        this.options = _.extend({
            placeholder: "Select item",
            allowClear: false,
            urlTemplate: undefined,
            initialValue: undefined,
            resultParser: undefined,
            formatResult: undefined,
            formatSelection: undefined,
            change: undefined,
            open: undefined
        }, options);

        this.el = this.options.element.select2({
            placeholder: this.options.placeholder,
            allowClear: this.options.allowClear,
            formatResult: this.options.formatResult,
            formatSelection: this.options.formatSelection,
            ajax: {
                url: function (term) {
                    return Mustache.render(that.options.urlTemplate, {term: term});
                },
                dataType: 'json',
                results: function (data, page) {
                    return that.options.resultParser ? {results: that.options.resultParser(data, page)} : data;
                }
            },
            initSelection: function(element, callback) {
                var el = $(element);

                if (that.options.initialValue) {
                    el.val(that.options.initialValue.id);
                } else {
                    that.options.initialValue = {id: el.val(), text: el.attr("data-text")};
                }

                callback(that.options.initialValue)
            }
        });

        this.options.change && this.el.on("change", this.options.change);
        this.options.open && this.el.on("open", this.options.open);
    };

    _.extend(DropDownWithSearch.prototype, {
        destroy: function() {
            this.options.change && this.el.off("change", this.options.change);
            this.options.open && this.el.off("open", this.options.change);
            this.el.select2("destroy");
        }
    });

    return DropDownWithSearch;
});