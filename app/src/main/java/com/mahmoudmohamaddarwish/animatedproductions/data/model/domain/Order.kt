package com.mahmoudmohamaddarwish.animatedproductions.data.model.domain

data class Order(
    val property: Property,
    val type: Type,
) {
    enum class Type(val label: String) { ASCENDING("Ascending"), DESCENDING("Descending") }
    enum class Property(val label: String) {
        VOTE_AVERAGE("Vote average"),
        RELEASE_DATE("Release date"),
        POPULARITY("Popularity"),
    }

    companion object {
        val default = Order(Property.POPULARITY, Type.ASCENDING)
        val defaultProperty = Property.POPULARITY
        val defaultType = Type.ASCENDING
    }
}