package foxgurev.blps.lab4.order

import foxgurev.blps.lab4.ProcessVariables
import foxgurev.blps.lab4.product.ProductService
import foxgurev.blps.lab4.util.splitByComma
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CreateOrderDelegate @Autowired constructor(
    private val productService: ProductService,
    private val orderService: OrderService
) : JavaDelegate {
    override fun execute(de: DelegateExecution) {
        val ids = splitByComma(de.getVariable(ProcessVariables.PRODUCT_IDS) as String).map { it.toLong() }
        val promocode = de.getVariable(ProcessVariables.PROMOCODE_NAME) as String
        val orderId = orderService.createOrder(ids, promocode)
        de.setVariable(ProcessVariables.ORDER_ID, orderId)
    }
}