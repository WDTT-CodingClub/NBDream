import kr.co.wdtt.convention.implementations
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("nbdream.android.library")
                apply("nbdream.android.hilt")
            }

            dependencies {
                implementations(
                    project(":core:ui"),
                    project(":core:common"),
                    project(":core:domain"),
                )
            }
        }
    }
}