var model = require("./model"),
    sequelize = require("./db/sequelize");

sequelize.sync().then(function() {
   process.exit(0);
});