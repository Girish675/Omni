package com.girish.premiumapp.presentation.search;

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
public final class SearchViewModel_Factory implements Factory<SearchViewModel> {
  private final Provider<TaskRepository> taskRepositoryProvider;

  private final Provider<NoteRepository> noteRepositoryProvider;

  private final Provider<ExpenseRepository> expenseRepositoryProvider;

  private final Provider<EventRepository> eventRepositoryProvider;

  private SearchViewModel_Factory(Provider<TaskRepository> taskRepositoryProvider,
      Provider<NoteRepository> noteRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<EventRepository> eventRepositoryProvider) {
    this.taskRepositoryProvider = taskRepositoryProvider;
    this.noteRepositoryProvider = noteRepositoryProvider;
    this.expenseRepositoryProvider = expenseRepositoryProvider;
    this.eventRepositoryProvider = eventRepositoryProvider;
  }

  @Override
  public SearchViewModel get() {
    return newInstance(taskRepositoryProvider.get(), noteRepositoryProvider.get(), expenseRepositoryProvider.get(), eventRepositoryProvider.get());
  }

  public static SearchViewModel_Factory create(Provider<TaskRepository> taskRepositoryProvider,
      Provider<NoteRepository> noteRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<EventRepository> eventRepositoryProvider) {
    return new SearchViewModel_Factory(taskRepositoryProvider, noteRepositoryProvider, expenseRepositoryProvider, eventRepositoryProvider);
  }

  public static SearchViewModel newInstance(TaskRepository taskRepository,
      NoteRepository noteRepository, ExpenseRepository expenseRepository,
      EventRepository eventRepository) {
    return new SearchViewModel(taskRepository, noteRepository, expenseRepository, eventRepository);
  }
}
