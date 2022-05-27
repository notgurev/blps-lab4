package foxgurev.blps.lab4.promocode

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PromocodeRepository : JpaRepository<Promocode, Long> {
    fun findByCode(code: String): Promocode?
}