/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/imageGallery',
    'common/messages',
    'common/context',
    'common/dropDownWithSearch',
    'text!orders/templates/orderProducts.html',
    'text!orders/templates/orderProductsSelectProduct.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, imageGallery, messages, context, DropDownWithSearch, listEntityTemplate, selectProductTemplate) {

    return Backbone.View.extend({

        events: {
            "click a.delete": "delete",
            "click #addProduct": "add"
        },

        initialize: function() {
            //this.model.on("error:addProduct", this.errorAddingProduct, this);
        },

        render: function () {
            var model = _.extend({context: context}, this.model.attributes, messages);
            this.$el.html(Mustache.render(listEntityTemplate, model));
        },

        delete: function(event) {
            this.model.deleteProducts($(event.currentTarget).attr("data-id"));
            event.preventDefault();
        }
    });
});