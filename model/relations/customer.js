'use strict';

var Order = require("../Order"),
    Customer = require("../Customer");

Customer.hasMany(Order, {
    as: "Orders",
    foreignKey: "customer_id"
});

