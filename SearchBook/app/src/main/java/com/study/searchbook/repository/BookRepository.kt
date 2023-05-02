package com.study.searchbook.repository

import com.study.searchbook.datasource.BookDataSource
import com.study.searchbook.model.BooksInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import com.study.searchbook.utils.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response

@Singleton
class BookRepository @Inject constructor(
    private val bookDataSource: BookDataSource
){
    fun getBooks(id: String, secret: String, title: String, display: Int, start: Int): Flow<Result<Response<BooksInfo>>> = flow {

        emit(Result.Loading)

        kotlinx.coroutines.delay(2000)

        bookDataSource.getBooks(id, secret, title, display, start).collect {
            if(it.code() == 200) {
                emit(Result.Success(it))
            } else if(it.code() == 501) {
                emit(Result.Error(Exception("서버 기능 오류")))
            } else {
                emit(Result.Error(Exception("오류")))
            }

        }

        kotlinx.coroutines.delay(3000)

        emit(Result.Error(Exception("Server 오류 400")))

    }.catch { e ->
        emit(Result.Error(e))
    }
}