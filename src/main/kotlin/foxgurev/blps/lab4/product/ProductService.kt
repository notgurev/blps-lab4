package foxgurev.blps.lab4.product

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductService @Autowired constructor(val productRepository: ProductRepository) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun getProducts(): List<Product> = productRepository.findTop10ByOrderById()

    fun getProduct(id: Long) = productRepository.findById(id)

    fun findAllById(IDs: Iterable<Long>) = productRepository.findAllById(IDs)

    fun returnToStock(items: List<Product>) {
        log.info("Returning to stock: ${items.map { "[" + it.id.toString() + "]" + it.name }}")
        items.forEach { it.changeAmountInStock(+1) }
    }
}