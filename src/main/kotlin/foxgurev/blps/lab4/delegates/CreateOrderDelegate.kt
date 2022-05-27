package foxgurev.blps.lab4.delegates

import foxgurev.blps.lab4.product.ProductService
import foxgurev.blps.lab4.util.splitByComma
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CreateOrderDelegate @Autowired constructor(
    private val productService: ProductService
) : JavaDelegate {
    override fun execute(de: DelegateExecution) {
        val ids = splitByComma(de.getVariable("productIDs") as String).map { it.toLong() }

        productService.findAllById(ids).forEach { println(it.name) }
    }
}