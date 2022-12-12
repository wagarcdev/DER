package com.wagarcdev.der.domain.usecase

import com.wagarcdev.der.data.repository.UsersRepository
import com.wagarcdev.der.domain.model.User
import javax.inject.Inject

/**
 * Use case to get the user by email.
 */
interface GetUserByEmailUseCase {
    /**
     * @param email the [String] email of user to be get.
     *
     * @return [User] or null if there is no user found for the provided [email].
     */
    suspend operator fun invoke(email: String): User?
}

/**
 * Implementation of [GetUserByEmailUseCase] that uses [UsersRepository].
 */
class GetUserByEmailUseCaseImpl @Inject constructor(
    private val usersRepository: UsersRepository
) : GetUserByEmailUseCase {
    override suspend fun invoke(email: String): User? =
        usersRepository.getUserByEmail(email = email)
}