package com.sweetdreams.sweetdreams

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long>
{
    fun findByEmail(email : String) : Optional<User>
    fun findByIdAndRoleListContains(id : Long, role : Role) : Optional<User>
    fun findByRoleListContains(role : Role) : Optional<List<User>>
}


@Repository
interface PrivilegeRepository : JpaRepository<Privilege, Long>

@Repository
interface RoleRepository : JpaRepository<Role, Long>{
    fun findByName (@Param("name") name : String) : Optional<Role>
}

@Repository
interface CategoryRepository : JpaRepository<Category, Long>

@Repository
interface ProductRepository : JpaRepository<Product, Long>

@Repository
interface StatusRepository : JpaRepository<Status, Long>

@Repository
interface OrderRepository : JpaRepository<Order, Long>

@Repository
interface OrderProductRepository : JpaRepository<OrderProduct, Long>

