package com.assignement.jet2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.assignement.jet2.R
import com.assignement.jet2.model.ArticleModel
import com.bumptech.glide.Glide
import kotlin.math.ln


class ArticleAdapter(articleList: ArrayList<ArticleModel.Article>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mArticleList = ArrayList<ArticleModel.Article>()

    init {
        mArticleList.addAll(articleList)
    }

    fun updateArticleList(articleList: List<ArticleModel.Article>) {
        this.mArticleList.addAll(articleList as ArrayList<ArticleModel.Article>)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mArticleList.size
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvHeader: TextView = view.findViewById(R.id.tv_header)
        var tvFooter: TextView = view.findViewById(R.id.tv_footer)
        var tvReadTime: TextView = view.findViewById(R.id.tv_time)
        var imgUser: ImageView = view.findViewById(R.id.img_user)
        var ivArticleImage: ImageView = view.findViewById(R.id.iv_article_image)
        var tvUserName: TextView = view.findViewById(R.id.tv_user_name)
        var tvUserDesignation: TextView = view.findViewById(R.id.tv_user_designation)
        var tvArticleContent: TextView = view.findViewById(R.id.tv_article_content)
        var tvArticleTitle: TextView = view.findViewById(R.id.tv_article_title)
        var tvArticleUrl: TextView = view.findViewById(R.id.tv_article_url)
        var tvArticleLikeCount: TextView = view.findViewById(R.id.tv_article_like_count)
        var tvArticleCommentCount: TextView = view.findViewById(R.id.tv_article_comment_count)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder
        if(position == 0) {
            itemViewHolder.tvHeader.visibility = View.VISIBLE
            itemViewHolder.tvFooter.visibility = View.GONE
        } else if (position == mArticleList.size) {
            itemViewHolder.tvFooter.visibility = View.VISIBLE
            itemViewHolder.tvHeader.visibility = View.GONE
        } else {
            itemViewHolder.tvFooter.visibility = View.GONE
            itemViewHolder.tvHeader.visibility = View.GONE
        }
        val article = mArticleList[position]
        val user = article.user
        val media = article.media
        val context = itemViewHolder.itemView.context
        Glide.with(context).load(user[0].avatar)
            .into(itemViewHolder.imgUser)
        itemViewHolder.tvUserName.text = user[0].name.plus(" ").plus(user[0].lastname)
        itemViewHolder.tvUserDesignation.text = user[0].designation
        itemViewHolder.tvArticleContent.text = article.content
        itemViewHolder.tvReadTime.text = article.readTime
        itemViewHolder.tvArticleLikeCount.text = withSuffix(article.likes.toLong()).plus(" ").plus(
            context.getString(
                R.string.likes
            )
        )
        itemViewHolder.tvArticleCommentCount.text =
            withSuffix(article.comments.toLong()).plus(" ").plus(
                context.getString(
                    R.string.comments
                )
            )
        if (media.isNotEmpty()) {

            itemViewHolder.tvArticleTitle.visibility = View.VISIBLE
            itemViewHolder.tvArticleUrl.visibility = View.VISIBLE
            itemViewHolder.ivArticleImage.visibility = View.VISIBLE
            itemViewHolder.tvArticleTitle.text = media[0].title
            itemViewHolder.tvArticleUrl.text = media[0].url
            Glide.with(context).load(media[0].image)
                .into(itemViewHolder.ivArticleImage)
        } else {
            itemViewHolder.tvArticleTitle.visibility = View.GONE
            itemViewHolder.tvArticleUrl.visibility = View.GONE
            itemViewHolder.ivArticleImage.visibility = View.GONE
        }
    }

    private fun withSuffix(count: Long): String? {
        if (count < 1000) return "" + count
        val exp = (Math.log(count.toDouble()) / ln(1000.0)).toInt()
        return String.format("%.1f%c", count / Math.pow(1000.0, exp.toDouble()), "kMGTPE"[exp - 1])
    }
}