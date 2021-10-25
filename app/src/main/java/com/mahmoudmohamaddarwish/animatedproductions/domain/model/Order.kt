package com.mahmoudmohamaddarwish.animatedproductions.domain.model

data class Order(
    val property: Property,
    val type: Type,
) {
    enum class Type { ASCENDING, DESCENDING }
    enum class Property { Name, RELEASE_DATE }

    companion object {
        val default = Order(Property.Name, Type.ASCENDING)
        val defaultProperty = Property.Name
        val defaultType = Type.ASCENDING
    }
}