package com.kreait.slack.api.test.group.users

import com.kreait.slack.api.contract.jackson.group.users.ErrorIdentityResponse
import com.kreait.slack.api.contract.jackson.group.users.SuccessfulIdentityResponse
import com.kreait.slack.api.group.ApiCallResult
import com.kreait.slack.api.group.users.UsersIdentityMethod
import com.kreait.slack.api.test.MockMethod

class MockUsersIdentityMethod : UsersIdentityMethod(), MockMethod<SuccessfulIdentityResponse, ErrorIdentityResponse, Unit> {
    override fun params(): Unit {
        return params
    }

    override var successResponse: SuccessfulIdentityResponse? = null
    override var failureResponse: ErrorIdentityResponse? = null

    override fun request(): ApiCallResult<SuccessfulIdentityResponse, ErrorIdentityResponse> {
        this.successResponse?.let { this.onSuccess?.invoke(it) }
        this.failureResponse?.let { this.onFailure?.invoke(it) }

        return ApiCallResult(this.successResponse, this.failureResponse)
    }
}