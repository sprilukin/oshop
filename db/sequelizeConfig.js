'use strict';

var Sequelize = require("sequelize"),
    dbConfig = require('config').get('database');

module.exports = new Sequelize(dbConfig.name, dbConfig.user, dbConfig.password, {
    host: dbConfig.host,
    dialect: dbConfig.dialect,

    pool: dbConfig.pool,

    //general table options
    freezeTableName: true,  //do not use plural table names
    timestamps: true,       //use timestamp tables to save timestamp when entity was created
                            //and last updated (true is default value)
    createdAt: 'created_at', //name of the createdAt table (createdAt is default)
    updatedAt: 'updated_at',  //name of the updatedAt table (updatedAt is default)

    isolationLevel: Sequelize.REPEATABLE_READ,

    logging: console.log
    /*logging: function() {
        console.log.apply(null, arguments);
        var sql = arguments[0];
        if (sql.indexOf("CREATE TABLE IF NOT EXISTS") < 0 && sql.indexOf("PRAGMA INDEX") < 0) {
            console.log(sql);
        }
    }*/
});