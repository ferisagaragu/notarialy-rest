package org.pechblenda.notarialyrest.repository

import java.util.UUID

import org.pechblenda.notarialyrest.entity.Company

import org.springframework.data.jpa.repository.JpaRepository

interface ICompanyRepository: JpaRepository<Company, UUID> {
	fun findAllByUserUuid(userUUID: UUID): List<Company>
	fun existsByName(name: String): Boolean
}