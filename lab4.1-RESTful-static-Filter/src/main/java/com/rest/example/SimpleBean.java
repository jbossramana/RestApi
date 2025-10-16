package com.rest.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(value = {"message1","message2"})
@JsonPropertyOrder({"message2", "message1", "message3"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleBean {

	
	private String message1;
	
	// @JsonProperty("second_message")
	private String message2;
	
  //@JsonIgnore
	private String message3;
	
	public SimpleBean(String msg1, String msg2, String msg3)
	{
		message1 = msg1;
		message2 = msg2;
		message3 = msg3;
	}

	public String getMessage1() {
		return message1;
	}

	public void setMessage1(String message1) {
		this.message1 = message1;
	}

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public String getMessage3() {
		return message3;
	}

	public void setMessage3(String message3) {
		this.message3 = message3;
	}

	
		
}
