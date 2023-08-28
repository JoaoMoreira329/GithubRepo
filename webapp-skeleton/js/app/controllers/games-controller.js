define(["views/games-view", "services/games-service"], function (
    gamesView,
    gamesService
) {
    var externals = {};
    var internals = {};

    var gamesData = [];
    var currentGameIndex = 0;

    externals.start = function () {
        internals.bindEventHandlers();
        gamesService.fetchGames().then(function (data) {
            gamesData = data;
            internals.displayGame(gamesData[currentGameIndex]);
        });
    };

    internals.bindEventHandlers = function () {
        $(document).on("click", "#nextButton", internals.showNextGame);
        $(document).on("click", "#prevButton", internals.showPrevGame);
    };

    internals.showNextGame = function () {
        currentGameIndex = (currentGameIndex + 1) % gamesData.length;
        internals.displayGame(gamesData[currentGameIndex]);
    };

    internals.showPrevGame = function () {
        currentGameIndex = (currentGameIndex - 1 + gamesData.length) % gamesData.length;
        internals.displayGame(gamesData[currentGameIndex]);
    };

    internals.displayGame = function (game) {
        gamesView.renderSinglGame(game);
    };

    return externals;
});
