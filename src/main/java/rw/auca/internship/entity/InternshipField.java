package rw.auca.internship.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity 
@Table (name = "internship_field")
public class InternshipField {
   
    @Id 
    @GeneratedValue 
    private UUID id;

    
    private String name;

    @JsonIgnore 
    @ManyToMany (mappedBy = "internshipFields")
    private List<Supervisor> supervisors = new ArrayList<>();

    public InternshipField() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public String getName(){
    return name;
    }

    public void setName(String name){
        this.name = name ;
    }
public List<Supervisor> getSupervisors() {
    return supervisors;
}

public void setSupervisors(List<Supervisor> supervisors){
    this.supervisors = supervisors;
}

}