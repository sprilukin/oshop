/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'orders/model',
    'orders/collection',
    'orders/editView',
    'common/warningView'
], function ($, _, Backbone, Mustache, Model, Collection, EditView, WarningView) {

    var getOrderId = function() {
        var matches = window.location.pathname.match(/orders\/([\d]+)([\/#\?].*)?$/);
        if (matches && matches.length > 0) {
            return matches[1];
        } else {
            return null;
        }
    };

    var OrderController = function() {
        this.model = new Model();
        this.editView = new EditView({model: this.model});
        this.initialize();
    };

    _.extend(OrderController.prototype, {
        initialize: function() {
            this.editView.on("close",function () {
                this.router.navigate(this.getListUrl(), {trigger: true});
            }, this);

            this.id = getOrderId();
            if (this.id) {
                this.model.set("id", this.id);
                this.model.fetch({
                    success: this.editView.render
                });
            } else {
                this.editView.render();
            }
        }
    });

    return OrderController;
});