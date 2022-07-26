package com.example.someservice.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pet")
public class Pet {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pet pet = (Pet) o;

        if (!id.equals(pet.id)) return false;
        if (bithDay != null ? !bithDay.equals(pet.bithDay) : pet.bithDay != null) return false;
        if (!sex.equals(pet.sex)) return false;
        return nickName.equals(pet.nickName);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (bithDay != null ? bithDay.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        return result;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "day_bith", nullable = false)
    @CreationTimestamp
    private LocalDateTime bithDay;

    @Column(name = "pet_sex", nullable = false)
    @NotBlank(message = "A sex is required")
    private String sex;

    @Column(name = "pet_name", nullable = true)
    private String nickName;

}
