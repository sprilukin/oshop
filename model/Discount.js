'use strict';

var Sequelize = require("sequelize"),
    sequelize = require("../db/sequelize"),
    discountEnum = require("./enum/discount");

var UK_DESCRIPTION_TYPE_AMOUNT = "UK_description_type_amount";

module.exports = sequelize.define('discount', {
        description: {
            type: Sequelize.STRING(255),
            allowNull: false,
            validate: {
                notEmpty: true
            },
            unique: UK_DESCRIPTION_TYPE_AMOUNT
        },
        type: {
            type: Sequelize.ENUM(discountEnum.FIXED, discountEnum.PERCENT),
            allowNull: false,
            unique: UK_DESCRIPTION_TYPE_AMOUNT
        },
        amount: {
            type: Sequelize.DECIMAL(10, 2),
            allowNull: false,
            unique: UK_DESCRIPTION_TYPE_AMOUNT
        }
    }, {
        tableName: "discount"
    }
);