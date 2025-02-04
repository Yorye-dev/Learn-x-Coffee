package com.RageRacoon.learm_x_coffee.di

import com.RageRacoon.learm_x_coffee.data.repository.AuthRepositoryImplement
import com.RageRacoon.learm_x_coffee.data.repository.TaskRepositoryImplement
import com.RageRacoon.learm_x_coffee.data.repository.UsersRepositoryImplement
import com.RageRacoon.learm_x_coffee.domain.repository.AuthRepository
import com.RageRacoon.learm_x_coffee.domain.repository.TaskRepository
import com.RageRacoon.learm_x_coffee.domain.repository.UsersRepository
import com.RageRacoon.learm_x_coffee.domain.use_cases.events.CreateTask
import com.RageRacoon.learm_x_coffee.domain.use_cases.events.EventsUsecase
import com.RageRacoon.learm_x_coffee.domain.use_cases.events.GetTask
import com.RageRacoon.learm_x_coffee.domain.use_cases.events.UpdateTask
import com.RageRacoon.learm_x_coffee.domain.use_cases.login.*
import com.RageRacoon.learm_x_coffee.domain.use_cases.users.*
import com.RageRacoon.learm_x_coffee.utiles.Constantes
import com.RageRacoon.learm_x_coffee.utiles.Constantes.TASK_COLLECTION_NAME
import com.RageRacoon.learm_x_coffee.utiles.Constantes.USERS_COLLECTION_NAME
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    /**
     * Firebase AuthRepository Proovedores de la inyeccion de dependencias
     */

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(implementation: AuthRepositoryImplement): AuthRepository = implementation

    @Provides
    fun provideLoginUseCases(repository: AuthRepository)= LoginUseCase(
        getUser = GetUser(repository),
        login = Login(repository),
        loginOut = LoginOut(repository),
        register = Register(repository),
    )

    /**
     * FirebaseFirebaseFirestore Proovedores de la inyeccion de dependencias
     */
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore
    @Provides
    @Named(USERS_COLLECTION_NAME)
    fun provideUserId(database: FirebaseFirestore): CollectionReference = database.collection(Constantes.USERS_COLLECTION_NAME)
    @Provides
    fun provideUserRepository(implementation: UsersRepositoryImplement):UsersRepository = implementation
    @Provides
    fun provideProfileUseCase(repository: UsersRepository) = ProfilesUseCase(    //esto es igual a la cal
        create = Create(repository),
        getUserById = GetUserById(repository),
        edit = Edit(repository),
        guardarImg = GuardarImg(repository)
    )
    // Task //
    @Provides
    @Named(TASK_COLLECTION_NAME)
    fun provideTaskId(database: FirebaseFirestore): CollectionReference = database.collection(Constantes.TASK_COLLECTION_NAME)
    @Provides
    fun provideTaskRepository (implementation: TaskRepositoryImplement): TaskRepository = implementation

    @Provides
    fun provideEventsUseCase(taskrepository: TaskRepository) = EventsUsecase(    //esto es igual a la cal
        createTask = CreateTask(taskrepository),
        getTask =  GetTask(taskrepository),
        updateTask = UpdateTask(taskrepository)
    )
    /**
     * FirebaseStorage Provedores de la inyeccion de dependencias
     */
    @Provides
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()
    @Provides
    fun provideStorageUsuerDir(storage: FirebaseStorage): StorageReference = storage.reference.child(Constantes.USERS_COLLECTION_NAME)
}