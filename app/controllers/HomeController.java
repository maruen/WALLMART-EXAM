package controllers;

import static constants.Constants.WALLMART_HOME;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import play.mvc.Controller;
import play.mvc.Result;


import views.html.index;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
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
    
    
    static String readFile(String path, Charset encoding) throws IOException  {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
     }

}
