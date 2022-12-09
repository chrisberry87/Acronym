package com.example.acronyms.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acronyms.repository.AbbItem
import com.example.acronyms.repository.AcromineApi
import com.example.acronyms.repository.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okio.IOException
import retrofit2.HttpException

const val TAG="MainViewModel"

class MainViewModel(private val apiService: AcromineApi)  : ViewModel() {
    var keyword: String = ""

    private var _meaningsData = MutableLiveData<List<AbbItem>>()
    private var _errorMessage = MutableLiveData<String>()

    val meaningsData: LiveData<List<AbbItem>> get() = _meaningsData
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getMeaningData() {
        viewModelScope.launch {
            val response = try {
                apiService.getMeanings(keyword)
            } catch (e: IOException){
                Log.e(TAG, "IOException, you might not have internect connection")
                _errorMessage.setValue("IOException, you might not have internect connection")
                return@launch
            } catch(e: HttpException){
                Log.e(TAG, "HTTPException, unexpected response")
                _errorMessage.setValue("HTTPException, unexpected response")
                return@launch
            }

            if(response.isSuccessful && response.body() != null){
                val data:List<AbbItem> = response.body() as List<AbbItem>
                _meaningsData.setValue(data)
                _errorMessage.setValue("SUCCESS")
            } else {
                Log.e(TAG, "Response not successful")
                _errorMessage.setValue("Response not successful")
            }
        }
    }
}