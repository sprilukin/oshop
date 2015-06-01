'use strict';

var Order = require("../Order"),
    OrderState = require("../OrderState"),
    OrderHasOrderState = require("../OrderHasOrderState"),
    Customer = require("../Customer"),
    ShippingAddress = require("../ShippingAddress"),
    Discount = require("../Discount"),
    AdditionalPayment = require("../AdditionalPayment"),
    Product = require("../Product");

Order.belongsTo(Customer, {
    as: "Customer",
    foreignKey: "customer_id"
});
Order.belongsTo(ShippingAddress, {
    as: "ShippingAddress",
    foreignKey: "shipping_address_id"
});
Order.belongsTo(Discount, {
    as: "Discount",
    foreignKey: "discount_id"
});
Order.belongsTo(AdditionalPayment, {
    as: "AdditionalPayment",
    foreignKey: "additional_payment_id"
});
Order.belongsToMany(OrderState, {
    through: OrderHasOrderState
});
Order.belongsToMany(Product, {
    through: "order_products"
});
