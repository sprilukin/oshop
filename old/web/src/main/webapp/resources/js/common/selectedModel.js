/**
 * Selected Items Model
 */
define([
    'underscore'
], function (_) {

    var SelectedModel = function() {
        this.model = {};
    };

    _.extend(SelectedModel.prototype, {
        add: function(item) {
            this.model[item] = true;
            return this;
        },

        remove: function(item) {
            delete this.model[item];
            return this;
        },

        contains: function(item) {
            return this.model[item]
        },

        toggle: function(item) {
            this.contains(item) ? this.remove(item) : this.add(item);
            return this;
        },

        clear: function() {
            for (var prop in this.model) {
                if (this.model.hasOwnProperty(prop)) {
                    delete this.model[prop];
                }
            }

            return this;
        },

        items: function() {
            return _.map(this.model, function(value, key) {
                return key;
            });
        }
    });

    return SelectedModel;
});