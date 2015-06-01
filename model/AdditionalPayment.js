'use strict';

var Sequelize = require("sequelize"),
    sequelize = require("../db/sequelize");

module.exports = sequelize.define('additional_payment', {
        description: {
            type: Sequelize.STRING(255),
            allowNull: false,
            validate: {
                notEmpty: true
            },
            unique: true
        },
        amount: {
            type: Sequelize.DECIMAL(10, 2),
            validate: {
                min: 0
            }
        }
    }, {
        tableName: "additional_payment"
    }
);