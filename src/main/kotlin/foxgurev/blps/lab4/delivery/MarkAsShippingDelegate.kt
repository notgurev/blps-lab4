package foxgurev.blps.lab4.delivery

import foxgurev.blps.lab4.ProcessVariables
import foxgurev.blps.lab4.order.OrderService
import foxgurev.blps.lab4.order.OrderStatus
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MarkAsShippingDelegate @Autowired constructor(
    private val orderService: OrderService
) : JavaDelegate {
    override fun execute(de: DelegateExecution) {
        val orderId = de.getVariable(ProcessVariables.ORDER_ID) as Long
        de.processEngineServices.runtimeService
            .createMessageCorrelation("SentToDelivery")
            .setVariable("orderID", orderId)
            .correlate()
        orderService.changeStatus(orderId, OrderStatus.SHIPPING)
    }
}