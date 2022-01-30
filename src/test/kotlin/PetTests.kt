import au.com.origin.snapshots.Expect
import au.com.origin.snapshots.junit5.SnapshotExtension
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import net.joshka.junit.json.params.JsonFileSource
import org.junit.jupiter.api.TestInfo
import org.junit.jupiter.params.ParameterizedTest
import javax.json.JsonObject
import models.PetPojo
import services.PetService

@ExtendWith(SnapshotExtension::class)
class PetTests: AbstractTests() {
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

    @ParameterizedTest
    @JsonFileSource(resources = ["testdata/pet/validPets.json"])
    fun postValidPet(json:JsonObject,testInfo: TestInfo){
        petService.postPet(json).then().statusCode(200)
        val responsePet = petService.getPetById(json["id"].toString()).`as`(PetPojo::class.java)


        expect.serializer("json").scenario("${responsePet.name}").toMatchSnapshot(responsePet)
    }
}