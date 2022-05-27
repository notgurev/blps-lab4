package foxgurev.blps.lab4.promocode

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PromocodeService @Autowired constructor(private val promocodeRepository: PromocodeRepository) {
    fun getPromocode(code: String) = promocodeRepository.findByCode(code)
}