/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/messages',
    'common/context'
], function ($, _, Backbone, Mustache, messages, context) {

    return Backbone.Model.extend({

        url: function() {
            return Mustache.render(context + "/api/orders/{{id}}", {id: this.id});
        },

        addProducts: function() {
            var url = Mustache.render(
                this.url() + "/products/batch;ids={{ids}}/add",
                {ids: Array.prototype.join.call(arguments, ",")});

            $.ajax({
                url: url,
                context: this,
                dataType: "json",
                type: "POST"
            }).done(function(data) {
                    this.fetch();
                }).fail(function(xhr) {
                    this.trigger("error:addProduct", this, xhr);
                    this.trigger("error", this, xhr);
                })
        },

        deleteProducts: function() {
            var url = Mustache.render(
                this.url() + "/products/batch;ids={{ids}}/delete?_method=DELETE",
                {ids: Array.prototype.join.call(arguments, ",")});

            $.ajax({
                url: url,
                context: this,
                dataType: "json",
                type: "POST"
            }).done(function(data) {
                    this.fetch();
                }).fail(function(xhr) {
                    this.trigger("error:deleteProduct", this, xhr);
                    this.trigger("error", this, xhr);
                })
        }
    });
});