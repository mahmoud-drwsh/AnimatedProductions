package com.mahmoudmohamaddarwish.animatedproductions.domain.model

data class Order(
    val property: Property,
    val type: Type,
) {
    enum class Type(val label: String) { ASCENDING("Ascending"), DESCENDING("Descending") }
    enum class Property(val label: String) { Name("Name"), RELEASE_DATE("Release Date") }

    companion object {
        val default = Order(Property.Name, Type.ASCENDING)
        val defaultProperty = Property.Name
        val defaultType = Type.ASCENDING
    }
}