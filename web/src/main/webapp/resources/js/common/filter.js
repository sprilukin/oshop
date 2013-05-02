/**
 * Fiter
 */
define([
    'jquery',
    'backbone',
    'mustache'
], function ($, Backbone, Mustache) {

    var DEFAULT_FILTER_TEMPLATE = "{{#fields}}{{name}}={{value}};{{/fields}}";

    var Filter = function(options) {
        options = _.extend({
            template: DEFAULT_FILTER_TEMPLATE,
            fields: []
        }, options);

        this.filterTemplate = options.template;
        this.fields = options.fields;
    };

    _.extend(Filter.prototype, {
        set: function(name, value) {
            var existingValue = _.find(this.fields, function(field) {
                return field.name === name;
            });

            if (existingValue) {
                existingValue.value = value;
            } else {
                this.fields.push({name: name, value: value});
            }

            return this;
        },

        reset: function() {
            this.fields = [];
        },

        format: function() {
            return Mustache.render(this.filterTemplate, {fields: this.fields})
        },

        parse: function(filterAsString) {
            this.reset();

            if (!filterAsString) {
                return;
            }

            _.each(filterAsString.split(";"), function(field) {
                if (field && field.indexOf("=") > -1) {
                    var pair = field.split("=");
                    this.set(pair[0], pair[1]);
                }
            }, this);
        }
    });

    return Filter;
});