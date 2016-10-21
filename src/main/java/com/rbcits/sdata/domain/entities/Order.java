package com.rbcits.sdata.domain.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ORDERS")
public class Order  implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;

    @OneToOne
    @JoinColumn(name="PET_ID")
    private Pet pet;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "SHIP_DATE")
    private Date shipDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private OrderStatus status;

    @Column(name = "COMPLETE")
    private Boolean complete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", pet=" + pet +
                ", quantity=" + quantity +
                ", shipDate=" + shipDate +
                ", status=" + status +
                ", complete=" + complete +
                '}';
    }
}
