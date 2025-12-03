package com.example.brickmanager.di

import com.brickmanager.domain.repository.InventoryRepository
import com.brickmanager.domain.usecase.AddSetToInventoryUseCase
import com.brickmanager.domain.usecase.GetSetInventoryUseCase
import com.brickmanager.domain.usecase.UpdateSetStatusUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Hilt module that provides domain-layer use cases to the presentation layer.
 * This module is installed in the [ViewModelComponent], meaning the provided use cases
 * will be scoped to the lifecycle of a ViewModel.
 */
@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    /**
     * Provides an instance of [GetSetInventoryUseCase].
     * @param inventoryRepository The repository required by the use case.
     * @return An instance of [GetSetInventoryUseCase].
     */
    @Provides
    fun provideGetSetInventoryUseCase(
        inventoryRepository: InventoryRepository
    ): GetSetInventoryUseCase {
        return GetSetInventoryUseCase(inventoryRepository)
    }

    /**
     * Provides an instance of [AddSetToInventoryUseCase].
     * @param inventoryRepository The repository required by the use case.
     * @return An instance of [AddSetToInventoryUseCase].
     */
    @Provides
    fun provideAddSetToInventoryUseCase(
        inventoryRepository: InventoryRepository
    ): AddSetToInventoryUseCase {
        return AddSetToInventoryUseCase(inventoryRepository)
    }

    /**
     * Provides an instance of [UpdateSetStatusUseCase].
     * @param inventoryRepository The repository required by the use case.
     * @return An instance of [UpdateSetStatusUseCase].
     */
    @Provides
    fun provideUpdateSetStatusUseCase(
        inventoryRepository: InventoryRepository
    ): UpdateSetStatusUseCase {
        return UpdateSetStatusUseCase(inventoryRepository)
    }
}
