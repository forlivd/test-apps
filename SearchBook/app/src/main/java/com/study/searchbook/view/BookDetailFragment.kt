package com.study.searchbook.view

import android.os.Build
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.navArgs
import com.study.searchbook.R
import com.study.searchbook.base.BaseFragment
import com.study.searchbook.databinding.FragmentBookDetailBinding


class BookDetailFragment : BaseFragment<FragmentBookDetailBinding>(R.layout.fragment_book_detail) {

    // navigation으로 전달받은 url 가져오기 위해
    val args: BookDetailFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun init() {
        binding.wvBookDetail.apply {
            // 새 창(Chrome) 띄우지 않고 프래그먼트에 띄우기
            webViewClient = WebViewClient()

            // 그외 세팅
            settings.apply {
                javaScriptEnabled = true // javascript 띄우기
                domStorageEnabled = true
            }

            // url 띄우기
            loadUrl(args.url)
        }
    }

}