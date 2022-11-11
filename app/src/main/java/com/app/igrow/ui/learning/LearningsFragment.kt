package com.app.igrow.ui.learning

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.igrow.adpters.LearningListAdapter
import com.app.igrow.base.BaseFragment
import com.app.igrow.databinding.FragmentLearningsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LearningsFragment : BaseFragment<FragmentLearningsBinding>() {


    private var youtubeVideosList = arrayListOf<String>()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLearningsBinding
        get() = FragmentLearningsBinding::inflate

    private lateinit var adapter: LearningListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createMockDataList()
        initView()
        activateListener()
    }

    private fun initView() {

        adapter = LearningListAdapter({ videoClickUrl ->
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse(videoClickUrl))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.google.android.youtube");
            startActivity(intent)
        }, { shareItemClick ->
            shareAppLink(shareItemClick)
        })

        binding.rcLearnings.adapter = adapter
        updateDataInList(youtubeVideosList)

    }


    private fun activateListener() {

    }


    private fun shareAppLink(youtubeVideoLink:String) {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_SUBJECT, youtubeVideoLink)
        startActivity(Intent.createChooser(i, "Share Video URL"))
    }

    private fun updateDataInList(myList: ArrayList<String>) {
        adapter.differ.submitList(myList)
        adapter.notifyDataSetChanged()
    }


    private fun createMockDataList() {
        youtubeVideosList.add("https://www.youtube.com/watch?v=1f0bObjoAqI")
        youtubeVideosList.add("https://www.youtube.com/watch?v=O0KSZn5QuX0")
        youtubeVideosList.add("https://www.youtube.com/watch?v=jso5-dz7mjY")
        youtubeVideosList.add("https://www.youtube.com/watch?v=K93e8MpKwo0")
    }

}