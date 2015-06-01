'use strict';

var Product = require("../Product"),
    ProductCategory = require("../ProductCategory");

ProductCategory.hasMany(Product, {
    as: "Product",
    foreignKey: "product_category_id"
});


