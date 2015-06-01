'use strict';

var Sequelize = require("sequelize"),
    sequelize = require("../db/sequelize");

module.exports = sequelize.define('order', {
        date: {
            type: Sequelize.DATE,
            field: "order_date"
        },
        description: {
            type: Sequelize.STRING(255)
        }
    }, {
        tableName: "order"
    }
);
