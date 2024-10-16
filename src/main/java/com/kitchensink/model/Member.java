package com.kitchensink.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="members", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

}
