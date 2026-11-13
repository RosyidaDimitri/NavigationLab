
package com.example.navigationlab

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigationlab.data.repository.StudentRepository
import com.example.navigationlab.ui.detail.DetailViewModel
import com.example.navigationlab.ui.home.HomeViewModel
import com.example.navigationlab.ui.screen.AboutScreen
import com.example.navigationlab.ui.screen.DetailScreen
import com.example.navigationlab.ui.screen.HomeScreen
import com.example.navigationlab.ui.screen.ProfileScreen

@Composable
fun NavigationLabApp() {

    val repository = StudentRepository()

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {

        composable(route = Routes.HOME) {

            val homeViewModel: HomeViewModel = viewModel(
                factory = viewModelFactory {
                    initializer {
                        HomeViewModel(repository)
                    }
                }
            )


            HomeScreen(
                uiState = homeViewModel.uiState,

                onSearchQueryChange = { query ->
                    homeViewModel.updateSearchQuery(query)
                },

                onSearch = {
                    homeViewModel.searchStudents()
                },

                onOpenDetail = { studentId ->
                    navController.navigate(
                        Routes.detail(studentId)
                    )
                },

                onOpenProfile = {
                    navController.navigate(Routes.PROFILE)
                },

                onOpenAbout = {
                    navController.navigate(Routes.ABOUT)
                }
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(
                navArgument("studentId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val studentId =
                backStackEntry.arguments?.getInt("studentId") ?: -1

            val detailViewModel: DetailViewModel = viewModel(
                viewModelStoreOwner = backStackEntry,
                factory = viewModelFactory {
                    initializer {
                        DetailViewModel(studentId, repository)
                    }
                }
            )

            DetailScreen(
                uiState = detailViewModel.uiState,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Routes.PROFILE) {
            ProfileScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Routes.ABOUT) {
            AboutScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}