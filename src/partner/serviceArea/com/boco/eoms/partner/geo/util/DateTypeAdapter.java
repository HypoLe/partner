package com.boco.eoms.partner.geo.util;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * A default type adapter for a {@link Date} object.
 *
 * @author Joel Leitch
 */
public class DateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

	private final DateFormat format = DateFormat.getInstance();

	public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
		String dateFormatAsString = format.format(src);
		return new JsonPrimitive(dateFormatAsString);
	}

	public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		if (!(json instanceof JsonPrimitive)) {
			throw new JsonParseException("The date should be a string value");
		}

		try {
			return format.parse(json.getAsString());
		} catch (ParseException e) {
			throw new JsonParseException(e);
		}
	}
}