package com.sohel.mvvmdemo.ui.issue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.happydemy.utils.hide
import com.google.android.material.snackbar.Snackbar
import com.sohel.mvvmdemo.R
import com.sohel.mvvmdemo.data.db.entity.IssueResponse
import com.sohel.mvvmdemo.data.repository.IssueRepository
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.issues_fragment.*


class IssueFragment : Fragment() {

    private lateinit var viewModel: IssueViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.issues_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(IssueViewModel::class.java)
        bindUI()
    }

    private fun bindUI() {
        viewModel.issueResponse.observe(this, Observer {
            issue_pb.hide()
            initRecyclerView(it.toIssueItems())
        })
    }

    private fun List<IssueResponse>.toIssueItems(): List<IssueItem> {
        return this.map {
            IssueItem(it)
        }
    }

    private fun initRecyclerView(items: List<IssueItem>) {
        val issueAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(items)
        }

        issues_rv.apply {
            layoutManager = LinearLayoutManager(this@IssueFragment.context)
            adapter = issueAdapter
        }

        issueAdapter.setOnItemClickListener { item, view ->
            (item as? IssueItem)?.let {
                if (it.issue.comments > 0)
                    goToIssueCommentsPage(it.issue.commentsUrl, view)
                else
                    Snackbar.make(view.rootView.findViewById(android.R.id.content),
                        "No comments found!",
                        Snackbar.LENGTH_SHORT
                    ).show()
            }
        }
    }

    private fun goToIssueCommentsPage(commentUrl: String, view: View) {
        val bundle = Bundle()
        bundle.putString("commentUrl", commentUrl)
        Navigation.findNavController(view).navigate(R.id.commentFragment, bundle)
    }

}
