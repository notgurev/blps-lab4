package foxgurev.blps.lab4.product

import foxgurev.blps.lab4.ProcessVariables
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CatalogueDelegate @Autowired constructor(
    private val productService: ProductService
) : JavaDelegate {
    override fun execute(de: DelegateExecution) {
        de.setVariable(ProcessVariables.CATALOGUE, productService.getProducts())
    }
}