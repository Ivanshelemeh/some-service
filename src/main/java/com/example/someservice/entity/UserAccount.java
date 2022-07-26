package com.example.someservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.jfr.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@NoArgsConstructor
@Entity
@Table(name = "account")
public class UserAccount {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(password, that.password) && Objects.equals(roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, roles);
    }

    @Id
   //@SequenceGenerator(name="seq-gen",sequenceName="MY_SEQ_GEN",initialValue=205, allocationSize=12)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "id")
    private Long id;

    @Column(name = "account_name", unique = true)
    @NotBlank(message = "This name is required")
    @Size(min = 4)
    private String name;

    @Column(name = "created_at")
    @Timestamp
    private LocalDateTime createdAt;

    @Column(name = "count_login")
    @JsonIgnore
    private Integer attemptLogin;

    @Column(name = "account_block")
    private boolean accountNonBlocked;

    @NotBlank(message = "This password is required")
    @Column(name = "account_password")
    @JsonIgnore
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "account_role", joinColumns = @JoinColumn(name = "account_id"))
    @Enumerated(value = EnumType.STRING)
    private Set<Role> roles;


    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }

}
