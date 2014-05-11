define(function(require){
    var Marionette = require("marionette"),
        Backbone = require("backbone");

    var Oshop = new Marionette.Application();

    Oshop.addRegions({
        mainRegion: "#main-region"
    });

    Oshop.navigate = function(route,  options){
        options || (options = {});
        Backbone.history.navigate(route, options);
    };

    Oshop.getCurrentRoute = function(){
        return Backbone.history.fragment
    };

    Oshop.on("initialize:after", function(){
        if(Backbone.history){
            Backbone.history.start();

            if(this.getCurrentRoute() === ""){
                Oshop.trigger("orders:list");
            }
        }
    });

    return Oshop;
});
