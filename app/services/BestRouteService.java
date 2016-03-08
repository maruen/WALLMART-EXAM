package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;


public class BestRouteService extends Controller {
	
	
	private static SimpleDateFormat sdf1               = new SimpleDateFormat("dd-MM-yyyy.HH-mm-ss");
	private static String           WALLMART_HOME      = "/Users/Maruen/Projects/WALLMART-EXAM";
    
    
    @SuppressWarnings("unused")
    private static void saveJsonToFile(JsonNode json) {
		Date currentDate 		= new Date();
    	String atsmsHome		= System.getenv(WALLMART_HOME);
    	String filename 		= atsmsHome.concat(File.separator)
							    		   .concat("logs")
							    		   .concat(File.separator)
							    		   .concat("files")
							    		   .concat(File.separator)
							    		   .concat(".")
							    		   .concat(sdf1.format(currentDate))
							    		   .concat(".json");

    	File jsonFile		 = new File(filename);
    	
    	try {
    		FileOutputStream out = new FileOutputStream(jsonFile);
    		out.write(Utils.beautifyJson(json.toString()).getBytes());
    		out.flush();
    		out.close();
    		Logger.info("Services.saveJsonToFile(): " + filename + " written successfully...");
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
 	}
    
    
    public static Result JsonResult(ObjectNode uglyJSONString) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		JsonReader reader = new JsonReader(new StringReader(uglyJSONString.toString()));
		reader.setLenient(true);
		JsonParser jp = new JsonParser();
	
		JsonElement je = jp.parse(reader);
		String prettyJsonString = gson.toJson(je);
		return ok(prettyJsonString);
	} 
    
    

}    
