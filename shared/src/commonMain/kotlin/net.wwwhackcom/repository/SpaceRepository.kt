package net.wwwhackcom.repository

import net.wwwhackcom.RocketLaunch
import net.wwwhackcom.network.Api

/**
 * @author nickwang
 * Created 5/07/23
 */

interface SpaceRepository {
    @Throws(Exception::class)
    suspend fun getLaunches(): List<RocketLaunch>
}

class SpaceRepositoryImpl constructor(
    private val api : Api
    ) : SpaceRepository {

    @Throws(Exception::class)
    override suspend fun getLaunches(): List<RocketLaunch> {
        return api.getAllLaunches()
    }
}