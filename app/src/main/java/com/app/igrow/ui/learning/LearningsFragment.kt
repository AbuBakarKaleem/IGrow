package com.app.igrow.ui.learning

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.app.igrow.R
import com.app.igrow.adpters.LearningListAdapter
import com.app.igrow.base.BaseFragment
import com.app.igrow.data.model.sheets.Videos
import com.app.igrow.databinding.FragmentLearningsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LearningsFragment : BaseFragment<FragmentLearningsBinding>() {


    private var youtubeVideosList = arrayListOf<Videos>()
    private val viewModel: LearningViewModel by viewModels()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLearningsBinding
        get() = FragmentLearningsBinding::inflate

    private lateinit var adapter: LearningListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLearningData()
        activateObserver()
    }

    private fun initRecyclerView() {
        adapter = LearningListAdapter({ videoClickUrl ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoClickUrl.link))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }, { shareItemClick ->
            shareAppLink(shareItemClick.link)
        })
        binding.rcLearnings.adapter = adapter
        updateDataInList(youtubeVideosList)
    }

    private fun activateObserver() {
        viewModel.getLearningDataLiveData.observe(viewLifecycleOwner) {
            if (it == null || it.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_data_found),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                youtubeVideosList.addAll(it)
                initRecyclerView()
            }
        }
    }

    private fun shareAppLink(youtubeVideoLink: String) {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_SUBJECT, youtubeVideoLink)
        startActivity(Intent.createChooser(i, "Share Video URL"))
    }

    private fun updateDataInList(myList: ArrayList<Videos>) {
        adapter.differ.submitList(myList)
        adapter.notifyDataSetChanged()
    }

}