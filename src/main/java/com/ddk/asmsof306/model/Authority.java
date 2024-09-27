package com.ddk.asmsof306.model;

import com.ddk.asmsof306.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table( uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account_id", "role"})
})
public class Authority implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne()
    @JsonBackReference
    private Account account;
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    public Authority(String role) {
        this.role = Role.valueOf(role);
    }
}
