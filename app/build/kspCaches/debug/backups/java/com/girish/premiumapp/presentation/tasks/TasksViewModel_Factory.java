package com.girish.premiumapp.presentation.tasks;

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
public final class TasksViewModel_Factory implements Factory<TasksViewModel> {
  private final Provider<TaskRepository> repositoryProvider;

  private TasksViewModel_Factory(Provider<TaskRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public TasksViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static TasksViewModel_Factory create(Provider<TaskRepository> repositoryProvider) {
    return new TasksViewModel_Factory(repositoryProvider);
  }

  public static TasksViewModel newInstance(TaskRepository repository) {
    return new TasksViewModel(repository);
  }
}
