package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import errorhandling.NotFoundException;
import utils.EMF_Creator;
import facades.FacadeExample;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/prep1",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    private static final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);
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
        long count = FACADE.getPersonCount();
        return "{\"Person count\":"+count+"}";  //Done manually so no need for a DTO
    }
    
    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAll() {
       return GSON.toJson(FACADE.getAllPersons());
    }
    
    @Path("id/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByID(@PathParam("id") long id) throws NotFoundException {
        PersonDTO person = FACADE.getPersonOnId(id);
        return GSON.toJson(person);
    }
    
    @Path("email/{email}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByEmail(@PathParam("email") String email) throws NotFoundException {
        PersonDTO person = FACADE.getPersonOnEmail(email);
        return GSON.toJson(person);
    }
    
    @Path("phone/{phone}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByPhone(@PathParam("phone") String phone) throws NotFoundException {
        PersonDTO person = FACADE.getPersonOnPhone(phone);
        return GSON.toJson(person);
    }
    
    @Path("hobby/{hobby}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByHobby(@PathParam("hobby") String hobby) throws NotFoundException {
        List<PersonDTO> persons = FACADE.getPersonsFromHobby(hobby);
        return GSON.toJson(persons);
    }
 
}
