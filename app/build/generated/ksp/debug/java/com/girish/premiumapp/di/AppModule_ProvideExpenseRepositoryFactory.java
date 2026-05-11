package com.girish.premiumapp.di;

import com.girish.premiumapp.data.local.ExpenseDao;
import com.girish.premiumapp.domain.repository.ExpenseRepository;
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
public final class AppModule_ProvideExpenseRepositoryFactory implements Factory<ExpenseRepository> {
  private final Provider<ExpenseDao> daoProvider;

  private AppModule_ProvideExpenseRepositoryFactory(Provider<ExpenseDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public ExpenseRepository get() {
    return provideExpenseRepository(daoProvider.get());
  }

  public static AppModule_ProvideExpenseRepositoryFactory create(Provider<ExpenseDao> daoProvider) {
    return new AppModule_ProvideExpenseRepositoryFactory(daoProvider);
  }

  public static ExpenseRepository provideExpenseRepository(ExpenseDao dao) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideExpenseRepository(dao));
  }
}
