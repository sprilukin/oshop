'use strict';

var Sequelize = require("sequelize"),
    sequelize = require("../db/sequelizeConfig");

module.exports = sequelize.define('order_has_order_states', {
        date: {
            type: Sequelize.DATE
        },
        description: {
            type: Sequelize.STRING(255)
        }
    }, {
        tableName: "order_has_order_states"
    }
);