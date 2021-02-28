package org.pechblenda.notarialyrest.repository

import java.util.UUID

import org.pechblenda.notarialyrest.entity.Client

import org.springframework.data.jpa.repository.JpaRepository

interface IClientRepository: JpaRepository<Client, UUID> {
	fun findAllByUserUuid(userUUID: UUID): List<Client>
}