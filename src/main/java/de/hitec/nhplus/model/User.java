package de.hitec.nhplus.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDateTime;

/**
 * User class representing system users (nurses, doctors, administrators, etc.)
 * Extends Person to inherit basic personal information
 */
public class User extends Person {
    private final SimpleIntegerProperty uid;
    private final SimpleStringProperty username;
    private final SimpleStringProperty email;
    private final SimpleStringProperty passwordHash;
    private final SimpleStringProperty role;
    private final SimpleBooleanProperty isActive;
    private final SimpleObjectProperty<LocalDateTime> createdAt;
    private final SimpleObjectProperty<LocalDateTime> updatedAt;

    /**
     * Constructor for creating a new User (without ID, for database insertion)
     */
    public User(String firstName, String surname, String username, String email,
            String passwordHash, String role, boolean isActive,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(firstName, surname);
        this.uid = new SimpleIntegerProperty();
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.passwordHash = new SimpleStringProperty(passwordHash);
        this.role = new SimpleStringProperty(role);
        this.isActive = new SimpleBooleanProperty(isActive);
        this.createdAt = new SimpleObjectProperty<>(createdAt);
        this.updatedAt = new SimpleObjectProperty<>(updatedAt);
    }

    /**
     * Constructor for existing User (with ID, from database)
     */
    public User(int uid, String firstName, String surname, String username, String email,
            String passwordHash, String role, boolean isActive,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(firstName, surname, username, email, passwordHash, role, isActive, createdAt, updatedAt);
        this.uid.set(uid);
    }

    /**
     * Simplified constructor (for basic usage)
     */
    public User(String firstName, String surname, String username, String email) {
        this(firstName, surname, username, email, "", "USER", true,
                LocalDateTime.now(), LocalDateTime.now());
    }

    // UID property methods
    public int getUid() {
        return uid.get();
    }

    public SimpleIntegerProperty uidProperty() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid.set(uid);
    }

    // Username property methods
    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    // Email property methods
    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    // Password hash property methods
    public String getPasswordHash() {
        return passwordHash.get();
    }

    public SimpleStringProperty passwordHashProperty() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash.set(passwordHash);
    }

    // Role property methods
    public String getRole() {
        return role.get();
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    // Active status property methods
    public boolean isActive() {
        return isActive.get();
    }

    public SimpleBooleanProperty isActiveProperty() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive.set(isActive);
    }

    // Created at property methods
    public LocalDateTime getCreatedAt() {
        return createdAt.get();
    }

    public SimpleObjectProperty<LocalDateTime> createdAtProperty() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt.set(createdAt);
    }

    // Updated at property methods
    public LocalDateTime getUpdatedAt() {
        return updatedAt.get();
    }

    public SimpleObjectProperty<LocalDateTime> updatedAtProperty() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt.set(updatedAt);
    }

    /**
     * Convenience method to get full name
     */
    public String getFullName() {
        return getFirstName() + " " + getSurname();
    }

    /**
     * Check if user has admin privileges
     */
    public boolean isAdmin() {
        return "ADMIN".equals(getRole());
    }

    /**
     * Check if user is medical staff (doctor or nurse)
     */
    public boolean isMedicalStaff() {
        String userRole = getRole();
        return "DOCTOR".equals(userRole) || "NURSE".equals(userRole);
    }

    @Override
    public String toString() {
        return String.format("User{uid=%d, username='%s', fullName='%s', role='%s', active=%s}",
                getUid(), getUsername(), getFullName(), getRole(), isActive());
    }
}