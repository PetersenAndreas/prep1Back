/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Hobby;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andre
 */
public class HobbyDTO {
    
    private String description;
    private String name;
    
     public HobbyDTO(Hobby hobby) {
        this.name = hobby.getName();
        this.description = hobby.getDescription();
    }

    public HobbyDTO() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public static List<HobbyDTO> convertHobbyListToDTO(List<Hobby> hobbies){
        List<HobbyDTO> hobbiesDTO = new ArrayList<>();
        for (Hobby hobby : hobbies) {
            hobbiesDTO.add(new HobbyDTO(hobby));
        }
        return hobbiesDTO;
    } 
    
}
