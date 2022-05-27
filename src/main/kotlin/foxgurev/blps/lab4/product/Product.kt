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

//    @Column
//    var watermark: Int? = null,
//
//    @Column
//    var markedForResupply: Boolean? = null
) {
//    constructor(name: String, price: Int, inStock: Int) : this(0, name, price, inStock)

    fun changeAmountInStock(delta: Int): Int {
        if (inStock == 0 && delta < 0) throw RuntimeException("No amount of product left in stock") // todo bpmn exception
        inStock += delta
        return inStock
    }
}