package foxgurev.blps.lab4.order

import foxgurev.blps.lab4.product.Product
import javax.persistence.*

@Entity
@Table(name = "order")
class Order(
    @Id @GeneratedValue
    var id: Long = 0,

    @ManyToMany
    var items: List<Product>,

//    @ManyToOne
//    private val promocode: Promocode,

    @Column
    var totalPrice: Int,

//    @Column
//    private val name: String,
//
//    @Column
//    private val surname: String,
//
//    @Column
//    private val phoneNumber: String,
//
//    @Column
//    private val email: String,
//
//    @Column
//    private val city: String,

    @Column
    var status: OrderStatus,
)