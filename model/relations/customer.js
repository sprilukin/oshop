'use strict';

var Order = require("../Order"),
    Customer = require("../Customer");

Customer.hasMany(Order, {
    as: "Order",
    foreignKey: "customer_id"
});

