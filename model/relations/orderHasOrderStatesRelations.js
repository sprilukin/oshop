'use strict';

var OrderHasOrderStates = require("../OrderHasOrderStates"),
    OrderState = require("../OrderState");

OrderHasOrderStates.belongsTo(OrderState, {as: "OrderState", foreignKey: "order_state_id"});