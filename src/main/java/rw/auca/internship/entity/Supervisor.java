package rw.auca.internship.entity;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "supervisor")

public class Supervisor {
    

    @Id 
    @GeneratedValue 
    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    @ManyToOne 
    @JoinColumn (name = "host_company_id")
private HostCompany hostCompany;

    @ManyToMany 
    @JoinTable (
        name = "supervisor_internship_field",
        joinColumns = @JoinColumn (name = "supervisor_id"),
        inverseJoinColumns = @JoinColumn (name = "internship_field_id")
    )
    private List<InternshipField> internshipFields = new ArrayList<>();

    public Supervisor() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HostCompany getHostCompany() {
        return hostCompany;
    }

    public void setHostCompany(HostCompany hostCompany) {
        this.hostCompany = hostCompany;
    }

    public List<InternshipField> getInternshipFields() {
        return internshipFields;
    }

    public void setInternshipFields(List<InternshipField> internshipFields) {
        this.internshipFields = internshipFields;
    }
    

}
