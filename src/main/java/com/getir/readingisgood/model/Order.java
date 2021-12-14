package com.getir.readingisgood.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(targetEntity = Book.class, cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "book_fk", referencedColumnName = "id")
    private List<Book> books;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_fk", referencedColumnName = "id")
    private Customer customer;

    @Column(nullable = false)
    private Date date;
}
