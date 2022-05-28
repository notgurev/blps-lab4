package foxgurev.blps.lab4.order

import foxgurev.blps.lab4.ProcessExceptions
import foxgurev.blps.lab4.delivery.Delivery
import foxgurev.blps.lab4.delivery.DeliveryService
import foxgurev.blps.lab4.order.OrderStatus.*
import foxgurev.blps.lab4.product.ProductService
import foxgurev.blps.lab4.promocode.PromocodeService
import foxgurev.blps.lab4.promocode.PromocodeStatus
import org.camunda.bpm.engine.delegate.BpmnError
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderService @Autowired constructor(
    val orderRepository: OrderRepository,
    val promocodeService: PromocodeService,
    val productService: ProductService,
    val deliveryService: DeliveryService
) {
    private val log = LoggerFactory.getLogger(javaClass)

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

        log.info("Removing from stock: ${products.map { "[${it.id}] " + it.name }}")
        products.forEach { it.changeAmountInStock(-1) }

        val price = products.sumOf { it.price } * (100 - (promocode?.discount ?: 0)) / 100
        val saved = orderRepository.save(
            Order(items = products, promocode = promocode, totalPrice = price, status = CREATED)
        )

        log.info(
            "New order with id = ${saved.id}, price = ${saved.totalPrice} and products ${products.map { it.name }}"
        )

        return saved.id
    }

    fun changeStatus(id: Long, newStatus: OrderStatus) {
        val order = orderRepository.findById(id).orElse(null)
        checkStatusFlow(order.status, newStatus)
        order.status = newStatus
        log.info("Changed status of order $id from ${order.status} to $newStatus")
    }

    private fun checkStatusFlow(current: OrderStatus, next: OrderStatus) {
        if (current === CANCELLED) {
            throw RuntimeException("Changing status of cancelled order is not allowed")
        }
        if (next === CANCELLED) {
            return
        }
        when (next.ordinal - current.ordinal) {
            0 -> throw RuntimeException("This status is already assigned")
            1 -> return
            else -> throw RuntimeException("Illegal change of status (" + current.name + " -> " + next.name + ")")
        }
    }

    fun cancelOrder(id: Long) {
        log.info("Cancelling order $id...")
        val order = orderRepository.findById(id).orElse(null)
        when (order.status) {
            CANCELLED -> throw RuntimeException("This order is already cancelled")
            DELIVERED -> throw RuntimeException("Cannot cancel a delivered order")
            SHIPPING -> throw RuntimeException("Cannot cancel an order already in shipping")
            PACKED -> {
                deliveryService.cancelDelivery(id)
                productService.returnToStock(order.items)
            }
            CREATED -> productService.returnToStock(order.items)
        }
        order.status = CANCELLED
        log.info("Cancelled order $id")
    }

    fun packOrderAndOrganizeDelivery(
        orderId: Long, name: String, phoneNumber: String?, email: String?, address: String
    ) {
        changeStatus(orderId, PACKED)
        val freeDate = deliveryService.selectDeliveryDate()
        val delivery = Delivery(
            orderId = orderId, date = freeDate, name = name,
            phoneNumber = phoneNumber, email = email, address = address
        )
        deliveryService.addDelivery(delivery)
        log.info("Packing order $orderId and organizing delivery for $freeDate to name $name")
    }
}