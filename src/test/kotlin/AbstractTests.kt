import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName

abstract class AbstractTest {
    companion object {
        @Container
        val petStore: GenericContainer<*>? = GenericContainer(DockerImageName.parse("swaggerapi/petstore3"))
            .withExposedPorts(8080).waitingFor(Wait.forListeningPort())
        var baseUrl:String
        init {
            petStore?.start()
            baseUrl = "http://${petStore?.host}:${petStore?.firstMappedPort}/api/v3"
        }
    }
}