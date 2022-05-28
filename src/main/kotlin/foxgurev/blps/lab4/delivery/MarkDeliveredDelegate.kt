package foxgurev.blps.lab4.delivery

import foxgurev.blps.lab4.ProcessVariables
import foxgurev.blps.lab4.order.OrderService
import foxgurev.blps.lab4.order.OrderStatus
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MarkDeliveredDelegate @Autowired constructor(
    private val orderService: OrderService
) : JavaDelegate{
    private val log = LoggerFactory.getLogger(javaClass)

    override fun execute(de: DelegateExecution) {
        val id = de.getVariable(ProcessVariables.ORDER_ID) as Long
        orderService.changeStatus(id, OrderStatus.DELIVERED)
        log.info("Order #$id has been delivered")
    }
}