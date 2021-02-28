package org.pechblenda.notarialyrest.entity

import java.util.UUID

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "roles")
class Role (
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var uid: UUID,
	var name: String
)