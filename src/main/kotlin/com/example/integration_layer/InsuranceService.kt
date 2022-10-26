package com.example.integration_layer

import com.example.model.BuyInsuranceRequest
import com.example.model.DealDataModel
import com.example.model.DealStatus

class InsuranceService {

    private val myHttpClient = MyHttpClient()

    fun buyInsurance(buyInsuranceRequest: BuyInsuranceRequest): Pair<Long, DealStatus> {
        val user = myHttpClient.createCustomer(buyInsuranceRequest.customer)
        val deal = myHttpClient.createDeal(DealDataModel(buyInsuranceRequest.insurance, user))
        val mailStatus = myHttpClient.sendDealMail(deal)
        val updatedDeal = myHttpClient.updateDeal(deal.copy(dealStatus = DealStatus.SENT))
        return Pair(updatedDeal.id, updatedDeal.dealStatus)
    }
}
