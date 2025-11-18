package com.brickmanager.data.di;

import com.brickmanager.data.dao.SetDao;
import com.brickmanager.data.db.AppDatabase;
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
public final class DatabaseModule_ProvideSetDaoFactory implements Factory<SetDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideSetDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public SetDao get() {
    return provideSetDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideSetDaoFactory create(Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideSetDaoFactory(databaseProvider);
  }

  public static SetDao provideSetDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSetDao(database));
  }
}
