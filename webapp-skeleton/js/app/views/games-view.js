define (function(){
    var internals = {
        handlers: {},
        elements: {},
    };

    var externals = {};

    internals.createButton = function () {
        return '<button id="searchButton" class="btn btn-primary mb-3">Search</button>';
      };
    
    internals.createGameCard = function(game) {
        console.log(game);
        return(`
        <div class="container" id = "tablecontainer">
        <div class="conteiner" id = "imgcontainer">
            <img src="${game.picture}" alt="${game.title} Image" class="img-fluid game-image">
        </div>
        </div>
    `
        );
    }

    internals.renderImages = function (gam) {
        var imagesHtml = '<div class="container" id="imgTable">';
        gam.forEach(function(gam){
            imagesHtml += internals.createGameCard(gam);
        })
        imagesHtml += '</div>'; // Close the row
        internals.elements.app.html(imagesHtml);
    };

    internals.renderSinglImg = function(game) {
        var imageUrl = game.picture;
        // Check if the imageUrl is a valid URL and if it returns a 404 status
        fetch(imageUrl)
            .then(response => {
                if (response.ok) {
                    // If the image is available, use it
                    renderGameWithImage(game, imageUrl);
                } else {
                    // If the image is not available, use the fallback image
                    renderGameWithImage(game, "resources/fallback-image.jpg");
                }
            })
            .catch(() => {
                // If there's an error (e.g., CORS issue), use the fallback image
                renderGameWithImage(game, "resources/fallback-image.jpg");
            });
    };

function renderGameWithImage(game, imageUrl) {
    var gameHtml = `
    <div class="container" id="singleGameContainer">
        <div class="container" id="imgcontainer">
            <img src="${imageUrl}" alt="${game.title} Image" class="img-fluid game-image">
        </div>
        <div class="container" id="gameDetails">
            <h2>${game.title}</h2>
            <p><strong>Platform:</strong> ${game.platform}</p>
            <p><strong>Release Date:</strong> ${game.releaseDate}</p>
            <p>${game.description}</p>
        </div>
        <div id="navigationButtons">
            <button id="prevButton" class="btn btn-primary">&lt; Prev</button>
            <button id="nextButton" class="btn btn-primary">Next &gt;</button>
        </div>
    </div>
    `;
    internals.elements.app.html(gameHtml);
}


    
    externals.bind = function (event, handler) {
        internals.handlers[event] = handler;
      };
    
    externals.renderGames = function (gm) {
        internals.elements.app = $("#app");
    
        if (gm) {
          internals.renderImages(gm);
        }
      };

    externals.renderSinglGame = function (gam){
        internals.elements.app = $("#app");
        internals.renderSinglImg(gam);
      }
    
      return externals;
})