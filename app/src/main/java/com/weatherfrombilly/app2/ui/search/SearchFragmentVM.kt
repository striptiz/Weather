package com.weatherfrombilly.app2.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weatherfrombilly.app2.data.repository.PreferencesRepository
import com.weatherfrombilly.app2.data.repository.WeatherRepository
import com.weatherfrombilly.app2.ui.model.LocationModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchFragmentVM(
    private val repository: WeatherRepository,
    private val prefRepo: PreferencesRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()

    private val _modelData: MutableLiveData<DataModel> = MutableLiveData()
    val modelData: LiveData<DataModel>
        get() = _modelData

    private val searchTextSubject = PublishSubject.create<String>()

    data class DataModel(val cities: List<LocationModel>)

    init {
        disposables.add(
            searchTextSubject
                .debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
                .subscribe({ city ->
                    fetchCities(city)
                }, Throwable::printStackTrace)
        )
    }

    private fun fetchCities(piece: String) {
        disposables.add(
            repository.getLocation(piece).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ cities ->
                    _modelData.postValue(DataModel(cities))
                }, Throwable::printStackTrace)
        )
    }

    fun onTextChanged(text: String) {
        searchTextSubject.onNext(text)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun onCityClicked(model: LocationModel) {
        prefRepo.saveCurrentCityId(model.cityId)
    }

    companion object {
        private const val DEBOUNCE_TIMEOUT = 250L
    }
}