'use strict';

var Sequelize = require("sequelize"),
    sequelize = require("../db/sequelize");

module.exports = sequelize.define('product', {
        name: {
            type: Sequelize.STRING(255),
            allowNull: false,
            unique: true,
            validate: {
                notEmpty: true
            }
        },
        price: {
            type: Sequelize.DECIMAL(10, 2),
            allowNull: false,
            validate: {
                min: 0
            }
        },
        description: {
            type: Sequelize.STRING(255)
        },
        imageId: {
            type:Sequelize.INTEGER,
            fieldName: "image_id"
        }
    }, {
        tableName: "product"
    }
);
