package services;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import models.Map;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;

public class RouteService extends Controller {
	
	
	private static SimpleDateFormat sdf1               = new SimpleDateFormat("dd-MM-yyyy.HH-mm-ss");
	private static String           WALLMART_HOME      = "WALLMART_HOME";
	
	
	public Result insertMap() {
	    JsonNode  json = request().body().asJson();
        
        saveJsonToFile(json);
        
        JsonNode routes = null;
        try {
            routes = new ObjectMapper().readTree(json.toString()).get("routes");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String name = json.get("name").asText();
        
        List<Map> mapList = Lists.newArrayList();
        if (routes != null && routes.isArray()) {
            for (JsonNode route : routes) {
                
                String origin      = route.get("origin").asText();
                String destiny     = route.get("destiny").asText();
                String distance    = route.get("distance").asText();

                if ( isNullOrEmpty(origin) || isNullOrEmpty(destiny) )  {
                    continue;
                }
                
                String id = name.concat("-").concat(origin).concat("-").concat(destiny);
                
                Map map = new Map(id,
                                  name,
                                  origin,
                                  destiny,
                                  distance);
                
                if ( Map.getById(id) == null ) {
                    map.save();
                } else {
                    map.update();
                }
                
                mapList.add(map);
                
            }
        }

        return ok(Json.toJson(mapList));

	    
	}
	
	public Result getRouteWithLessCost(String map,
	                                   String origin,
	                                   String destiny,
	                                   String consumeAvg,
	                                   String fuelCost) {
	    
	    
	    Logger.info("RouteService.getRouteWithLessCost" +
	            "[map="            + map                + 
	            ", origin="        + origin             + 
	            ", destiny="       + destiny            + 
	            ", consumeAvg="    + consumeAvg         + 
	            ", fuelCost="      + fuelCost           + 
	            "]");
	    
	    
	    List<Map> routes = Map.getRoutesByMap(map);
	    
	    LinkedListMultimap<String, Double> routesMultiMap = LinkedListMultimap.create();
	    LinkedListMultimap<String, String> allWaysMap     = LinkedListMultimap.create();
	    for (Map route : routes) {
	        
	        Number distance;
	        try {
	            distance = NumberFormat.getInstance().parse(route.distance);
	        } catch (ParseException parseException) {
	            continue;
	        }
	        
	        String key = route.origin.concat("-").concat(route.destiny);
	        routesMultiMap.put(key,distance.doubleValue() );
	    }
	    
	    List<String> keys = Lists.newArrayList();
	    for ( Entry<String, Double> entry : routesMultiMap.entries()) { 
	        keys.add(entry.getKey());
	    }
	    
	    for (int i=0; i < keys.size(); i++ ) {
	        
	        String currentKey = keys.get(i);
	        String endsWith   = currentKey.split("-")[1];
	       
	        for (int j= i+1; j < keys.size(); j++ ) {
	            
	            String compareKey = keys.get(j);
	            String startsWith   = compareKey.split("-")[0];
	            
	            if (endsWith.equals(startsWith)) {
	                String key = currentKey.split("-")[0].concat("-").concat(compareKey.split("-")[1]);
	                
	                List<Double> distances1 = routesMultiMap.get(currentKey);
	                List<Double> distances2 = routesMultiMap.get(compareKey);
	                
	                for (Double distance1 : distances1) {
	                    for(Double distance2 : distances2) {
	                        Double finaldistance  = distance1 + distance2;
	                        routesMultiMap.put(key,finaldistance);
	                    }
	                }
	                
	                String allWayValue = currentKey.split("-")[0]
	                                            .concat(currentKey.split("-")[1])
	                                            .concat(compareKey.split("-")[1]);
	                
	                allWaysMap.put(key, allWayValue);
	            }
	        
	        }
	    }
	    
	    JsonNode routesAsJson   = Json.toJson(routesMultiMap.toString());
	    JsonNode allWayAsJson   = Json.toJson(allWaysMap.toString());
	    
	    String logResult = "All possible routes are -> ".concat(routesAsJson.toString());
	    logResult        = logResult.concat("\nAll complete ways are   -> ").concat(allWayAsJson.toString());
	    
	    Logger.info("logResult: " + logResult);
	    
	    
	    String key         = origin.concat("-").concat(destiny);
	    List<Double> list  = routesMultiMap.get(key);
	    
	    int lowerCostIndex         = 0;
	    Double lowerCostDistance   = Double.MIN_VALUE; 
	    
	    for(int i=0 ; i < list.size() ; i++) {
	        
	        if (list.get(i)  < lowerCostDistance  ) {
	            lowerCostIndex = i;
	            lowerCostDistance = list.get(i);
	        }
	    }
	    
	    Number consumeAvgAsNumber;
        try {
            consumeAvgAsNumber = NumberFormat.getInstance().parse(consumeAvg);
        } catch (ParseException parseException) {
            consumeAvgAsNumber = 10.0;
        }
        
        Number fuelCostAsNumber;
        try {
            fuelCostAsNumber = NumberFormat.getInstance().parse(fuelCost);
        } catch (ParseException parseException) {
            fuelCostAsNumber = 2.50;
        }
        
	    Double nearestDistance = routesMultiMap.get(key).get(lowerCostIndex);
	    Double fuelConsume     = nearestDistance / consumeAvgAsNumber.doubleValue(); 
	    Double totalCost       = fuelConsume * fuelCostAsNumber.doubleValue();
	    
	    String result = "The best route is -> ".concat(allWaysMap.get(key).get(lowerCostIndex));
	    result        = result.concat("\nThe cost is   -> ").concat(totalCost.toString());
	      
	    return ok(result);
	}
	

    private static void saveJsonToFile(JsonNode json) {
		Date currentDate 		= new Date();
    	String wallmartHome		= System.getenv(WALLMART_HOME);
    	String filename 		= wallmartHome.concat(File.separator)
							    		   .concat("logs")
							    		   .concat(File.separator)
							    		   .concat("jsonFiles")
							    		   .concat(File.separator)
							    		   .concat(sdf1.format(currentDate))
							    		   .concat(".json");

    	File jsonFile		 = new File(filename);
    	
    	try {
    		FileOutputStream out = new FileOutputStream(jsonFile);
    		out.write(Utils.beautifyJson(json.toString()).getBytes());
    		out.flush();
    		out.close();
    		Logger.info("RouteService.saveJsonToFile(): " + filename + " written successfully...");
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
 	}
    
    
    

}    
