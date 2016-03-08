package utils;

import java.io.StringReader;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class Utils {
	
	
	public static String beautifyJson(String uglyJSONString) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		JsonReader reader = new JsonReader(new StringReader(uglyJSONString));
		reader.setLenient(true);
		JsonParser jp = new JsonParser();
	
		JsonElement je = jp.parse(reader);
		String prettyJsonString = gson.toJson(je);
		return prettyJsonString;
	}
	
	
	
	public static long getDateInterval(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	
		
}
