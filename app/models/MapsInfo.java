package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints;

import com.avaje.ebean.Model;

@Entity
public class MapsInfo extends Model {

    @Id
    @Constraints.Min(10)
    public Long id;

    @Constraints.Required
    public String map;
    
    @Constraints.Required
    public String origin;
    
    @Constraints.Required
    public String destiny;
    
    @Constraints.Required
    public String distance;
    
    
    
}
