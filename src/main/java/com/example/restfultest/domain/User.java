package com.example.restfultest.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@Table(name = "usr")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Это поле не может быть пустым")
    private String username;

    public User(String username) {
        this.username = username;
    }

    @OneToOne(cascade = CascadeType.ALL)
    private Account account = new Account();


}
