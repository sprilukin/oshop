/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    "bundle!messages",
    'common/context',
    'common/dropDownWithSearch',
    'common/dateFormatter',
    'text!orders/templates/orderStates.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, messages, context, DropDownWithSearch, dateFormatter, listEntityTemplate) {

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
                    date: dateFormatter(state.date).format(),
                    state: state.orderState.name,
                    description: state.description}
            });

            var model = _.extend({states: states}, messages);

            this.$el.html(Mustache.render(listEntityTemplate, model));
            this.errorPlaceHolder = $("#addStateContainer").find(".help-inline");

            this.orderStatusSelect && this.orderStatusSelect.destroy();
            this.orderStatusSelect = new DropDownWithSearch({
                element: $("#field_orderStatus"),
                placeholder: messages["order_select_order_status_placeholder"],
                allowClear: true,
                urlTemplate: context + "/api/orderStates/filter;name={{term}};/sort;",
                resultParser: function(data) {
                    return data ? _.map(data, function (item) {
                        return {id: item.id, text: item.name}
                    }) : [];
                }
            });
        },

        add: function(event) {
            this.errorPlaceHolder.text("");

            var description = $("#orderStatusDescription").val();
            var orderStatusId = parseInt($("#field_orderStatus").val(), 10);
            if (!orderStatusId) {
                this.errorPlaceHolder.text(messages["ui_validation_select_order_status"]);
                return;
            }

            this.model.addOrderStatus(orderStatusId, description);
        },

        errorAddingStatus: function(model, xhr) {
            this.errorPlaceHolder.text(messages["error_could_not_add_order_status"]);
        }
    });
});