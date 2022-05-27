package foxgurev.blps.lab4.promocode

import javax.persistence.*

@Entity
@Table(name = "promocode")
class Promocode(
    @Id @GeneratedValue
    var id: Long = 0,

    @Column
    var code: String,

    @Column
    var discount: Int = 0,

    @Column
    @Enumerated(EnumType.STRING)
    var status: PromocodeStatus = PromocodeStatus.INACTIVE
)