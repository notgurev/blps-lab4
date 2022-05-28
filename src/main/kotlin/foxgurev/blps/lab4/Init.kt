package foxgurev.blps.lab4

import foxgurev.blps.lab4.product.Product
import foxgurev.blps.lab4.product.ProductRepository
import foxgurev.blps.lab4.promocode.Promocode
import foxgurev.blps.lab4.promocode.PromocodeRepository
import foxgurev.blps.lab4.promocode.PromocodeStatus
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class Init @Autowired constructor(
    val productRepository: ProductRepository,
    val promocodeRepository: PromocodeRepository,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @EventListener(ApplicationReadyEvent::class)
    @Transactional
    fun init() {
        if (productRepository.count() > 0) {
            log.info("Already initialized, skipping adding initial entities")
            return
        }
        log.info("Adding initial entities...")
        productRepository.saveAll(
            listOf(
                Product(name = "Набор для шитья", price = 100, inStock = 1000),
                Product(name = "Пластилин", price = 250, inStock = 2000),
                Product(name = "Полимерная глина", price = 150, inStock = 500),
                Product(name = "Бумага для печати", price = 50, inStock = 0)
            )
        )
        promocodeRepository.saveAll(
            listOf(
                Promocode(code = "TSOPA", discount = 30, status = PromocodeStatus.ACTIVE),
                Promocode(code = "GORBUNOV", discount = 50, status = PromocodeStatus.ACTIVE),
                Promocode(code = "USKOV", discount = 70, status = PromocodeStatus.INACTIVE)
            )
        )
        log.info("Successfully added initial entities")
    }
}