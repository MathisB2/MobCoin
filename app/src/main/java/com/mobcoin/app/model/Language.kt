package com.mobcoin.app.model

enum class Language(val code: String, vararg val names: String) {
    FRENCH("fr", "french", "français","法语"),
    ENGLISH("en", "english", "anglais","英语"),
    CHINESE("zh", "chinese", "chinois","中文");

    companion object {
        fun getLanguageCode(name: String): String {
            for (language in values()) {
                for (languageName in language.names) {
                    if (languageName.equals(name, ignoreCase = true)) {
                        return language.code
                    }
                }
            }
            throw IllegalArgumentException("Unsupported language name: $name")
        }
    }
}

