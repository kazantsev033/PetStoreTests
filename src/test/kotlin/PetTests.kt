import au.com.origin.snapshots.Expect
import au.com.origin.snapshots.junit5.SnapshotExtension
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.karumi.kotlinsnapshot.KotlinSnapshot
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import models.PetPojo
import services.PetService


@ExtendWith(SnapshotExtension::class)
class PetTests {
    private val petService = PetService()
    private lateinit var expect: Expect

    @Test
    fun getPetByValidId() {
//        val response = petService.getPetById("1")
//        response.then().statusCode(200)
//        val petPojo = response.`as`(PetPojo::class.java)

        val response = """{
                              "id": 1,
                              "category": {
                                "id": 2,
                                "name": "Cats"
                              },
                              "name": "Cat 1",
                              "photoUrls": [
                                "url1",
                                "url2"
                              ],
                              "tags": [
                                {
                                  "id": 1,
                                  "name": "tag1"
                                },
                                {
                                  "id": 2,
                                  "name": "tag2"
                                }
                              ],
                              "status": "available"
                            }"""

        // java-snapshot-testing
        var petPojo = jacksonObjectMapper().readValue(response,PetPojo::class.java)
        expect.serializer("json").toMatchSnapshot(petPojo)

        // kotlinSnapshot
        val kotlinSnapshot = KotlinSnapshot(snapshotsFolder = "src/test/kotlin/", serializationModule = CustomKotlinJsonSerialization())
        kotlinSnapshot.matchWithSnapshot(petPojo)
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
}