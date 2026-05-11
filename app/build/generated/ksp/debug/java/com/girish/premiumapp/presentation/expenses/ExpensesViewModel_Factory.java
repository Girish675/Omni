package com.girish.premiumapp.presentation.expenses;

import com.girish.premiumapp.domain.repository.ExpenseRepository;
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
public final class ExpensesViewModel_Factory implements Factory<ExpensesViewModel> {
  private final Provider<ExpenseRepository> repositoryProvider;

  private ExpensesViewModel_Factory(Provider<ExpenseRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ExpensesViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static ExpensesViewModel_Factory create(Provider<ExpenseRepository> repositoryProvider) {
    return new ExpensesViewModel_Factory(repositoryProvider);
  }

  public static ExpensesViewModel newInstance(ExpenseRepository repository) {
    return new ExpensesViewModel(repository);
  }
}
