package com.app.igrow.ui.learning

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.app.igrow.R
import com.app.igrow.adpters.LearningListAdapter
import com.app.igrow.base.BaseFragment
import com.app.igrow.data.model.sheets.Videos
import com.app.igrow.databinding.FragmentLearningsBinding
import com.app.igrow.utils.gone
import com.app.igrow.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class LearningsFragment : BaseFragment<FragmentLearningsBinding>() {


    private var youtubeVideosList = arrayListOf<Videos>()
    private var filteredList = arrayListOf<Videos>()
    private val viewModel: LearningViewModel by viewModels()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLearningsBinding
        get() = FragmentLearningsBinding::inflate

    private lateinit var adapter: LearningListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLearningData()
        activateObserver()
        activateListeners()
        initRecyclerView()

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
                updateDataInList(it)
            }
        }
    }

    private fun activateListeners(){
        binding.etSearch.doAfterTextChanged { editable ->
            if (!editable.isNullOrEmpty() && editable.isNotBlank()) {
                val myResult = searchList(editable.toString())
                if(myResult.isNotEmpty()){
                    binding.tvNoRecordFound.gone()
                    updateDataInList(myResult)
                } else {
                    filteredList.clear()
                    updateDataInList(filteredList)
                    binding.tvNoRecordFound.visible()
                }
            } else {
                filteredList.clear()
                updateDataInList(youtubeVideosList)
                binding.tvNoRecordFound.gone()
            }
        }
    }

    private fun searchList(searchValue: String): java.util.ArrayList<Videos> {
        try {
            filteredList.clear()
            if (youtubeVideosList.size < 0) {
                return arrayListOf()
            }
            filteredList = youtubeVideosList.filter { model ->
                model.title.lowercase(Locale.getDefault())
                    .contains(searchValue.lowercase(Locale.getDefault()))
            } as java.util.ArrayList<Videos>

            if (filteredList.isEmpty()) {
                return filteredList
            }
            return filteredList
        } catch (ex: Exception) {
            Log.e("TAG", ex.stackTraceToString())
        }
        return arrayListOf()
    }

    private fun shareAppLink(youtubeVideoLink: String) {
        val videoLink = youtubeVideoLink
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_SUBJECT, videoLink)
        startActivity(Intent.createChooser(i, "Share Video URL"))
    }

    private fun updateDataInList(myList: ArrayList<Videos>) {
        adapter.differ.submitList(myList)
        adapter.notifyDataSetChanged()
    }

}