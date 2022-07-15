package com.ismaelviss.hulkstore.ui.store.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ismaelviss.hulkstore.R
import com.ismaelviss.hulkstore.services.product.model.Product
import com.ismaelviss.hulkstore.utils.Tools.Companion.getFormatMoney
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class AdapterProducts(
    private val products: List<Product>,
    private val listener: Listener
) : RecyclerView.Adapter<AdapterProducts.ViewHolder>()  {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val imageProduct: ImageView = itemView.findViewById(R.id.imageProduct)
        private val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        private val price: TextView = itemView.findViewById(R.id.price)
        private val add: ImageButton = itemView.findViewById(R.id.add)
        private val quantity: TextView = itemView.findViewById(R.id.quantity)
        private val sub: ImageButton = itemView.findViewById(R.id.sub)

        fun binding(item: Product, listener: Listener) {
            productName.text = item.name

            Picasso.get()
                .load(item.photoUrls)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.progress_animation)
                .into(imageProduct, object :
                    Callback {
                    override fun onSuccess() {
                        imageProduct.tag = true
                    }

                    override fun onError(e: java.lang.Exception?) {
                        imageProduct.tag = false
                    }
                })

            categoryName.text = item.category
            price.text = getFormatMoney(item.price)

            quantity.text = item.quantity.toString()

            add.setOnClickListener {
                listener.onAdd(item, bindingAdapterPosition)
            }

            sub.setOnClickListener {
                listener.onSub(item, bindingAdapterPosition)
            }

        }
    }

    interface Listener {
        fun onAdd(item: Product, position: Int)
        fun onSub(item: Product, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_product, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(products[position], listener)
    }

    override fun getItemCount(): Int {
        return products.size
    }

}