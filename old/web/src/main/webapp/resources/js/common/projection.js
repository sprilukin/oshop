/**
 * Fiter
 */
define([
    'underscore',
    'backbone',
    'mustache'
], function (_, Backbone, Mustache) {

    var DEFAULT_PROJECTION_TEMPLATE = "{{#projections}}{{name}}={{value}};{{/projections}}";

    var Projection = function(options) {
        options = _.extend({
            template: DEFAULT_PROJECTION_TEMPLATE,
            projections: []
        }, options);

        this.projectionTemplate = options.template;
        this.projections = [];
        this.projections.push.apply(this.projections, options.projections);
    };

    _.extend(Projection.prototype, {
        set: function(name, value, options) {
            var existingProjection = _.find(this.projections, function(field) {
                return field.name === name;
            });

            var encodedValue = encodeURIComponent(value);

            if (existingProjection) {
                existingProjection.value = encodedValue;
            } else {
                this.projections.push({name: name, value: encodedValue});
            }

            if (!options || !options.silent) {
                this.trigger("projection:change");
            }
        },

        get: function(name) {
            var existingProjection = _.find(this.projections, function(field) {
                return field.name === name;
            });

            return existingProjection && existingProjection.value;
        },

        remove: function(name) {
            if (this.projections && this.projections.length > 0) {
                this.projections = _.filter(this.projections, function(field) {
                    return field.name !== name;
                });
            }
        },

        getAll: function() {
            return this.projections;
        },

        reset: function() {
            this.projections = [];
        },

        format: function() {
            return Mustache.render(this.projectionTemplate, {projections: this.projections}) || ";"
        },

        parse: function(projectionAsString, options) {
            this.reset();

            if (!projectionAsString) {
                return;
            }

            _.each(projectionAsString.split(";"), function(field) {
                if (field && field.indexOf("=") > -1) {
                    var pair = field.split("=");
                    this.set(pair[0], pair[1], {silent: true});
                }
            }, this);

            if (!options || !options.silent) {
                this.trigger("projection:change");
            }
        }
    });

    _.extend(Projection.prototype, Backbone.Events);

    return Projection;
});
