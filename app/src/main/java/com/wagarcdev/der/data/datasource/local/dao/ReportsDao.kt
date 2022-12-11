package com.wagarcdev.der.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.wagarcdev.der.data.datasource.local.entity.ReportEntity

/**
 * Data Access Object to deal with [ReportEntity].
 */
@Dao
interface ReportsDao {
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