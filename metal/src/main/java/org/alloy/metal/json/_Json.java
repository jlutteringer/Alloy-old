package org.alloy.metal.json;

public class _Json {
	// TODO
	public static JsonStatus success() {
		JsonStatus status = new JsonStatus();
		status.setMessage("success");
		return status;
	}

	// TODO
	public static JsonStatus failure() {
		return null;
	}

	// TODO
	public static JsonStatus failure(String message, Object... objects) {
		return null;
	}
}