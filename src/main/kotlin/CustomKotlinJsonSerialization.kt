import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.karumi.kotlinsnapshot.core.KotlinSerialization
import com.karumi.kotlinsnapshot.core.SerializationModule


class CustomKotlinJsonSerialization: SerializationModule {

    var strategy: ExclusionStrategy = object : ExclusionStrategy {
        override fun shouldSkipClass(clazz: Class<*>?): Boolean {
            return false
        }

        override fun shouldSkipField(field: FieldAttributes): Boolean {
            return field.getAnnotation(Exclude::class.java) != null
        }
    }

    private val customGson = KotlinSerialization.gsonBuilder
        .addSerializationExclusionStrategy(strategy)
        .create()

    override fun serialize(value: Any?): String = customGson.toJson(value)
}