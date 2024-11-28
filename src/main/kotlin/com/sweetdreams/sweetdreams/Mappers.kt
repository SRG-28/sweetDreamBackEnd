package com.sweetdreams.sweetdreams
import org.mapstruct.*
import java.time.LocalDateTime

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface RoleMapper { //Role Mapper
    fun roleListToRoleDetailsList(
        roleList: Set<Role>?,
    ): Set<RoleDetails>
}
@Mapper(
    imports = [LocalDateTime::class],
    componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface UserMapper {

    fun userToUserResult(
        user: User,
    ): UserResult

    fun userToUserResultList(
        userResultList: List<User>,
    ): List<UserResult>

    @Mapping(target = "createDate", defaultExpression = "java(new java.util.Date())")
    fun userInputToUser(
        userInput: UserInput,
    ): User

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun userInputToUser(dto: UserInput, @MappingTarget user: User)
}

//Status
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface StatusMapper {
    fun statusToStatusDetails(
        status: Status
    ): StatusDetails

    fun statusListToStatusDetailsList(
        statusList: List<Status>
    ): List<StatusDetails>
}

//OrderProduct
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface OrderProductMapper {
    fun orderProductToOrderProductDetails(
        orderProduct: OrderProduct
    ): OrderProductDetails

    fun orderProductListToOrderProductDetailsList(
        orderProductList: List<OrderProduct>
    ): List<OrderProductDetails>
}

//Order
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface OrderMapper {
    fun orderToOrderDetails(
        order: Order
    ): OrderDetails

    fun orderListToOrderDetailsList(
        orderList: List<Order>
    ): List<OrderDetails>

    @Mapping(target = "reservationDate", defaultExpression = "java(new java.util.Date())")
    @Mapping(target = "deliveryDate", defaultExpression = "java(new java.util.Date())")
    fun orderInputToOrder(
        orderInput: OrderInput
    ): Order

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun orderInputToOrder(dto: OrderInput, @MappingTarget order: Order)
}

//Category
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface CategoryMapper {
    fun categoryToCategoryDetails(
        category: Category
    ): CategoryDetails

    fun categoryListToCategoryDetailsList(
        categoryList: List<Category>
    ): List<CategoryDetails>
}

//Product
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ProductMapper {
    fun productToProductDetails(
        product: Product
    ): ProductDetails

    fun productListToProductDetailsList(
        productList: List<Product>
    ): List<ProductDetails>

    fun productInputToProduct(
        productInput: ProductInput
    ): Product

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun productInputToProduct(dto: ProductInput, @MappingTarget product: Product)
}
