package com.study.searchbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.study.searchbook.databinding.ItemBookBinding
import com.study.searchbook.model.Book
import com.study.searchbook.model.BooksInfo


class BookAdapter: ListAdapter<Book, BookAdapter.BookItemViewHolder>(diffUtil){

    // Viewholder : 리사이클러 뷰의 미리 만들어져 있는, 뷰를 담을 수 있는 홀더
    inner class BookItemViewHolder(private val binding: ItemBookBinding): RecyclerView.ViewHolder(binding.root) {
        // BOOK 데이터를 가져와 넣는다.
        // onBindViewHolder로 부터 아이템을 하나 가져와 Layout과 연결
        fun bind(bookModel: Book) {
            binding.tvTitle.text = bookModel.title
            // 이미지 넣어주기 image view에
        }
    }

    // 만들어진 Viewholder가 없으면 새로 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        // ViewHolder 객체를 생성한다.
        // LayoutInflater는 context에서 가져오는데, View에 있는 Constext 즉 ViewGroup을 참조하자.
        // layoutinflater, 어디에 붙을지, 붙을지 말 지 정보를 itemBookBinding의 inflate로 가져온다.
        // ItemBookBinding을 return 한다.
        return BookItemViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    // 데이터가 그려질 때 ViewHolder와 데이터를 Binding
    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        //ViewHolder의 Bind 함수를 호출한다.
        //currentList는 ListAdapter의 DataList ... 이 경우 Book의 List
        //Book List [position]의 Book을 ViewHolder의 bind에 전달
        holder.bind(currentList[position])
    }



    //difutil로 다른 값만 갱신
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Book>() {
            // 새로운, 이전의 아이템이 실제로 같은가?
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                // 객체 비교
                return oldItem == newItem
            }

            // 아이템의 내부가 같은가?
            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                // 어떤 걸 기준으로?
                return oldItem.isbn == newItem.isbn
            }

        }
    }
}