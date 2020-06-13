package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import errorhandling.NotFoundException;
import utils.EMF_Creator;
import facades.FacadeExample;
import facades.HobbyFacade;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("hobby")
public class HobbyResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/prep1",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    private static final HobbyFacade FACADE =  HobbyFacade.getHobbyFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = FACADE.getHobbyCount();
        return "{\"Hobby count\":"+count+"}";  //Done manually so no need for a DTO
    }
    
    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAll() {
       return GSON.toJson(FACADE.getAllHobbies());
    }
    
    
 
}
