'use strict';

var Sequelize = require("sequelize"),
    sequelize = require("../db/sequelize"),
    Product = require("./Product");

var ProductCategory = sequelize.define('product_category', {
        name: {
            type: Sequelize.STRING(255),
            allowNull: false,
            validate: {
                notEmpty: true
            },
            unique: true
        }
    }, {
        tableName: "product_category"
    }
);

ProductCategory.hasMany(Product, {as: "Products"});

module.exports = ProductCategory;