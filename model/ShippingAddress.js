'use strict';

var Sequelize = require("sequelize"),
    sequelize = require("../db/sequelize");

module.exports = sequelize.define('shipping_address', {
        date: {
            type: Sequelize.DATE
        },
        recipient: {
            type: Sequelize.STRING(255),
            allowNull: false,
            validate: {
                notEmpty: true
            }
        },
        address: {
            type: Sequelize.STRING(255),
            allowNull: false,
            validate: {
                notEmpty: true
            }
        },
        postalCode: {
            type: Sequelize.STRING(50),
            field: "postal_code"
        },
        phone: {
            type: Sequelize.STRING(20),
            field: "postal_code"
        }
    }, {
        tableName: "shipping_address"
    }
);