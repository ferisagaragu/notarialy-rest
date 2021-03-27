package org.pechblenda.notarialyrest.repository

import java.util.UUID

import org.pechblenda.notarialyrest.entity.Company

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ICompanyRepository: JpaRepository<Company, UUID> {
	fun existsByName(name: String): Boolean

	@Query(
		nativeQuery = true,
		value =
			"select distinct CAST (c.uuid as varchar), c.name, c.slogan, c.title, c.logo_url as \"logoUrl\", c.color, " +
			"(q.uuid IS NULL) as delete, CAST (c.user_uuid as varchar) as \"userUuid\" " +
			"from companies c left join quotes q on c.uuid = q.company_uuid " +
			"inner join users u on c.user_uuid = u.uuid where u.uuid = :userUUID " +
			"order by c.name"
	)
	fun findAllByUserUuidDetail(@Param("userUUID") userUUID: UUID): List<Map<String, Any>>
}