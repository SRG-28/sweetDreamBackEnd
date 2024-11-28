package com.sweetdreams.sweetdreams

import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(
    statements = [
        "DELETE FROM public.order_product",
        "DELETE FROM public.orders",
        "DELETE FROM public.user_role",
        "DELETE FROM public.users",
        "DELETE FROM public.role",
        "DELETE FROM public.privilege",
        "DELETE FROM public.product",
        "DELETE FROM public.category",
        "DELETE FROM public.status"
    ],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
    scripts = [
        "/import-categories.sql",
        "/import-products.sql",
        "/import-privileges.sql",
        "/import-roles.sql",
        "/import-users.sql",
        "/import-user-roles.sql",
        "/import-status.sql",
        "/import-orders.sql",
        "/import-order-product.sql" //order product
    ],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class LoadInitData(
    @Autowired
    val productRepository: ProductRepository,
    @Autowired
    val categoryRepository: CategoryRepository,
    @Autowired
    val roleRepository: RoleRepository,
    @Autowired
    val privilegeRepository: PrivilegeRepository,
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val statusRepository: StatusRepository,
    @Autowired
    val orderRepository: OrderRepository,
    @Autowired
    val orderProductRepository: OrderProductRepository // order product
) {

    @Test
    // Prueba que verifica que la lista de productos tenga 4 elementos
    fun testProductFindAll() {
        val productList: List<Product> = productRepository.findAll()
        Assertions.assertTrue(productList.size == 4)
    }

    @Test
    fun testProductFindById() {
        val product: Product = productRepository.findById(1).get()
        Assertions.assertTrue(product.idProduct?.toInt() == 1)
    }
    @Test
// Prueba que verifica que la lista de category tenga 4 elementos
    fun testCategoryFindAll() {
        val categoryList: List<Category> = categoryRepository.findAll()
        Assertions.assertTrue(categoryList.size == 3)
    }
    @Test
    fun testCategoryFindById() {
        val category: Category = categoryRepository.findById(1).get()
        Assertions.assertTrue(category.idCategory?.toInt() == 1)
    }

    @Test
    // Prueba lista de usuarios con 7 elementos
    fun testUsersFindAll() {
        val userList: List<User> = userRepository.findAll()
        Assertions.assertTrue(userList.size == 7)
    }

    @Test
    fun testUsersFindById() {
        val user: User = userRepository.findById(1).get()
        Assertions.assertTrue(user.id?.toInt() == 1)
    }

    @Test

    fun testStatusFindAll() {
        val statusList: List<Status> = statusRepository.findAll()
        Assertions.assertTrue(statusList.size == 3)
    }

    @Test
    fun testStatusFindById() {
        val status: Status = statusRepository.findById(1).get()
        Assertions.assertTrue(status.idStatus?.toInt() == 1)
    }

    @Test
    fun testOrdersFindAll() {
        val orderList: List<Order> = orderRepository.findAll()
        Assertions.assertTrue(orderList.size == 3)
    }

    @Test
    fun testOrderFindById() {
        val order: Order = orderRepository.findById(1).get()
        Assertions.assertTrue(order.idOrder?.toInt() == 1)
    }

    @Test
    fun testOrderProductsFindAll() {
        val orderProductList: List<OrderProduct> = orderProductRepository.findAll()
        Assertions.assertTrue(orderProductList.size == 2)
    }

    @Test
    fun testOrderProductFindById() {
        val orderProduct: OrderProduct = orderProductRepository.findById(1).get()
        Assertions.assertTrue(orderProduct.idOrderProduct?.toInt() == 1)
    }

}
