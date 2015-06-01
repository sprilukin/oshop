'use strict';

var Product = require("../Product"),
    ProductCategory = require("../ProductCategory");

ProductCategory.hasMany(Product, {
    as: "Products",
    foreignKey: "product_category_id"
});


