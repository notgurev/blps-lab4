package foxgurev.blps.lab4.product

import javax.persistence.*

@Entity
@Table(name = "product")
class Product(
    @Id @GeneratedValue
    var id: Long = 0,

    @Column
    var name: String = "",

    @Column
    var price: Int = 0,

    @Column
    var inStock: Int = 0,

    @Column
    var watermark: Int = 1,

    @Column
    var markedForResupply: Boolean = false
) {
    fun changeAmountInStock(delta: Int): Int {
        if (inStock == 0 && delta < 0) throw RuntimeException("No amount of product left in stock") // todo bpmn exception
        inStock += delta
        return inStock
    }
}