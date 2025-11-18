package com.brickmanager.data.di

import com.brickmanager.data.repository.InventoryRepositoryImpl
import com.brickmanager.domain.repository.InventoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing repository implementations.
 * This module is responsible for binding repository interfaces to their concrete implementations.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Binds the [InventoryRepositoryImpl] to the [InventoryRepository] interface.
     * This tells Hilt to provide an instance of [InventoryRepositoryImpl] whenever
     * an [InventoryRepository] is requested.
     *
     * @param inventoryRepositoryImpl The concrete implementation of the repository.
     * @return An instance that conforms to the [InventoryRepository] interface.
     */
    @Binds
    @Singleton
    abstract fun bindInventoryRepository(
        inventoryRepositoryImpl: InventoryRepositoryImpl
    ): InventoryRepository
}
