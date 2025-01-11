package com.mobcoin.app.domain


import com.mobcoin.app.domain.api.FNGNetworkDataSource
import com.mobcoin.app.model.FNGContainer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

object FNGRepository {

    suspend fun getFNG() : Flow<Response<FNGContainer>> = flow {
        emit(
            FNGNetworkDataSource.apiService.getFNG()
        )
    }

}