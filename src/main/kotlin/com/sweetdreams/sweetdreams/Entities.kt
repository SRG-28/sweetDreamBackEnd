package com.sweetdreams.sweetdreams

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "privilege")
data class Privilege(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "privilege_sequence")
    @SequenceGenerator(name = "privilege_sequence", sequenceName = "privilege_sequence", allocationSize = 1)
    var id:Long? = null,
    var name: String,
    // Entity Relationship
    @ManyToMany(fetch = FetchType.LAZY)
    var userList: Set<User>,
    @ManyToMany(fetch = FetchType.LAZY)
    var roleList: Set<Role>,

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Privilege) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Privilege(id=$id, name='$name', userList=$userList, roleList=$roleList)"
    }
}
@Entity
@Table(name = "role")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "role_sequence")
    @SequenceGenerator(name = "role_sequence", sequenceName = "role_sequence", allocationSize = 1)
    var id: Long? = null,
    var name: String?=null,
    // Entity Relationship
    @ManyToMany
    @JoinTable(
        name = "role_privilege",
        joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "privilege_id", referencedColumnName = "id")]
    )
    var privilegeList: Set<Privilege>? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Role) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Role(id=$id, name='$name', privilegeList=$privilegeList)"
    }

}

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "users_sequence")
    @SequenceGenerator(name = "users_sequence", sequenceName = "users_sequence", allocationSize = 1)
    var id: Long? = null,
    var firstName: String,
    var lastName: String,
    var email: String,
    var phoneNumber: String,
    var password: String,
    var createDate: Date? = null,
    var enabled: Boolean?,
    var tokenExpired: Boolean?,
    // Entity Relationship
    @OneToMany(mappedBy = "user")
    var orders: List<Order>? = null, //relación con order
    @ManyToMany
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roleList: Set<Role>? = null,

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (id != other.id) return false
        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + email.hashCode()
        return result
    }

    override fun toString(): String {
        return "User(id=$id, firstName='$firstName', lastName='$lastName',email='$email',phoneNumber='$phoneNumber', password='$password', createDate=$createDate,\" + enabled=$enabled, tokenExpired=$tokenExpired, roleList=$roleList)"
    }
}


@Entity
@Table(name = "category")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category") //esta anotación permite coincidir con la columna de la base de datos
    var idCategory: Long? = null,

    var name: String,
    var description: String? = null,

    @OneToMany(mappedBy = "category")
    var products: List<Product>? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Category) return false
        if (idCategory != other.idCategory) return false
        return true
    }

    override fun hashCode(): Int {
        return idCategory?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Category(idCategory=$idCategory, name='$name', description=$description)"
    }
}

@Entity
@Table(name = "product")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product") // esta anotación permite coincidir con la columna de la base de datos
    var idProduct: Long? = null,

    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false, referencedColumnName = "id_category")
    var category: Category,

    var name: String,
    var description: String? = null,
    var price: Double,
    var count: Int,
    var image: String? = null,

    @Column(name = "created_at")
    var createdAt: Date = Date(),

    @Column(name = "updated_at")
    var updatedAt: Date = Date(),

    @OneToMany(mappedBy = "product")
    var orders: List<Order>? = null,  // Relación con Order

    @OneToMany(mappedBy = "product")
    var orderProducts: List<OrderProduct>? = null  // Relación con OrderProduct

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Product) return false
        if (idProduct != other.idProduct) return false
        return true
    }

    override fun hashCode(): Int {
        return idProduct?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Product(idProduct=$idProduct, category=$category, name='$name', description=$description, price=$price, count=$count, image=$image, createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}

//Status Entity
@Entity
@Table(name = "status")
data class Status(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status")
    var idStatus: Long? = null,

    var name: String,
    var description: String? = null,

    @OneToMany(mappedBy = "status")
    var orders: List<Order>? = null // Relación de uno a muchos con Order
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Status) return false
        if (idStatus != other.idStatus) return false
        return true
    }

    override fun hashCode(): Int {
        return idStatus?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Status(idStatus=$idStatus, name='$name', description=$description)"
    }
}


//Order
//Order
@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    var idOrder: Long? = null,

    @OneToMany(mappedBy = "order") //orderProduct
    var orderProducts: List<OrderProduct>? = null,

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false, referencedColumnName = "id_product") // Relación con Product
    var product: Product,

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false, referencedColumnName = "id") // Relación con User
    var user: User,

    @ManyToOne
    @JoinColumn(name = "id_status", nullable = false, referencedColumnName = "id_status")
    var status: Status,

    @Column(name = "reservation_date", nullable = false)
    var reservationDate: Date,

    @Column(name = "delivery_date", nullable = false)
    var deliveryDate: Date,

    @Column(name = "delivery_status", nullable = false)
    var deliveryStatus: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Order) return false
        if (idOrder != other.idOrder) return false
        return true
    }

    override fun hashCode(): Int {
        return idOrder?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Order(idOrder=$idOrder, product=$product, user=$user, status=$status, reservationDate=$reservationDate, deliveryDate=$deliveryDate, deliveryStatus=$deliveryStatus)"
    }
}

@Entity
@Table(name = "order_product")
data class OrderProduct(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_product")
    var idOrderProduct: Long? = null,

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false, referencedColumnName = "id_product")
    var product: Product,

    @ManyToOne
    @JoinColumn(name = "id_order", nullable = false, referencedColumnName = "id_order")
    var order: Order,

    @Column(name = "quantity", nullable = false)
    var quantity: Int,

    @Column(name = "description", length = 255)
    var description: String? = null // Optional description
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OrderProduct) return false
        return idOrderProduct == other.idOrderProduct
    }

    override fun hashCode(): Int {
        return idOrderProduct?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "OrderProduct(idOrderProduct=$idOrderProduct, product=$product, order=$order, quantity=$quantity, description=$description)"
    }
}
