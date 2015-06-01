'use strict';

var Sequelize = require("sequelize"),
    sequelize = require("../db/sequelize");

module.exports = sequelize.define('shipping_type', {
        name: {
            type: Sequelize.STRING(255),
            allowNull: false,
            validate: {
                notEmpty: true
            },
            unique: true
        }
    }, {
        tableName: "shipping_type"
    }
);