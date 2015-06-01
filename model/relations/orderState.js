'use strict';

var Order = require("../Order"),
    OrderState = require("../OrderState"),
    OrderHasOrderStates = require("../OrderHasOrderStates");

OrderState.belongsToMany(Order, {
    through: OrderHasOrderStates
});