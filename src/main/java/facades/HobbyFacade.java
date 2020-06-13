/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.HobbyDTO;
import dto.PersonDTO;
import entities.Hobby;
import entities.Person;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author andre
 */
public class HobbyFacade {

    public HobbyFacade() {
    }
    
    private static HobbyFacade instance;
    private static EntityManagerFactory emf;
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public static HobbyFacade getHobbyFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyFacade();
        }
        return instance;
    }
    
    public long getHobbyCount() {
        EntityManager em = getEntityManager();
        try {
            long hobbyCount = (long) em.createQuery("SELECT COUNT(h) FROM Hobby h").getSingleResult();
            return hobbyCount;
        } finally {
            em.close();
        }
    }
    
    public List<HobbyDTO> getAllHobbies() {
        EntityManager em = getEntityManager();
        List<HobbyDTO> hDTOList = new ArrayList();
        try {
            TypedQuery<Hobby> q = em.createNamedQuery("Hobby.getAll", Hobby.class);
            for (Hobby h : q.getResultList()) {
                hDTOList.add(new HobbyDTO(h));
            }
            return hDTOList;
        } finally {
            em.close();
        }
    }
}
