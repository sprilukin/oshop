'use strict';

var Sequelize = require("sequelize"),
    _ = require("underscore"),
    config = require('/../db-config.json'),
    dbConfig = JSON.parse(config);

var sequelize = new Sequelize(dbConfig.db, dbConfig.user, dbConfig.password, {
    host: dbConfig.host,
    dialect: dbConfig.dialect,

    pool: dbConfig.pool,

    //general table options
    freezeTableName: true,  //do not use plural table names
    timestamps: true,       //use timestamp tables to save timestamp when entity was created
                            //and last updated (true is default value)
    createdAt: 'createdAt', //name of the createdAt table (createdAt is default)
    updatedAt: 'updatedAt',  //name of the updatedAt table (updatedAt is default)

    isolationLevel: Sequelize.REPEATABLE_READ
});

//mix optimistic locking behaviour
sequelize.addHook("beforeDefine", optimisticLockingBeforeDefineHook);
sequelize.addHook("afterDefine", optimisticLockingAfterDefineHook);

//fix dialect for optimistic locking
dialectPatch(sequelize, sequelize.dialect);

module.exports = sequelize;