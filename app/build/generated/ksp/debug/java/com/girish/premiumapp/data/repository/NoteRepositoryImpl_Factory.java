package com.girish.premiumapp.data.repository;

import com.girish.premiumapp.data.local.NoteDao;
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
public final class NoteRepositoryImpl_Factory implements Factory<NoteRepositoryImpl> {
  private final Provider<NoteDao> daoProvider;

  private NoteRepositoryImpl_Factory(Provider<NoteDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public NoteRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static NoteRepositoryImpl_Factory create(Provider<NoteDao> daoProvider) {
    return new NoteRepositoryImpl_Factory(daoProvider);
  }

  public static NoteRepositoryImpl newInstance(NoteDao dao) {
    return new NoteRepositoryImpl(dao);
  }
}
