package com.study.searchbook.view

import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.searchbook.R
import com.study.searchbook.adapter.BookAdapter
import com.study.searchbook.base.BaseFragment
import com.study.searchbook.databinding.FragmentBookBinding
import com.study.searchbook.model.Book
import kotlinx.coroutines.launch
import com.study.searchbook.utils.Result
import kotlinx.coroutines.flow.collect


class BookFragment : BaseFragment<FragmentBookBinding>(R.layout.fragment_book) {

    private val client_id = "JByY3rxLsDenflIN8gfB"
    private val client_secret = "R6RRNnD1Um"

    private val bookViewModel: BookViewModel by activityViewModels()
    
    private lateinit var bookAdapter: BookAdapter

    override fun init() {
        initRecyclerView()
        initObserver()

        getBookInfo(20, 1)
    }

    private fun initRecyclerView() {
        bookAdapter = BookAdapter()

        binding.rcBook.apply {
            // 어떤 방식으로?
            layoutManager = LinearLayoutManager(requireContext())

            // adpater 클릭 이벤트 세팅
            adapter = bookAdapter.apply {
                setBookClickListener(object: BookAdapter.BookClickListener {
                    override fun onClick(view: View, position: Int, item: Book) {
                        findNavController().navigate(
                            BookFragmentDirections.actionBookFragmentToBookDetailFragment(item.link))
                    }
                })
            }

            // 스크롤 마지막 위치 확인 20개 12 13 17
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    // 마지막 스크롤된 항목 위치 - 화면에 보이는 마지막 아이템 position
                    val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                    // 항목 전체 개수
                    val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                    // 마지막으로 보이는 아이템이 = 마지막 인덱스다
                    if (lastVisibleItemPosition == itemTotalCount) {
                        Log.d("SCROLL", "last Position...");
                        getBookInfo(20, bookAdapter.itemCount+1)
                    }
                }

            })
        }

    }

    private fun initObserver() {
        // getBook 으로 데이터 가져오면 Recycler 갱신
        viewLifecycleOwner.lifecycleScope.launch {
            bookViewModel.books.collect {
                // 현재 가지고 있는 아이템 리스트 가져옴 20
                val newList = bookAdapter.currentList.toMutableList()
                // 새롭게 가져온 아이템 넣기 40
                newList.addAll(it)
                // 데이터 갱신 20 -> 40
                bookAdapter.submitList(newList)
            }
        }

        // Result로 getBookInfo 시작 시 Loading을 발행하는 BookInfo observe
        // it == Result.Loading 이면 대기 띄워줌
        // Error 시 Error 알림
        viewLifecycleOwner.lifecycleScope.launch {
            bookViewModel.booksInfo.collect {
                if (it is Result.Loading) {
                    showProgressBar()
                } else if (it is Result.Success) {
                    hideProgressBar()
                } else {
                    hideProgressBar()
                }
            }
        }
    }

    // 검색 버튼 누르면 호출
    // display : 몇 개씩? start : 어디서 부터? - 현재 아이템 갯수 + 1 부터 가져온다.
    private fun getBookInfo(display: Int, start: Int) {
        // 검색어 저장 및 검색
        // 안드로이드 대신 String EditText 입력
        // Room 검색어 저장
        bookViewModel.getBooks(client_id, client_secret, "안드로이드", display, start)
    }

    private fun showProgressBar() {
        binding.pbSearchBook.visibility = View.VISIBLE
        // 화면 클릭 막기
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun hideProgressBar() {
        binding.pbSearchBook.visibility = View.GONE
        // 화면 클릭 풀기
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

}