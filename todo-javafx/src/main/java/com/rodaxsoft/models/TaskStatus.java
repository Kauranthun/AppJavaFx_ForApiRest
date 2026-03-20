package com.rodaxsoft.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * TaskStatus enum
 */
public enum TaskStatus {
	RESERVED, OPEN {
		@Override
		@JsonValue
		public String toString() {
			return "open";
		}
	},

	COMPLETED {

		@Override
		@JsonValue
		public String toString() {
			return "completed";
		}
	};

	@JsonCreator
	public static TaskStatus getValue(String value) {
		String strVal = value.toUpperCase();
		switch (strVal) {
		case "OPEN":
			return OPEN;
		case "COMPLETED":
			return COMPLETED;
		default:
			throw new IllegalArgumentException("Unsupported value");
		}

	}

}
