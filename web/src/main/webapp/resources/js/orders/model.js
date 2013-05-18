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

        ajax: function(options) {
            $.ajax({
                url: options.url,
                context: this,
                dataType: "json",
                type: options.type || "POST",
                contentType: "application/json",
                data: options.data
            }).done(function(data) {
                    this.fetch();
                }).fail(function(xhr) {
                    options.errorEvent && this.trigger("error:" + options.errorEvent, this, xhr);
                    this.trigger("error", this, xhr);
                })
        },

        addOrderStatus: function(stateId, description) {
            this.ajax({
                url: this.url() + "/orderHasStates/",
                data: JSON.stringify({"description": description, "orderState": {"id": stateId}}),
                errorEvent: "addStatus"
            })
        },

        addProducts: function() {
            var url = Mustache.render(
                this.url() + "/products/batch;ids={{ids}}/add",
                {ids: Array.prototype.join.call(arguments, ",")});

            this.ajax({url: url, errorEvent: "addProduct"});
        },

        deleteProducts: function() {
            var url = Mustache.render(
                this.url() + "/products/batch;ids={{ids}}/delete?_method=DELETE",
                {ids: Array.prototype.join.call(arguments, ",")});

            this.ajax({url: url, errorEvent: "deleteProduct"});
        },

        validate: function(attributes) {
            if (!attributes.customer.id) {
                return "Please select customer";
            }
        }
    });
});