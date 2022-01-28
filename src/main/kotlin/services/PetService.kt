package services

import io.restassured.RestAssured.*
import io.restassured.response.Response

class PetService : RestService() {
    override var basePath:String = "pet/"

    fun getPetById(id:String):Response{
        return given().spec(requestSpecification).pathParam("petId",id).get("$basePath{petId}")
    }
}