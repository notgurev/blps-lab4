package foxgurev.blps.lab4

import foxgurev.blps.lab4.product.Product
import foxgurev.blps.lab4.product.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class Startup @Autowired constructor(
    val productRepository: ProductRepository,
//    promocodeRepository: PromocodeRepository,
//    userRepository: UserRepository
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
        productRepository.saveAll(Arrays.asList(
            Product(name = "Набор для шитья", price = 100, inStock = 1000),
            Product(name = "Пластилин", price = 250, inStock = 2000),
            Product(name = "Полимерная глина", price = 150, inStock = 500)
        ))
//        promocodeRepository.saveAll(Arrays.asList(
//            Promocode("GORBUNOV", 50, PromocodeStatus.ACTIVE),
//            Promocode("USKOV", 50, PromocodeStatus.INACTIVE)
//        ))
//        userRepository.save(User("user@sd.com", "username",
//            "$2b$12\$CekwqWUxTHJKKCa8qEAOo.8pyOhGMMjdKDoBceMqAAp4/2TEAdr2.",
//            "89023457654", Role.ROLE_USER))
//        userRepository.save(User("admin@sd.com", "admin",
//            "$2b$12\$CekwqWUxTHJKKCa8qEAOo.8pyOhGMMjdKDoBceMqAAp4/2TEAdr2.",
//            "88005555555",
//            Role.ROLE_ADMIN))
        log.info("Successfully added initial entities")
    }
}