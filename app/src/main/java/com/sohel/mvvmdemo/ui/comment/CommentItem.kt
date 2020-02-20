package com.sohel.mvvmdemo.ui.comment

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sohel.mvvmdemo.R
import com.sohel.mvvmdemo.data.remote.models.CommentResponse
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.comment_item.*


class CommentItem(private val comment: CommentResponse) : Item() {

    override fun getLayout() = R.layout.comment_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            Glide.with(this.containerView)
                .load(comment.user.avatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(user_iv)
            user_name_tv.text = comment.user.login
            comment_tv.text = comment.body
        }

    }
}