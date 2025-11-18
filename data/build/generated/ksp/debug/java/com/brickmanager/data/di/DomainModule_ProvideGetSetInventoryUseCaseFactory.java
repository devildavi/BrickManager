package com.brickmanager.data.di;

import com.brickmanager.domain.repository.InventoryRepository;
import com.brickmanager.domain.usecase.GetSetInventoryUseCase;
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
public final class DomainModule_ProvideGetSetInventoryUseCaseFactory implements Factory<GetSetInventoryUseCase> {
  private final Provider<InventoryRepository> inventoryRepositoryProvider;

  public DomainModule_ProvideGetSetInventoryUseCaseFactory(
      Provider<InventoryRepository> inventoryRepositoryProvider) {
    this.inventoryRepositoryProvider = inventoryRepositoryProvider;
  }

  @Override
  public GetSetInventoryUseCase get() {
    return provideGetSetInventoryUseCase(inventoryRepositoryProvider.get());
  }

  public static DomainModule_ProvideGetSetInventoryUseCaseFactory create(
      Provider<InventoryRepository> inventoryRepositoryProvider) {
    return new DomainModule_ProvideGetSetInventoryUseCaseFactory(inventoryRepositoryProvider);
  }

  public static GetSetInventoryUseCase provideGetSetInventoryUseCase(
      InventoryRepository inventoryRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideGetSetInventoryUseCase(inventoryRepository));
  }
}
