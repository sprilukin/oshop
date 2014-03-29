/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    "common/context",
    'orders/collection',
    "googlemapsLoader",
    "text!dashboard/templates/infoWindowContentTemplate.html"
], function ($, _, Backbone, Mustache, context, Collection, mapsloader, contentTemplate) {

    return Backbone.View.extend({
        el : "#googlemap",

        initialize: function(options) {
            _.bindAll(this, "render", "_initMap");
            this.collection = new Collection({customerId: null});
            this.filter = options.filter;
            this.markers = {};

            mapsloader.done(this._initMap);

            this.filter.on("filter:change", this.onFilterChange, this);
            this.collection.on("sync", this.onCollectionSync, this);
        },

        render: function () {
            this._clearMarkers();

            var self = this;
            _.each(this.collection.models, function(model) {
                self._addMarker(model.attributes);
            })
        },

        _initMap: function(google) {
            this.google = google;
            this.map = new google.maps.Map($(this.el).get(0), {
                center: new google.maps.LatLng(49.4333333, 32.0666667),
                zoom: 6
            });

            this.infoWindow = new google.maps.InfoWindow({
                content: ""
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

            var key = city.name + ", " + city.region;

            if (!this.markers[key]) {
                var lat = parseFloat(city.latitude),
                    long = parseFloat(city.longitude),
                    position = new this.google.maps.LatLng(lat, long);

                this.markers[key] = this._createMarker(position, order);
            } else {
                this._addOrderToMarker(this.markers[key], order);
            }
        },

        _createMarker: function(position, order) {
            var marker = new this.google.maps.Marker({
                position: position,
                map: this.map
            });

            var data = {
                marker: marker,
                orders: [order]
            };

            var self = this;
            this.google.maps.event.addListener(marker, 'click', function() {
                self.infoWindow.setContent(self._getInfoWindowContent(data.orders));
                self.infoWindow.open(self.map, marker);
            });

            return data;
        },

        _addOrderToMarker: function(data, order) {
            data.orders.push(order);
        },

        _getInfoWindowContent: function(orders) {
            return Mustache.render(contentTemplate, {
                orders: orders,
                city: orders[0].shippingAddress.city,
                context: context
            });
        },

        _clearMarkers: function() {
            if (this.markers) {
                for (var key in this.markers) {
                    if (this.markers.hasOwnProperty(key)) {
                        var data = this.markers[key];
                        this.google.maps.event.clearInstanceListeners(data.marker);
                        data.marker.setMap(null);
                    }
                }
            }

            this.infoWindow.close();
            this.markers = {};
        },

        /* Event handlers */

        onCollectionSync: function() {
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