'use strict';

var Sequelize = require("sequelize"),
    sequelize = require("../db/sequelizeConfig");

module.exports = sequelize.define('customer', {
        name: {
            type: Sequelize.STRING(255),
            allowNull: false,
            validate: {
                notEmpty: true
            },
            unique: true
        },
        description: {
            type: Sequelize.STRING(255)
        },
        imageId: {
            type: Sequelize.INTEGER
        }
    }, {
        tableName: "customer"
    }
);