'use strict';

var Order = require("./Order"),
    Customer = require("./Customer"),
    ShippingAddress = require("./ShippingAddress"),
    Discount = require("./Discount"),
    AdditionalPayment = require("./AdditionalPayment");

Order.belongsTo(Customer, {as: "Customer", foreignKey: "customer_id"});
Order.belongsTo(ShippingAddress, {as: "ShippingAddress", foreignKey: "shipping_address_id"});
Order.belongsTo(Discount, {as: "Discount", foreignKey: "discount_id"});
Order.belongsTo(AdditionalPayment, {as: "AdditionalPayment", foreignKey: "additional_payment_id"});

module.exports = Order;