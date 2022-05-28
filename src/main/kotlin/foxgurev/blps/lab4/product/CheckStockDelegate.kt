package foxgurev.blps.lab4.product

import foxgurev.blps.lab4.ProcessVariables
import foxgurev.blps.lab4.util.splitByComma
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CheckStockDelegate @Autowired constructor(
    private val productService: ProductService
) : JavaDelegate {
    override fun execute(de: DelegateExecution) {
        val ids = splitByComma(de.getVariable(ProcessVariables.PRODUCT_IDS) as String).map { it.toLong() }
        val products = productService.findAllById(ids)
        val outOfStock = products.removeIf { it.inStock < 1 }
        de.setVariable(ProcessVariables.IS_OUT_OF_STOCK, outOfStock)
        de.setVariable(ProcessVariables.PRODUCT_IDS, products.map { it.id }.joinToString())
    }
}