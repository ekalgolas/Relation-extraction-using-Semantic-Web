/**
 * Custom jquery functions
 */

$(document).ready(function() {
	$('a[id*=hide]').click(function(event) {
		$(this).parent().parent().find(".well").toggle();
	});
});