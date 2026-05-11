package com.girish.premiumapp.presentation.dashboard;

import android.app.Application;
import com.girish.premiumapp.domain.repository.EventRepository;
import com.girish.premiumapp.domain.repository.ExpenseRepository;
import com.girish.premiumapp.domain.repository.NoteRepository;
import com.girish.premiumapp.domain.repository.TaskRepository;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<TaskRepository> taskRepositoryProvider;

  private final Provider<NoteRepository> noteRepositoryProvider;

  private final Provider<EventRepository> eventRepositoryProvider;

  private final Provider<ExpenseRepository> expenseRepositoryProvider;

  private final Provider<Application> applicationProvider;

  private DashboardViewModel_Factory(Provider<TaskRepository> taskRepositoryProvider,
      Provider<NoteRepository> noteRepositoryProvider,
      Provider<EventRepository> eventRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<Application> applicationProvider) {
    this.taskRepositoryProvider = taskRepositoryProvider;
    this.noteRepositoryProvider = noteRepositoryProvider;
    this.eventRepositoryProvider = eventRepositoryProvider;
    this.expenseRepositoryProvider = expenseRepositoryProvider;
    this.applicationProvider = applicationProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(taskRepositoryProvider.get(), noteRepositoryProvider.get(), eventRepositoryProvider.get(), expenseRepositoryProvider.get(), applicationProvider.get());
  }

  public static DashboardViewModel_Factory create(Provider<TaskRepository> taskRepositoryProvider,
      Provider<NoteRepository> noteRepositoryProvider,
      Provider<EventRepository> eventRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<Application> applicationProvider) {
    return new DashboardViewModel_Factory(taskRepositoryProvider, noteRepositoryProvider, eventRepositoryProvider, expenseRepositoryProvider, applicationProvider);
  }

  public static DashboardViewModel newInstance(TaskRepository taskRepository,
      NoteRepository noteRepository, EventRepository eventRepository,
      ExpenseRepository expenseRepository, Application application) {
    return new DashboardViewModel(taskRepository, noteRepository, eventRepository, expenseRepository, application);
  }
}
