package facades;

import dto.HobbyDTO;
import dto.PersonDTO;
import entities.Address;
import entities.Hobby;
import entities.Person;
import utils.EMF_Creator;
import entities.RenameMe;
import errorhandling.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.Settings;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class HobbyFacadeTest {

    private static EntityManagerFactory emf;
    private static HobbyFacade FACADE;
    private static Person person1, person2, person3;
    private static Hobby hobby1, hobby2, hobby3;
    private static Address address1, address2;
    private static Person[] personArray;
    private static Long highestId;
    private static Hobby[] hobbyArray;

    public HobbyFacadeTest() {
    }

    //@BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/startcode_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.DROP_AND_CREATE);
        FACADE = HobbyFacade.getHobbyFacade(emf);
    }

    /*   **** HINT **** 
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that. 
        See below for how to use these files. This is our RECOMENDED strategy
     */
    @BeforeAll
    public static void setUpClassV2() {
       emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
       FACADE = HobbyFacade.getHobbyFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        //emptyDatabase();
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
            address1 = new Address("Strandgade 14", "Copenhagen", "1401");
            address2 = new Address("Lyngbyvej 23", "Lyngby", "2800");
            hobby1 = new Hobby("Handball", "Sticky");
            hobby2 = new Hobby("Football", "Not fun");
            hobby3 = new Hobby("Swimming", "Is wet");
            List<Hobby> hobbies1 = new ArrayList<>(); 
            List<Hobby> hobbies2 = new ArrayList<>(); 
            hobbies1.add(hobby1);
            hobbies1.add(hobby3);
            hobbies2.add(hobby2);
            person1 = new Person("Lars", "Larsen", "larsen@gmail.com", "98765432", hobbies2, address1);
            person2 = new Person("Jens", "Jensen", "jensen@hotmail.dk", "12345678", hobbies1, address2);
            person3 = new Person("Bo", "Bosen", "bosen@hotmail.dk", "32165487", hobbies2, address2);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            
            em.persist(hobby1);
            em.persist(hobby2);
            em.persist(hobby3);
            
            em.persist(address1);
            em.persist(address2);
            
            em.persist(person1);
            em.persist(person2);
            em.persist(person3);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
        personArray = new Person[]{person1, person2, person3};
        hobbyArray = new Hobby[] {hobby1, hobby2, hobby3};

        highestId = 0L;
        for (Person person : personArray) {
            if (person.getId() > highestId) {
                highestId = person.getId();
            }
        }
    }
    
    

    @AfterEach
    public void tearDown() {
        //emptyDatabase();
    }
    
    private static void emptyDatabase() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testHobbyFacade() {
        long result = FACADE.getHobbyCount();
        int expectedResult = hobbyArray.length;
        assertEquals(expectedResult, result);
    }
    
    @Test
    public void testGetAllHobby() throws NotFoundException {
        List<HobbyDTO> hobbies = FACADE.getAllHobbies();
        assertEquals(3, hobbies.size());
    }
    
}
