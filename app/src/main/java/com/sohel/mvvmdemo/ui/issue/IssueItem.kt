package com.sohel.mvvmdemo.ui.issue

import com.sohel.mvvmdemo.R
import com.sohel.mvvmdemo.data.db.entity.IssueResponse
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.issue_item.*


class IssueItem(val issue: IssueResponse) : Item() {

    override fun getLayout() = R.layout.issue_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.issue_title_tv.text = issue.title
        viewHolder.issue_desc_tv.text = issue.body
    }
}