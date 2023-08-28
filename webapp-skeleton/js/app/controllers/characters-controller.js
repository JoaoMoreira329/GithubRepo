define(["views/characters-view", "services/characters-service"], function (
    charactersView,
    charactersService
  ) {
  var externals = {};
  var internals = {};

  externals.start = function () {
    internals.bindEventHandlers();
    charactersView.render();
  };

  internals.changeView = function () {
    window.location.hash = "#characters";
  };

  internals.bindEventHandlers = function () {
    charactersView.bind("button", internals.buttonHandler);
  };



  internals.buttonHandler = function () {
    console.log("Button clicked!");
        var searchQuery = document.getElementById("searchInput").value;
        charactersService.searchCharacter(searchQuery, internals.displayCharacter); 
  };


  internals.displayCharacter = function (character) {
    charactersView.render(character);
};

return externals;
});