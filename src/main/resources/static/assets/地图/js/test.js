(
	function(){
		$(document).click(
    function(e){
        console.log($(e.target).attr("id"));
    });
	})();