/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.Address;
import entities.Hobby;
import entities.Person;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author andre
 */
public class SetupTestPersons {
    public static void main(String[] args) {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.DROP_AND_CREATE);
    EntityManager em = emf.createEntityManager();
    
    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords
    
    Hobby hobby1 = new Hobby("Football", "Fun");
    Hobby hobby2 = new Hobby("Swimming", "Fun");
    Hobby hobby3 = new Hobby("Dancing", "Fun");
    Hobby hobby4 = new Hobby("Sailing", "Fun");
    
    Address address1 = new Address("Strandgade 1", "Copenhagen", "2400");
    Address address2 = new Address("Strandgade 2", "Copenhagen", "2400");
    Address address3 = new Address("Strandgade 3", "Copenhagen", "2400");
    Address address4 = new Address("Strandgade 4", "Copenhagen", "2400");
    
    Person person1 = new Person("Andreas1", "Petersen1", "email1@email.com", "12345678");
    Person person2 = new Person("Andreas2", "Petersen2", "email2@email.com", "22345678");
    Person person3 = new Person("Andreas3", "Petersen3", "email3@email.com", "32345678");
    Person person4 = new Person("Andreas4", "Petersen4", "email4@email.com", "42345678");
    
    person1.addAddressToPerson(address1);
    person2.addAddressToPerson(address2);
    person3.addAddressToPerson(address3);
    person4.addAddressToPerson(address4);
    
    person1.addHobbyToPerson(hobby1);
    person1.addHobbyToPerson(hobby2);
    person2.addHobbyToPerson(hobby2);
    person3.addHobbyToPerson(hobby3);
    person4.addHobbyToPerson(hobby4);
    
    em.getTransaction().begin();
    em.persist(hobby1);
    em.persist(hobby2);
    em.persist(hobby3);
    em.persist(hobby4);
    em.persist(address1);
    em.persist(address2);
    em.persist(address3);
    em.persist(address4);
    em.persist(person1);
    em.persist(person2);
    em.persist(person3);
    em.persist(person4);
    em.getTransaction().commit();
    
    
    }
}
