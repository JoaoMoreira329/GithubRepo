// the define function creates a new requireJS module
define([], function () {
  var private = {};
  var public = {};

  private.routes = {
    initial: {
      hash: "#initial",
      controller: "initial-controller",
    },
    games: {
      hash: "#games",
      controller: "games-controller",
    },

    characters: {
      hash: "#characters",
      controller: "characters-controller",
    },
  };

  private.defaultRoute = "initial";
  private.currentHash = "";

  public.start = function () {
    window.location.hash = private.routes[private.defaultRoute].hash;
    setInterval(private.hashCheck, 150);
  };

  private.hashCheck = function () {
    if (window.location.hash == private.currentHash) {
      return;
    }

    var routeName = Object.keys(private.routes).find(function (name) {
      return window.location.hash === private.routes[name].hash; // #pokemon
    });

    if (!routeName) {
      routeName = private.defaultRoute;
      window.location.hash = private.routes[routeName].hash;
    }

    private.loadController(private.routes[routeName].controller);
  };

  private.loadController = function (controllerName) {
    private.currentHash = window.location.hash;

    require(["controllers/" + controllerName], function (controller) {
      controller.start();
    });
  };

  return public;
});
