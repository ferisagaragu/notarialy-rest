package org.pechblenda.notarialyrest.entity

import java.util.UUID

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

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
	var logoUrl: String,
	var color: String,

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
		user = null
	)

}