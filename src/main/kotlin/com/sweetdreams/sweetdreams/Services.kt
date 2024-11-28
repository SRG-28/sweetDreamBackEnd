package com.sweetdreams.sweetdreams

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import kotlin.NoSuchElementException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.beans.factory.annotation.Value
import org.json.JSONObject
import org.json.JSONArray


interface UserService {
    fun findAll(roleId: Long?): List<UserResult>?
    fun findById(id: Long): UserResult?
    fun findByEmail(email: String): UserResult?
    fun findByIdAndRole(id: Long, role: Role): UserResult?
    fun create(userInput: UserInput): UserResult?
    fun update(userInput: UserInput): UserResult?
}

@Service
class AbstractUserService(
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val userMapper: UserMapper,
    @Autowired
    val entityManager: EntityManager,
) : UserService {

    override fun findAll(roleId: Long?): List<UserResult>? {
        if (roleId == null) {
            return userMapper.userToUserResultList(userRepository.findAll())
        }
        return userMapper.userToUserResultList(userRepository.findByRoleListContains(Role(roleId)).orElse(null))
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): UserResult? {
        val user = userRepository.findById(id).orElse(null)
            ?: throw NoSuchElementException(String.format("The User with the id: %s not found!", id))

        return userMapper.userToUserResult(user)
    }

    @Throws(NoSuchElementException::class)
    override fun findByEmail(email: String): UserResult? {
        val user = userRepository.findByEmail(email).orElse(null)
            ?: throw NoSuchElementException(String.format("The User with the email: %s not found!", email))

        return userMapper.userToUserResult(user)
    }

    override fun findByIdAndRole(id: Long, role: Role): UserResult? {
        val user = userRepository.findByIdAndRoleListContains(id, role).orElse(null)
            ?: throw NoSuchElementException("The Client with the id: $id and rol: $role not found!")

        return userMapper.userToUserResult(user)
    }

    @Transactional
    override fun create(userInput: UserInput): UserResult? {
        val user = userMapper.userInputToUser(userInput)
        val savedUser = userRepository.saveAndFlush(user)

        entityManager.refresh(savedUser)

        return userMapper.userToUserResult(savedUser)
    }

    @Throws(NoSuchElementException::class)
    override fun update(userInput: UserInput): UserResult? {
        val user = userRepository.findById(userInput.id!!).orElse(null)
            ?: throw NoSuchElementException(String.format("The User with the id: %s not found!", userInput.id))

        userMapper.userInputToUser(userInput, user)

        return userMapper.userToUserResult(userRepository.save(user))
    }
}

//Status(Find All and Find By Id)
interface StatusService {
    fun findAll(): List<StatusDetails>?
    fun findById(id: Long): StatusDetails?
}

@Service
class AbstractStatusService(
    @Autowired
    val statusRepository: StatusRepository,

    @Autowired
    val statusMapper: StatusMapper,

    ) : StatusService {
    /**
     * Find all
     *
     * @return a list
     */
    override fun findAll(): List<StatusDetails>? {
        return statusMapper.statusListToStatusDetailsList(
            statusRepository.findAll()
        )
    }
    override fun findById(id: Long): StatusDetails? {
        val status: Optional<Status> = statusRepository.findById(id)
        return statusMapper.statusToStatusDetails(
            status.get(),
        )
    }
}

//Category(Find All and Find By Id)
interface CategoryService {
    fun findAll(): List<CategoryDetails>?
    fun findById(id: Long): CategoryDetails?
}

@Service
class AbstractCategoryService(
    @Autowired
    val categoryRepository: CategoryRepository,

    @Autowired
    val categoryMapper: CategoryMapper,

    ) : CategoryService {
    /**
     * Find all
     *
     * @return a list
     */
    override fun findAll(): List<CategoryDetails>? {
        return categoryMapper.categoryListToCategoryDetailsList(
            categoryRepository.findAll()
        )
    }
    override fun findById(id: Long): CategoryDetails? {
        val category: Optional<Category> = categoryRepository.findById(id)
        return categoryMapper.categoryToCategoryDetails(
            category.get(),
        )
    }
}
//OrderProduct(Find All and Find By Id)
interface OrderProductService {
    /**
     * Find all
     *
     * @return a list
     */
    fun findAll(): List<OrderProductDetails>?

    /**
     * Get one  by id
     *
     * @param id of the OrderProduct
     * @return the OrderProduct found
     */
    fun findById(id: Long): OrderProductDetails?
}

@Service
class AbstractOrderProductService(
    @Autowired
    val orderProductRepository: OrderProductRepository,

    @Autowired
    val orderProductMapper: OrderProductMapper,

    ) : OrderProductService {
    /**
     * Find all
     *
     * @return a list
     */
    override fun findAll(): List<OrderProductDetails>? {
        return orderProductMapper.orderProductListToOrderProductDetailsList(
            orderProductRepository.findAll()
        )
    }

    /**
     * Get one by id
     *
     * @param id of the OrderProduct
     * @return the OrderProduct found
     */
    override fun findById(id: Long): OrderProductDetails? {
        val orderProduct: Optional<OrderProduct> = orderProductRepository.findById(id)
        return orderProductMapper.orderProductToOrderProductDetails(
            orderProduct.get(),
        )
    }
}
//Order(CRUD)
interface OrderService {
    /**
     * Find all
     * @return a list
     */
    fun findAll(): List<OrderDetails>?

    /**
     * Get one  by id
     * @param id of the Order
     * @return the Order found
     */
    fun findById(id: Long): OrderDetails?

    /**
     * Save and flush a Order entity in the database
     * @param orderInput
     * @return the Order created
     */
    fun create(orderInput: OrderInput): OrderDetails?

    /**
     * Update a Order entity in the database
     * @param orderInput the dto input for Task
     * @return the new Order created
     */
    fun update(orderInput: OrderInput): OrderDetails?

    /**
     * Delete a Order by id from Database
     * @param id of the Order
     */
    fun deleteById(id: Long)
}

@Service
class AbstractOrderService(
    @Autowired
    val orderRepository: OrderRepository,
    @Autowired
    val orderMapper: OrderMapper,
) : OrderService {
    /**
     * Find all Order
     * @return a list of Orders
     */
    override fun findAll(): List<OrderDetails>? {
        return orderMapper.orderListToOrderDetailsList(
            orderRepository.findAll()
        )
    }

    /**
     * Get one Order by id
     * @param id of the Order
     * @return the Order found
     */
    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): OrderDetails? {
        val order: Optional<Order> = orderRepository.findById(id)
        if (order.isEmpty) {
            throw NoSuchElementException(String.format("The order with the id: %s not found!", id))
        }
        return orderMapper.orderToOrderDetails(
            order.get(),
        )
    }

    /**
     * Save and flush a Order entity in the database
     * @param orderInput
     * @return the Order created
     */
    override fun create(orderInput: OrderInput): OrderDetails? {
        val order: Order = orderMapper.orderInputToOrder(orderInput)
        return orderMapper.orderToOrderDetails(
            orderRepository.save(order)
        )
    }

    /**
     * Update a Order entity in the database
     * @param orderInput the dto input for Order
     * @return the new Order created
     */
    @Throws(NoSuchElementException::class)
    override fun update(orderInput: OrderInput): OrderDetails? {
        val order: Optional<Order> = orderRepository.findById(orderInput.idOrder!!)
        if (order.isEmpty) {
            throw NoSuchElementException(String.format("The Order with the id: %s not found!", orderInput.idOrder))
        }
        val orderUpdated: Order = order.get()
        orderMapper.orderInputToOrder(orderInput, orderUpdated)
        return orderMapper.orderToOrderDetails(orderRepository.save(orderUpdated))
    }

    /**
     * Delete a Order by id from Database
     * @param id of the Order
     */
    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        if (!orderRepository.findById(id).isEmpty) {
            orderRepository.deleteById(id)
        } else {
            throw NoSuchElementException(String.format("The Order with the id: %s not found!", id))
        }
    }

}

//Product(CRUD)
interface ProductService {
    /**
     * Find all
     * @return a list
     */
    fun findAll(): List<ProductDetails>?

    /**
     * Get one  by id
     * @param id of the Product
     * @return the Order found
     */
    fun findById(id: Long): ProductDetails?

    /**
     * Save and flush a Product entity in the database
     * @param productInput
     * @return the Product created
     */
    fun create(productInput: ProductInput): ProductDetails?

    /**
     * Update a Product entity in the database
     * @param productInput the dto input for Task
     * @return the new Product created
     */
    fun update(productInput: ProductInput): ProductDetails?

    /**
     * Delete a Product by id from Database
     * @param id of the Product
     */
    fun deleteById(id: Long)
}

@Service
class AbstractProductService(
    @Autowired
    val productRepository: ProductRepository,
    @Autowired
    val productMapper: ProductMapper,
) : ProductService {
    /**
     * Find all Product
     * @return a list of Products
     */
    override fun findAll(): List<ProductDetails>? {
        return productMapper.productListToProductDetailsList(
            productRepository.findAll()
        )
    }

    /**
     * Get one product by id
     * @param id of the Product
     * @return the Product found
     */
    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): ProductDetails? {
        val product: Optional<Product> = productRepository.findById(id)
        if (product.isEmpty) {
            throw NoSuchElementException(String.format("The Product with the id: %s not found!", id))
        }
        return productMapper.productToProductDetails(
            product.get(),
        )
    }

    /**
     * Save and flush a Product entity in the database
     * @param productInput
     * @return the Product created
     */
    override fun create(productInput: ProductInput): ProductDetails? {
        val product: Product = productMapper.productInputToProduct(productInput)
        return productMapper.productToProductDetails(
            productRepository.save(product)
        )
    }

    /**
     * Update a Product entity in the database
     * @param productInput the dto input for Product
     * @return the new Product created
     */
    @Throws(NoSuchElementException::class)
    override fun update(productInput: ProductInput): ProductDetails? {
        val product: Optional<Product> = productRepository.findById(productInput.idProduct!!)
        if (product.isEmpty) {
            throw NoSuchElementException(String.format("The Product with the id: %s not found!", productInput.idProduct))
        }
        val productUpdated: Product = product.get()
        productMapper.productInputToProduct(productInput, productUpdated)
        return productMapper.productToProductDetails(productRepository.save(productUpdated))
    }

    /**
     * Delete a Product by id from Database
     * @param id of the Product
     */
    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        if (!productRepository.findById(id).isEmpty) {
            productRepository.deleteById(id)
        } else {
            throw NoSuchElementException(String.format("The Product with the id: %s not found!", id))
        }
    }

}

@Service
@Transactional
class AppUserDetailsService(
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val roleRepository: RoleRepository,
) : UserDetailsService {

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the `UserDetails`
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never `null`)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     * GrantedAuthority
     */
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val userAuth: org.springframework.security.core.userdetails.User
        val user: User = userRepository.findByEmail(username).orElse(null)
            ?: return org.springframework.security.core.userdetails.User(
                "", "", true, true, true, true,
                getAuthorities(
                    listOf(
                        roleRepository.findByName("Admin").get()
                    )
                )
            )

        userAuth = org.springframework.security.core.userdetails.User(
            user.email, user.password, user.enabled?: false, true, true,
            true, getAuthorities(user.roleList!!.toMutableList())
        )

        return userAuth
    }

    private fun getAuthorities(roles: Collection<Role>): Collection<GrantedAuthority> {
        return roles.flatMap { role ->
            sequenceOf(SimpleGrantedAuthority(role.name)) +
                    (role.privilegeList?.map { privilege -> SimpleGrantedAuthority(privilege.name) } ?: emptyList())
        }.toList()
    }
}

//Open AI
@Service
class OpenAIService(
    private val client: OkHttpClient = OkHttpClient()
) {
    @Value("\${openai.api.key}")
    lateinit var apiKey: String

    fun getResponse(prompt: String): String? {
        // Crear el cuerpo JSON para el endpoint de chat
        val json = JSONObject().apply {
            put("model", "gpt-3.5-turbo")
            put("messages", JSONArray().put(
                JSONObject().apply {
                    put("role", "user")
                    put("content", prompt)
                }
            ))
            put("max_tokens", 150)
        }

        val mediaType = "application/json".toMediaTypeOrNull()
        val body = json.toString().toRequestBody(mediaType)

        // Crear la solicitud HTTP
        val request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .post(body)
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .build()

        // Ejecutar la solicitud y procesar la respuesta
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception("Unexpected code $response")

            response.body?.let {
                val responseBody = it.string()
                val jsonResponse = JSONObject(responseBody)

                // Extraer el contenido de la respuesta en "choices[0].message.content"
                return jsonResponse.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim()
            }
        }

        return null
    }
}

