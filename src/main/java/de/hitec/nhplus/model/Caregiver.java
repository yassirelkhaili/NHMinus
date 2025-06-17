package de.hitec.nhplus.model;

public class Caregiver {
    private long cid;
    private String firstName;
    private String surname;
    private String telephone;
    private String qualification;
    private boolean isActive;

    public Caregiver(String firstName, String surname, String telephone, String qualification) {
        this.firstName = firstName;
        this.surname = surname;
        this.telephone = telephone;
        this.qualification = qualification;
        this.isActive = true;
    }

    public Caregiver(long cid, String firstName, String surname, String telephone, String qualification, boolean isActive) {
        this.cid = cid;
        this.firstName = firstName;
        this.surname = surname;
        this.telephone = telephone;
        this.qualification = qualification;
        this.isActive = isActive;
    }

    // Getters and setters
    public long getCid() { return cid; }
    public void setCid(long cid) { this.cid = cid; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    @Override
    public String toString() {
        return surname + ", " + firstName;
    }
}