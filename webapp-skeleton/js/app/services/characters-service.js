define(function () {
    var internals = {}; // internal state
    var externals = {}; // external api

    const apiUrl = "https://www.moogleapi.com/api/v1/characters";

    let charactersData = [];

    internals.characters = fetch(apiUrl)
                .then(response => response.json())
                .then(data => {
                        charactersData = data; // Store the fetched characters data
                        return data; 
                    })
                .catch(error => {
                        console.error("Error fetching data:", error);
                    });

    externals.searchCharacter = function(searchQuery, displayCharacterFunction){
        const selectedCharacter = charactersData.find(character => character.name.toLowerCase() === searchQuery.toLowerCase());

        if (selectedCharacter){
            displayCharacterFunction(selectedCharacter);
        } else {
            
        }
    }
    
    return externals;
})