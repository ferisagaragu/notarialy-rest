package org.pechblenda.notarialyrest.entity

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "quotes")
class Quote(
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var uuid: UUID,
	var workforce: String,
	var createDate: String,

	@OneToOne
	var user: User,

	@OneToOne
	var company: Company,

	@OneToOne
	var client: Client
)