// Copyright (C) 2023 Slack Technologies, LLC
// SPDX-License-Identifier: Apache-2.0
package com.jsz.testcircuit.repository

import com.jsz.testcircuit.model.Ingredient
import kotlinx.collections.immutable.ImmutableList

interface IngredientsRepository {
    suspend fun getFillings(): ImmutableList<Ingredient>
    suspend fun getToppings(): ImmutableList<Ingredient>
}
