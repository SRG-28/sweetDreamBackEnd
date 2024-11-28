package com.sweetdreams.sweetdreams
import java.util.*

data class PrivilegeDetails (
    var id: Long? = null,
    var name: String? = null
)

data class RoleDetails(
    var id: Long? = null,
    var name: String? = null,
    var privilegeList: List<PrivilegeDetails>? = null,
)

data class UserInput(
    var id: Long?=null,
    var firstName: String?=null,
    var lastName: String?=null,
    var email: String?=null,
    var phoneNumber: String? = null,
    var password: String?=null,
    var createDate: Date? = null,
    var enabled: Boolean?=null,
    var tokenExpired: Boolean?=null,
    var roleList: List<RoleDetails>?=null,
)

data class UserLoginInput(
    var username: String = "",
    var password: String = "",
)

data class UserResult(
    var id: Long,
    var firstName: String,
    var lastName: String,
    var email: String,
    var phoneNumber: String,
    var password: String,
    var createDate: Date,
    var enabled: Boolean?,
    var tokenExpired: Boolean?,
    var roleList: List<RoleDetails>? = null,
)

data class UserSignUpInput(
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var password: String? = null,
)

data class StatusDetails(
    var idStatus: Long? = null,
    var name: String? = null,
    var description: String? = null,
)

data class OrderProductDetails(
    var idOrderProduct: Long? = null,
    var order: OrderDetails? = null, //FK
    var product: ProductDetails? = null,
    var quantity: Long? = null,
    var description: String? = null,
)

data class OrderInput(
    var idOrder: Long? = null,
    var product: ProductDetails? = null, // FK
    var user: UserResult? = null,    // FK
    var status: StatusDetails? = null,  // FK
    var reservationDate: Date? = null,
    var deliveryDate: Date? = null,
    var deliveryStatus: Boolean? = null,
)

data class OrderDetails(
    var idOrder: Long? = null,
    var product: ProductDetails? = null, //FK
    var user: UserResult? = null,  //FK
    var status: StatusDetails? = null,  //FK
    var reservationDate: Date?=null,
    var deliveryDate: Date?=null,
    var deliveryStatus: Boolean?=null,
)

data class CategoryDetails(
    var idCategory: Long? = null,
    var name: String? = null,
    var description: String? = null,
)

data class ProductInput(
    var idProduct: Long? = null,
    var category: CategoryDetails,
    var name: String,
    var description: String? = null,
    var price: Double,
    var count: Long,
    var image: String? = null,
    var createdAt: Date?=null,
    var updatedAt: Date?=null,
)

data class ProductDetails(
    var idProduct: Long? = null,
    var category: CategoryDetails? = null,
    var name: String,
    var description: String? = null,
    var price: Double,
    var count: Long,
    var image: String? = null,
    var createdAt: Date?=null,
    var updatedAt: Date?=null,
)

data class PromptRequest(
    val model: String,
    val messages: List<Message>,
    val max_tokens: Int?,
    val temperature: Float?
)

data class Message(
    val role: String,
    val content: String
)