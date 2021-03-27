package org.pechblenda.notarialyrest.repository

import java.util.UUID

import org.pechblenda.notarialyrest.entity.Quote

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface IQuoteRepository: JpaRepository<Quote, UUID> {

	@Query(
		"select quote from Quote quote " +
		"where quote.user.uuid = :userUUID " +
		"order by quote.createDate"
	)
	fun findAllByUserUuid(userUUID: UUID): List<Quote>

}
