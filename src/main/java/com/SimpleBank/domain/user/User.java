package com.SimpleBank.domain.user;

import com.SimpleBank.dtos.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name="users")
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor //Create a no args construct.
@EqualsAndHashCode(of ="id") //Set the identifier of a User.

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Generate a random number for entity, automatically but not the safest option.
    private Long id;

    private String FirstName;

    private String LastName;

    @Column(unique = true)
    private Long document;

    @Column(unique = true) //says that the email and document has to be unique
    private String email;

    private String password;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(UserDTO data){
        this.FirstName = data.firstName();
        this.LastName = data.lastName();
        this.document = data.document();
        this.email = data.email();
        this.password = data.password();
        this.balance = data.balance();
    }
}
