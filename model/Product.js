'use strict';

var Sequelize = require("sequelize"),
    sequelize = require("../db/sequelize"),
    ProductCategory = require("./ProductCategory");

var Product  = sequelize.define('product', {
        name: {
            type: Sequelize.STRING(255),
            allowNull: false,
            unique: true,
            validate: {
                notEmpty: true
            }
        },
        price: {
            type: Sequelize.DECIMAL(10, 2),
            allowNull: false,
            validate: {
                min: 0
            }
        },
        description: {
            type: Sequelize.STRING(255)
        }
    }, {
        tableName: "product"
    }
);

Product.hasOne(ProductCategory, {as: "ProductCategory"});

module.exports = Product;