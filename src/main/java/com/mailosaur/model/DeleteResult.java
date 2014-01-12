package com.mailosaur.model;

import com.google.api.client.util.Key;

public class DeleteResult {
	@Key
	public Boolean ok;
	
	public String toString() {
		return ok.toString();
	}
}
