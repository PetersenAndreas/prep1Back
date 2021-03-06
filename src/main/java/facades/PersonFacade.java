/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.PersonDTO;
import entities.Hobby;
import entities.Person;
import errorhandling.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author andre
 */
public class PersonFacade {
    
    private static PersonFacade instance;
    private static EntityManagerFactory emf;
    
    private PersonFacade() {
    }
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }
    
    public long getPersonCount() {
        EntityManager em = getEntityManager();
        try {
            long personCount = (long) em.createQuery("SELECT COUNT(p) FROM Person p").getSingleResult();
            return personCount;
        } finally {
            em.close();
        }
    }
    
     public List<PersonDTO> getAllPersons() {
        EntityManager em = getEntityManager();
        List<PersonDTO> pDTOList = new ArrayList();
        try {
            TypedQuery<Person> q = em.createNamedQuery("Person.getAll", Person.class);
            for (Person p : q.getResultList()) {
                pDTOList.add(new PersonDTO(p));
            }
            return pDTOList;
        } finally {
            em.close();
        }
    }
    
    public PersonDTO getPersonOnId(Long id) throws NotFoundException {
        EntityManager em = getEntityManager();
        try {
           Person person = em.find(Person.class, id);
        if(person == null) {
            throw new NotFoundException("No matching id");
        } else {
            return new PersonDTO(person);
        }
        } finally {
            em.close();
        }
    }
    
    public PersonDTO getPersonOnEmail(String email) throws NotFoundException {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> tq = em.createQuery("SELECT p FROM Person p WHERE p.email = :email", Person.class);
            tq.setParameter("email", email);
            Person person = tq.getSingleResult();
            PersonDTO result = new PersonDTO(person);
            return result;
        } catch (NoResultException e) {
            throw new NotFoundException("No person with given Email");
        } finally {
            em.close();
        }
    }
    
    public PersonDTO getPersonOnPhone(String phone) throws NotFoundException {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> tq = em.createQuery("SELECT p FROM Person p WHERE p.phone = :phone", Person.class);
            tq.setParameter("phone", phone);
            Person person = tq.getSingleResult();
            PersonDTO result = new PersonDTO(person);
            return result;
        } catch (NoResultException e) {
            throw new NotFoundException("No person with given Phone");
        } finally {
            em.close();
        }
    }
    
    public List<PersonDTO> getPersonsFromHobby(String hobby) throws NotFoundException {
        EntityManager em = getEntityManager();
        List<PersonDTO> phDTOList = new ArrayList();
        try {
            Hobby q = em.createQuery("SELECT h FROM Hobby h WHERE h.name = :hobby", Hobby.class).setParameter("hobby", hobby).getSingleResult();
            for (Person p : q.getPersons()) {
                phDTOList.add(new PersonDTO(p));
            }
            return phDTOList;
        } finally {
            em.close();
        }
    }
     
}
