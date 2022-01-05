[![](https://jitpack.io/v/Barbapaue/ItalianTaxCodeValidator.svg)](https://jitpack.io/#Barbapaue/ItalianTaxCodeValidator)

# ItalianTaxCodeValidator

This small library was developed to validate Italian tax codes.

This library is based on another library from which I took inspiration by adapting it to the style of Kotlin.
https://www.icosaedro.it/cf-pi/vedi-codice.cgi?f=cf-java.txt

# Getting started

### Script

1. Add the JitPack repository to your build file (root build.gradle):

``` groovy 
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

2. Add the dependency

``` groovy
dependencies {
    implementation 'com.github.Barbapaue:ItalianTaxCodeValidator:Tag'
}
```

# Credit

Thanks, https://www.icosaedro.it/it-index.html
