'use strict';

var Order = require("../Order"),
    OrderState = require("../OrderState"),
    OrderHasOrderState = require("../OrderHasOrderState");

OrderState.belongsToMany(Order, {
    through: OrderHasOrderState
});