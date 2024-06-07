plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.booki.ai"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.booki.ai"
        minSdk = 24
        targetSdk = 34
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
    buildFeatures {
        viewBinding = true
    }

    configurations.all {
        exclude(group = "xmlpull", module = "xmlpull")
    }
}


dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("com.google.firebase:firebase-storage:21.0.0")
    implementation("com.firebaseui:firebase-ui-storage:7.2.0")
    implementation("com.github.bumptech.glide:glide:4.14.2")
    implementation("androidx.activity:activity:1.8.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")
    implementation("com.google.android.play:integrity:1.3.0")



    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("co.aenterhy:toggleswitch:1.0.0")
    implementation("com.google.android.gms:play-services-auth:21.1.1")

    implementation("com.github.hoomanmmd:fadeoutparticle:1.1.0")
    implementation("com.github.yuyakaido:CardStackView:v2.3.4")


    implementation("com.github.Dimezis:BlurView:version-2.0.3")
    implementation("com.github.NitishGadangi:TypeWriter-TextView:v1.3")
    implementation("com.github.valkriaine:Bouncy:2.3")
    implementation("com.github.EyalBira:loading-dots:-SNAPSHOT")


    implementation("com.ncorti:slidetoact:0.11.0")

//    implementation("com.github.mertakdut:EpubParser:1.0.95")
//    implementation("com.github.HamedTaherpour:ht-epub-reader-android:0.0.5")
    implementation("org.jsoup:jsoup:1.14.3")

    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("com.github.psiegman:epublib:69ac6b0")
    implementation("com.airbnb.android:lottie:3.4.0")
}
