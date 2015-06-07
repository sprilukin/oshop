define([
    "jquery",
    "underscore",
    "mustache"
], function ($, _, Mustache) {
    "use strict";

    var GEOCODE_SERVICE_URI_TEMPLATE = "http://maps.googleapis.com/maps/api/geocode/json?address={{address}}&sensor=false&language=ru";

    var GeocodeService = function(options) {
        this.initialize(options)
    };

    _.extend(GeocodeService.prototype, {
        initialize: function() {

        },

        find: function(address) {
            var url = Mustache.render(GEOCODE_SERVICE_URI_TEMPLATE, this._prepareAddressModel(address));
            var xhr = $.ajax(url, {

            });

            return $.when(xhr).then(this._processJsonResult);
        },

        _prepareAddressModel: function(address) {
            return {
                address: encodeURIComponent(address)
            }
        },

        _processJsonResult: function(json) {
            return json
        }
    });

    return GeocodeService;
});