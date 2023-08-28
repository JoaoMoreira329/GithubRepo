// the require function loads 0 to infinite requireJS modules/dependencies
require(["router"], function (router) {
  $(document).ready(function () {
    console.log("DOM is ready to be populated!");
    router.start();
  });
});
