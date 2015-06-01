'use strict';

var Sequelize = require("sequelize"),
    sequelize = require("../db/sequelizeConfig");

module.exports = sequelize.define('image', {
        data: {
            type: Sequelize.BLOB,
            allowNull: false,
            validate: {
                maxSize: function(img) {
                    console.log(img, typeof img);
                }
            }
        },
        contentType: {
            type: Sequelize.STRING(255),
            allowNull: false,
            field: "content_type"
        }
    }, {
        tableName: "image"
    }
);