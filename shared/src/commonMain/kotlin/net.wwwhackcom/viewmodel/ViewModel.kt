package net.wwwhackcom.viewmodel

import net.wwwhackcom.RocketLaunch
import net.wwwhackcom.repository.SpaceRepository

/**
 * @author nickwang
 * Created 6/07/23
 */


class ViewModel constructor(
    private val repository: SpaceRepository
) {
    suspend fun getLaunches(): List<RocketLaunch> {
        return repository.getLaunches()
    }
}