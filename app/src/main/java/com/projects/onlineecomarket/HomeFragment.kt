package com.projects.onlineecomarket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.projects.onlineecomarket.Adapter.ImageSliderAdapter
import com.projects.onlineecomarket.Adapter.CategoryAdapter
import com.projects.onlineecomarket.Category

class HomeFragment : Fragment() {

    private lateinit var viewPager2 : ViewPager2
    private lateinit var adapter: ImageSliderAdapter
    private lateinit var imageList : ArrayList<Int>
    private lateinit var handler: Handler
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryList: ArrayList<Category>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        viewPager2 = view.findViewById(R.id.imageSlider)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setTransformer()
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewPager2.removeCallbacks(runnable)
                viewPager2.postDelayed(runnable, 3000)
            }

        })

    }

    private val runnable = Runnable{
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun setTransformer() {

        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(10))
        transformer.addTransformer{page, position ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.14f

        }

        viewPager2.setPageTransformer(transformer)
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStart() {
        super.onStart()
        viewPager2.postDelayed(runnable, 3000)
    }

    private fun init() {
        categoryList = ArrayList()
        categoryAdapter = CategoryAdapter()
        handler = Handler(Looper.myLooper()!!)

        categoryList.add(Category("Fruits", R.drawable.fuits))
        categoryList.add(Category("Dried Fruits", R.drawable.driedfruits))
        categoryList.add(Category("Vegetables", R.drawable.vegetables))
        categoryList.add(Category("Greenery", R.drawable.greenery))
        categoryList.add(Category("Coffee", R.drawable.coffee))
        categoryList.add(Category("Milk", R.drawable.milk))

        viewPager2.adapter = categoryAdapter
        categoryAdapter.setCategories(categoryList)
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    }
