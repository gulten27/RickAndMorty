package com.gultendogan.rickandmorty.domain.usecase

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.gultendogan.rickandmorty.data.entities.Character.toCharacterUIModel
import com.gultendogan.rickandmorty.data.repo.AppRepository
import com.gultendogan.rickandmorty.domain.uimodel.CharacterUIModel
import com.gultendogan.rickandmorty.utils.Constants.FIRST_PAGE_INDEX
import com.gultendogan.rickandmorty.utils.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCharacterUIModelUseCase @Inject constructor(var repo: AppRepository) {
    fun executeGetCharacters(coroutineScope: CoroutineScope): Flow<NetworkResult<PagingData<CharacterUIModel>>> =
        flow {
            try {
                emit(NetworkResult.Loading())
                val response = repo.getCharactersNetworkResult(FIRST_PAGE_INDEX)
                if(response.characters.isNullOrEmpty()){
                    emit(NetworkResult.Error(message = "No Rick And Morty Character"))
                }else{
                    repo.getCharacters().cachedIn(coroutineScope).collect {
                        emit(NetworkResult.Success(it.map {
                                character -> character.toCharacterUIModel()
                        }))
                    }
                }

            } catch (e: HttpException) {
                emit(NetworkResult.Error(message = e.localizedMessage ?: "Error!"))
            } catch (e: IOException) {
                emit(NetworkResult.Error(message = "Could not reach URL"))
            }
        }
}