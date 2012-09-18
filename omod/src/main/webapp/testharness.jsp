<html>
<head>
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/redmond/jquery-ui.css" />
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.1/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/jquery-ui.min.js"></script>
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.3/jquery.dataTables.min.js"></script>
<script type="text/javascript">
$(function() {
	var dialog = $("#popup").dialog({
		autoOpen: false
	});
	
	$("#launch-popup").button().click(function() {
		dialog.load("concepts.list", function() {
			dialog.dialog("open");
		})
		
	});
})
</script>

</head>
<body>
<a id="launch-popup">click me</a>
<div id="popup">
	<p>crazy!</p>
</div>
</body>
</html>