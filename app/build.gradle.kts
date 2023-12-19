plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.myhivemq"
    compileSdk = 34

    packagingOptions {
        exclude("META-INF/INDEX.LIST")
        exclude("META-INF/io.netty.versions.properties")
    }

    defaultConfig {
        applicationId = "com.example.myhivemq"
        minSdk = 33
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.hivemq:hivemq-mqtt-client:1.3.0")
    implementation(platform("com.hivemq:hivemq-mqtt-client-websocket:1.3.0"))
    implementation(platform("com.hivemq:hivemq-mqtt-client-proxy:1.3.0"))
    implementation(platform("com.hivemq:hivemq-mqtt-client-epoll:1.3.0"))
    implementation("com.hivemq:hivemq-mqtt-client-reactor:1.3.0")
}