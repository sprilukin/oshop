/**
 * Fiter
 */
define([
    'underscore',
    'backbone',
    'mustache'
], function (_, Backbone, Mustache) {

    var DEFAULT_FILTER_TEMPLATE = "{{#filters}}{{name}}={{value}};{{/filters}}";

    var Filter = function(options) {
        options = _.extend({
            template: DEFAULT_FILTER_TEMPLATE,
            filters: []
        }, options);

        this.filterTemplate = options.template;
        this.filters = options.filters;
    };

    _.extend(Filter.prototype, {
        set: function(name, value, options) {
            var existingFilter = _.find(this.filters, function(field) {
                return field.name === name;
            });

            if (existingFilter) {
                existingFilter.value = value;
            } else {
                this.filters.push({name: name, value: value});
            }

            if (!options || !options.silent) {
                this.trigger("filter:change");
            }
        },

        get: function(name) {
            var existingFilter = _.find(this.filters, function(field) {
                return field.name === name;
            });

            return existingFilter && existingFilter.value;
        },

        reset: function() {
            this.filters = [];
        },

        format: function() {
            return Mustache.render(this.filterTemplate, {filters: this.filters}) || ";"
        },

        parse: function(filterAsString, options) {
            this.reset();

            if (!filterAsString) {
                return;
            }

            _.each(filterAsString.split(";"), function(field) {
                if (field && field.indexOf("=") > -1) {
                    var pair = field.split("=");
                    this.set(pair[0], pair[1], {silent: true});
                }
            }, this);

            if (!options || !options.silent) {
                this.trigger("filter:change");
            }
        }
    });

    _.extend(Filter.prototype, Backbone.Events);

    return Filter;
});