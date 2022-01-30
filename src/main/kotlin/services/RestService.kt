package services

import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.specification.RequestSpecification

abstract class RestService (baseUrl:String) {
    abstract var basePath:String
    val requestSpecification:RequestSpecification = RequestSpecBuilder()
        .setBaseUri(baseUrl)
        .addHeader("accept","application/json")
        .addFilter(SwaggerCoverageRestAssured())
        .build()
}