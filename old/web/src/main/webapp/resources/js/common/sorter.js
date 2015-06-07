/**
 * Sorter
 */
define([
    'underscore',
    'backbone',
    'mustache'
], function (_, Backbone, Mustache) {

    var DEFAULT_SORTER_TEMPLATE = "{{#sorters}}{{name}}={{type}};{{/sorters}}";

    var Sorter = function (options) {
        options = _.extend({
            template: DEFAULT_SORTER_TEMPLATE,
            sorters: []
        }, options);

        this.sorterTemplate = options.template;

        this.sorters = [];

        if (options.sorters.length > 0) {
            this.sorters.push.apply(this.sorters, options.sorters);
            this.trigger("sort:change");
        }
    };

    _.extend(Sorter.prototype, {
        set: function (name, type, options) {
            var existingSorter = _.find(this.sorters, function (field) {
                return field.name === name;
            });

            if (existingSorter) {
                existingSorter.type = type;
            } else {
                this.sorters.push({name: name, type: type});
            }

            if (!options || !options.silent) {
                this.trigger("sort:change");
            }
        },

        asc: function (name) {
            this.set(name, "asc");
        },

        desc: function (name) {
            this.set(name, "desc");
        },

        toggle: function (name) {
            var type = this.get(name);
            if (type === "desc") {
                this.remove(name);
            } else if (type === "asc") {
                this.desc(name);
            } else {
                this.asc(name);
            }
        },

        remove: function (name, options) {
            var size = this.sorters.length;
            this.sorters = _.reject(this.sorters, function (sorter) {
                return sorter.name === name;
            }, this);

            if (this.sorters.length < size && (!options || !options.silent)) {
                this.trigger("sort:change");
            }
        },

        get: function (name) {
            var existingSorter = _.find(this.sorters, function (field) {
                return field.name === name;
            });

            return existingSorter && existingSorter.type;
        },

        reset: function () {
            this.sorters = [];
        },

        format: function () {
            return Mustache.render(this.sorterTemplate, {sorters: this.sorters}) || ";"
        },

        parse: function (sorterAsString, options) {
            this.reset();

            if (!sorterAsString) {
                return;
            }

            _.each(sorterAsString.split(";"), function (field) {
                if (field && field.indexOf("=") > -1) {
                    var pair = field.split("=");
                    this.set(pair[0], pair[1], {silent: true});
                }
            }, this);

            if (!options || !options.silent) {
                this.trigger("sort:change");
            }
        }
    });

    _.extend(Sorter.prototype, Backbone.Events);

    return Sorter;
});