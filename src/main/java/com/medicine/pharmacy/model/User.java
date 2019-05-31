package com.medicine.pharmacy.model;

import com.medicine.pharmacy.config.ValidPassword;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NotNull(message = "Write your name")
    @Size(min = 2, max = 40)
    private String name;

    @Column
    @NotNull(message = "Write your email")
    @Email
    private String email;

    @Column
    @NotNull
    private String password;

    @Column
    private int active;

    @Column
    @NotNull(message = "Write your phone number")
    private String phoneNumber;

    @OneToOne
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Role role;
}
