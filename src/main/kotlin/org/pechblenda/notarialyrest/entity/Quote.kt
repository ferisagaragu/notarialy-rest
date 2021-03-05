package org.pechblenda.notarialyrest.entity

import java.util.Date
import java.util.UUID

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

import org.pechblenda.service.annotation.Key
import org.pechblenda.service.enum.DefaultValue

@Entity
@Table(name = "quotes")
class Quote(
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var uuid: UUID,
	var workforce: Double,
	var createDate: Date,
	var color: String,

	@OneToOne
	var user: User?,

	@OneToOne
	var company: Company?,

	@OneToOne
	var client: Client?,

	@OneToMany(mappedBy = "quote")
	var works: MutableList<Work>
) {

	constructor(): this(
		uuid = UUID.randomUUID(),
		workforce = 0.0,
		createDate = Date(),
		color = "",
		user = null,
		company = null,
		client = null,
		works = mutableListOf()
	)

	@Key(name = "user", autoCall = true, defaultNullValue = DefaultValue.NULL)
	fun user(): User? {
		return user
	}

	@Key(name = "company", autoCall = true, defaultNullValue = DefaultValue.NULL)
	fun company(): Company? {
		return company
	}

	@Key(name = "client", autoCall = true, defaultNullValue = DefaultValue.NULL)
	fun client(): Client? {
		return client
	}

}