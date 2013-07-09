package io.clickity.model;

import com.google.api.client.util.Key;

public class Link {
	@Key
	public String href;
	@Key
	public String text;
	
	public String toString() {
		return href;
	}
}
