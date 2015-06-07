/**
 * Select2 wrapped in component
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
            formatResult: function(data) {
                if (typeof data.text !== "undefined") {
                    return data.text;
                } else if (that.options.formatResult) {
                    return that.options.formatResult(data)
                } else {
                    return "Format result is undefined";
                }
            },
            formatSelection: function(data) {
                if (typeof data.text !== "undefined") {
                    return data.text;
                } else if (that.options.formatSelection) {
                    return that.options.formatSelection(data)
                } else {
                    return "Format selection is undefined";
                }
            },
            ajax: {
                url: function (term) {
                    return Mustache.render(that.options.urlTemplate, {term: term});
                },
                dataType: 'json',
                results: function (data, page) {
                    return that.options.resultParser ? {results: that.options.resultParser(data, page)} : {results: data};
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