package com.epam.learn.shchehlov.webspringshop.entity;

import com.epam.learn.shchehlov.webspringshop.entity.attribute.DeliveryPayment;
import com.epam.learn.shchehlov.webspringshop.entity.attribute.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "state_detail")
    private String stateDetail;

    @Column(name = "payment_details")
    private String paymentDetails;

    @Column(name = "delivery_payment")
    @Enumerated(EnumType.STRING)
    private DeliveryPayment deliveryPayment;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private Set<OrderedProduct> productSet;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Order)) {
            return false;
        }
        Order order = (Order) object;
        return getId() == order.getId() && getOrderStatus() == order.getOrderStatus() && Objects.equals(getStateDetail(), order.getStateDetail()) && Objects.equals(getPaymentDetails(), order.getPaymentDetails()) && getDeliveryPayment() == order.getDeliveryPayment() && Objects.equals(getDateTime(), order.getDateTime()) && Objects.equals(getUser(), order.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOrderStatus(), getStateDetail(), getPaymentDetails(), getDeliveryPayment(), getDateTime(), getUser());
    }
}
