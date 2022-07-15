package com.ismaelviss.hulkstore.ui.store

import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ismaelviss.hulkstore.R
import com.ismaelviss.hulkstore.databinding.ActivityStoreCartBinding
import com.ismaelviss.hulkstore.domain.model.ResultGet
import com.ismaelviss.hulkstore.domain.model.ResultProcess
import com.ismaelviss.hulkstore.services.product.model.Product
import com.ismaelviss.hulkstore.ui.store.adapter.AdapterProducts
import com.ismaelviss.hulkstore.utils.Tools
import com.ismaelviss.hulkstore.utils.Tools.Companion.formatDate
import com.ismaelviss.hulkstore.utils.Tools.Companion.getFormatMoney
import com.ismaelviss.hulkstore.utils.show
import dagger.android.AndroidInjection
import java.util.*
import javax.inject.Inject

class StoreCartActivity : AppCompatActivity() {

    private val show_dialog: AppCompatButton? = null
    private lateinit var binding: ActivityStoreCartBinding

    private lateinit var progress: ProgressBar

    private var recyclerView: RecyclerView? = null
    private var adapterProduct: RecyclerView.Adapter<AdapterProducts.ViewHolder>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    @Inject
    lateinit var storeCartViewModel: StoreCartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)


        binding = ActivityStoreCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progress = binding.progressBar

        initToolbar()

        observerEventsResult()
        observerAmountTotal()
        observerPay()
    }

    private fun observerPay() {
        storeCartViewModel.payment.observe(this, Observer {
            val payment = it ?: return@Observer

            progress.show(payment is ResultProcess.Loading)

            if (payment is ResultProcess.Success) {



                showDialogPaymentSuccess(
                    Calendar.getInstance().time.toString(),
                    storeCartViewModel.fullName,
                    storeCartViewModel.user?.email ?: "",
                    storeCartViewModel.amountTotal.value!!
                )
            }

            if (payment is ResultProcess.Error)
                Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show()
        })

        binding.pay.setOnClickListener {
            storeCartViewModel.pay()
        }
    }

    private fun observerAmountTotal() {
        storeCartViewModel.amountTotal.observe(this, Observer {
            val amountTotal = it ?: return@Observer

            binding.amountTotal.text = getFormatMoney(amountTotal)
        })
    }

    private fun initToolbar() {
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.app_title)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Tools.setSystemBarColor(this, R.color.grey_5)
        Tools.setSystemBarLight(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observerEventsResult() {
        storeCartViewModel.resultProducts.observe(this,  Observer {
            val productsResult = it ?: return@Observer

            progress.show(productsResult is ResultGet.Loading)

            if (productsResult is ResultGet.Success) {
                setAdapterProducts(productsResult.data)
            }

            if (productsResult is ResultGet.Error) {
                Toast.makeText(this, productsResult.exception.message?:"Vuelve a intentar mas tarde", Toast.LENGTH_LONG).show()
            }
        })

        storeCartViewModel.products()
    }

    private fun setAdapterProducts(products: List<Product>?) {

        recyclerView = binding.products
        layoutManager = LinearLayoutManager (this)

        adapterProduct = AdapterProducts(
            products?: listOf(),
            object : AdapterProducts.Listener {
                override fun onAdd(item: Product, position: Int) {
                    if (item.quantity?:0 > 0) {
                        item.quantity = item.quantity!! - 1
                        adapterProduct?.notifyItemChanged(position)

                        storeCartViewModel.addProduct(item)
                    }
                    else
                        Toast.makeText(applicationContext, getString(R.string.no_stock), Toast.LENGTH_LONG).show()
                }

                override fun onSub(item: Product, position: Int) {
                    val order = storeCartViewModel.existsProduct(item)
                    if (order != null && order.quantity!! > 0) {

                        item.quantity = item.quantity!! + 1
                        adapterProduct?.notifyItemChanged(position)

                        storeCartViewModel.subProduct(item)
                    }
                    else
                        Toast.makeText(applicationContext, getString(R.string.no_cart), Toast.LENGTH_LONG).show()
                }

            }
        )

        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapterProduct
    }

    private fun showDialogPaymentSuccess(date: String, name: String, email: String, amount: Double) {
        val fragmentManager = supportFragmentManager
        val newFragment = DialogPaymentSuccessFragment()

        val argument = Bundle()
        argument.putString("date", formatDate(formatDate(date)))
        argument.putString("time", "")
        argument.putString("name", name)
        argument.putString("email", email)
        argument.putDouble("amount", amount)

        newFragment.arguments = argument



        val transaction = fragmentManager.beginTransaction()



        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit()

        /*newFragment.onDismiss(object : DialogInterface {
            override fun cancel() {

            }

            override fun dismiss() {
                storeCartViewModel.products()
            }

        })*/
    }
}