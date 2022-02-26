package services

import io.restassured.RestAssured.*
import io.restassured.response.Response
import javax.json.JsonObject

class PetService(baseUrl: String) : RestService(baseUrl) {
    override val basePath: String = "pet"

    fun getPetById(id:String):Response{
        return given()
                .spec(requestSpecification)
                .pathParam("petId",id)
            .`when`()
                .get("$basePath/{petId}")
    }

    fun postPet(petPojo: JsonObject):Response{
        return  given()
                .spec(requestSpecification)
                .body(petPojo.toString())
            .`when`()
                .post(basePath)
    }
}