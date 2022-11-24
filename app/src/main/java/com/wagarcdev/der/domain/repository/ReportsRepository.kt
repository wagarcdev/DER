package com.wagarcdev.der.domain.repository

import com.wagarcdev.der.domain.model.Report

interface ReportsRepository {
    suspend fun createNewReport(report: Report)
}