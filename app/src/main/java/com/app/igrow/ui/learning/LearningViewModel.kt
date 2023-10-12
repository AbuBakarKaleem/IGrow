package com.app.igrow.ui.learning

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Video
import com.app.igrow.data.usecase.user.videos.GetLearningDataUsecase
import com.app.igrow.ui.admin.AdminUIStates
import com.app.igrow.ui.admin.LoadingState
import com.app.igrow.ui.admin.UnloadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearningViewModel @Inject constructor(
    private val getLearningDataUsecase: GetLearningDataUsecase
) : ViewModel() {
    private var _uiState = MutableLiveData<AdminUIStates>()
    var uiStateLiveData: LiveData<AdminUIStates> = _uiState

    private var getLearningDataMutableLiveData = MutableLiveData<ArrayList<Video>?>()
    var getLearningDataLiveData: MutableLiveData<ArrayList<Video>?> = getLearningDataMutableLiveData

    fun getLearningData() {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            getLearningDataUsecase.invoke().collect {
                when (it) {
                    is DataState.Success -> {
                        getLearningDataMutableLiveData.postValue(it.data)
                    }
                    is DataState.Error -> {
                        getLearningDataMutableLiveData.postValue(null)
                    }

                }
                _uiState.postValue(UnloadingState)
            }
        }
    }
}
