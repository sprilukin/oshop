'use strict';

var Sequelize = require("sequelize"),
    sequelize = require("../db/sequelize");

var UK_NAME_REGION = "UK_name_region";

module.exports = sequelize.define('city', {
        name: {
            type: Sequelize.STRING(255),
            allowNull: false,
            validate: {
                notEmpty: true
            },
            unique: UK_NAME_REGION
        },
        region: {
            type: Sequelize.STRING(255),
            allowNull: false,
            validate: {
                notEmpty: true
            },
            unique: UK_NAME_REGION
        },
        latitude: {
            type: Sequelize.STRING(255)
        },
        longitude: {
            type: Sequelize.STRING(255)
        }
    }, {
        tableName: "city"
    }
);