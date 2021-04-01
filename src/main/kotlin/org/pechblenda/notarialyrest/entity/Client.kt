package org.pechblenda.notarialyrest.entity

import java.util.UUID

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

import org.pechblenda.service.annotation.Key
import org.pechblenda.service.enum.DefaultValue

@Entity
@Table(name = "clients")
class Client(
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var uuid: UUID,
	var name: String,
	var surname: String,
	var motherSurname: String,
	var address: String,
	var lat: Double,
	var lng: Double,
	var phoneNumber: String,
	var color: String,

	@OneToMany(mappedBy = "client")
	var quotes: List<Quote>?,

	@ManyToOne
	var user: User?
) {

	constructor(): this(
		uuid = UUID.randomUUID(),
		name = "",
		surname = "",
		motherSurname = "",
		address = "",
		lat = 0.0,
		lng = 0.0,
		phoneNumber = "",
		color = "",
		quotes = null,
		user = null
	)

	@Key(name= "delete", autoCall = true, defaultNullValue = DefaultValue.BOOLEAN)
	fun delete(): Boolean {
		return quotes?.size!! <= 0
	}

}