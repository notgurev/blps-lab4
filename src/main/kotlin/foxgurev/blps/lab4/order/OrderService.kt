package foxgurev.blps.lab4.order

import foxgurev.blps.lab4.ProcessExceptions
import foxgurev.blps.lab4.product.ProductService
import foxgurev.blps.lab4.promocode.PromocodeService
import foxgurev.blps.lab4.promocode.PromocodeStatus
import org.camunda.bpm.engine.delegate.BpmnError
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
//@Transactional
class OrderService @Autowired constructor(
    val orderRepository: OrderRepository,
    val promocodeService: PromocodeService,
    val productService: ProductService
) {
    private val log = LoggerFactory.getLogger(javaClass)

    //    private final ProductRepository productRepository;
    //    private final DeliveryService deliveryService;
    //    private final ProductService productService;
    //    private final KafkaTemplate<String, ProductSupply> productSupplyQueue;

    fun createOrder(productIDs: List<Long>, promocodeName: String?): Long {
        val promocode = promocodeName?.let {
            promocodeService.getPromocode(promocodeName) ?: throw BpmnError(
                ProcessExceptions.PROMOCODE_NOT_FOUND,
                "Промокод не найден"
            )
        }

        promocode?.let {
            if (promocode.status == PromocodeStatus.INACTIVE) {
                throw BpmnError(ProcessExceptions.PROMOCODE_INACTIVE, "Промокод не активен")
            }
        }

        val products = productService.findAllById(productIDs)

        products.forEach {
            it.changeAmountInStock(-1)
//            val inStock: Int = it.changeAmountInStock(-1)
//            if (inStock <= p.getWatermark() && !p.getMarkedForResupply()) {
//                p.setMarkedForResupply(true)
//                OrderService.log.info("Marked product with id = {} for resupply", p.getId())
//                productSupplyQueue.send("resupply", ProductSupply(p.getId(), 1))
//            }
        }

        val price = products.sumOf { it.price } * (100 - (promocode?.discount ?: 0)) / 100
        val saved = orderRepository.save(Order(items = products, totalPrice = price, status = OrderStatus.CREATED))

        log.info("Created an order with id = ${saved.id} and products ${products.map { it.name }}")

        return saved.id
    }
//
//    fun changeStatus(id: Long, newStatus: OrderStatus) {
//        val order = findOrder(id)
//        checkStatusFlow(order.getStatus(), newStatus)
//        order.setStatus(newStatus)
//    }
//
//    private fun findOrder(id: Long): Order {
//        return orderRepository!!.findById(id).orElseThrow(
//            Supplier<RuntimeException> { VisibleException("Order (id = $id) doesn't exist") }
//        )
//    }

//    private fun checkStatusFlow(current: OrderStatus, next: OrderStatus) {
//        if (current === OrderStatus.CANCELLED) {
//            throw VisibleException("Changing status of cancelled order is not allowed")
//        }
//        if (next === OrderStatus.CANCELLED) {
//            return
//        }
//        when (next.ordinal - current.ordinal) {
//            0 -> throw VisibleException("This status is already assigned")
//            1 -> return
//            else -> throw VisibleException("Illegal change of status (" + current.name + " -> " + next.name + ")")
//        }
//    }

//    fun cancelOrder(id: Long) {
//        val order = findOrder(id)
//        when (order.getStatus()) {
//            CANCELLED -> throw VisibleException("This order is already cancelled")
//            DELIVERED -> throw VisibleException("Cannot cancel a delivered order")
//            SHIPPING -> throw VisibleException("Cannot cancel an order in shipping")
//            PACKED -> {
//                deliveryService.cancelDelivery(id)
//                productService.returnToStock(order.getItems())
//            }
//            CREATED -> productService.returnToStock(order.getItems())
//        }
//        order.setStatus(OrderStatus.CANCELLED)
//    }
//
//    fun packOrderAndOrganizeDelivery(orderId: Long) {
//        changeStatus(orderId, OrderStatus.PACKED)
//        val freeDate: LocalDateTime = deliveryService.selectDeliveryDate()
//        deliveryService.addDelivery(Delivery(orderId, freeDate))
//    }
}