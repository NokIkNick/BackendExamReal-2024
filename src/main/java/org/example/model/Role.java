package org.example.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Role {

    @Id
    private String name;

    @ManyToMany()
    List<User> users;

    public Role(String name) {
        this.name = name;
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        if(user != null && !users.contains(user)){
            users.add(user);
        }
    }
}
