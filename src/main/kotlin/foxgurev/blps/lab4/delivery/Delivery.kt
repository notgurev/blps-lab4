package foxgurev.blps.lab4.delivery

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "delivery")
class Delivery(
    @Id
    @GeneratedValue
    var id: Long = 0,

    @Column
    var orderId: Long,

    @Column
    var date: LocalDateTime,

    @Column
    var name: String,

    @Column
    var phoneNumber: String?,

    @Column
    var email: String?,

    @Column
    var address: String,
)