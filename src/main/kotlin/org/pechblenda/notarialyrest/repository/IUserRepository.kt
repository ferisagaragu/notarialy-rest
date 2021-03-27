package org.pechblenda.notarialyrest.repository

import java.util.UUID

import org.pechblenda.notarialyrest.entity.User

import org.springframework.data.jpa.repository.JpaRepository

interface IUserRepository: JpaRepository<User, UUID>