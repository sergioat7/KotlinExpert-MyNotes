package com.aragones.sergio.kotlinexpert.data

data class Note(val title: String, val description: String, val type: Type) {
    enum class Type { TEXT, AUDIO }
}