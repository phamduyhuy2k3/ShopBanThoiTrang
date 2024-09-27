package com.ddk.asmsof306.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String imageCover;
    private Double price;
    @Min(0)
    @Max(100)
    private int disCount;
    private Integer stock;
    private boolean available;
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Column(columnDefinition = "TEXT",length = 5000)
    private String description;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

}
