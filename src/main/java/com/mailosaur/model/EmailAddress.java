package com.mailosaur.model;

import com.google.api.client.util.Key;

public class EmailAddress {
	@Key
	public String address;
	@Key
	public String name;
	
	public String toString() {
		return (name == null || name.trim().isEmpty()) ?
				address :
				String.format("%s <%s>", name, address);
	}
}
