package com.girish.premiumapp.di;

import com.girish.premiumapp.data.local.AppDatabase;
import com.girish.premiumapp.data.local.EventDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class AppModule_ProvideEventDaoFactory implements Factory<EventDao> {
  private final Provider<AppDatabase> dbProvider;

  private AppModule_ProvideEventDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public EventDao get() {
    return provideEventDao(dbProvider.get());
  }

  public static AppModule_ProvideEventDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideEventDaoFactory(dbProvider);
  }

  public static EventDao provideEventDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideEventDao(db));
  }
}
