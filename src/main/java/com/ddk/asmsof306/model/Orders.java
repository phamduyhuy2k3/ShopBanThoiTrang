package com.ddk.asmsof306.model;

import com.ddk.asmsof306.enums.OrdersStatus;
import com.ddk.asmsof306.enums.PAYMENTMETHOD;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JsonBackReference
    private Account account;

    private String address;
    @NotBlank(message = "Số điện thoại đang để trống")
    private String phoneNumber;
    private Double totalPrice;
    @Temporal(TemporalType.DATE)
    private Date orderedAt;
    @OneToMany(mappedBy = "orders",cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.EAGER)
    @JsonIgnore
    private List<OrdersDetail> ordersDetails;
    @Enumerated
    private PAYMENTMETHOD paymentmethod;
    @Enumerated
    private OrdersStatus ordersStatus;
}
