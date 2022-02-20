package com.baibilim.fourpicsoneword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.content.SharedPreferences
import com.baibilim.fourpicsoneword.databinding.ActivityMainBinding
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeResponseListener
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.SkuDetails
import com.android.billingclient.api.SkuDetailsParams
import com.android.billingclient.api.BillingClientStateListener

class MainActivity : AppCompatActivity() {

    //Создаю billingClient
    private var billingClient: BillingClient? = null

    //Глобальный переменный для сохранения
    private var sharedPreferences: SharedPreferences? = null

    //binding
    private lateinit var binding: ActivityMainBinding

    //onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Создаю глобальную переменную
        sharedPreferences = getSharedPreferences("SP_COINS", MODE_PRIVATE)

        //Иницилизация
        titleUI()

        //BillingClient
        billingClient = BillingClient.newBuilder(applicationContext)
            .setListener { billingResult, list ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && list != null) {
                    for (purchase in list) {
                        verifyPayment(purchase)
                    }
                }
            }
            .enablePendingPurchases()
            .build()

        //connectGooglePlayBilling
        connectGooglePlayBilling()


    }

    //UI Text
    private fun titleUI(){
        binding.title.text = "I have " + loadData("dollars") + " dollar"
    }

    //connectGooglePlayBilling
    fun connectGooglePlayBilling() {

        //
        billingClient!!.startConnection(object : BillingClientStateListener {

            //
            override fun onBillingServiceDisconnected() {
                connectGooglePlayBilling()
            }

            //
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    getProducts()
                }
            }
        })
    }

    //getProducts
    fun getProducts() {

        //products
        val products: MutableList<String> = ArrayList()
        //add
        products.add("dollar_1")
        products.add("dollar_5")
        products.add("dollar_10")
        products.add("dollar_20")

        //params
        val params = SkuDetailsParams.newBuilder()

        //setSkusList
        params.setSkusList(products).setType(BillingClient.SkuType.INAPP)

        //querySkuDetailsAsync
        billingClient!!.querySkuDetailsAsync(
            params.build()
        ) { billingResult, list ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && list != null) {
                for (skuDetails in list) {
                    when (skuDetails.sku) {
                        "dollar_1" -> {
                            binding.btn1.text = "Price " + skuDetails.price
                            binding.btn1.setOnClickListener { launchPurchaseFlow(skuDetails) }
                        }
                        "dollar_5" -> {
                            binding.btn5.text = "Price " + skuDetails.price
                            binding.btn5.setOnClickListener { launchPurchaseFlow(skuDetails) }
                        }
                        "dollar_10" -> {
                            binding.btn10.text = "Price " + skuDetails.price
                            binding.btn10.setOnClickListener { launchPurchaseFlow(skuDetails) }
                        }
                        "dollar_20" -> {
                            binding.btn20.text = "Price " + skuDetails.price
                            binding.btn20.setOnClickListener { launchPurchaseFlow(skuDetails) }
                        }
                    }
                }
            }
        }
    }

    //launchPurchaseFlow
    private fun launchPurchaseFlow(skuDetails: SkuDetails?) {
        val billingFlowParams = BillingFlowParams.newBuilder()
            .setSkuDetails(skuDetails!!)
            .build()
        billingClient!!.launchBillingFlow(this, billingFlowParams)
    }

    //verifyPayment
    private fun verifyPayment(purchase: Purchase) {

        when(purchase.skus[0]) {
            "dollar_1" -> {
                updateData(1)
            }
            "dollar_5" -> {
                updateData(5)
            }
            "dollar_10" -> {
                updateData(10)
            }
            "dollar_20" -> {
                updateData(20)
            }
        }

        //
        val consumeParams = ConsumeParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        //
        val listener =
            ConsumeResponseListener { billingResult, suu ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    when(purchase.skus[0]) {
                        "" -> { true }
                        else->{ true }
                    }
                }
            }

        billingClient!!.consumeAsync(consumeParams, listener)
    }

    //--My Functions Data--//

    //put Dollar
    private fun dollar(dollar: Int): Int? {

        var dollars = loadData("dollars")?.toInt()

        if (dollars != null) {
            dollars += dollar
        }

        return dollars
    }

    //update Data
    private fun updateData(dollar: Int) {
        //Saving
        saveData("dollars", dollar.toString())
        //Log
        Log.d("dollars", "You just purchased -> $dollar")
    }

    //save data
    private fun saveData(key: String?, value: String?) {
        if (value != null){
            //Save data
            val result = dollar(value.toInt()).toString()
            val editor = sharedPreferences!!.edit()
            editor.putString(key, result)
            editor.apply()
            //Update UI
            titleUI()
        }
    }

    //Load data
    private fun loadData(key: String?): String? {
        return sharedPreferences!!.getString(key, "0")
    }

}