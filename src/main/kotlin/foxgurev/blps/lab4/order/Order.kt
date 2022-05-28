package foxgurev.blps.lab4.order

import foxgurev.blps.lab4.product.Product
import foxgurev.blps.lab4.promocode.Promocode
import javax.persistence.*

@Entity
@Table(name = "order")
class Order(
    @Id @GeneratedValue
    var id: Long = 0,

    @ManyToMany
    var items: List<Product>,

    @ManyToOne
    var promocode: Promocode? = null,

    @Column
    var totalPrice: Int,

    @Column
    @Enumerated(EnumType.STRING)
    var status: OrderStatus,
)