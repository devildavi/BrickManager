package com.brickmanager.data.di;

import com.brickmanager.domain.repository.InventoryRepository;
import com.brickmanager.domain.usecase.AddSetToInventoryUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class DomainModule_ProvideAddSetToInventoryUseCaseFactory implements Factory<AddSetToInventoryUseCase> {
  private final Provider<InventoryRepository> inventoryRepositoryProvider;

  public DomainModule_ProvideAddSetToInventoryUseCaseFactory(
      Provider<InventoryRepository> inventoryRepositoryProvider) {
    this.inventoryRepositoryProvider = inventoryRepositoryProvider;
  }

  @Override
  public AddSetToInventoryUseCase get() {
    return provideAddSetToInventoryUseCase(inventoryRepositoryProvider.get());
  }

  public static DomainModule_ProvideAddSetToInventoryUseCaseFactory create(
      Provider<InventoryRepository> inventoryRepositoryProvider) {
    return new DomainModule_ProvideAddSetToInventoryUseCaseFactory(inventoryRepositoryProvider);
  }

  public static AddSetToInventoryUseCase provideAddSetToInventoryUseCase(
      InventoryRepository inventoryRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideAddSetToInventoryUseCase(inventoryRepository));
  }
}
