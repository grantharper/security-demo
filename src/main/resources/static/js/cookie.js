
var cookie = document.cookie;
document.cookie = "path=/";
console.log("cookies: " + cookie);
console.log(getCookie('JSESSIONID'));

function getCookie(name) {
	  var value = "; " + document.cookie;
	  var parts = value.split("; " + name + "=");
	  if (parts.length == 2) return parts.pop().split(";").shift();
	}