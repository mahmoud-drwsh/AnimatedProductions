package com.mahmoudmohamaddarwish.animatedproductions.screens.shared_viewmodels

import androidx.lifecycle.ViewModel
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ProductionDetailsViewModel @Inject constructor() : ViewModel() {

    private val _productionObject: MutableStateFlow<Resource<Production>> =
        MutableStateFlow(Resource.Loading)
    val productionObjectFlow: Flow<Resource<Production>>
        get() = _productionObject

    fun loadProductionObject(production: Production?) {
        val objectHasNotBeenLoaded = _productionObject.value !is Resource.Success

        if (objectHasNotBeenLoaded && production != null) {
            _productionObject.value = Resource.Success(production)
        } else if (objectHasNotBeenLoaded) {
            _productionObject.value = Resource.Error(UNEXPECTED_ERROR_MESSAGE)
        }
    }

    companion object {
        const val UNEXPECTED_ERROR_MESSAGE = "Unexpected error"
    }
}

