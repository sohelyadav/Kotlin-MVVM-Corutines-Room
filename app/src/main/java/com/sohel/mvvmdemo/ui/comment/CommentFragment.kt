package com.sohel.mvvmdemo.ui.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.happydemy.utils.hide
import com.sohel.mvvmdemo.R
import com.sohel.mvvmdemo.data.remote.models.CommentResponse
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.comment_fragment.*


class CommentFragment : Fragment() {


    private lateinit var viewModel: CommentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.comment_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CommentViewModel::class.java)
        arguments!!.getString("commentUrl")?.let { viewModel.getComment(it) }
        bindUI()
    }

    private fun bindUI() {
        viewModel.commentResponse.observe(this, Observer {
            comment_pb.hide()
            initRecyclerView(it.toCommentItems())
        })
    }


    private fun List<CommentResponse>.toCommentItems(): List<CommentItem> {
        return this.map {
            CommentItem(it)
        }
    }

    private fun initRecyclerView(items: List<CommentItem>) {
        val commentAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(items)
        }

        comment_rv.apply {
            layoutManager = LinearLayoutManager(this@CommentFragment.context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = commentAdapter
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }
}
