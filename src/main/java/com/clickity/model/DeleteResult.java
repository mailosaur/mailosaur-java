package com.clickity.model;

import com.google.api.client.util.Key;

public class DeleteResult {
	@Key("ok")
	public Boolean Ok;
	
	public String toString() {
		return Ok.toString();
	}
}
