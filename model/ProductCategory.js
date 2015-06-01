'use strict';

var Sequelize = require("sequelize"),
    sequelize = require("../db/sequelizeConfig");

module.exports = sequelize.define('product_category', {
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
