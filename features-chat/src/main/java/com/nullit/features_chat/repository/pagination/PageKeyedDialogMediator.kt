package com.nullit.features_chat.repository.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.nullit.core.generateBearerToken
import com.nullit.core.persistance.dao.UserDao
import com.nullit.features_chat.api.ApiService
import com.nullit.features_chat.mappers.DialogMapper
import com.nullit.features_chat.persistance.dao.DialogDao
import com.nullit.features_chat.persistance.entity.DialogEntity
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PageKeyedDialogMediator
@Inject
constructor(
    private val dialogDao: DialogDao,
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val dialogMapper: DialogMapper
) : RemoteMediator<Int, DialogEntity>() {

    var token: String? = null

    companion object {
        const val FIRST_PAGE = 1
        const val PAGE_SIZE = 15
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DialogEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                FIRST_PAGE
            }
            LoadType.APPEND -> {
                state.pages.size + 1
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
        }
        try {
            token = userDao.requestUserInfo()?.token?.generateBearerToken()
            val response = apiService.requestDialogListByPage(page = page, token = token, qty = PAGE_SIZE)
            dialogDao.insertAllDialogs(dialogMapper.fromDialogListDtoToListDialogEntity(response))
            val endOfPagination = response.total != PAGE_SIZE || response.dialogList.isEmpty()
            return MediatorResult.Success(endOfPaginationReached = endOfPagination)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}
