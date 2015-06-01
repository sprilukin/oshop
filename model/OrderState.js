'use strict';

var Sequelize = require("sequelize"),
    sequelize = require("../db/sequelize");

module.exports = sequelize.define('order_state', {
        name: {
            type: Sequelize.STRING(255),
            allowNull: false,
            validate: {
                notEmpty: true
            },
            unique: true
        }
    }, {
        tableName: "order_state"
    }
);