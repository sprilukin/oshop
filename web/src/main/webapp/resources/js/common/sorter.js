/**
 * Sorter
 */
define([
    'underscore',
    'mustache'
], function (_, Mustache) {

    var DEFAULT_SORTER_TEMPLATE = "{{#sorters}}{{name}}={{type}};{{/sorters}}";

    var Sorter = function(options) {
        options = _.extend({
            template: DEFAULT_SORTER_TEMPLATE,
            sorters: []
        }, options);

        this.sorterTemplate = options.template;
        this.sorters = options.sorters;
    };

    _.extend(Sorter.prototype, {
        set: function(name, type) {
            var existingSorter = _.find(this.sorters, function(field) {
                return field.name === name;
            });

            if (existingSorter) {
                existingSorter.type = type;
            } else {
                this.sorters.push({name: name, type: type});
            }

            return this;
        },

        asc: function(name) {
            this.set(name, "asc");
        },

        desc: function(name) {
            this.set(name, "desc");
        },

        get: function(name) {
            var existingSorter = _.find(this.sorters, function(field) {
                return field.name === name;
            });

            return existingSorter && existingSorter.type;
        },

        reset: function() {
            this.sorters = [];
        },

        format: function() {
            return Mustache.render(this.sorterTemplate, {sorters: this.sorters}) || ";"
        },

        parse: function(sorterAsString) {
            this.reset();

            if (!sorterAsString) {
                return;
            }

            _.each(sorterAsString.split(";"), function(field) {
                if (field && field.indexOf("=") > -1) {
                    var pair = field.split("=");
                    this.set(pair[0], pair[1]);
                }
            }, this);
        }
    });

    return Sorter;
});