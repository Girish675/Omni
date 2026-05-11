package com.girish.premiumapp.di;

import com.girish.premiumapp.data.local.NoteDao;
import com.girish.premiumapp.domain.repository.NoteRepository;
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
public final class AppModule_ProvideNoteRepositoryFactory implements Factory<NoteRepository> {
  private final Provider<NoteDao> daoProvider;

  private AppModule_ProvideNoteRepositoryFactory(Provider<NoteDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public NoteRepository get() {
    return provideNoteRepository(daoProvider.get());
  }

  public static AppModule_ProvideNoteRepositoryFactory create(Provider<NoteDao> daoProvider) {
    return new AppModule_ProvideNoteRepositoryFactory(daoProvider);
  }

  public static NoteRepository provideNoteRepository(NoteDao dao) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideNoteRepository(dao));
  }
}
