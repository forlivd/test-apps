package com.study.searchbook.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.searchbook.model.Book
import com.study.searchbook.model.BooksInfo
import com.study.searchbook.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.study.searchbook.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Response

private const val TAG = "BookViewModel"

@HiltViewModel
class BookViewModel @Inject constructor(
    private val bookRepository: BookRepository
): ViewModel() {

    // Loading 위한 observe
    private val _booksInfo: MutableStateFlow<Result<Response<BooksInfo>>> =
        MutableStateFlow(Result.Unintialized)
    val booksInfo get() = _booksInfo.asStateFlow()

    private val _books: MutableStateFlow<List<Book>> =
        MutableStateFlow(mutableListOf())
    val books get() = _books.asStateFlow()


    fun getBooks(id: String, secret: String, title: String, display: Int, start: Int) {
        viewModelScope.launch(Dispatchers.IO){
            bookRepository.getBooks(id, secret, title, display, start).collectLatest {
                if(it is Result.Success) {
//                    // Live data
//                    _booksInfo.value = it
//                    _books.value = it.data.body()!!.items

                    //state flow
                    //if Loading booksInfo 넣기
                    _booksInfo.emit(it)

                    //success 면 _booksInfo, books 다 넣기
                    _books.emit(it.data.body()!!.items)

                    Log.d(TAG, "Success getBooks: ${it.data.body()}")
                    Log.d(TAG, "Success _books: ${_books}")

                } else if(it is Result.Error) {

                    Log.d(TAG, "Error getBooks: $it")
                }
            }
        }
    }
}