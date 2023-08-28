define(function(){
    var internals = {
        handlers: {},
    };

    var externals = [];

    internals.createDropdown = function() {
        return `
        <div class="container" id="mainInitContainer">
        <div class="jumbotron">
        <p class="lead">Step into the enchanting world of Final Fantasy, where epic adventures, captivating characters, and immersive stories await. Our website is your ultimate destination for exploring the rich tapestry of Final Fantasy games and characters.</p>
        <p>Whether you're a die-hard fan or a curious newcomer, our platform is designed to provide you with in-depth information, insights, and details about every aspect of the Final Fantasy universe.</p>
        <p>Discover the iconic titles that have shaped the gaming industry and left an indelible mark on players around the globe. Immerse yourself in the narratives that have taken players on unforgettable journeys through magical realms, distant galaxies, and awe-inspiring landscapes.</p>
        <p>This little project of mine goes beyond games. Delve into the diverse cast of characters that populate the Final Fantasy universe. Whether you're drawn to the heroic knights, enigmatic mages, or charming rogues, you'll find a wealth of information about each character's background and contributions.</p>
        <p>Explore game details and character profiles.</p>
    </div>
        <div class="container" id="initialContainer">
            <img src="resources/mainimg.jpg" alt="mainImg" class="img-fluid main-image">
        </div>
        
        <div class="dropdown" id="dropDown">
            <a class="btn btn-secondary dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
            What do you want to check out?
            </a>

            <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="#games">Games</a></li>
                <li><a class="dropdown-item" href="#characters">Characters</a></li>
            </ul>
        </div>
        </div>
        `
        ;
    };

    externals.renderDropdown = function(){
        var dropdownHtml = internals.createDropdown();
        $("#app").html(dropdownHtml);
    };

    externals.bind = function (event, handler){
        internals.handlers[event] = handler;
        $("#routeDropdown").on("change", function() {
            var selectedRoute = $(this).val();
            internals.handlers[event](selectedRoute);
        });
    };

    return externals
})