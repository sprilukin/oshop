define(function (require) {

    var Oshop = require("oshop"),
        context = require('common/context'),
        messages = require("bundle!messages");

    Oshop.module("Entities", function(Entities, Oshop, Backbone, Marionette, $, _){

        Entities.Order = Backbone.Model.extend({
            urlRoot: "api/orders",

            validate: function(attributes) {
                if (!attributes.customer.id) {
                    return messages["ui_validation_select_customer"];
                }
            }
        });

        Entities.OrderCollection = Backbone.Collection.extend({
            url: "api/orders",
            model: Entities.Order
        });

        var API = {
            getContactEntities: function(){
                var orders = new Entities.OrderCollection();
                return orders.fetch();
            },

            getContactEntity: function(orderId){
                var contact = new Entities.Order({id: orderId});
                return contact.fetch();
            }
        };

        Oshop.reqres.setHandler("order:entities", function(){
            return API.getContactEntities();
        });

        Oshop.reqres.setHandler("order:entity", function(id){
            return API.getContactEntity(id);
        });
    });

    return ;
});