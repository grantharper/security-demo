package org.grantharper.websecurity.validator;

import java.util.regex.Pattern;

import org.grantharper.websecurity.domain.Customer;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CustomerValidator implements Validator {
	
	private Pattern namePattern = Pattern.compile("^[A-Za-z0-9-\\'\\s]*$");

	@Override
	public boolean supports(Class<?> clazz) {
		return Customer.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors e) {
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "firstName", "firstName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "lastName", "lastName.required");
		Customer customer = (Customer) obj;
		if(!namePattern.matcher(customer.getFirstName()).matches()){
			e.rejectValue("firstName", "firstName.invalid");
		}
		if(!namePattern.matcher(customer.getLastName()).matches()){
			e.rejectValue("lastName", "lastName.invalid");
		}
	}

}
