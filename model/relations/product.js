'use strict';

var Product = require("../Product"),
    ProductCategory = require("../ProductCategory"),
    Order = require("../Order");

Product.belongsTo(ProductCategory, {
    as: "ProductCategory",
    foreignKey: "product_category_id"
});
Product.belongsToMany(Order, {
    through: "order_products"
});

