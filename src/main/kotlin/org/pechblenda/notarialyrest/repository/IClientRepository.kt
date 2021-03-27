package org.pechblenda.notarialyrest.repository

import java.util.UUID

import org.pechblenda.notarialyrest.entity.Client

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface IClientRepository: JpaRepository<Client, UUID> {

	@Query(
		nativeQuery = true,
		value =
			"select distinct CAST (c.uuid as varchar), c.name, c.surname, " +
			"c.mother_surname as \"motherSurname\", " +
			"c.address, c.lat, c.lng, c.phone_number as \"phoneNumber\", " +
			"c.color, (q.uuid IS NULL) as delete " +
			"from clients c left join quotes q on c.uuid = q.client_uuid " +
			"inner join users u on c.user_uuid = u.uuid where u.uuid = :userUUID " +
			"order by c.name"
	)
	fun findAllByUserUuidDetail(@Param("userUUID") userUUID: UUID): List<Map<String, Any>>

}