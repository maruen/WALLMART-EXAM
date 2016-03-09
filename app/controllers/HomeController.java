package controllers;

import static constants.Constants.WALLMART_HOME;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Utils;

import views.html.index;

public class HomeController extends Controller {

    public Result index() {
        String wallmartHome = System.getenv(WALLMART_HOME);
        wallmartHome = wallmartHome.concat(File.separator).concat("README");
        
        String readme = null;
        try {
            readme = readFile(wallmartHome,Charset.defaultCharset());
        } catch (IOException e) {
            readme    = ""; 
        }
        
        return ok(index.render(readme));
    }

    public Result downloadInsertMapJson() {

        Date currentDate = new Date();
        SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMyyyyHHmmss");
        String filename = "/var/tmp/insertMapJson." + sdf2.format(currentDate) + ".json";
        
        String json    =  System.getenv(WALLMART_HOME);
        json           =  json.concat(File.separator)
                                                .concat("scripts")
                                                .concat(File.separator)
                                                .concat("insertMap.json");

        try {
            FileOutputStream out =  new FileOutputStream(new File(filename));
            String insertMapJsonAsString = readFile(json, Charset.defaultCharset());
            out.write(insertMapJsonAsString.getBytes());
            out.close();
            Logger.info("HomeController.downloadInsertMapJson: " + filename + " downloaded successfully..");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        response().setHeader("Content-disposition","attachment; filename=insertMap.json");

        return ok(new File(filename));

    }
    
    
    public Result downloadInsertMapScript() {

        Date currentDate = new Date();
        SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMyyyyHHmmss");
        String filename = "/var/tmp/insertMapScript." + sdf2.format(currentDate) + ".sh";
        
        String script    =  System.getenv(WALLMART_HOME);
        script           =  script.concat(File.separator)
                                               .concat("scripts")
                                               .concat(File.separator)
                                               .concat("insertMap.sh");

        try {
            FileOutputStream out =  new FileOutputStream(new File(filename));
            String insertMapJsonAsString = readFile(script, Charset.defaultCharset());
            out.write(insertMapJsonAsString.getBytes());
            out.close();
            Logger.info("HomeController.downloadInsertMapScript: " + filename + " downloaded successfully..");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        response().setHeader("Content-disposition","attachment; filename=insertMap.sh");

        return ok(new File(filename));

    }
    
    
    public Result downloadGetRouteWithLessCostScript() {

        Date currentDate = new Date();
        SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMyyyyHHmmss");
        String filename = "/var/tmp/getRouteWithLessCostScript." + sdf2.format(currentDate) + ".sh";
        
        String script    =  System.getenv(WALLMART_HOME);
        script           =  script.concat(File.separator)
                                               .concat("scripts")
                                               .concat(File.separator)
                                               .concat("getRouteWithLessCostScript.sh");

        try {
            FileOutputStream out =  new FileOutputStream(new File(filename));
            String insertMapJsonAsString = readFile(script, Charset.defaultCharset());
            out.write(insertMapJsonAsString.getBytes());
            out.close();
            Logger.info("HomeController.downloadGetRouteWithLessCostScript: " + filename + " downloaded successfully..");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        response().setHeader("Content-disposition","attachment; filename=getRouteWithLessCost.sh");

        return ok(new File(filename));

    }
    
    
    
    
    
    
    static String readFile(String path, Charset encoding) throws IOException  {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
     }

}
