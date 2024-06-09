package kr.co.wdtt.convention

import java.util.Locale
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ConfigKey(
    val name: String
)

enum class Secrets(
    @ConfigKey("PACKAGE_NAME")
    val packageName: String,
) {
    DEV(
        "kr.co.nbdream.dev"
    ),
    PROD(
        "kr.co.nbdream"
    );

    fun toManifestPlaceholders(): Map<String, Any>{
        return toMap()
    }

    fun toBuildConfig(): Map<String, String>{
        return toMap(valueConverter = { "\"$it\"" })
    }

    fun toResValues(): Map<String, String>{
        return toMap(keyConverter = { "key_${it.lowercase(Locale.ENGLISH)}" })
    }

    private inline fun toMap(
        crossinline keyConverter: (key: String) -> String = { it },
        crossinline valueConverter: (value: String) -> String = { it }
    ): Map<String, String> {
        return Secrets::class.declaredMemberProperties.asSequence()
            .map {
                val key = it.javaField!!.getAnnotation(ConfigKey::class.java)
                keyConverter.invoke(key.name) to valueConverter.invoke(it.get(this) as String)
            }.associateBy({ it.first }, { it.second })
    }
}