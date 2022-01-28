package services

import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.specification.RequestSpecification

abstract class RestService {
    private val baseUrl:String = "http://localhost:8080/api/v3/"
    abstract var basePath:String
    val requestSpecification:RequestSpecification = RequestSpecBuilder()
        .setBaseUri(baseUrl)
        .addHeader("accept","application/json")
        .addFilter(SwaggerCoverageRestAssured())
        .build()
}