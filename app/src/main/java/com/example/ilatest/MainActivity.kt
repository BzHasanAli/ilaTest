package com.example.ilatest

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.ilatest.adapter.CarouselAdapter
import com.example.ilatest.adapter.ItemAdapter
import com.example.ilatest.databinding.ActivityMainBinding
import com.example.ilatest.dto.Item
import com.example.ilatest.dto.PageContent
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    private var contentList: List<PageContent> = listOf(
        PageContent(
            R.drawable.carousel_1,
            listOf(
                Item(R.drawable.carousel_1, "Apples"),
                Item(R.drawable.carousel_2, "Bicycle"),
                Item(R.drawable.carousel_3, "Notebook"),
                Item(R.drawable.carousel_1, "Sunglasses"),
                Item(R.drawable.carousel_2, "Toothbrush"),
                Item(R.drawable.carousel_2, "Backpack"),
                Item(R.drawable.carousel_3, "Coffee mug"),
                Item(R.drawable.carousel_2, "Headphones"),
                Item(R.drawable.carousel_1, "Wallet"),
                Item(R.drawable.carousel_2, "Guitar"),
                Item(R.drawable.carousel_2, "Tennis shoes"),
                Item(R.drawable.carousel_3, "Car keys"),
                Item(R.drawable.carousel_3, "Coffee mug"),
                Item(R.drawable.carousel_2, "Headphones"),
                Item(R.drawable.carousel_1, "Wallet"),
                Item(R.drawable.carousel_2, "Guitar"),
                Item(R.drawable.carousel_2, "Tennis shoes"),
                Item(R.drawable.carousel_3, "Car keys"),
            )
        ),
        PageContent(
            R.drawable.carousel_2,
            listOf(
                Item(R.drawable.carousel_3, "Coffee mug"),
                Item(R.drawable.carousel_2, "Headphones"),
                Item(R.drawable.carousel_1, "Wallet"),
                Item(R.drawable.carousel_2, "Guitar"),
                Item(R.drawable.carousel_2, "Tennis shoes"),
                Item(R.drawable.carousel_3, "Car keys"),
                Item(R.drawable.carousel_3, "Coffee mug"),
                Item(R.drawable.carousel_2, "Headphones"),
                Item(R.drawable.carousel_1, "Wallet"),
                Item(R.drawable.carousel_2, "Guitar"),
                Item(R.drawable.carousel_2, "Tennis shoes"),
                Item(R.drawable.carousel_3, "Car keys"),
            )
        ),
        PageContent(
            R.drawable.carousel_3,
            listOf(
                Item(R.drawable.carousel_1, "Hammer"),
                Item(R.drawable.carousel_1, "Umbrella"),
                Item(R.drawable.carousel_3, "Soccer ball"),
                Item(R.drawable.carousel_3, "Chess board"),
                Item(R.drawable.carousel_3, "Houseplant"),
                Item(R.drawable.carousel_2, "Alarm clock")
            )
        )
    )

    private val imageList: MutableList<Int>
        get() {
            val list = mutableListOf<Int>()
            contentList.forEach {
                list.add(it.image)
            }
            return list
        }
    private var activeList = contentList[0].itemList

    private lateinit var carouselAdapter: CarouselAdapter
    private lateinit var adapter: ItemAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureView()
    }

    private fun configureView() {
        setUpCarousel()
        setUpList()
    }
    private fun setUpCarousel() {
        with (binding) {
            // setting up the carousel
            carouselAdapter = CarouselAdapter(imageList)
            viewPager.adapter = carouselAdapter

            viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            viewPager.currentItem = 0
            viewPager.registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {

                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        activeList = contentList[position].itemList
                        binding.etSearch.text.clear()
                        setItemList(activeList)
                    }
                }
            )

            // setting up the indicator
            TabLayoutMediator(
                tlIndicator, viewPager
            ) { tab: TabLayout.Tab?, position: Int ->
//                if (position == viewPager.currentItem) {
//                    (tab?.customView as ImageView?)?.setImageResource(R.drawable.view_pager_dot_selected)
//                } else {
//                    (tab?.customView as ImageView?)?.setImageResource(R.drawable.view_pager_dot)
//                }
            }.attach()
        }
    }
    private fun setUpList() {
        binding.rvItemList.layoutManager = LinearLayoutManager(this)
        setItemList(activeList)

        // Set up text change listener for the search EditText
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filterItems(s.toString())
            }
        })
    }

    private fun setItemList(items: List<Item>) {
        adapter = ItemAdapter(items)
        binding.rvItemList.adapter = adapter
    }
    private fun filterItems(query: String) {
        val filteredList = activeList.filter { item ->
            item.label.contains(query, ignoreCase = true)
        }
        setItemList(filteredList)
    }

    private fun unregisterViewPager() {
        binding.viewPager.unregisterOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {}
        )
    }
    override fun onDestroy() {
        super.onDestroy()

        unregisterViewPager()
    }
}