package foxgurev.blps.lab4.delivery

import foxgurev.blps.lab4.ProcessVariables
import foxgurev.blps.lab4.order.OrderService
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OrganizeDeliveryDelegate @Autowired constructor(
    private val orderService: OrderService
) : JavaDelegate {
    override fun execute(de: DelegateExecution) {
        val orderId = de.getVariable(ProcessVariables.ORDER_ID) as Long
        val name = de.getVariable(ProcessVariables.DELIVERY_NAME) as String
        val email = de.getVariable(ProcessVariables.DELIVERY_EMAIL) as String?
        val address = de.getVariable(ProcessVariables.DELIVERY_ADDRESS) as String
        val phoneNumber = de.getVariable(ProcessVariables.DELIVERY_PHONE_NUMBER) as String?

        orderService.packOrderAndOrganizeDelivery(orderId, name, phoneNumber, email, address)
    }
}