package org.pechblenda.notarialyrest.entity

import java.util.UUID

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

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
	var phoneNumber: String,
	var color: String,

	@ManyToOne
	var user: User?
)