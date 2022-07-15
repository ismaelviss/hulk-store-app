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

    private var loginRepository: ILoginRepository? = null

    @Provides
    fun provideILoginRepository(loginService: ILoginService) : ILoginRepository {

        if (loginRepository == null)
            loginRepository = LoginRepository(loginService)

        return loginRepository!!
    }

    @Provides
    fun provideIProductRepository(productService: IProductService) : IProductRepository {
        return ProductRepository(productService)
    }
}