package foxgurev.blps.lab4.delivery

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalTime

@Service
class DeliveryService @Autowired constructor(private val deliveryRepository: DeliveryRepository) {
    private val startTime = LocalTime.of(9, 0)
    private val endTime = LocalTime.of(18, 0)

//    fun selectDeliveryDate(): LocalDateTime {
//        val delivery: Optional<Delivery>? = deliveryRepository.findByOrderByDate()
//        if (!delivery!!.isPresent) {
//            return LocalDateTime.now()
//        }
//        var date = delivery.get().date
//        if (LocalTime.of(date.hour + 1, date.minute).isAfter(endTime)) {
//            date = date.plusDays(1)
//            return LocalDateTime.of(LocalDate.of(date.year, date.month, date.dayOfMonth), startTime)
//        }
//        return date.plusHours(1)
//    }
//
//    fun cancelDelivery(orderId: Long) {
//        val delivery: Delivery = deliveryRepository.findByOrderId(orderId)
//            .orElseThrow { VisibleException("Failed to find delivery for order #$orderId") }
//        deliveryRepository.delete(delivery)
//    }
//
//    fun addDelivery(delivery: Delivery) {
//        deliveryRepository.save(delivery)
//    }
}