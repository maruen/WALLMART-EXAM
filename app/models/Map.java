package models;

import static com.avaje.ebean.Expr.eq;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.Logger;
import play.data.validation.Constraints;

import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;

@Entity
public class Map extends Model {

    @Id  public String id;
    
    @Constraints.Required   public String name;
    @Constraints.Required   public String origin;
    @Constraints.Required   public String destiny;
    @Constraints.Required   public String distance;
    
    public Map() {}
    
    public Map(String id,
               String name,
               String origin,
               String destiny,
               String distance) {
        
        super();
        this.id         = id;
        this.name       = name;
        this.origin     = origin;
        this.destiny    = destiny;
        this.distance   = distance;
    }
    
    public static Finder<String,Map> find = new Finder<String,Map>(Map.class); 
    
    public static Map getById(String id) {
        Map map = find.byId(id);
        return map;
    }
    
    public static List<Map> getRoutesByMap(String name) {
        ExpressionList<Map> criteria = find.where();
        criteria.add(eq("name", name));
        return criteria.findList();
    }
    
}


