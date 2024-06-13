package com.trendyol.showcase.ui.slidablecontent

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.trendyol.showcase.databinding.ItemSlidableContentBinding
import com.trendyol.showcase.ui.loadImage
import com.trendyol.showcase.ui.setTextSizeInSp
import com.trendyol.showcase.util.getHeightInPixels

internal class SlidableContentAdapter(private val slidableContentList: List<SlidableContent>) :
    RecyclerView.Adapter<SlidableContentAdapter.ViewPagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder =
        ViewPagerViewHolder(
            ItemSlidableContentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(slidableContentList[position])
    }

    override fun getItemCount(): Int = slidableContentList.size

    inner class ViewPagerViewHolder(
        private val binding: ItemSlidableContentBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SlidableContent) {
            with(binding) {
                val viewState = SlidableContentViewState(item)

                with(textViewTitle) {
                    typeface = Typeface.create(
                        viewState.slidableContent.titleTextFontFamily,
                        viewState.slidableContent.titleTextStyle,
                    )
                    text = viewState.slidableContent.title
                    textAlignment = viewState.getTextPosition()
                    setTextColor(viewState.slidableContent.titleTextColor)
                    isVisible = viewState.isTitleVisible()
                    setTextSizeInSp(viewState.slidableContent.titleTextSize)
                }
                with(textViewDescription) {
                    typeface = Typeface.create(
                        viewState.slidableContent.descriptionTextFontFamily,
                        viewState.slidableContent.descriptionTextStyle,
                    )
                    text = viewState.slidableContent.description
                    textAlignment = viewState.getTextPosition()
                    setTextColor(viewState.slidableContent.descriptionTextColor)
                    isVisible = viewState.isDescriptionVisible()
                    setTextSizeInSp(viewState.slidableContent.descriptionTextSize)
                }
                imageView.layoutParams.height =
                    binding.root.context.resources.getHeightInPixels() / SLIDABLE_IMAGE_HEIGHT_RATIO
                imageView.loadImage(viewState.slidableContent.imageUrl)
            }
        }
    }

    companion object {
        private const val SLIDABLE_IMAGE_HEIGHT_RATIO = 5
    }
}
