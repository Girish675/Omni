package com.girish.premiumapp.presentation.calendar;

import com.girish.premiumapp.domain.repository.EventRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class CalendarViewModel_Factory implements Factory<CalendarViewModel> {
  private final Provider<EventRepository> repositoryProvider;

  private CalendarViewModel_Factory(Provider<EventRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public CalendarViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static CalendarViewModel_Factory create(Provider<EventRepository> repositoryProvider) {
    return new CalendarViewModel_Factory(repositoryProvider);
  }

  public static CalendarViewModel newInstance(EventRepository repository) {
    return new CalendarViewModel(repository);
  }
}
