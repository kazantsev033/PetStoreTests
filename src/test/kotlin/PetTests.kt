import au.com.origin.snapshots.Expect
import au.com.origin.snapshots.junit5.SnapshotExtension
import io.qameta.allure.Allure.step
import models.PetPojo
import net.joshka.junit.json.params.JsonFileSource
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import services.PetService
import javax.json.JsonObject

@ExtendWith(SnapshotExtension::class)
class PetTests: AbstractTest() {
    private val petService = PetService(baseUrl)
    private lateinit var expect: Expect

    @Test
    fun getPetByValidId() {
        val response = petService.getPetById("1")
        response.then().statusCode(200)
        val petPojo = response.`as`(PetPojo::class.java)

        expect.serializer("json").toMatchSnapshot(petPojo)
    }

    @Test
    fun getPetByNonexistentId() {
        val response = petService.getPetById("111")
        response.then().statusCode(404)
        response.then().body(containsString("Pet not found"))
    }

    @Test
    fun getPetByInvalidId() {
        val response = petService.getPetById("test")
        response.then().statusCode(400)
    }

    @ParameterizedTest(name = "{index}")
    @JsonFileSource(resources = ["testdata/pet/validPets.json"])
    @DisplayName("Добавление питомца")
    fun postValidPet(json: JsonObject) {
        lateinit var responsePet:PetPojo

        step("Добавить питомца") { ->
            petService.postPet(json).then().statusCode(200)
        }

        step("Питомец добавлен") { ->
            responsePet = petService.getPetById(json["id"].toString()).`as`(PetPojo::class.java)
            expect.serializer("json").scenario(responsePet.name).toMatchSnapshot(responsePet)
        }
    }
}
