package foxgurev.blps.lab4.product

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductService @Autowired constructor(val productRepository: ProductRepository) {
    fun getProducts(): List<Product> = productRepository.findTop10ByOrderById()

    fun getProduct(id: Long) = productRepository.findById(id) // todo err handling

    fun findAllById(IDs: Iterable<Long>) = productRepository.findAllById(IDs)

//    fun returnToStock(items: List<Product>) {
//        items.forEach(Consumer { product: Product -> product.changeAmountInStock(+1) })
//        productRepository.saveAll(items)
//    }
}