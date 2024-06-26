package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User {
    @Id
    private String username;
    private String password;

    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name="user_name", referencedColumnName = "username")},
            inverseJoinColumns = {
            @JoinColumn(name = "role_name", referencedColumnName = "name")})
    @ManyToMany(cascade = CascadeType.DETACH)
    Set<Role> roles = new HashSet<>();

    @PrePersist
    private void PrePersist(){
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, Role role){
        this.username = username;
        this.password = password;
        addRole(role);
    }


    public Set<String> getRolesToString(){
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }

    public boolean verifyPassword(String password){
        return BCrypt.checkpw(password, this.password);
    }


    public void addRole(Role role){
        if(role != null && !roles.contains(role)){
            roles.add(role);
            role.addUser(this);
        }
    }

    public String rolesToString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Role role : roles) {
            stringBuilder.append(role.getName()).append(", ");
        }

        // Fjerner det sidste komma og mellemrum
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }

        return stringBuilder.toString();
    }

}
