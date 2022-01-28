import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.assertj.core.api.SoftAssertions
import services.PetService
import models.PetPojo
import models.TagsItem
import models.Category

class PetTests {
    private val petService = PetService()

    @Test
    fun getPetByValidId() {
        val response = petService.getPetById("1")
        response.then().statusCode(200)
        val petPojo: PetPojo = response.`as`(PetPojo::class.java)

        val softly = SoftAssertions()
        softly.assertThat(petPojo.id).isEqualTo(1)
        softly.assertThat(petPojo.category).isEqualTo(Category(2,"Cats"))
        softly.assertThat(petPojo.name).isEqualTo("Cat 1")
        softly.assertThat(petPojo.photoUrls).isEqualTo(mutableListOf("url1","url2"))
        softly.assertThat(petPojo.tags).isEqualTo(mutableListOf(TagsItem(1,"tag1"), TagsItem(2,"tag2")))
        softly.assertThat(petPojo.status).isEqualTo("available")
        softly.assertAll()
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