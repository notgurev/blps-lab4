package foxgurev.blps.lab4.product

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ResupplyDelegate @Autowired constructor(
    private val productService: ProductService
) : JavaDelegate {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun execute(de: DelegateExecution) {
        log.info("Resupplying...")
        log.info("Actually not doing anything at all")
    }
}