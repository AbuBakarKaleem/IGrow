package com.app.igrow.data.usecase.user.videos

import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Video
import com.app.igrow.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLearningDataUsecase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(
    ): Flow<DataState<ArrayList<Video>>> {
        return repository.getLearningData()
    }
}