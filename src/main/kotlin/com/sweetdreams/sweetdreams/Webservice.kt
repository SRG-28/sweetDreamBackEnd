package com.sweetdreams.sweetdreams


import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import com.sweetdreams.sweetdreams.OpenAIService
//import com.sweetdreams.demo.service.OpenAIService

//1 User...
@RestController
@RequestMapping("\${url.user}")
class UserController(private val userService: UserService) {
    @GetMapping
    @ResponseBody
    fun findAll(@RequestParam(name = "role_id", required = false) roleId: Long?) = userService.findAll(roleId)

    @Throws(NoSuchElementException::class)
    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id:Long) = userService.findById(id)

    @Throws(NoSuchElementException::class)
    @RequestMapping(value = ["/",""], method = [RequestMethod.GET],  params = ["email"])
    @ResponseBody
    fun findByEmail(@RequestParam("email") email: String) = userService.findByEmail(email)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun create(@RequestBody userInput: UserInput) : UserResult? {
        return userService.create(userInput)
    }

    @Throws(NoSuchElementException::class)
    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody userInput: UserInput): UserResult? {
        return userService.update(userInput)
    }


}


//2
//Category
@RestController
@RequestMapping("\${url.category}")
class CategoryController(private val categoryService: CategoryService){

    /**
     * WS to find all elements of type Category
     * @return A list of elements of type Category
     */
    @GetMapping
    @ResponseBody
    fun findAll() = categoryService.findAll()

    /**
     * WS to find one Category by the id
     * @param id to find Category
     * @return the Category found
     */
    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id:Long) = categoryService.findById(id)
}
//Status
@RestController
@RequestMapping("\${url.status}")
class StatusController(private val statusService: StatusService){

    /**
     * WS to find all elements of type status
     * @return A list of elements of type status
     */
    @GetMapping
    @ResponseBody
    fun findAll() = statusService.findAll()

    /**
     * WS to find one status by the id
     * @param id to find status
     * @return the status found
     */
    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id:Long) = statusService.findById(id)
}
//Product
@RestController
@RequestMapping("\${url.product}")
class ProductController(private val productService: ProductService) {
    @GetMapping
    @ResponseBody
    fun findAll() = productService.findAll()

    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id:Long) = productService.findById(id)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun create(@RequestBody productInput: ProductInput) : ProductDetails? {
        return productService.create(productInput)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody productInput: ProductInput) : ProductDetails? {
        return productService.update(productInput)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id:Long) {
        productService.deleteById(id)
    }
}
//Order
@RestController
@RequestMapping("\${url.order}")
class OrderController(private val orderService: OrderService) {
    @GetMapping
    @ResponseBody
    fun findAll() = orderService.findAll()

    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id:Long) = orderService.findById(id)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun create(@RequestBody orderInput: OrderInput) : OrderDetails? {
        return orderService.create(orderInput)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody orderInput: OrderInput) : OrderDetails? {
        return orderService.update(orderInput)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id:Long) {
        orderService.deleteById(id)
    }
}

//orderProduct
@RestController
@RequestMapping("\${url.orderProduct}")
class OrderProductController(private val orderProductService: OrderProductService){

    /**
     * WS to find all elements of type orderProduct
     * @return A list of elements of type orderProduct
     */
    @GetMapping
    @ResponseBody
    fun findAll() = orderProductService.findAll()

    /**
     * WS to find one orderProduct by the id
     * @param id to find orderProduct
     * @return the orderProduct found
     */
    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id:Long) = orderProductService.findById(id)
}

//OpenAI
@RestController
@RequestMapping("/api/openai")
class OpenAIController(
    private val openAIService: OpenAIService
) {
    data class PromptRequest(val prompt: String)
    data class Response(val result: String?)

    @PostMapping("/generate")
    fun generateResponse(@RequestBody request: PromptRequest): Response {
        val result = openAIService.getResponse(request.prompt)
        return Response(result)
    }
}