package com.girish.premiumapp.di;

import com.girish.premiumapp.data.local.EventDao;
import com.girish.premiumapp.domain.repository.EventRepository;
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
public final class AppModule_ProvideEventRepositoryFactory implements Factory<EventRepository> {
  private final Provider<EventDao> daoProvider;

  private AppModule_ProvideEventRepositoryFactory(Provider<EventDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public EventRepository get() {
    return provideEventRepository(daoProvider.get());
  }

  public static AppModule_ProvideEventRepositoryFactory create(Provider<EventDao> daoProvider) {
    return new AppModule_ProvideEventRepositoryFactory(daoProvider);
  }

  public static EventRepository provideEventRepository(EventDao dao) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideEventRepository(dao));
  }
}
