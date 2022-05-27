package foxgurev.blps.lab4.delivery

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeliveryRepository : JpaRepository<Delivery, Long> {
    fun findByOrderByDate(): Delivery?
    fun findByOrderId(orderId: Long): Delivery?
}