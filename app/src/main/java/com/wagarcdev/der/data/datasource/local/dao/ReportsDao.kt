package com.wagarcdev.der.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wagarcdev.der.data.datasource.local.entity.ReportEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object to deal with [ReportEntity].
 */
@Dao
interface ReportsDao {
    /**
     * Get a data stream list of report entity for an contract number.
     *
     * @param contractNumber the [String] that represents the contract number.
     *
     * @return [Flow] of list of [ReportEntity]. This list will be empty if no reports are saved
     * for this contract number.
     */
    @Query(value = "SELECT * FROM reports WHERE contractNumber = :contractNumber")
    fun getReportsForContractStream(contractNumber: String): Flow<List<ReportEntity>>

    /**
     * Get a data stream of nullable report entity by id.
     *
     * @param id the [String] id of report to be get.
     *
     * @return [Flow] of [ReportEntity] or null if there is no report found for the provided [id].
     */
    @Query(value = "SELECT * FROM reports WHERE id = :id LIMIT 1")
    fun getReportByIdStream(id: String): Flow<ReportEntity?>

    /**
     * Insert an report with [OnConflictStrategy.REPLACE].
     *
     * @param reportEntity the [ReportEntity] to be inserted.
     *
     * @return an [Long] value that represents the current id of the inserted report.
     * -1 is returned if this operation fails.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReportAndReplace(reportEntity: ReportEntity): Long
}