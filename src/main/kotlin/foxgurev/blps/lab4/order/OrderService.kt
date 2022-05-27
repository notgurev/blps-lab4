package foxgurev.blps.lab4.order

import foxgurev.blps.lab4.product.ProductService
import foxgurev.blps.lab4.promocode.PromocodeService
import org.camunda.bpm.model.bpmn.BpmnModelException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OrderService @Autowired constructor(
    val orderRepository: OrderRepository,
    val promocodeService: PromocodeService,
    val productService: ProductService
) {
    //    private final ProductRepository productRepository;
    //    private final DeliveryService deliveryService;
    //    private final ProductService productService;
    //    private final KafkaTemplate<String, ProductSupply> productSupplyQueue;

    fun createOrder(ocr: OrderCreationRequest): Long {
        val promocode = promocodeService.getPromocode(ocr.promocode) ?: throw BpmnModelException("Промокод не найден")
        val products = productService.findAllById(ocr.products)

        products.forEach {
            it.changeAmountInStock(-1)
//            val inStock: Int = it.changeAmountInStock(-1)
//            if (inStock <= p.getWatermark() && !p.getMarkedForResupply()) {
//                p.setMarkedForResupply(true)
//                OrderService.log.info("Marked product with id = {} for resupply", p.getId())
//                productSupplyQueue.send("resupply", ProductSupply(p.getId(), 1))
//            }
        }

        val price = products.map { it.price }.sum() * (100 - promocode.discount) / 100
        val saved = orderRepository.save(Order(items = products, totalPrice = price, status = OrderStatus.CREATED))
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