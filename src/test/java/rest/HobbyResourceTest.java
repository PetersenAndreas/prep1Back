package rest;

import entities.Address;
import entities.Hobby;
import entities.Person;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class HobbyResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person p1, p2, p3;
    private static Hobby hobby1, hobby2, hobby3, hobby4;
    private static Address address1, address2, address3;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }
    
    public HobbyResourceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
//        // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
//        // CHANGE the three passwords below, before you uncomment and execute the code below
//        // Also, either delete this file, when users are created or rename and add to .gitignore
//        // Whatever you do DO NOT COMMIT and PUSH with the real passwords

    EntityManager em = emf.createEntityManager();
        address1 = new Address("KagsåKollegiet", "Copenhagen", "2300");
        address2 = new Address("Fredensbovej", "Hvidovre", "2650");
        address3 = new Address("Kattevej", "Ballerup", "2750");
        
        hobby1 = new Hobby("Gaming", "Wasting time in front of computer or TV");
        hobby2 = new Hobby("Swimming", "Getting wet");
        hobby3 = new Hobby("Fishing", "Getting up early and doing nothing for 5 hours");
        hobby4 = new Hobby("D&D", "Very nerdy game");
        
        List<Hobby> hobbiesL1 = new ArrayList<>();
        List<Hobby> hobbiesL2 = new ArrayList<>();
        List<Hobby> hobbiesL3 = new ArrayList<>();
        hobbiesL1.add(hobby1);
        hobbiesL1.add(hobby2);
        hobbiesL2.add(hobby1);
        hobbiesL2.add(hobby3);
        hobbiesL3.add(hobby4);  
        hobbiesL3.add(hobby2);
        
        p1 = new Person("Caroline", "HoegIversen", "carol@hoeg.iversen", "20394857", hobbiesL1, address1);
        p2 = new Person("Tobias", "AnkerB-J", "tobias@anker.boldtJ", "12345678", hobbiesL2, address2);
        p3 = new Person("Allan", "Simonsen", "allan@bo.simonsen", "98786756", hobbiesL3, address3);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            
            em.persist(hobby1);
            em.persist(hobby2);
            em.persist(hobby3);
            em.persist(hobby4);
            
            em.persist(address1);
            em.persist(address2);
            em.persist(address3);
            
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/hobby").then().statusCode(200);
    }

    //GET
    @Test
    public void testGetAllHobbies() {
        given()
                .contentType("application/json")
                .get("/hobby/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", containsInAnyOrder("Swimming", "Fishing", "Gaming", "D&D"))
                .body("description", containsInAnyOrder("Wasting time in front of computer or TV",
                        "Getting wet",
                        "Very nerdy game",
                        "Getting up early and doing nothing for 5 hours"));
    }


}
