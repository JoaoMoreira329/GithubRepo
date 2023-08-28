define (function(){
    var internals = {
        handlers: {},
        elements: {},
    };

    var externals = {};

    internals.createSearchBar = function(){
        return '<input type="text" id="searchInput" class="form-control mb-3" placeholder="Search by character name"></input>'
    }
    internals.noCharFound = function(){
        return '<p>Character not found</p>'; 
    }

    internals.createButton = function () {
        return '<button id="searchButton" class="btn btn-primary mb-3">Search</button>';
      };
    
    internals.createCharCard = function(character) {
        console.log(character);
        return(`
        <div class="container" id="charview">
        <h2 id="charname">${character.name}</h2>
        <p><strong>Age:</strong> ${character.age}</p>
        <p><strong>Gender:</strong> ${character.gender}</p>
        <p><strong>Race:</strong> ${character.race}</p>
        <p><strong>Job:</strong> ${character.job}</p>
        <p><strong>Origin:</strong> ${character.origin}</p>
        <p>${character.description}</p>
        <br>
        <br>
        <div class="container image-container" id="charimg">
        <div class="col-md-6 text-center">
            <img src="${character.pictures[0].url}" alt="${character.name} Image" class="img-fluid rounded">
        </div>
        </div>
        <hr class="w-100">
        </div>
    `
        );
    }

    internals.renderChar = function (char) {
        if (internals.elements.charCard) {
            internals.elements.charCard.remove();
        }
    
        internals.elements.charCard = $(internals.createCharCard(char));
        internals.elements.app.append(internals.elements.charCard);
    };

      internals.renderSearchBar = function(){
        if (internals.elements.input){
            return;
        }
        internals.elements.input = $(internals.createSearchBar());
        internals.elements.app.append(internals.elements.input);
      }
    
      internals.renderButton = function () {
        if (internals.elements.button) {
          return;
        }
    
        internals.elements.button = $(internals.createButton());
        internals.elements.button.click(internals.handlers["button"]);
        internals.elements.app.append(internals.elements.button);
      };
    
      externals.bind = function (event, handler) {
        internals.handlers[event] = handler;
      };
    
      externals.render = function (char) {
        internals.elements.app = $("#app");
        internals.renderButton();
        internals.renderSearchBar();
    
        if (char) {
          internals.renderChar(char);
        }
      };
    
      return externals;
})