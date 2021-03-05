package org.pechblenda.notarialyrest.repository

import java.util.UUID

import org.pechblenda.notarialyrest.entity.Quote

import org.springframework.data.jpa.repository.JpaRepository

interface IQuoteRepository: JpaRepository<Quote, UUID> {
	fun findAllByUserUuid(userUUID: UUID): List<Quote>
}
