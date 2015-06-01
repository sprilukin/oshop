'use strict';

var Sequelize = require("sequelize"),
    sequelize = require("../db/sequelize");

module.exports = sequelize.define('income', {
        date: {
            type: Sequelize.DATE
        },
        description: {
            type: Sequelize.STRING(255),
            allowNull: false,
            validate: {
                notEmpty: true
            }
        },
        amount: {
            type: Sequelize.DECIMAL(10, 2),
            validate: {
                min: 0
            }
        }
    }, {
        tableName: "income"
    }
);