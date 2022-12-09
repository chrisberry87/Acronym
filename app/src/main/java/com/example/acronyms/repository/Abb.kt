package com.example.acronyms.repository

data class AbbItem(
    val lfs: List<Lf>,
    val sf: String
)
data class Lf(
    val freq: Int,
    val lf: String,
    val since: Int,
)