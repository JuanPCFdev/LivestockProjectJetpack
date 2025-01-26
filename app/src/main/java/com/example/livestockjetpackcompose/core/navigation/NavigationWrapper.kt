package com.example.livestockjetpackcompose.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.livestockjetpackcompose.domain.utils.CowTypeFilter
import com.example.livestockjetpackcompose.ui.screens.cows.CowHomeScreen
import com.example.livestockjetpackcompose.ui.screens.cows.MultiCowScreen
import com.example.livestockjetpackcompose.ui.screens.cows.RegisterCowScreen
import com.example.livestockjetpackcompose.ui.screens.farm.EditFarmScreen
import com.example.livestockjetpackcompose.ui.screens.farm.RegisterFarmScreen
import com.example.livestockjetpackcompose.ui.screens.home.HomePageScreen
import com.example.livestockjetpackcompose.ui.screens.farm.ListFarmScreen
import com.example.livestockjetpackcompose.ui.screens.finance.FinanceHomeScreen
import com.example.livestockjetpackcompose.ui.screens.login.LoginScreen
import com.example.livestockjetpackcompose.ui.screens.user.EditUserScreen
import com.example.livestockjetpackcompose.ui.screens.user.RegisterUserScreen

@Composable
fun NavigationWrapper(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Login) {

        composable<Login> {
            LoginScreen(
                modifier = modifier,
                navigateToRegisterFarm = { user ->
                    navController.navigate(
                        ListFarm(
                            userKey = user
                        )
                    )
                },
                navigateToRegisterUser = {
                    navController.navigate(RegisterUser)
                }
            )
        }

        composable<ListFarm> { navBackStackEntry ->
            val listItems = navBackStackEntry.toRoute<ListFarm>()
            ListFarmScreen(
                modifier = modifier,
                userKey = listItems.userKey,
                navigateToRegisterFarm = { user ->
                    navController.navigate(
                        RegisterFarm(
                            userKey = user
                        )
                    )
                },
                navigateToHomePage = { farmKey ->
                    navController.navigate(
                        HomePage(
                            userKey = listItems.userKey,
                            farmKey = farmKey
                        )
                    )
                }
            )
        }

        composable<RegisterUser> {
            RegisterUserScreen(
                modifier = modifier,
                onRegisterUserDone = { navController.popBackStack() })
        }

        composable<RegisterFarm> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<RegisterFarm>()
            RegisterFarmScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                onRegisterFarmDone = { navController.popBackStack() }
            )
        }

        composable<HomePage> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<HomePage>()
            HomePageScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey,
                navigateToFarm = {
                    navController.navigate(
                        EditFarm(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey
                        )
                    )
                },
                navigateToUser = {
                    navController.navigate(
                        EditUser(
                            userKey = navItems.userKey
                        )
                    )
                },
                navigateToCattle = {
                    navController.navigate(
                        CowHomePage(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey
                        )
                    )
                },
                navigateToFinance = {
                    navController.navigate(
                        FinanceHome(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey
                        )
                    )
                }
            )
        }

        composable<EditFarm> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<EditFarm>()
            EditFarmScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey,
                onEditFarmDone = { navController.popBackStack() }
            )
        }

        composable<EditUser> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<EditUser>()
            EditUserScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                onEditUserDone = { navController.popBackStack() })

        }

        composable<CowHomePage> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<CowHomePage>()
            CowHomeScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey,
                navigateToBreeding = {
                    navController.navigate(
                        BreedingHomeCow(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey
                        )
                    )
                },
                navigateToLifting = {
                    navController.navigate(
                        LiftingHomeCow(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey
                        )
                    )
                },
                navigateToCorral = {
                    navController.navigate(
                        CorralHomeCow(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey
                        )
                    )
                },
                navigateToDeadCow = {
                    navController.navigate(
                        DeadCowHome(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey
                        )
                    )
                },
                navigateToSoldCow = {
                    navController.navigate(
                        SoldCowHome(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey
                        )
                    )
                }
            )
        }

        composable<FinanceHome> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<CowHomePage>()
            FinanceHomeScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey
            )
        }

        composable<BreedingHomeCow> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<CowHomePage>()
            MultiCowScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey,
                CowTypeFilter.BREEADING,
                navigateToRegisterLiftingCow = {
                    navController.navigate(
                        RegisterLiftingCow(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey
                        )
                    )
                },
                navigateToRegisterBreedingCow = {
                    navController.navigate(
                        RegisterBreedingCow(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey
                        )
                    )
                },
                navigateToCowsResume = { cowKey ->

                }
            )
        }

        composable<LiftingHomeCow> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<CowHomePage>()
            MultiCowScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey,
                CowTypeFilter.LIFTING, navigateToRegisterLiftingCow = {
                    navController.navigate(
                        RegisterLiftingCow(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey
                        )
                    )
                },
                navigateToRegisterBreedingCow = {
                    navController.navigate(
                        RegisterBreedingCow(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey
                        )
                    )
                },
                navigateToCowsResume = { cowKey ->

                }
            )
        }

        composable<CorralHomeCow> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<CowHomePage>()
            MultiCowScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey,
                CowTypeFilter.CORRAL, navigateToRegisterLiftingCow = {
                    navController.navigate(
                        RegisterLiftingCow(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey
                        )
                    )
                },
                navigateToRegisterBreedingCow = {
                    navController.navigate(
                        RegisterBreedingCow(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey
                        )
                    )
                },
                navigateToCowsResume = { cowKey ->

                }
            )
        }

        composable<DeadCowHome> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<CowHomePage>()
            MultiCowScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey,
                CowTypeFilter.DEAD, navigateToRegisterLiftingCow = {
                    navController.navigate(
                        RegisterLiftingCow(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey
                        )
                    )
                },
                navigateToRegisterBreedingCow = {
                    navController.navigate(
                        RegisterBreedingCow(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey
                        )
                    )
                },
                navigateToCowsResume = { cowKey ->

                }
            )
        }

        composable<SoldCowHome> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<CowHomePage>()
            MultiCowScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey,
                CowTypeFilter.SOLD,
                navigateToRegisterLiftingCow = {
                    navController.navigate(
                        RegisterLiftingCow(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey
                        )
                    )
                },
                navigateToRegisterBreedingCow = {
                    navController.navigate(
                        RegisterBreedingCow(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey
                        )
                    )
                },
                navigateToCowsResume = { cowKey ->

                }
            )
        }

        composable<RegisterLiftingCow> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<CowHomePage>()

            RegisterCowScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey,
                cowType = false,
                onRegisterCowDone = {
                    navController.popBackStack()
                }
            )

        }

        composable<RegisterBreedingCow> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<CowHomePage>()

            RegisterCowScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey,
                cowType = true,
                onRegisterCowDone = {
                    navController.popBackStack()
                }
            )
        }

    }
}