package com.example.navigationlab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigationlab.data.remote.RetrofitClient
import com.example.navigationlab.data.remote.StudentRemoteDataSource
import com.example.navigationlab.data.repository.DefaultStudentRepository
import com.example.navigationlab.ui.detail.DetailViewModel
import com.example.navigationlab.ui.home.HomeViewModel
import com.example.navigationlab.ui.screen.AboutScreen
import com.example.navigationlab.ui.screen.DetailScreen
import com.example.navigationlab.ui.screen.HomeScreen
import com.example.navigationlab.ui.screen.ProfileScreen

@Composable
fun NavigationLabApp() {

    // GANTI RetrofitClient.api dengan objek StudentApiService yang kamu pakai di Modul 07
    val repository = remember {
        DefaultStudentRepository(StudentRemoteDataSource(RetrofitClient.instance))
    }

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {

        composable(Routes.HOME) {
            val homeViewModel: HomeViewModel = viewModel(
                factory = viewModelFactory {
                    initializer { HomeViewModel(repository) }
                }
            )
            val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()
            val searchQuery by homeViewModel.searchQuery.collectAsStateWithLifecycle()

            HomeScreen(
                uiState = homeUiState,
                searchQuery = searchQuery,
                onSearchQueryChange = homeViewModel::onSearchQueryChange,
                onOpenDetail = { id -> navController.navigate(Routes.detail(id)) },
                onOpenProfile = { navController.navigate(Routes.PROFILE) },
                onOpenAbout = { navController.navigate(Routes.ABOUT) },
                onRetry = homeViewModel::retry,
                onRefresh = homeViewModel::refresh
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("studentId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("studentId") ?: return@composable

            val detailViewModel: DetailViewModel = viewModel(
                factory = viewModelFactory {
                    initializer { DetailViewModel(repository) }
                }
            )
            val detailUiState by detailViewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(id) {
                detailViewModel.loadStudent(id)
            }

            DetailScreen(
                uiState = detailUiState,
                onBack = { navController.popBackStack() },
                onRetry = { detailViewModel.loadStudent(id) }
            )
        }

        composable(route = Routes.PROFILE) {
            ProfileScreen(onBack = { navController.popBackStack() })
        }

        composable(route = Routes.ABOUT) {
            AboutScreen(onBack = { navController.popBackStack() })
        }
    }
}
