package ru.skillbranch.skillarticles.extensions.data

import ru.skillbranch.skillarticles.ArticleViewModel
import ru.skillbranch.skillarticles.data.AppSettings
import ru.skillbranch.skillarticles.data.ArticlePersonalInfo

fun ArticleViewModel.ArticleState.toAppSettings() = AppSettings(isDarkMode, isBigText)

fun ArticleViewModel.ArticleState.toArticlePersonalInfo() = ArticlePersonalInfo(isLike, isBookmark)