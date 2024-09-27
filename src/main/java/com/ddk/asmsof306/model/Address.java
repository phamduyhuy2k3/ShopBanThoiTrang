package com.ddk.asmsof306.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Địa chỉ giao hàng đang để trống")
    private String addressLine;
    @NotBlank(message = "Tỉnh/thành đang  để trống")
    private String city;
    @NotBlank(message = "Quốc gia đang để trống")
    private String country;
    @NotBlank(message = "Mã bưu điện đang để trống")
    private String postalCode;
    @JsonBackReference
    @ManyToOne
    private Account account;
}