'use strict';

var ShippingAddress = require("../ShippingAddress"),
    ShippingType = require("../ShippingType"),
    Customer = require("../Customer");

ShippingAddress.belongsTo(ShippingType, {
    as: "ShippingType",
    foreignKey: "shipping_type_id"
});
ShippingAddress.belongsTo(Customer, {
    as: "Customer",
    foreignKey: "customer_id"
});

