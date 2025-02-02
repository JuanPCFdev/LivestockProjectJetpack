package com.example.livestockjetpackcompose.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.livestockjetpackcompose.domain.utils.CowTypeFilter
import com.example.livestockjetpackcompose.ui.screens.cows.CowHomeScreen
import com.example.livestockjetpackcompose.ui.screens.cows.CowResumeScreen
import com.example.livestockjetpackcompose.ui.screens.cows.MultiCowScreen
import com.example.livestockjetpackcompose.ui.screens.cows.RegisterCowScreen
import com.example.livestockjetpackcompose.ui.screens.cows.breeading.born.BreadingPerformanceStats
import com.example.livestockjetpackcompose.ui.screens.cows.breeading.born.RegisterBornCowScreen
import com.example.livestockjetpackcompose.ui.screens.cows.breeading.insemination.InseminationStatsScreen
import com.example.livestockjetpackcompose.ui.screens.cows.breeading.insemination.RegisterInseminationScreen
import com.example.livestockjetpackcompose.ui.screens.cows.lifting.LiftingStatsScreen
import com.example.livestockjetpackcompose.ui.screens.cows.lifting.weight.RegisterWeightScreen
import com.example.livestockjetpackcompose.ui.screens.cows.vaccine.EditVaccineScreen
import com.example.livestockjetpackcompose.ui.screens.cows.vaccine.RegisterVaccineScreen
import com.example.livestockjetpackcompose.ui.screens.cows.vaccine.VaccineListScreen
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
                navigateToCowsResume = { cowKey, cowType ->
                    navController.navigate(
                        CowResume(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey,
                            cowKey = cowKey,
                            cowType = cowType
                        )
                    )
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
                navigateToCowsResume = { cowKey, cowType ->
                    navController.navigate(
                        CowResume(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey,
                            cowKey = cowKey,
                            cowType = cowType
                        )
                    )
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
                navigateToCowsResume = { cowKey, cowType ->
                    navController.navigate(
                        CowResume(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey,
                            cowKey = cowKey,
                            cowType = cowType
                        )
                    )
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
                navigateToCowsResume = { cowKey, cowType ->
                    navController.navigate(
                        CowResume(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey,
                            cowKey = cowKey,
                            cowType = cowType
                        )
                    )
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
                navigateToCowsResume = { cowKey, cowType ->
                    navController.navigate(
                        CowResume(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey,
                            cowKey = cowKey,
                            cowType = cowType
                        )
                    )
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

        composable<CowResume> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<CowResume>()

            CowResumeScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey,
                cowKey = navItems.cowKey,
                cowTypeFilter = navItems.cowType,
                navigateToVaccineListHome = {
                    navController.navigate(
                        VaccineHome(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey,
                            cowKey = navItems.cowKey
                        )
                    )
                },
                navigateToLiftingStats = {
                    navController.navigate(
                        LiftingStats(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey,
                            cowKey = navItems.cowKey
                        )
                    )
                },
                navigateToInsemination = {
                    navController.navigate(
                        InseminationStats(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey,
                            cowKey = navItems.cowKey
                        )
                    )
                },
                navigateToBreadingStats = {
                    navController.navigate(
                        BreadingStats(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey,
                            cowKey = navItems.cowKey
                        )
                    )
                }
            )

        }

        composable<VaccineHome> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<VaccineHome>()
            VaccineListScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey,
                cowKey = navItems.cowKey,
                onNavigateToRegisterVaccine = {
                    navController.navigate(
                        RegisterVaccine(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey,
                            cowKey = navItems.cowKey
                        )
                    )
                },
                onVaccineSelected = { vaccineKey ->
                    navController.navigate(
                        EditVaccine(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey,
                            cowKey = navItems.cowKey,
                            vaccineKey = vaccineKey
                        )
                    )
                }
            )
        }

        composable<RegisterVaccine> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<RegisterVaccine>()
            RegisterVaccineScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey,
                cowKey = navItems.cowKey,
                onRegisterDone = {
                    navController.popBackStack()
                }
            )
        }

        composable<EditVaccine> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<EditVaccine>()

            EditVaccineScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey,
                cowKey = navItems.cowKey,
                vaccineKey = navItems.vaccineKey,
                onEditDone = {
                    navController.popBackStack()
                }
            )

        }

        composable<LiftingStats> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<LiftingStats>()

            LiftingStatsScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey,
                cowKey = navItems.cowKey,
                onRegisterLiftingPerformance = {
                    navController.navigate(
                        RegisterLiftingPerformance(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey,
                            cowKey = navItems.cowKey
                        )
                    )
                }
            )
        }

        composable<RegisterLiftingPerformance> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<LiftingStats>()

            RegisterWeightScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey,
                cowKey = navItems.cowKey,
                onRegisterDone = {
                    navController.popBackStack()
                }
            )
        }

        composable<InseminationStats> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<InseminationStats>()

            InseminationStatsScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey,
                cowKey = navItems.cowKey,
                navigateToRegisterInsemination = {
                    navController.navigate(
                        RegisterInsemination(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey,
                            cowKey = navItems.cowKey
                        )
                    )
                }
            )

        }

        composable<RegisterInsemination> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<RegisterInsemination>()

            RegisterInseminationScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey,
                cowKey = navItems.cowKey,
                onRegisterDone = {
                    navController.popBackStack()
                }
            )

        }

        composable<BreadingStats> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<RegisterInsemination>()

            BreadingPerformanceStats(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey,
                cowKey = navItems.cowKey,
                navigateToRegisterBreadingPerformance = {
                    navController.navigate(
                        RegisterBreadingPerformance(
                            userKey = navItems.userKey,
                            farmKey = navItems.farmKey,
                            cowKey = navItems.cowKey
                        )
                    )
                }
            )

        }

        composable<RegisterBreadingPerformance> { navBackStackEntry ->
            val navItems = navBackStackEntry.toRoute<RegisterInsemination>()

            RegisterBornCowScreen(
                modifier = modifier,
                userKey = navItems.userKey,
                farmKey = navItems.farmKey,
                cowKey = navItems.cowKey,
                onRegisterDone = {
                    navController.popBackStack()
                }
            )

        }
    }
}