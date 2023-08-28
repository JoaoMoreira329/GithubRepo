define(["views/initial-view"], function(initialView){
    var external = {};
    var internal = {};

    internal.initDropdown = function () {
        initialView.renderDropdown();
        initialView.bind("dropdownChange", internal.dropdownChangeHandler);
    };

    internal.dropdownChangeHandler = function (selectedRoute) {
        window.location.hash = selectedRoute;
    }

    external.start = function (){
        internal.initDropdown();
    };

    return external;
})