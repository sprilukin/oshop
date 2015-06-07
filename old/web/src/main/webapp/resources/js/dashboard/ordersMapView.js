/*jslint nomen: true, regexp: true */
/*global define, google */

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
    "text!dashboard/templates/infoWindowContentTemplate.html",
    "async!https://maps.googleapis.com/maps/api/js?v=3.exp&key=AIzaSyCMwyEdvR3chyzoi34mr78Jr0Xai52AHZY&sensor=false!callback"
], function ($, _, Backbone, Mustache, context, Collection, contentTemplate) {

    var ICON_TEMPLATE = "http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld={{letter}}|{{color}}",
        ONE_COLOR = "FE7569",
        TWO_TO_FIVE_COLOR = "FA9D41",
        FIVE_TO_TEN_COLOR = "5CB34D",
        TEN_TO_TWENTY_COLOR = "4D83B3",
        TWENTY_PLUS_COLOR = "E23DFF";


    return Backbone.View.extend({
        el : "#googlemap",

        initialize: function(options) {
            this.collection = new Collection({customerId: null});
            this.filter = options.filter;
            this.markers = {};

            this.filter.on("filter:change", this.onFilterChange, this);
            this.collection.on("sync", this.onCollectionSync, this);

            this._initMap();
        },

        render: function () {
            this._clearMarkers();

            var self = this;
            _.each(this.collection.models, function(model) {
                self._addMarker(model.attributes);
            })

            _.each(this.markers, function(data) {
                var ordersCount = data.orders.length;
                if (ordersCount > 1) {
                    data.marker.setIcon(self._getMarkerIcon(ordersCount));
                }
            })
        },

        _initMap: function() {
            this.map = new google.maps.Map($(this.el).get(0), {
                center: new google.maps.LatLng(49.4333333, 32.0666667),
                zoom: 6
            });

            this.infoWindow = new google.maps.InfoWindow({
                content: ""
            });

            var self = this;
            google.maps.event.addListener(this.map, 'click', function() {
                self.infoWindow.close();
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
                    position = new google.maps.LatLng(lat, long);

                this.markers[key] = this._createMarker(position, order);
            } else {
                this._addOrderToMarker(this.markers[key], order);
            }
        },

        _createMarker: function(position, order) {
            var marker = new google.maps.Marker({
                position: position,
                icon: this._getMarkerIcon(1),
                map: this.map
            });

            var data = {
                marker: marker,
                orders: [order]
            };

            var self = this;
            google.maps.event.addListener(marker, 'click', function() {
                self.infoWindow.setContent(self._getInfoWindowContent(data.orders));
                self.infoWindow.open(self.map, marker);
            });

            return data;
        },

        _addOrderToMarker: function(data, order) {
            data.orders.push(order);
        },

        _getMarkerIcon: function(ordersCount) {
            var color = TWENTY_PLUS_COLOR;

            if (ordersCount == 1) {
                color = ONE_COLOR;
            } else if (ordersCount >= 2 && ordersCount < 5) {
                color = TWO_TO_FIVE_COLOR;
            } else if (ordersCount >= 5 && ordersCount < 10) {
                color = FIVE_TO_TEN_COLOR;
            } else if (ordersCount >= 10 && ordersCount < 20) {
                color = TEN_TO_TWENTY_COLOR;
            }

            var url = Mustache.render(ICON_TEMPLATE, {
                color: color,
                letter: "" + ordersCount
            });

            return new google.maps.MarkerImage(
                url, new google.maps.Size(21, 34),
                new google.maps.Point(0,0), new google.maps.Point(10, 34));
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
                        google.maps.event.clearInstanceListeners(data.marker);
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
            this.collection.setFilterString(this.filter.format());
            this.collection.reset({silent: true});
            this.collection.fetch();
        }
    });
});