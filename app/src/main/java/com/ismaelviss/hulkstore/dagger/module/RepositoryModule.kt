package com.ismaelviss.hulkstore.dagger.module


import com.ismaelviss.hulkstore.domain.repositories.ILoginRepository
import com.ismaelviss.hulkstore.domain.repositories.IProductRepository
import com.ismaelviss.hulkstore.repositories.login.ILoginService
import com.ismaelviss.hulkstore.repositories.login.LoginRepository
import com.ismaelviss.hulkstore.repositories.store.IProductService
import com.ismaelviss.hulkstore.repositories.store.ProductRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideILoginRepository(loginService: ILoginService) : ILoginRepository {
        return LoginRepository(loginService)
    }

    @Provides
    fun provideIProductRepository(productService: IProductService) : IProductRepository {
        return ProductRepository(productService)
    }
}