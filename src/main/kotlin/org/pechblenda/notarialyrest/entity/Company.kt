package org.pechblenda.notarialyrest.entity

import java.util.UUID

import javax.persistence.Column
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
@Table(name = "companies")
class Company(
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var uuid: UUID,

	@Column(unique = true)
	var name: String,
	var slogan: String,
	var title: String,

	@Column(length = 10000)
	var logoUrl: String,
	var color: String,

	@OneToMany(mappedBy = "company")
	var quotes: List<Quote>?,

	@ManyToOne
	var user: User?
) {

	constructor(): this(
		uuid = UUID.randomUUID(),
		name = "",
		slogan = "",
		title = "",
		logoUrl = "",
		color = "",
		quotes = null,
		user = null
	)

	@Key(name= "delete", autoCall = true, defaultNullValue = DefaultValue.BOOLEAN)
	fun delete(): Boolean {
		return quotes?.size!! <= 0
	}

}