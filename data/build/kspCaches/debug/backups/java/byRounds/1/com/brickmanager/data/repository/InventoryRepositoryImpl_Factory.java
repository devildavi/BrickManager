package com.brickmanager.data.repository;

import com.brickmanager.data.dao.SetDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class InventoryRepositoryImpl_Factory implements Factory<InventoryRepositoryImpl> {
  private final Provider<SetDao> setDaoProvider;

  public InventoryRepositoryImpl_Factory(Provider<SetDao> setDaoProvider) {
    this.setDaoProvider = setDaoProvider;
  }

  @Override
  public InventoryRepositoryImpl get() {
    return newInstance(setDaoProvider.get());
  }

  public static InventoryRepositoryImpl_Factory create(Provider<SetDao> setDaoProvider) {
    return new InventoryRepositoryImpl_Factory(setDaoProvider);
  }

  public static InventoryRepositoryImpl newInstance(SetDao setDao) {
    return new InventoryRepositoryImpl(setDao);
  }
}
