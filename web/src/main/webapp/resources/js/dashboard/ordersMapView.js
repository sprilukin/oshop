/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'orders/collection',
    "googlemapsLoader"
], function ($, _, Backbone, Mustache, Collection, mapsloader) {

    return Backbone.View.extend({
        el : "#googlemap",

        initialize: function(options) {
            _.bindAll(this, "render", "_initMap");
            this.collection = new Collection({customerId: null});
            this.filter = options.filter;
            this.markers = [];

            mapsloader.done(this._initMap);

            this.filter.on("filter:change", this.onFilterChange, this);
            this.collection.on("sync", this.onCollectionSync, this);
        },

        render: function () {
            var self = this;
            _.each(this.collection.models, function(model) {
                self._addMarker(model.attributes);
            })
        },

        _initMap: function(google) {
            //http://maps.googleapis.com/maps/api/geocode/json?address=Васильковцы Тернопольская обл Украина&sensor=false&language=ru

            this.map = new google.maps.Map($(this.el).get(0), {
                center: new google.maps.LatLng(49.4333333, 32.0666667),
                zoom: 6
            });
        },

        _addMarker: function(order) {
            if (!order || !order.shippingAddress || !order.shippingAddress.city) {
                return;
            }

            var city = order.shippingAddress.city;

            if (typeof city.latitude === "undefined" || typeof city.longitude === "undefined") {
                return;
            }

            var lat = parseFloat(city.latitude),
                long = parseFloat(city.longitude),
                position = new google.maps.LatLng(lat, long);

            var marker = new google.maps.Marker({
                position: position,
                map: this.map,
                title: city.name
            });

            this.markers.push(marker);
        },

        _clearMarkers: function() {
            if (this.markers) {
                for (var i = 0; i < this.markers.length; i++) {
                    this.markers[i].setMap(null);
                }
            }

            this.markers = [];
        },

        /* Event handlers */
        onCollectionSync: function() {
            this._clearMarkers();
            this.render();
        },

        onFilterChange: function() {
            var self = this;
            mapsloader.done(function() {
                self.collection.setFilterString(self.filter.format());
                self.collection.reset({silent: true});
                self.collection.fetch();
            });
        }
    });
});