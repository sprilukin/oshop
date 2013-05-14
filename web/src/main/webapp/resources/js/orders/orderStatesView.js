/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/messages',
    'common/context',
    'common/dropDownWithSearch',
    'common/dateFormatter',
    'text',
    'text!templates/orders/orderStates.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, messages, context, DropDownWithSearch, dateFormatter, text, listEntityTemplate) {

    return Backbone.View.extend({

        events: {
            "click a.delete": "delete",
            "click #addStatus": "add"
        },

        initialize: function() {
            //this.model.on("error:addProduct", this.errorAddingProduct, this);
        },

        render: function () {
            var states = _.map(this.model.get("states"), function(state) {
                return {
                    date: dateFormatter.format(state.date),
                    state: state.orderState.name,
                    description: state.description}
            });

            var model = _.extend({states: states}, messages);

            this.$el.html(Mustache.render(listEntityTemplate, model));
            this.errorPlaceHolder = $("#addStateContainer").find(".help-inline");

            this.orderStatusSelect && this.orderStatusSelect.destroy();
            this.orderStatusSelect = new DropDownWithSearch({
                element: $("#field_orderStatus"),
                placeholder: "Select order status",
                allowClear: true,
                urlTemplate: context + "/api/orderStates/filter;name={{term}};/sort;",
                resultParser: function(data) {
                    return data ? _.map(data.values, function (item) {
                        return {id: item.id, text: item.name}
                    }) : [];
                }
            });
        },

        add: function(event) {
            this.errorPlaceHolder.text("");

            var productId = $("#field_orderStatus").val();
            if (!productId) {
                this.errorPlaceHolder.text("Please select order status");
                return;
            }

            this.model.addOrderStatus(productId);
        },

        errorAddingStatus: function(model, xhr) {
            this.errorPlaceHolder.text("Could not add status");
        }
    });
});