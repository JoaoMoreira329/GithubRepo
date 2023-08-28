define(function () {
    var internals = {}; // internal state
    var externals = {}; // external api

    const apiUrl = "https://www.moogleapi.com/api/v1/games";

    

    externals.fetchGames = function() {
        return fetch(apiUrl)
                .then(response => response.json())
                .catch(error => {
                        console.error("Error fetching data:", error);
                    });
                }; 

    
    return externals;
})